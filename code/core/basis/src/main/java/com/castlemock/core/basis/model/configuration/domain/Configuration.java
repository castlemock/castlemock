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

package com.castlemock.core.basis.model.configuration.domain;

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
public class Configuration {


    private String key;


    private String value;


    private ConfigurationType type;

    public Configuration(){

    }

    private Configuration(final Builder builder){
        this.key = Objects.requireNonNull(builder.key);
        this.value = Objects.requireNonNull(builder.value);
        this.type = Objects.requireNonNull(builder.type);
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the identifier for the configuration
     * @return The configuration key
     */
    @XmlElement
    public String getKey() {
        return key;
    }

    /**
     * Sets the configuration key
     * @param key The new configuration key
     */
    public void setKey(final String key) {
        this.key = key;
    }

    /**
     * Returns the value for the configuration
     * @return Configuration value
     */
    @XmlElement
    public String getValue() {
        return value;
    }

    /**
     * Sets the configuration value
     * @param value The new configuration value
     */
    public void setValue(final String value) {
        this.value = value;
    }

    /**
     * Returns the configuration type
     * @return The configuration type
     */
    @XmlElement
    public ConfigurationType getType() {
        return type;
    }

    /**
     * Sets the configuration type
     * @param type The new configuration type
     */
    public void setType(ConfigurationType type) {
        this.type = type;
    }


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
