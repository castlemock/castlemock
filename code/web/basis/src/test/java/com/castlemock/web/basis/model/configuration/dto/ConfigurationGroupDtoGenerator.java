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

package com.castlemock.web.basis.model.configuration.dto;

import com.castlemock.core.basis.model.configuration.domain.Configuration;
import com.castlemock.core.basis.model.configuration.domain.ConfigurationGroup;
import com.castlemock.core.basis.model.configuration.dto.ConfigurationDto;
import com.castlemock.core.basis.model.configuration.dto.ConfigurationGroupDto;
import com.castlemock.core.basis.model.event.domain.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 * @see Event
 * @see ConfigurationGroupDtoGenerator
 */
public class ConfigurationGroupDtoGenerator {

    public static ConfigurationGroupDto generateConfigurationGroupDto(){
        return generateConfigurationGroupDto(1);
    }


    public static ConfigurationGroup generateConfigurationGroup(){
        return generateConfigurationGroup(1);
    }

    public static ConfigurationGroupDto generateConfigurationGroupDto(final int count){
        final ConfigurationGroupDto configurationGroupDto = new ConfigurationGroupDto();
        final List<ConfigurationDto> configurationDtos = new ArrayList<ConfigurationDto>();
        for(int index = 0; index < count; index++){
            ConfigurationDto configurationDto = ConfigurationDtoGenerator.generateConfigurationDto();
            configurationDtos.add(configurationDto);
        }

        configurationGroupDto.setName("COnfiguration group name");
        configurationGroupDto.setId("ConfigurationGroupId");
        configurationGroupDto.setConfigurations(configurationDtos);
        return configurationGroupDto;
    }

    public static ConfigurationGroup generateConfigurationGroup(final int count){
        final ConfigurationGroup configurationGroup = new ConfigurationGroup();
        final List<Configuration> configurations = new ArrayList<Configuration>();
        for(int index = 0; index < count; index++){
            Configuration configurationDto = ConfigurationDtoGenerator.generateConfiguration();
            configurations.add(configurationDto);
        }

        configurationGroup.setName("COnfiguration group name");
        configurationGroup.setId("ConfigurationGroupId");
        configurationGroup.setConfigurations(configurations);
        return configurationGroup;
    }
}
