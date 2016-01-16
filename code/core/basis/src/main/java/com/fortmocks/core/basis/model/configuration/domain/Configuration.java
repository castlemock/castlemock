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

package com.fortmocks.core.basis.model.configuration.domain;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The configuration class contains a specific configuration. A key is used to retrieve the actual configured value.
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
public class Configuration {

    private String key;
    private String value;
    private ConfigurationType type;

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
    public void setKey(String key) {
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
    public void setValue(String value) {
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

}


