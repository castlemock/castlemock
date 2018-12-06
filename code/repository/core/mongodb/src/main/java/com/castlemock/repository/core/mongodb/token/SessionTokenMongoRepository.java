package com.castlemock.repository.core.mongodb.token;

import com.castlemock.repository.Profiles;
import com.castlemock.repository.token.SessionTokenRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * The session token repository is responsible for managing all the ongoing sessions and their corresponding
 * tokens. The repository is also responsible for providing the functionality to both save and load all the
 * tokens to and from mongodb.
 *
 * @author Mohammad Hewedy
 * @see SessionToken
 * @since 1.34
 */
@Profile(Profiles.MONGODB)
@Component("tokenRepository")
public class SessionTokenMongoRepository implements SessionTokenRepository {

    private static final Logger LOGGER = Logger.getLogger(SessionTokenMongoRepository.class);

    @Autowired
    protected MongoOperations mongoOperations;

    /**
     * The initialize method is responsible for initiating the token repository and load
     * all the stored tokens
     */
    public void initialize() {

    }

    /**
     * The method provides the functionality to store a new token in the token repository
     *
     * @param token The token that will be stored in the token repository
     */
    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        try {
            mongoOperations.insert(new SessionToken(token));
        } catch (DataIntegrityViolationException ex) {
            LOGGER.error(ex.getMessage());
            throw new DataIntegrityViolationException("Series Id \'" + token.getSeries() + "\' already exists!");
        }
    }

    /**
     * Updates a specific token with a new values to a specific token
     *
     * @param series     The token that will be updated
     * @param tokenValue The new token value
     * @param lastUsed   Date for when it was last used
     */
    @Override
    public synchronized void updateToken(String series, String tokenValue, Date lastUsed) {

        SessionToken current = mongoOperations.findOne(getSeriesQuery(series), SessionToken.class);
        if (current != null) {
            current.tokenValue = tokenValue;
            current.date = new Date();
            mongoOperations.save(current);
        } else {
            throw new IllegalArgumentException("cannot find SessionToken with series value: " + series);
        }
    }

    /**
     * The method provides the functionality to update the token with a new username. The token
     * will be identified with the old username and upon found, the username will be updated to the
     * new provided username value
     *
     * @param oldUsername The old username. It is used to identify the token
     * @param newUsername The new username. It will replace the old username
     */
    public synchronized void updateToken(String oldUsername, String newUsername) {

        SessionToken current = mongoOperations.findOne(getUsernameQuery(oldUsername), SessionToken.class);
        if (current != null) {
            current.username = newUsername;
            mongoOperations.save(current);
        } else {
            throw new IllegalArgumentException("cannot find SessionToken with username value: " + oldUsername);
        }
    }

    /**
     * Get a specific token for a series
     *
     * @param seriesId The id of the series that the token belongs to
     * @return Token that matches the provided series id. Null will be returned if no token matches
     * the provided series id
     */
    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {

        SessionToken sessionToken = mongoOperations.findOne(getSeriesQuery(seriesId), SessionToken.class);
        return Optional.ofNullable(sessionToken)
                .map(SessionToken::toPersistentRememberMeToken)
                .orElse(null);
    }

    /**
     * Remove a user token from the repository
     *
     * @param username The token that matches this user name will be removed
     */
    @Override
    public void removeUserTokens(String username) {
        mongoOperations.remove(getUsernameQuery(username), SessionToken.class);
    }

    /**
     * The session token represent the token which will be used to identify the user
     * if the user has chosen the "Remember me" option when logging in
     */
    private static class SessionToken {

        private String username;
        @Id
        private String series;
        private String tokenValue;
        private Date date;

        /**
         * Default constructor for the Token.
         */
        public SessionToken() {

        }

        public SessionToken(PersistentRememberMeToken persistentRememberMeToken) {
            this.username = persistentRememberMeToken.getUsername();
            this.series = persistentRememberMeToken.getSeries();
            this.tokenValue = persistentRememberMeToken.getTokenValue();
            this.date = persistentRememberMeToken.getDate();
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getSeries() {
            return series;
        }

        public void setSeries(String series) {
            this.series = series;
        }

        public String getTokenValue() {
            return tokenValue;
        }

        public void setTokenValue(String tokenValue) {
            this.tokenValue = tokenValue;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public PersistentRememberMeToken toPersistentRememberMeToken() {
            return new PersistentRememberMeToken(this.username, this.series, this.tokenValue, this.date);
        }
    }

    private Query getSeriesQuery(Object series) {
        return new Query(getSeriesCriteria(series));
    }

    private Criteria getSeriesCriteria(Object series) {
        return where("series").is(series);
    }

    private Query getUsernameQuery(Object username) {
        return new Query(getUsernameCriteria(username));
    }

    private Criteria getUsernameCriteria(Object username) {
        return where("username").is(username);
    }
}
