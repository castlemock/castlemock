package com.fortmocks.core.model.configuration.message;

import com.fortmocks.core.model.Output;
import com.fortmocks.core.model.configuration.ConfigurationGroup;
import com.fortmocks.core.model.configuration.dto.ConfigurationGroupDto;
import com.fortmocks.core.model.user.dto.UserDto;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class FindAllConfigurationGroupsOutput implements Output {

    private List<ConfigurationGroupDto> configurationGroups;

    public List<ConfigurationGroupDto> getConfigurationGroups() {
        return configurationGroups;
    }

    public void setConfigurationGroups(List<ConfigurationGroupDto> configurationGroups) {
        this.configurationGroups = configurationGroups;
    }
}
