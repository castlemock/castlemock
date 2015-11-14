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

package com.fortmocks.core.basis.model.configuration.dto;

import com.fortmocks.core.basis.model.configuration.domain.ConfigurationGroup;
import org.dozer.Mapping;

import java.util.List;

/**
 * The configuration group DTO is a DTO (Data transfer object) class for the configuration group class. The
 * class contains both an identifier (See {@link #name}) and all the configurations (See {@link #configurations}) that
 * are being grouped by the configuration group.
 * @author Karl Dahlgren
 * @since 1.0
 * @see ConfigurationGroup
 * @see ConfigurationDto
 */
public class ConfigurationGroupDto {

    @Mapping("id")
    private Long id;

    @Mapping("name")
    private String name;

    @Mapping("configurations")
    private List<ConfigurationDto> configurations;

    /**
     * Returns the configuration group id
     * @return The configuration group id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets a new id value for the configuration group
     * @param id The new id for the configuration group
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the name of the configuration group
     * @return The new of the configuration group
     */
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
    public List<ConfigurationDto> getConfigurations() {
        return configurations;
    }

    /**
     * Set a new list of configurations that belong to the configuration group
     * @param configurations The new list of configurations
     */
    public void setConfigurations(List<ConfigurationDto> configurations) {
        this.configurations = configurations;
    }
}
