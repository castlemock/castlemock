/*
 * Copyright 2018 Karl Dahlgren
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

package com.castlemock.repository.token;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import java.util.Date;

/**
 * The session token repository is responsible for managing all the ongoing sessions and their corresponding
 * tokens. The repository is also responsible for providing the functionality to both save and load all the
 * tokens to and from the local file system.
 * @author Karl Dahlgren
 * @since 1.0
 */
public interface SessionTokenRepository extends PersistentTokenRepository {

    /**
     * The initialize method is responsible for initiating the token repository and load
     * all the stored tokens
     */
    public void initialize();

    /**
     * The method provides the functionality to store a new token in the token repository
     * @param token The token that will be stored in the token repository
     */
    public void createNewToken(PersistentRememberMeToken token);

    /**
     * Updates a specific token with a new values to a specific token
     * @param series The token that will be updated
     * @param tokenValue The new token value
     * @param lastUsed Date for when it was last used
     */
    public void updateToken(String series, String tokenValue, Date lastUsed);

    /**
     * The method provides the functionality to update the token with a new username. The token
     * will be identified with the old username and upon found, the username will be updated to the
     * new provided username value
     * @param oldUsername The old username. It is used to identify the token
     * @param newUsername The new username. It will replace the old username
     */
    public void updateToken(String oldUsername, String newUsername);

    /**
     * Get a specific token for a series
     * @param seriesId The id of the series that the token belongs to
     * @return Token that matches the provided series id. Null will be returned if no token matches
     * the provided series id
     */
    public PersistentRememberMeToken getTokenForSeries(String seriesId);
    /**
     * Remove a user token from the repository
     * @param username The token that matches this user name will be removed
     */
    public void removeUserTokens(String username);
}
