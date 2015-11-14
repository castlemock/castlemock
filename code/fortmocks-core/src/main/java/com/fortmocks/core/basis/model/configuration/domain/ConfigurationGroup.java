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

import com.fortmocks.core.basis.model.Saveable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

/**
 * The configuration group is responsible for grouping configurations together.
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
public class ConfigurationGroup implements Saveable<Long> {

    private Long id;
    private String name;
    private Set<Configuration> configurations;

    /**
     * Returns the configuration group id
     * @return The configuration group id
     */
    @XmlElement
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Sets a new id value for the configuration group
     * @param id The new id for the configuration group
     */
    @Override
    public void setId(Long id) {
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
    public Set<Configuration> getConfigurations() {
        return configurations;
    }

    /**
     * Set a new list of configurations that belong to the configuration group
     * @param configurations The new list of configurations
     */
    public void setConfigurations(Set<Configuration> configurations) {
        this.configurations = configurations;
    }

}
