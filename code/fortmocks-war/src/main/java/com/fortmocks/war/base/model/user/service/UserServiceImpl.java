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

package com.fortmocks.war.base.model.user.service;

import com.fortmocks.core.base.model.user.Role;
import com.fortmocks.core.base.model.user.Status;
import com.fortmocks.core.base.model.user.User;
import com.fortmocks.core.base.model.user.dto.UserDto;
import com.fortmocks.core.base.model.user.service.UserService;
import com.fortmocks.war.base.model.ServiceImpl;
import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The UserService provides functionality that involves the user.
 * @author Karl Dahlgren
 * @since 1.0
 * @see com.fortmocks.core.base.model.user.User
 * @see com.fortmocks.core.base.model.user.dto.UserDto
 * @see UserService
 */
@Service
public class UserServiceImpl extends ServiceImpl<User, UserDto, Long> implements UserService {

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    /**
     * The method provides the functionality to find a user by username.
     * @param username The username that the user has to match
     * @return A user that has the same username as the provided username. Null will be returned if no user matches
     *         the provided username
     * @throws java.lang.NullPointerException Throws NullPointerException if provided username is null
     * @throws java.lang.IllegalArgumentException Throws IllegalArgumentException if provided username is empty
     */
    @Override
    public UserDto findByUsername(final String username) {
        Preconditions.checkNotNull(username, "Username cannot be null");
        Preconditions.checkArgument(!username.isEmpty(), "Username cannot be empty");
        for (User user : findAllTypes()) {
            if(username.equalsIgnoreCase(user.getUsername())){
                return mapper.map(user, dtoClass);
            }
        }
        return null;
    }

    /**
     * Get a list of users that has the same role as the provided role
     * @param role The users has to have the same role in order to be a part of the result
     * @return A list of users that match the search credentials
     * @throws java.lang.NullPointerException Throws NullPointerException if provided role is null
     */
    @Override
    public List<UserDto> findByRole(final Role role) {
        Preconditions.checkNotNull(role, "Role cannot be null");
        LOGGER.debug("Finding user with role " + role);
        final List<UserDto> result = new ArrayList<UserDto>();

        for (User user : findAllTypes()) {
            if(role.equals(user.getRole())){
                result.add(mapper.map(user, dtoClass));
            }
        }
        LOGGER.debug("User found with role " + role + ": " + result.size());
        return result;
    }

    /**
     * The method provides the functionality to save a user.
     * @param user The user that will be saved
     * @return Return the newly created user
     */
    @Override
    public UserDto save(final UserDto user){
        LOGGER.debug("Creating new user");

        Preconditions.checkArgument(!user.getPassword().isEmpty(), "The user password cannot be empty");

        final UserDto existingUser = findByUsername(user.getUsername());
        Preconditions.checkArgument(existingUser == null, "Invalid username. Username is already being used");


        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setUpdated(new Date());
        user.setCreated(new Date());
        user.setStatus(Status.ACTIVE);
        final UserDto savedUser = super.save(user);

        LOGGER.debug("User created: \n" +
                "Id: " + user.getId() + "\n" +
                "Username: " + user.getUsername() + "\n" +
                "Email: " + user.getEmail() + "\n" +
                "Created " + user.getCreated() + "\n" +
                "Updated: " + user.getUpdated() + "\n" +
                "Status: " + user.getStatus() + "\n" +
                "Role: " + user.getRole());

        return savedUser;
    }

    /**
     * Update an already existing user
     * @param userId The id is used to identify which user should be updated
     * @param updatedUser The updatedUser contains all the new information about the user that will be updated
     * @return Returns the updated user
     */
    @Override
    public UserDto update(final Long userId, final UserDto updatedUser){
        Preconditions.checkNotNull(updatedUser);
        Preconditions.checkArgument(!updatedUser.getUsername().isEmpty(), "Invalid username. Username cannot be empty");
        final UserDto existingUser = findByUsername(updatedUser.getUsername());
        Preconditions.checkArgument(existingUser == null || existingUser.getId().equals(userId), "Invalid username. Username is already being used");

        final List<UserDto> administrators = findByRole(Role.ADMIN);
        if(administrators.size() == 1 && userId.equals(administrators.get(0).getId()) && !updatedUser.getRole().equals(Role.ADMIN)){
            throw new IllegalArgumentException("Invalid user update. The last admin cannot be deleted");
        }

        final UserDto user = findOne(userId);
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

        return super.save(user);
    }

    /**
     * The service provides the functionality to update the current logged user with new information
     * @param updatedUser The updated version of the user
     * @return The new updated version of the user
     */
    @Override
    public UserDto updateCurrentUser(final UserDto updatedUser){
        Preconditions.checkNotNull(updatedUser);

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String loggedInUser = authentication.getName();

        if(!updatedUser.getUsername().equalsIgnoreCase(loggedInUser)){
            final UserDto existingUser = findByUsername(updatedUser.getUsername());
            Preconditions.checkArgument(existingUser == null, "Invalid username. Username is already used");
        }


        final UserDto user = findByUsername(loggedInUser);
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setUpdated(new Date());

        if(updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()){
            user.setPassword(PASSWORD_ENCODER.encode(updatedUser.getPassword()));
        }

        return super.save(user);
    }

    /**
     * Delete user with matching user id
     * @param userId The user with the user id that will be deleted
     */
    @Override
    public void delete(final Long userId){
        LOGGER.debug("Deleting user with id " + userId);
        Preconditions.checkNotNull(userId, "User id cannot be null");
        Preconditions.checkArgument(userId >= 0, "User id cannot be less than zero");
        final UserDto userDto = findOne(userId);

        if(userDto.getRole().equals(Role.ADMIN) && findByRole(Role.ADMIN).size() == 1){
            throw new IllegalArgumentException("Unable to delete the last administrator");
        }
        super.delete(userId);
    }



}
