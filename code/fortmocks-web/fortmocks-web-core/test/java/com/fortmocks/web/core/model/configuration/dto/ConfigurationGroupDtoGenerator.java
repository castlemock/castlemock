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

package com.fortmocks.web.core.model.configuration.dto;

import com.fortmocks.core.model.configuration.dto.ConfigurationDto;
import com.fortmocks.core.model.configuration.dto.ConfigurationGroupDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 * @see com.fortmocks.core.model.event.Event
 * @see ConfigurationGroupDtoGenerator
 */
public class ConfigurationGroupDtoGenerator {

    public static ConfigurationGroupDto generateConfigurationGroup(){
        return generateConfigurationGroup(1);
    }

    public static ConfigurationGroupDto generateConfigurationGroup(final int count){
        final ConfigurationGroupDto configurationGroupDto = new ConfigurationGroupDto();
        final List<ConfigurationDto> configurationDtos = new ArrayList<ConfigurationDto>();
        for(int index = 0; index < count; index++){
            ConfigurationDto configurationDto = ConfigurationDtoGenerator.generateConfigurationGroup();
            configurationDtos.add(configurationDto);
        }

        configurationGroupDto.setName("COnfiguration group name");
        configurationGroupDto.setId(1L);
        configurationGroupDto.setConfigurations(configurationDtos);
        return configurationGroupDto;
    }
}
