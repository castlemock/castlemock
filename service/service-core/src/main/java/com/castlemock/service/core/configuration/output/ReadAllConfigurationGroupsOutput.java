/*
 * Copyright 2015 Karl Dahlgren
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

package com.castlemock.service.core.configuration.output;

import com.castlemock.model.core.Output;
import com.castlemock.model.core.configuration.ConfigurationGroup;
import com.castlemock.service.core.configuration.input.ReadAllConfigurationGroupsInput;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @see ReadAllConfigurationGroupsInput
 * @since 1.0
 */
public record ReadAllConfigurationGroupsOutput(
        List<ConfigurationGroup> configurationGroups) implements Output {

}
