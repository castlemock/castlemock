package com.castlemock.repository.core.file.configuration.converter;

import com.castlemock.model.core.configuration.Configuration;
import com.castlemock.repository.core.file.configuration.model.ConfigurationFile;

public final class ConfigurationConverter {

    private ConfigurationConverter() {

    }

    public static ConfigurationFile toConfigurationFile(final Configuration type) {
        return ConfigurationFile.builder()
                .key(type.getKey())
                .value(type.getValue())
                .type(type.getType())
                .build();
    }

}
