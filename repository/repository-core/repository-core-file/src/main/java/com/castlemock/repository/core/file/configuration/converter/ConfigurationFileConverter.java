package com.castlemock.repository.core.file.configuration.converter;

import com.castlemock.model.core.configuration.Configuration;
import com.castlemock.repository.core.file.configuration.model.ConfigurationFile;

public final class ConfigurationFileConverter {

    private ConfigurationFileConverter() {

    }

    public static Configuration toConfiguration(final ConfigurationFile type) {
        return Configuration.builder()
                .key(type.getKey())
                .value(type.getValue())
                .type(type.getType())
                .build();
    }

}
