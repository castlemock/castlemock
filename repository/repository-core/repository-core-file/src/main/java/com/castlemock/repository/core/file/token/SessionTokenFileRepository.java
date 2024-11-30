/*
 * Copyright 2024 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.castlemock.repository.core.file.token;

import com.castlemock.repository.Profiles;
import com.castlemock.repository.token.SessionTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * The session token repository is responsible for managing all the ongoing sessions and their corresponding
 * tokens. The repository is also responsible for providing the functionality to both save and load all the
 * tokens to and from the local file system.
 * @author Karl Dahlgren
 * @since 1.0
 * @see SessionToken
 * @see SessionTokenList
 */
@Profile(Profiles.FILE)
@Component("tokenRepository")
public class SessionTokenFileRepository implements SessionTokenRepository {

    @Value(value = "${token.file.directory}")
    private String tokenDirectory;
    @Value(value = "${token.file.name}")
    private String tokenFileName;
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionTokenFileRepository.class);
    private final Map<String, PersistentRememberMeToken> seriesTokens = new HashMap<>();

    /**
     * The initialize method is responsible for initiating the token repository and load
     * all the stored tokens
     */
    public void initialize(){
        loadTokens();
    }

    /**
     * The method provides the functionality to store a new token in the token repository
     * @param token The token that will be stored in the token repository
     */
    @Override
    public synchronized void createNewToken(PersistentRememberMeToken token) {
        PersistentRememberMeToken current = this.seriesTokens.get(token.getSeries());
        if(current != null) {
            throw new IllegalArgumentException("Series Id '" + token.getSeries() + "' already exists!");
        } else {
            this.seriesTokens.put(token.getSeries(), token);
            saveTokens();
        }
    }

    /**
     * Updates a specific token with a new values to a specific token
     * @param series The token that will be updated
     * @param tokenValue The new token value
     * @param lastUsed Date for when it was last used
     */
    @Override
    public synchronized void updateToken(String series, String tokenValue, Date lastUsed) {
        PersistentRememberMeToken token = this.getTokenForSeries(series);
        PersistentRememberMeToken newToken = new PersistentRememberMeToken(token.getUsername(), series, tokenValue, new Date());
        this.seriesTokens.put(series, newToken);
        saveTokens();
    }

    /**
     * The method provides the functionality to update the token with a new username. The token
     * will be identified with the old username and upon found, the username will be updated to the
     * new provided username value
     * @param oldUsername The old username. It is used to identify the token
     * @param newUsername The new username. It will replace the old username
     */
    public synchronized void updateToken(String oldUsername, String newUsername) {
        final List<PersistentRememberMeToken> tokens = new LinkedList<>();
        for(PersistentRememberMeToken token : seriesTokens.values()){
            if(token.getUsername().equalsIgnoreCase(oldUsername)){
                PersistentRememberMeToken newToken = new PersistentRememberMeToken(newUsername, token.getSeries(), token.getTokenValue(), token.getDate());
                tokens.add(newToken);
            }
        }
        for(PersistentRememberMeToken token : tokens){
           seriesTokens.put(token.getSeries(), token);
        }
        saveTokens();
    }

    /**
     * Get a specific token for a series
     * @param seriesId The id of the series that the token belongs to
     * @return Token that matches the provided series id. Null will be returned if no token matches
     * the provided series id
     */
    @Override
    public synchronized PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return this.seriesTokens.get(seriesId);
    }

    /**
     * Remove a user token from the repository
     * @param username The token that matches this user name will be removed
     */
    @Override
    public synchronized void removeUserTokens(final String username) {
        final Iterator<String> series = this.seriesTokens.keySet().iterator();

        while(series.hasNext()) {
            final String seriesId = series.next();
            final PersistentRememberMeToken token = this.seriesTokens.get(seriesId);
            if(username.equals(token.getUsername())) {
                series.remove();
            }
        }
        saveTokens();
    }

    /**
     * Saves all the tokens into the file system
     */
    private synchronized void saveTokens(){
        final SessionTokenList tokens = getTokens();
        final String filename = tokenDirectory + File.separator +  tokenFileName;
        Writer writer = null;
        try {
            JAXBContext context = JAXBContext.newInstance(SessionTokenList.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            writer = new FileWriter(filename, StandardCharsets.UTF_8);
            marshaller.marshal(tokens, writer);
        } catch (JAXBException e) {
            LOGGER.error("Unable to parse the following file: {}", tokenFileName, e);
            throw new IllegalStateException("Unable to parse the following file: " + tokenFileName);
        } catch (IOException e) {
            LOGGER.error("Unable to read file: {}", tokenFileName, e);
            throw new IllegalStateException("Unable to read the following file: " + tokenFileName);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    LOGGER.error("Unable to close file writer for type {}", tokens.getClass().getSimpleName(), e);
                }
            }
        }
    }

    /**
     * Load all tokens stored on the local file system
     */
    private void loadTokens(){
        final Path path = FileSystems.getDefault().getPath(tokenDirectory);
        if(!Files.exists(path)){
            try {
                LOGGER.debug("Creating the following directory: {}", path);
                Files.createDirectories(path);
            } catch (IOException e) {
                LOGGER.error("Unable to create the following directory: {}", path, e);
                throw new IllegalStateException("Unable to create the following folder: " + tokenDirectory);
            }
        }
        if(!Files.isDirectory(path)){
            throw new IllegalStateException("The provided path is not a directory: " + path);
        }

        final File file = new File(tokenDirectory + File.separator +  tokenFileName);
        try {
            if (file.isFile()) {
                JAXBContext jaxbContext = JAXBContext.newInstance(SessionTokenList.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                SessionTokenList tokens = (SessionTokenList) jaxbUnmarshaller.unmarshal(file);
                for(SessionToken token : tokens){
                    PersistentRememberMeToken persistentRememberMeToken = new PersistentRememberMeToken(token.getUsername(), token.getSeries(), token.getTokenValue(), token.getDate());
                    seriesTokens.put(persistentRememberMeToken.getSeries(), persistentRememberMeToken);
                }
                LOGGER.debug("\tLoaded {}", file.getName());
            }
        } catch (JAXBException e) {
            LOGGER.error("Unable to parse files for type {}", SessionTokenList.class.getSimpleName(), e);
        }
    }

    /**
     * Get all tokens in a token list
     * @return A list of tokens
     */
    private SessionTokenList getTokens(){
        final SessionTokenList tokens = new SessionTokenList();
        for(PersistentRememberMeToken persistentRememberMeToken : seriesTokens.values()){
            final SessionToken token = new SessionToken(persistentRememberMeToken);
            tokens.add(token);
        }
        return tokens;
    }

    /**
     * The session token represent the token which will be used to identify the user
     * if the user has chosen the "Remember me" option when logging in
     */
    @XmlRootElement(name = "token")
    private static class SessionToken {

        private String username;
        private String series;
        private String tokenValue;
        private Date date;

        /**
         * Default constructor for the Token.
         * It is required in order to marshal and unmarshal the token
         */
        public SessionToken(){

        }

        public SessionToken(PersistentRememberMeToken persistentRememberMeToken){
            this.username = persistentRememberMeToken.getUsername();
            this.series = persistentRememberMeToken.getSeries();
            this.tokenValue = persistentRememberMeToken.getTokenValue();
            this.date = persistentRememberMeToken.getDate();
        }

        @XmlElement
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        @XmlElement
        public String getSeries() {
            return series;
        }

        public void setSeries(String series) {
            this.series = series;
        }

        @XmlElement
        public String getTokenValue() {
            return tokenValue;
        }

        public void setTokenValue(String tokenValue) {
            this.tokenValue = tokenValue;
        }

        @XmlElement
        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }

    /**
     * The SessionTokenList is a container class for all the tokens which will be stored on
     * the local file system.
     */
    @XmlRootElement(name = "tokens")
    @XmlSeeAlso({SessionToken.class})
    public static class SessionTokenList extends LinkedList<SessionToken> {

        @Serial
        private static final long serialVersionUID = 1L;

        @XmlElement(name = "token")
        private List<SessionToken> getSessionTokens() {
            return this;
        }
    }

}
