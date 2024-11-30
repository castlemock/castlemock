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

package com.castlemock.repository.core.file.http.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "httpParameter")
@XmlAccessorType(XmlAccessType.NONE)
public class HttpParameterFile {

    @XmlElement
    private String name;
    @XmlElement
    private String value;

    private HttpParameterFile() {

    }

    private HttpParameterFile(final Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "name");
        this.value = Objects.requireNonNull(builder.value, "value");
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private String value;

        private Builder() {
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder value(final String value) {
            this.value = value;
            return this;
        }

        public HttpParameterFile build() {
            return new HttpParameterFile(this);
        }
    }
}