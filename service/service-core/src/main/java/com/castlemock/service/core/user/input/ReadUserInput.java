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
import com.castlemock.service.core.user.output.ReadUserOutput;

import java.util.Objects;

/**
 * Read a user with a specific user id
 * @author Karl Dahlgren
 * @since 1.0
 * @see ReadUserOutput
 */
public final class ReadUserInput implements Input {

    private final String userId;

    public ReadUserInput(final Builder builder) {
        this.userId = Objects.requireNonNull(builder.userId, "userId");
    }


    public static Builder builder() {
        return new Builder();
    }

    public String getUserId() {
        return userId;
    }

    public static final class Builder {
        private String userId;

        private Builder() {
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public ReadUserInput build() {
            return new ReadUserInput(this);
        }
    }
}
