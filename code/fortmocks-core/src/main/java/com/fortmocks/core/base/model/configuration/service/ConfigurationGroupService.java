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

package com.fortmocks.core.base.model.configuration.service;

import com.fortmocks.core.base.model.Service;
import com.fortmocks.core.base.model.configuration.ConfigurationGroup;
import com.fortmocks.core.base.model.configuration.dto.ConfigurationGroupDto;

import java.util.List;

/**
 * The ConfigurationGroupService provides functionality that involves the configuration group.
 * @author Karl Dahlgren
 * @since 1.0
 * @see com.fortmocks.core.base.model.configuration.ConfigurationGroup
 * @see com.fortmocks.core.base.model.configuration.dto.ConfigurationGroupDto
 */
public interface ConfigurationGroupService extends Service<ConfigurationGroup, ConfigurationGroupDto, Long> {

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
      List<ConfigurationGroupDto> updateAll(List<ConfigurationGroupDto> configurationGroupDtos);
}
