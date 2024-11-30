/*
 * Copyright 2024 Karl Dahlgren
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

package com.castlemock.model.core.project;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = OverviewProject.Builder.class)
public class OverviewProject extends Project{

    @XmlElement
    private final String type;

    private OverviewProject(final Builder builder){
        super(builder);
        this.type = Objects.requireNonNull(builder.type, "type");
    }

    public String getType() {
        return type;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static <T extends Project> Builder toBuilder(final T other) {
        return new Builder()
                .name(other.name)
                .id(other.id)
                .created(other.created)
                .updated(other.updated)
                .description(other.description);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder extends Project.Builder<Builder> {

        private String type;
        private Builder() {
        }

        public Builder type(final String type) {
            this.type = type;
            return this;
        }

        public OverviewProject build() {
            return new OverviewProject(this);
        }
    }

}
