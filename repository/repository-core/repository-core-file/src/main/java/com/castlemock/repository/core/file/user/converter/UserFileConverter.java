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

package com.castlemock.repository.core.file.user.converter;

import com.castlemock.model.core.user.User;
import com.castlemock.repository.core.file.user.model.UserFile;

public final class UserFileConverter {

    private UserFileConverter() {

    }

    public static User toUser(final UserFile user) {
        return User.builder()
                .id(user.getId())
                .created(user.getCreated())
                .role(user.getRole())
                .status(user.getStatus())
                .updated(user.getUpdated())
                .password(user.getPassword())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .build();
    }

}
