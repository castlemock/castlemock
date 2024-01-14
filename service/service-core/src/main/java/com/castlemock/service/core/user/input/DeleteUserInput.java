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
import com.castlemock.service.core.user.output.DeleteUserOutput;

import java.util.Objects;

/**
 * Deletes a user
 * @author Karl Dahlgren
 * @since 1.0
 * @see DeleteUserOutput
 */
public final class DeleteUserInput implements Input {

    private final String userId;

    public DeleteUserInput(final Builder builder) {
        this.userId = Objects.requireNonNull(builder.userId);
    }

    public String getUserId() {
        return userId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String userId;

        private Builder() {
        }


        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public DeleteUserInput build() {
            return new DeleteUserInput(this);
        }
    }
}
