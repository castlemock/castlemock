/*
 * Copyright 2020 Karl Dahlgren
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

package com.castlemock.web.core.model;

import java.util.Objects;

public class UpdateProfileRequest {

    private String username;
    private String password;
    private String email;
    private String fullName;

    private UpdateProfileRequest() {

    }

    private UpdateProfileRequest(final Builder builder){
        this.username = Objects.requireNonNull(builder.username);
        this.password = Objects.requireNonNull(builder.password);
        this.email = builder.email;
        this.fullName = builder.fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String username;
        private String password;
        private String email;
        private String fullName;

        private Builder() {
        }

        public Builder username(final String username) {
            this.username = username;
            return this;
        }

        public Builder password(final String password) {
            this.password = password;
            return this;
        }

        public Builder email(final String email) {
            this.email = email;
            return this;
        }

        public Builder fullName(final String fullName) {
            this.fullName = fullName;
            return this;
        }

        public UpdateProfileRequest build() {
            return new UpdateProfileRequest(this);
        }
    }

}
