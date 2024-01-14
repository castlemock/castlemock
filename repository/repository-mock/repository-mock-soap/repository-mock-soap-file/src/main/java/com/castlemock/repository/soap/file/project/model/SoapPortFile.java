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

package com.castlemock.repository.soap.file.project.model;

import com.castlemock.model.core.Saveable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "soapPort")
@XmlAccessorType(XmlAccessType.NONE)
public class SoapPortFile implements Saveable<String> {

    @XmlElement
    private String id;
    @XmlElement
    private String name;
    @XmlElement
    private String uri;
    @XmlElement
    private String projectId;

    private SoapPortFile() {

    }

    private SoapPortFile(final Builder builder) {
        this.id = Objects.requireNonNull(builder.id);
        this.name = Objects.requireNonNull(builder.name);
        this.uri = Objects.requireNonNull(builder.uri);
        this.projectId = Objects.requireNonNull(builder.projectId);
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public String getProjectId() {
        return projectId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String name;
        private String uri;
        private String projectId;

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

        public Builder uri(final String uri) {
            this.uri = uri;
            return this;
        }

        public Builder projectId(final String projectId) {
            this.projectId = projectId;
            return this;
        }

        public SoapPortFile build() {
            return new SoapPortFile(this);
        }
    }
}