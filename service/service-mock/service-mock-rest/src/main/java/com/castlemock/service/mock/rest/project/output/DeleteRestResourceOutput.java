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

package com.castlemock.service.mock.rest.project.output;

import com.castlemock.model.core.Output;
import com.castlemock.model.mock.rest.domain.RestResource;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class DeleteRestResourceOutput implements Output {

    private final RestResource resource;

    private DeleteRestResourceOutput(final Builder builder){
        this.resource = builder.resource;
    }

    public Optional<RestResource> getResource() {
        return Optional.ofNullable(resource);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DeleteRestResourceOutput that = (DeleteRestResourceOutput) o;
        return Objects.equals(resource, that.resource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resource);
    }

    @Override
    public String toString() {
        return "DeleteRestResourceOutput{" +
                "resource=" + resource +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private RestResource resource;

        private Builder() {
        }

        public Builder resource(final RestResource resource) {
            this.resource = resource;
            return this;
        }

        public DeleteRestResourceOutput build(){
            return new DeleteRestResourceOutput(this);
        }

    }
}
