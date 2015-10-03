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

package com.fortmocks.war.base.model.configuration.service;

import com.fortmocks.core.base.model.configuration.ConfigurationGroup;
import com.fortmocks.core.base.model.configuration.dto.ConfigurationDto;
import com.fortmocks.core.base.model.configuration.dto.ConfigurationGroupDto;
import com.fortmocks.core.base.model.configuration.service.ConfigurationGroupService;
import com.fortmocks.war.base.model.ServiceImpl;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The ConfigurationGroupServiceImpl provides functionality that involves the configuration group.
 * @author Karl Dahlgren
 * @since 1.0
 * @see com.fortmocks.core.base.model.configuration.ConfigurationGroup
 * @see com.fortmocks.core.base.model.configuration.dto.ConfigurationGroupDto
 * @see ConfigurationGroupService
 */
@Service
public class ConfigurationGroupServiceImpl extends ServiceImpl<ConfigurationGroup, ConfigurationGroupDto, Long> implements ConfigurationGroupService {


    /**
     * The method updateAll is responsible to update all the configurations with new set of data.
     * The method matches the provided configuration groups with the ones already stored and update
     * their values.
     * @param configurationGroupDtos The updated configuration group which contains the all the
     *                               configurations with new values
     * @return Returns the updated configuration group with the configurations with the updated values.
     * @see com.fortmocks.core.base.model.configuration.ConfigurationGroup
     * @see com.fortmocks.core.base.model.configuration.dto.ConfigurationGroupDto
     * @see com.fortmocks.core.base.model.configuration.Configuration
     */
    @Override
    public List<ConfigurationGroupDto> updateAll(final List<ConfigurationGroupDto> configurationGroupDtos) {
        Preconditions.checkNotNull(configurationGroupDtos, "The updated configuration group cannot be null");
        final List<ConfigurationGroupDto> sourceConfigurationGroups = findAll();
        Preconditions.checkNotNull(sourceConfigurationGroups, "The source configuration group cannot be null");
        final List<ConfigurationGroupDto> updateConfigurations = new ArrayList<ConfigurationGroupDto>();

        for(ConfigurationGroupDto sourceConfigurationGroup : sourceConfigurationGroups){
            for(ConfigurationGroupDto configurationGroup : configurationGroupDtos){
                if(sourceConfigurationGroup.getId().equals(configurationGroup.getId())){
                    update(sourceConfigurationGroup, configurationGroup);
                    ConfigurationGroupDto updateConfigurationGroup = update(sourceConfigurationGroup, configurationGroup);
                    updateConfigurations.add(updateConfigurationGroup);
                    save(updateConfigurationGroup);
                }
            }
        }


        return updateConfigurations;
    }

    /**
     * The method update provides functionality to update an entire configuration group with new set of data
     * @param sourceConfigurationGroupDto The current version of the configuration group
     * @param updatedConfigurationGroupDto The updated version of the configuration group
     * @return The updated version of the configuration group
     * @see com.fortmocks.core.base.model.configuration.ConfigurationGroup
     * @see com.fortmocks.core.base.model.configuration.dto.ConfigurationGroupDto
     */
    private ConfigurationGroupDto update(final ConfigurationGroupDto sourceConfigurationGroupDto, final ConfigurationGroupDto updatedConfigurationGroupDto){
        Preconditions.checkNotNull(sourceConfigurationGroupDto, "The source configuration group cannot be null");
        Preconditions.checkNotNull(updatedConfigurationGroupDto, "The updated configuration group cannot be null");
        for(ConfigurationDto sourceConfigurationDto : sourceConfigurationGroupDto.getConfigurations()){
            for(ConfigurationDto updatedConfigurationDto : updatedConfigurationGroupDto.getConfigurations()){
                if(sourceConfigurationDto.getKey().equals(updatedConfigurationDto.getKey())){
                    sourceConfigurationDto.setValue(updatedConfigurationDto.getValue());
                }
            }
        }
        return sourceConfigurationGroupDto;
    }
}
