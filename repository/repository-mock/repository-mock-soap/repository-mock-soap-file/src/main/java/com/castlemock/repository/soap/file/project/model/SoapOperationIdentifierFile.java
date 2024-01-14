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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "soapOperationIdentifier")
@XmlAccessorType(XmlAccessType.NONE)
public class SoapOperationIdentifierFile {

    @XmlElement
    private String name;
    @XmlElement
    private String namespace;

    private SoapOperationIdentifierFile(){

    }

    private SoapOperationIdentifierFile(final Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "name");
        this.namespace = builder.namespace;
    }

    public String getName() {
        return name;
    }

    public String getNamespace() {
        return namespace;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private String namespace;

        private Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder namespace(String namespace) {
            this.namespace = namespace;
            return this;
        }

        public SoapOperationIdentifierFile build() {
            return new SoapOperationIdentifierFile(this);
        }
    }
}