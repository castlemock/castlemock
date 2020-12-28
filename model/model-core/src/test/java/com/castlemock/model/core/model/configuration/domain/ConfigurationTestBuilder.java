package com.castlemock.model.core.model.configuration.domain;

public final class ConfigurationTestBuilder {

    private String key;
    private String value;
    private ConfigurationType type;

    private ConfigurationTestBuilder() {
        this.key = "Key";
        this.value = "Value";
        this.type = ConfigurationType.STRING;
    }

    public static ConfigurationTestBuilder builder() {
        return new ConfigurationTestBuilder();
    }

    public ConfigurationTestBuilder key(final String key) {
        this.key = key;
        return this;
    }

    public ConfigurationTestBuilder value(final String value) {
        this.value = value;
        return this;
    }

    public ConfigurationTestBuilder type(final ConfigurationType type) {
        this.type = type;
        return this;
    }

    public Configuration build() {
        return Configuration.builder()
                .key(key)
                .value(value)
                .type(type)
                .build();
    }

}
