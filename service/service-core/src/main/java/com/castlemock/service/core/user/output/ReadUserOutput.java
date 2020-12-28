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
import com.castlemock.service.core.user.input.ReadUserInput;

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 * @see ReadUserInput
 */
public final class ReadUserOutput implements Output {

    @NotNull
    private final User user;

    public ReadUserOutput(final Builder builder) {
        this.user = Objects.requireNonNull(builder.user);
    }

    public User getUser() {
        return user;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private User user;

        private Builder() {
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public ReadUserOutput build() {
            return new ReadUserOutput(this);
        }
    }
}
