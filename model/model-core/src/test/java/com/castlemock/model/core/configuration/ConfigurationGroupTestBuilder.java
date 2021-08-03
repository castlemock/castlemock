package com.castlemock.model.core.configuration;

import java.util.List;

public final class ConfigurationGroupTestBuilder {

    private String id;
    private String name;
    private List<Configuration> configurations;

    private ConfigurationGroupTestBuilder() {
        this.id = "ConfigurationGroupId";
        this.name = "Configuration group name";
        this.configurations = List.of(ConfigurationTestBuilder.builder().build());
    }

    public static ConfigurationGroupTestBuilder builder() {
        return new ConfigurationGroupTestBuilder();
    }

    public ConfigurationGroupTestBuilder id(final String id) {
        this.id = id;
        return this;
    }

    public ConfigurationGroupTestBuilder name(final String name) {
        this.name = name;
        return this;
    }

    public ConfigurationGroupTestBuilder configurations(final List<Configuration> configurations) {
        this.configurations = configurations;
        return this;
    }

    public ConfigurationGroup build() {
        return ConfigurationGroup.builder()
                .id(id)
                .name(name)
                .configurations(configurations)
                .build();
    }

}
