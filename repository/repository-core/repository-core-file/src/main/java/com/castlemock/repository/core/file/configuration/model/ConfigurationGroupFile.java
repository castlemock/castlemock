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

package com.castlemock.repository.core.file.configuration.model;

import com.castlemock.model.core.Saveable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

/**
 * The configuration group is responsible for grouping configurations together.
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement(name = "configurationGroup")
@XmlAccessorType(XmlAccessType.NONE)
public class ConfigurationGroupFile implements Saveable<String> {

    @XmlElement
    private String id;
    @XmlElement
    private String name;
    @XmlElementWrapper(name = "configurations")
    @XmlElement(name = "configuration")
    private List<ConfigurationFile> configurations;

    private ConfigurationGroupFile() {

    }

    private ConfigurationGroupFile(final Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "id");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.configurations = Objects.requireNonNull(builder.configurations, "configurations");
    }


    /**
     * Returns the configuration group id
     * @return The configuration group id
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Returns the name of the configuration group
     * @return The new of the configuration group
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a list of configurations the belongs to the group
     * @return Configurations that belongs to the configuration group
     */
    public List<ConfigurationFile> getConfigurations() {
        return configurations;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String name;
        private List<ConfigurationFile> configurations;

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

        public Builder configurations(final List<ConfigurationFile> configurations) {
            this.configurations = configurations;
            return this;
        }

        public ConfigurationGroupFile build() {
            return new ConfigurationGroupFile(this);
        }
    }

}