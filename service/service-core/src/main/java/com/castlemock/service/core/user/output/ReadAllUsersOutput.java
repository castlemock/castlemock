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

package com.castlemock.service.core.user.output;

import com.castlemock.model.core.Output;
import com.castlemock.model.core.user.User;
import com.castlemock.model.core.validation.NotNull;
import com.castlemock.service.core.user.input.ReadAllUsersInput;

import java.util.List;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 * @see ReadAllUsersInput
 */
public final class ReadAllUsersOutput implements Output {

    @NotNull
    private final List<User> users;

    public ReadAllUsersOutput(final Builder builder) {
        this.users = Optional.ofNullable(builder.users).orElseGet(List::of);
    }

    public List<User> getUsers() {
        return users;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<User> users;

        private Builder() {
        }

        public Builder users(List<User> users) {
            this.users = users;
            return this;
        }

        public ReadAllUsersOutput build() {
            return new ReadAllUsersOutput(this);
        }
    }
}
