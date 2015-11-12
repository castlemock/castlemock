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

package com.fortmocks.web.core.model.user.dto;

import com.fortmocks.core.model.user.domain.Role;
import com.fortmocks.core.model.user.domain.Status;
import com.fortmocks.core.model.user.domain.User;
import com.fortmocks.core.model.user.dto.UserDto;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Karl Dahlgren
 * @since 1.0
 * @see User
 * @see com.fortmocks.core.model.user.dto.UserDto
 */
public class UserDtoGenerator {

    public static UserDto generateUserDto(){
        final UserDto userDto = new UserDto();
        userDto.setUpdated(new Timestamp(new Date().getTime()));
        userDto.setCreated(new Timestamp(new Date().getTime()));
        userDto.setRole(Role.ADMIN);
        userDto.setStatus(Status.ACTIVE);
        userDto.setEmail("test@test.com");
        userDto.setId(1L);
        userDto.setPassword("password");
        userDto.setUsername("admin");
        return userDto;
    }
}
