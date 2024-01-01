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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.16
 */
@XmlRootElement
public class SoapResource {

    private String id;
    private String name;
    private String projectId;
    private String content;
    private SoapResourceType type;

    private SoapResource(){

    }

    private SoapResource(final Builder builder){
        this.id = Objects.requireNonNull(builder.id, "id");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.content = builder.content;
        this.type = Objects.requireNonNull(builder.type, "type");
    }

    @XmlElement
    public String getId() {
        return id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    @XmlElement
    public SoapResourceType getType() {
        return type;
    }

    @XmlElement
    public String getProjectId() {
        return projectId;
    }

    @XmlElement
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
