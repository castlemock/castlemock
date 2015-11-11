package com.fortmocks.core.model.configuration.message;

import com.fortmocks.core.model.Output;
import com.fortmocks.core.model.configuration.dto.ConfigurationGroupDto;
import com.fortmocks.core.model.validation.NotNull;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateAllConfigurationGroupsOutput implements Output {

    @NotNull
    private List<ConfigurationGroupDto> updatedConfigurationGroups;

    public List<ConfigurationGroupDto> getUpdatedConfigurationGroups() {
        return updatedConfigurationGroups;
    }

    public void setUpdatedConfigurationGroups(List<ConfigurationGroupDto> updatedConfigurationGroups) {
        this.updatedConfigurationGroups = updatedConfigurationGroups;
    }
}
