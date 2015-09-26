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

import com.fortmocks.core.base.model.Service;
import com.fortmocks.core.base.model.user.Role;
import com.fortmocks.core.base.model.user.User;
import com.fortmocks.core.base.model.user.dto.UserDto;

import java.util.List;

/**
 * The UserService provides functionality that involves the user.
 * @author Karl Dahlgren
 * @since 1.0
 * @see com.fortmocks.core.base.model.user.User
 * @see com.fortmocks.core.base.model.user.dto.UserDto
 * @see com.fortmocks.war.base.model.user.service.UserServiceImpl
 */
public interface UserService extends Service<User, UserDto, Long> {

    /**
     * The method provides the functionality to find a user by username.
     * @param username The username that the user has to match
     * @return A user that has the same username as the provided username. Null will be returned if no user matches
     *         the provided username
     * @throws java.lang.NullPointerException Throws NullPointerException if provided username is null
     * @throws java.lang.IllegalArgumentException Throws IllegalArgumentException if provided username is empty
     */
    UserDto findByUsername(final String username);

    /**
     * Get a list of users that has the same role as the provided role
     * @param role The users has to have the same role in order to be a part of the result
     * @return A list of users that match the search credentials
     * @throws java.lang.NullPointerException Throws NullPointerException if provided role is null
     */
    List<UserDto> findByRole(final Role role);

    /**
     * The service provides the functionality to update the current logged user with new information
     * @param updatedUser The updated version of the user
     * @return The new updated version of the user
     */
    UserDto updateCurrentUser(final UserDto updatedUser);
}
