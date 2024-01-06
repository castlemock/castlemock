package com.castlemock.repository.core.file.configuration.converter;

import com.castlemock.model.core.configuration.ConfigurationGroup;
import com.castlemock.repository.core.file.configuration.model.ConfigurationGroupFile;

import java.util.stream.Collectors;

public final class ConfigurationGroupFileConverter {

    private ConfigurationGroupFileConverter() {

    }

    public static ConfigurationGroup toConfigurationGroup(final ConfigurationGroupFile type) {
        return ConfigurationGroup.builder()
                .id(type.getId())
                .name(type.getName())
                .configurations(type.getConfigurations()
                        .stream()
                        .map(ConfigurationFileConverter::toConfiguration)
                        .collect(Collectors.toList()))
                .build();
    }
}
