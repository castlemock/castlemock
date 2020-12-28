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

    public SoapResource(){

    }

    private SoapResource(final Builder builder){
        this.id = Objects.requireNonNull(builder.id);
        this.name = Objects.requireNonNull(builder.name);
        this.projectId = Objects.requireNonNull(builder.projectId);
        this.content = Objects.requireNonNull(builder.content);
        this.type = Objects.requireNonNull(builder.type);
    }

    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public SoapResourceType getType() {
        return type;
    }

    public void setType(SoapResourceType type) {
        this.type = type;
    }

    @XmlElement
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @XmlElement
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static Builder builder() {
        return new Builder();
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
