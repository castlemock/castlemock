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

import com.castlemock.model.core.model.Output;
import com.castlemock.model.core.model.user.domain.User;
import com.castlemock.model.core.model.validation.NotNull;
import com.castlemock.service.core.user.input.CreateUserInput;

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 * @see CreateUserInput
 */
public final class CreateUserOutput implements Output {

    @NotNull
    private final User savedUser;

    public CreateUserOutput(final Builder builder) {
        this.savedUser = Objects.requireNonNull(builder.savedUser);
    }

    public User getSavedUser() {
        return savedUser;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private User savedUser;

        private Builder() {
        }

        public Builder savedUser(User savedUser) {
            this.savedUser = savedUser;
            return this;
        }

        public CreateUserOutput build() {
            return new CreateUserOutput(this);
        }
    }
}
