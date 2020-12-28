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

package com.castlemock.model.core.model.configuration.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

/**
 * The configuration group DTO is a DTO (Data transfer object) class for the configuration group class. The
 * class contains both an identifier (See {@link #name}) and all the configurations (See {@link #configurations}) that
 * are being grouped by the configuration group.
 * @author Karl Dahlgren
 * @since 1.0
 * @see ConfigurationGroup
 * @see Configuration
 */
@XmlRootElement
public class ConfigurationGroup {


    private String id;


    private String name;


    private List<Configuration> configurations;

    public ConfigurationGroup(){

    }

    private ConfigurationGroup(final Builder builder){
        this.id = Objects.requireNonNull(builder.id);
        this.name = Objects.requireNonNull(builder.name);
        this.configurations = Objects.requireNonNull(builder.configurations);
    }

    /**
     * Returns the configuration group id
     * @return The configuration group id
     */
    @XmlElement
    public String getId() {
        return id;
    }

    /**
     * Sets a new id value for the configuration group
     * @param id The new id for the configuration group
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the name of the configuration group
     * @return The new of the configuration group
     */
    @XmlElement
    public String getName() {
        return name;
    }

    /**
     * Sets a new name of the configuration group
     * @param name The new name for the configuration group
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a list of configurations the belongs to the group
     * @return Configurations that belongs to the configuration group
     */
    @XmlElementWrapper(name = "configurations")
    @XmlElement(name = "configuration")
    public List<Configuration> getConfigurations() {
        return configurations;
    }

    /**
     * Set a new list of configurations that belong to the configuration group
     * @param configurations The new list of configurations
     */
    public void setConfigurations(List<Configuration> configurations) {
        this.configurations = configurations;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String name;
        private List<Configuration> configurations;

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

        public Builder configurations(final List<Configuration> configurations) {
            this.configurations = configurations;
            return this;
        }

        public ConfigurationGroup build() {
            return new ConfigurationGroup(this);
        }
    }
}
