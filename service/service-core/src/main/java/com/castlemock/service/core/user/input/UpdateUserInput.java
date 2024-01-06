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

package com.castlemock.service.core.user.input;

import com.castlemock.model.core.Input;
import com.castlemock.model.core.user.Role;
import com.castlemock.model.core.user.Status;
import com.castlemock.model.core.validation.NotNull;
import com.castlemock.service.core.user.output.UpdateUserOutput;

import java.util.Objects;
import java.util.Optional;

/**
 * Update a user with a specific user id
 * @author Karl Dahlgren
 * @since 1.0
 * @see UpdateUserOutput
 */
public final class UpdateUserInput implements Input {

    @NotNull
    private final String id;
    @NotNull
    private final String username;
    @NotNull
    private final String password;
    private final String email;
    private final String fullName;
    @NotNull
    private final Status status;
    @NotNull
    private final Role role;

    public UpdateUserInput(final Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "id");
        this.username = Objects.requireNonNull(builder.username, "username");
        this.password = builder.password;
        this.email = builder.email;
        this.fullName = builder.fullName;
        this.status = Objects.requireNonNull(builder.status, "status");
        this.role = Objects.requireNonNull(builder.role, "role");
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Optional<String> getPassword() {
        return Optional.ofNullable(password);
    }

    public Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }

    public Optional<String> getFullName() {
        return Optional.ofNullable(fullName);
    }

    public Status getStatus() {
        return status;
    }

    public Role getRole() {
        return role;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private String id;
        private String username;
        private String password;
        private String email;
        private String fullName;
        private Status status;
        private Role role;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public UpdateUserInput build() {
            return new UpdateUserInput(this);
        }
    }
}
