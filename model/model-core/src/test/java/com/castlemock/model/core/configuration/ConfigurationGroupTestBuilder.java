/*
 * Copyright 2024 Karl Dahlgren
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
