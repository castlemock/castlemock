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
import com.castlemock.service.core.user.input.UpdateCurrentUserInput;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 * @see UpdateCurrentUserInput
 */
public final class UpdateCurrentUserOutput implements Output {

    private final User updatedUser;

    private UpdateCurrentUserOutput(final Builder builder) {
        this.updatedUser = builder.updatedUser;
    }

    public Optional<User> getUpdatedUser() {
        return Optional.ofNullable(updatedUser);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateCurrentUserOutput that = (UpdateCurrentUserOutput) o;
        return Objects.equals(updatedUser, that.updatedUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(updatedUser);
    }

    @Override
    public String toString() {
        return "UpdateCurrentUserOutput{" +
                "updatedUser=" + updatedUser +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private User updatedUser;

        private Builder() {
        }

        public Builder updatedUser(final User updatedUser) {
            this.updatedUser = updatedUser;
            return this;
        }

        public UpdateCurrentUserOutput build() {
            return new UpdateCurrentUserOutput(this);
        }
    }
}
