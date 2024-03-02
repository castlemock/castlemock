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

package com.castlemock.service.mock.rest.project.input;

import com.castlemock.model.core.Input;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class CreateRestProjectInput implements Input {

    private final String name;

    private final String description;

    private CreateRestProjectInput(final Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "name");
        this.description = builder.description;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CreateRestProjectInput that = (CreateRestProjectInput) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return "CreateRestProjectInput{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {

        private String name;
        private String description;

        private Builder(){

        }

        public Builder name(final String name){
            this.name = name;
            return this;
        }

        public Builder description(final String description){
            this.description = description;
            return this;
        }

        public CreateRestProjectInput build(){
            return new CreateRestProjectInput(this);
        }
    }

}
