package com.castlemock.repository.core.file.configuration.converter;

import com.castlemock.model.core.configuration.ConfigurationGroup;
import com.castlemock.repository.core.file.configuration.model.ConfigurationGroupFile;

import java.util.stream.Collectors;

public final class ConfigurationGroupConverter {

    private ConfigurationGroupConverter() {

    }


    public static ConfigurationGroupFile toConfigurationGroupFile(final ConfigurationGroup type) {
        return ConfigurationGroupFile.builder()
                .id(type.getId())
                .name(type.getName())
                .configurations(type.getConfigurations()
                        .stream()
                        .map(ConfigurationConverter::toConfigurationFile)
                        .collect(Collectors.toList()))
                .build();
    }

}