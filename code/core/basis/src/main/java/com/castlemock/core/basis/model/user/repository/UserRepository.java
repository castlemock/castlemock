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

package com.castlemock.core.basis.model.user.repository;


import com.castlemock.core.basis.model.Repository;
import com.castlemock.core.basis.model.user.domain.User;
import com.castlemock.core.basis.model.user.dto.UserDto;

/**
 * The user file repository provides the functionality to interact with the file system.
 * The repository is responsible for loading and saving users to the file system. Each
 * user is stored as a separate file.
 * @author Karl Dahlgren
 * @since 1.0
 * @see User
 * @see UserDto
 * @see Repository
 */
public interface UserRepository extends Repository<User, UserDto, String> {
}
