/*
 * Copyright 2018 Karl Dahlgren
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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import java.util.Optional;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class SoapOperationIdentifier {

    @XmlElement
    private String name;

    @XmlElement
    private String namespace;

    private SoapOperationIdentifier(){

    }

    private SoapOperationIdentifier(final Builder builder){
        this.name = Objects.requireNonNull(builder.name, "name");
        this.namespace = builder.namespace;
    }


    public String getName() {
        return name;
    }

    public Optional<String> getNamespace() {
        return Optional.ofNullable(namespace);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SoapOperationIdentifier that = (SoapOperationIdentifier) o;
        return Objects.equals(name, that.name) && Objects.equals(namespace, that.namespace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, namespace);
    }

    @Override
    public String toString() {
        return "SoapOperationIdentifier{" +
                "name='" + name + '\'' +
                ", namespace='" + namespace + '\'' +
                '}';
    }

    public static final class Builder {
        private String name;
        private String namespace;

        private Builder() {
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder namespace(final String namespace) {
            this.namespace = namespace;
            return this;
        }

        public SoapOperationIdentifier build() {
            return new SoapOperationIdentifier(this);
        }
    }
}
