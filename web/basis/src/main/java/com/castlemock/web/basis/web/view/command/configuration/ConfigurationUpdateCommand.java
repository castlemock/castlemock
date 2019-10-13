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

package com.castlemock.web.basis.web.view.command.configuration;

import com.castlemock.core.basis.model.configuration.domain.ConfigurationGroup;

import java.util.List;

/**
 * ConfigurationUpdateCommand is used to update all the configurations and
 * configuration groups with new values.
 * @author Karl Dahlgren
 * @since 1.0
 * @see ConfigurationGroup
 * @see ConfigurationGroup
 */
public class ConfigurationUpdateCommand {

    private List<ConfigurationGroup> configurationGroups;

    /**
     * Returns the value for configuration groups
     * @return The value for configuration groups
     */
    public List<ConfigurationGroup> getConfigurationGroups() {
        return configurationGroups;
    }

    /**
     * Set new configuration groups value.
     * @param configurationGroups The new value for configuration groups
     */
    public void setConfigurationGroups(List<ConfigurationGroup> configurationGroups) {
        this.configurationGroups = configurationGroups;
    }
}
