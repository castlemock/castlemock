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