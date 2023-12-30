/*
 * Copyright 2015 Karl Dahlgren
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

package com.castlemock.service.core.user;

import com.castlemock.model.core.user.Role;
import com.castlemock.model.core.user.Status;
import com.castlemock.model.core.user.User;
import com.castlemock.repository.token.SessionTokenRepository;
import com.castlemock.repository.user.UserRepository;
import com.castlemock.service.core.AbstractService;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public abstract class AbstractUserService extends AbstractService<User, String, UserRepository> {

    @Autowired
    private SessionTokenRepository sessionTokenRepository;

    protected static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractUserService.class);

    /**
     * Get a list of users that has the same role as the provided role
     * @param role The users has to have the same role in order to be a part of the result
     * @return A list of users that match the search credentials
     * @throws NullPointerException Throws NullPointerException if provided role is null
     */
    public List<User> findByRole(final Role role) {
        Preconditions.checkNotNull(role, "Role cannot be null");
        LOGGER.debug("Finding user with role " + role);
        final List<User> result = new ArrayList<User>();

        for (User user : findAll()) {
            if(role.equals(user.getRole())){
                result.add(user);
            }
        }
        LOGGER.debug("User found with role " + role + ": " + result.size());
        return result;
    }

    /**
     * The method provides the functionality to find a user by username.
     * @param username The username that the user has to match
     * @return A user that has the same username as the provided username. Null will be returned if no user matches
     *         the provided username
     * @throws NullPointerException Throws NullPointerException if provided username is null
     * @throws IllegalArgumentException Throws IllegalArgumentException if provided username is empty
     */
    protected Optional<User> findByUsername(final String username) {
        Preconditions.checkNotNull(username, "Username cannot be null");
        Preconditions.checkArgument(!username.isEmpty(), "Username cannot be empty");
        for (User user : findAll()) {
            if(username.equalsIgnoreCase(user.getUsername())){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    /**
     * Update an already existing user
     * @param userId The id is used to identify which user should be updated
     * @param updatedUser The updatedUser contains all the new information about the user that will be updated
     * @return Returns the updated user
     */
    @Override
    public Optional<User> update(final String userId, final User updatedUser){
        Preconditions.checkNotNull(updatedUser);
        Preconditions.checkArgument(!updatedUser.getUsername().isEmpty(), "Invalid username. Username cannot be empty");
        final User existingUser = findByUsername(updatedUser.getUsername())
                .orElse(null);
        Preconditions.checkArgument(existingUser == null || existingUser.getId().equals(userId), "Invalid username. Username is already being used");

        final List<User> administrators = findByRole(Role.ADMIN);
        if(administrators.size() == 1 && userId.equals(administrators.getFirst().getId()) && !updatedUser.getRole().equals(Role.ADMIN)){
            throw new IllegalArgumentException("Invalid user update. The last admin cannot be deleted");
        }
        if(administrators.size() == 1 && userId.equals(administrators.getFirst().getId()) && !updatedUser.getStatus().equals(Status.ACTIVE)){
            throw new IllegalArgumentException("Invalid user update. The last admin cannot be inactivated or locked");
        }

        final User user = find(userId);
        final String oldUsername = user.getUsername();
        final Date updatedTimestamp = new Date();

        LOGGER.debug("Updating user with id " + userId + "\n" +
                "Username: " + user.getUsername() + " -> " + updatedUser.getUsername() + "\n" +
                "Email: " + user.getEmail() + " -> " + updatedUser.getEmail() + "\n" +
                "Status: " + user.getStatus() + " -> " + updatedUser.getStatus() + "\n" +
                "Role: " + user.getRole() + " -> " + updatedUser.getRole() + "\n" +
                "Updated: " + user.getUpdated() + " -> " + updatedTimestamp);

        final User.Builder userBuilder = user.toBuilder()
                .id(userId)
                .username(updatedUser.getUsername())
                .email(updatedUser.getEmail())
                .status(updatedUser.getStatus())
                .role(updatedUser.getRole())
                .fullName(updatedUser.getFullName())
                .updated(updatedTimestamp);

        if(updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()){
            userBuilder.password((PASSWORD_ENCODER.encode(updatedUser.getPassword())));
        }
        final User savedUser = super.save(userBuilder.build());
        sessionTokenRepository.updateToken(oldUsername, user.getUsername());
        return Optional.of(savedUser);
    }

    /**
     * Delete user with matching user id
     * @param userId The user with the user id that will be deleted
     */
    @Override
    public User delete(final String userId){
        LOGGER.debug("Deleting user with id " + userId);
        Preconditions.checkNotNull(userId, "User id cannot be null");
        final User userDto = find(userId);

        if(userDto == null){
            throw new IllegalArgumentException("Unable to find the user with the user id " + userId);
        }

        if(userDto.getRole().equals(Role.ADMIN) && findByRole(Role.ADMIN).size() == 1){
            throw new IllegalArgumentException("Unable to delete the last administrator");
        }
        return super.delete(userId);
    }

}
