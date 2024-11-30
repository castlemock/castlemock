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

package com.castlemock.web.mock.rest.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.52
 */
@XmlRootElement
@JsonDeserialize(builder = UpdateRestApplicationRequest.Builder.class)
public class UpdateRestApplicationRequest {

    private final String name;

    private UpdateRestApplicationRequest(final Builder builder){
        this.name = Objects.requireNonNull(builder.name, "name");
    }

    @XmlElement
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UpdateRestApplicationRequest that = (UpdateRestApplicationRequest) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "RestApplication{" +
                "name='" + name + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {

        private String name;

        private Builder() {
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public UpdateRestApplicationRequest build() {
            return new UpdateRestApplicationRequest(this);
        }
    }
}
