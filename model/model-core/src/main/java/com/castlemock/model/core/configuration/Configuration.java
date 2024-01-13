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

package com.castlemock.model.core.configuration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * The configuration DTO is a DTO (Data transfer object) class for the configuration class. The configuration class
 * represent an individual configuration and contains both the identifier (key) and the value
 * @author Karl Dahlgren
 * @since 1.0
 * @see Configuration
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = Configuration.Builder.class)
public class Configuration {

    @XmlElement
    private final String key;

    @XmlElement
    private final String value;

    @XmlElement
    private final ConfigurationType type;

    private Configuration(final Builder builder){
        this.key = Objects.requireNonNull(builder.key, "key");
        this.value = Objects.requireNonNull(builder.value, "value");
        this.type = Objects.requireNonNull(builder.type, "type");
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the identifier for the configuration
     * @return The configuration key
     */
    public String getKey() {
        return key;
    }


    /**
     * Returns the value for the configuration
     * @return Configuration value
     */
    public String getValue() {
        return value;
    }


    /**
     * Returns the configuration type
     * @return The configuration type
     */
    public ConfigurationType getType() {
        return type;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private String key;
        private String value;
        private ConfigurationType type;

        private Builder() {
        }

        public Builder key(final String key) {
            this.key = key;
            return this;
        }

        public Builder value(final String value) {
            this.value = value;
            return this;
        }

        public Builder type(final ConfigurationType type) {
            this.type = type;
            return this;
        }

        public Configuration build() {
            return new Configuration(this);
        }
    }
}
