/*
 * Copyright 2017 Karl Dahlgren
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

package com.castlemock.model.mock.soap.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.16
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = SoapResource.Builder.class)
public class SoapResource {

    @XmlElement
    private final String id;

    @XmlElement
    private final String name;

    @XmlElement
    private final String projectId;

    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String content;

    @XmlElement
    private final SoapResourceType type;


    private SoapResource(final Builder builder){
        this.id = Objects.requireNonNull(builder.id, "id");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.type = Objects.requireNonNull(builder.type, "type");
        this.content = builder.content;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public SoapResourceType getType() {
        return type;
    }

    public String getProjectId() {
        return projectId;
    }

    public Optional<String> getContent() {
        return Optional.ofNullable(content);
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder()
                .id(this.id)
                .name(this.name)
                .projectId(this.projectId)
                .content(this.content)
                .type(this.type);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final SoapResource that = (SoapResource) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) &&
                Objects.equals(projectId, that.projectId) && Objects.equals(content, that.content) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, projectId, content, type);
    }

    @Override
    public String toString() {
        return "SoapResource{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", projectId='" + projectId + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                '}';
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private String id;
        private String name;
        private String projectId;
        private String content;
        private SoapResourceType type;

        private Builder() {
        }

        public Builder id(final String id) {
            this.id = id;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder projectId(final String projectId) {
            this.projectId = projectId;
            return this;
        }

        public Builder content(final String content) {
            this.content = content;
            return this;
        }

        public Builder type(final SoapResourceType type) {
            this.type = type;
            return this;
        }

        public SoapResource build() {
            return new SoapResource(this);
        }
    }
}
