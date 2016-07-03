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

package com.castlemock.web.basis.model.user.service;

import com.castlemock.core.basis.model.user.domain.Role;
import com.castlemock.core.basis.model.user.domain.Status;
import com.castlemock.core.basis.model.user.domain.User;
import com.castlemock.core.basis.model.user.dto.UserDto;
import com.castlemock.core.basis.model.user.repository.UserRepository;
import com.castlemock.web.basis.model.AbstractService;
import com.castlemock.web.basis.model.session.token.repository.SessionTokenRepository;
import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public abstract class AbstractUserService extends AbstractService<User, UserDto, String, UserRepository> {

    @Autowired
    private SessionTokenRepository sessionTokenRepository;

    protected static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    private static final Logger LOGGER = Logger.getLogger(AbstractUserService.class);

    /**
     * Get a list of users that has the same role as the provided role
     * @param role The users has to have the same role in order to be a part of the result
     * @return A list of users that match the search credentials
     * @throws NullPointerException Throws NullPointerException if provided role is null
     */
    public List<UserDto> findByRole(final Role role) {
        Preconditions.checkNotNull(role, "Role cannot be null");
        LOGGER.debug("Finding user with role " + role);
        final List<UserDto> result = new ArrayList<UserDto>();

        for (UserDto user : findAll()) {
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
    protected UserDto findByUsername(final String username) {
        Preconditions.checkNotNull(username, "Username cannot be null");
        Preconditions.checkArgument(!username.isEmpty(), "Username cannot be empty");
        for (UserDto user : findAll()) {
            if(username.equalsIgnoreCase(user.getUsername())){
                return user;
            }
        }
        return null;
    }

    /**
     * Update an already existing user
     * @param userId The id is used to identify which user should be updated
     * @param updatedUser The updatedUser contains all the new information about the user that will be updated
     * @return Returns the updated user
     */
    @Override
    public UserDto update(final String userId, final UserDto updatedUser){
        Preconditions.checkNotNull(updatedUser);
        Preconditions.checkArgument(!updatedUser.getUsername().isEmpty(), "Invalid username. Username cannot be empty");
        final UserDto existingUser = findByUsername(updatedUser.getUsername());
        Preconditions.checkArgument(existingUser == null || existingUser.getId().equals(userId), "Invalid username. Username is already being used");

        final List<UserDto> administrators = findByRole(Role.ADMIN);
        if(administrators.size() == 1 && userId.equals(administrators.get(0).getId()) && !updatedUser.getRole().equals(Role.ADMIN)){
            throw new IllegalArgumentException("Invalid user update. The last admin cannot be deleted");
        }
        if(administrators.size() == 1 && userId.equals(administrators.get(0).getId()) && !updatedUser.getStatus().equals(Status.ACTIVE)){
            throw new IllegalArgumentException("Invalid user update. The last admin cannot be inactivated or locked");
        }

        final UserDto user = find(userId);
        final String oldUsername = user.getUsername();
        final Date updatedTimestamp = new Date();

        LOGGER.debug("Updating user with id " + userId + "\n" +
                "Username: " + user.getUsername() + " -> " + updatedUser.getUsername() + "\n" +
                "Email: " + user.getEmail() + " -> " + updatedUser.getEmail() + "\n" +
                "Status: " + user.getStatus() + " -> " + updatedUser.getStatus() + "\n" +
                "Role: " + user.getRole() + " -> " + updatedUser.getRole() + "\n" +
                "Updated: " + user.getUpdated() + " -> " + updatedTimestamp);

        user.setId(userId);
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setStatus(updatedUser.getStatus());
        user.setRole(updatedUser.getRole());
        user.setUpdated(updatedTimestamp);

        if(!updatedUser.getPassword().isEmpty()){
            user.setPassword(PASSWORD_ENCODER.encode(updatedUser.getPassword()));
        }
        UserDto savedUser = super.save(user);
        sessionTokenRepository.updateToken(oldUsername, user.getUsername());
        return savedUser;
    }

    /**
     * Delete user with matching user id
     * @param userId The user with the user id that will be deleted
     */
    @Override
    public void delete(final String userId){
        LOGGER.debug("Deleting user with id " + userId);
        Preconditions.checkNotNull(userId, "User id cannot be null");
        final UserDto userDto = find(userId);

        if(userDto == null){
            throw new IllegalArgumentException("Unable to find the user with the user id " + userId);
        }

        if(userDto.getRole().equals(Role.ADMIN) && findByRole(Role.ADMIN).size() == 1){
            throw new IllegalArgumentException("Unable to delete the last administrator");
        }
        super.delete(userId);
    }

}
