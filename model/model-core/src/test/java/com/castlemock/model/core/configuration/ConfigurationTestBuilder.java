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
