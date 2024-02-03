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

package com.castlemock.service.core.configuration;

import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.core.configuration.Configuration;
import com.castlemock.model.core.configuration.ConfigurationGroup;
import com.castlemock.model.core.configuration.ConfigurationType;
import com.castlemock.repository.configuration.ConfigurationRepository;
import com.castlemock.service.core.configuration.input.ReadAllConfigurationGroupsInput;
import com.castlemock.service.core.configuration.output.ReadAllConfigurationGroupsOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadAllConfigurationGroupsServiceTest {

    @Mock
    private ConfigurationRepository repository;

    @InjectMocks
    private ReadAllConfigurationGroupsService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final List<ConfigurationGroup> configurationGroups = new ArrayList<>();
        final Configuration configuration = Configuration.builder()
                .key("Key")
                .value("Value")
                .type(ConfigurationType.BOOLEAN)
                .build();
        final ConfigurationGroup configurationGroup = ConfigurationGroup.builder()
                .id("123")
                .name("Configuration group")
                .configurations(List.of(configuration))
                .build();
        configurationGroups.add(configurationGroup);

        Mockito.when(repository.findAll()).thenReturn(configurationGroups);
        final ReadAllConfigurationGroupsInput input = new ReadAllConfigurationGroupsInput();
        final ServiceTask<ReadAllConfigurationGroupsInput> serviceTask = ServiceTask.of(input, "user");
        final ServiceResult<ReadAllConfigurationGroupsOutput> serviceResult = service.process(serviceTask);
        final ReadAllConfigurationGroupsOutput output = serviceResult.getOutput();

        final List<ConfigurationGroup> returnedConfigurationGroups = output.configurationGroups();
        Assertions.assertEquals(returnedConfigurationGroups.size(), configurationGroups.size());
        final ConfigurationGroup returnedConfigurationGroup = returnedConfigurationGroups.getFirst();
        Assertions.assertEquals(returnedConfigurationGroup.getId(), configurationGroup.getId());
        Assertions.assertEquals(returnedConfigurationGroup.getName(), configurationGroup.getName());
        Assertions.assertEquals(returnedConfigurationGroup.getConfigurations().size(), configurationGroup.getConfigurations().size());
        final Configuration returnedConfiguration = returnedConfigurationGroup.getConfigurations().getFirst();
        Assertions.assertEquals(returnedConfiguration.getType(), configuration.getType());
        Assertions.assertEquals(returnedConfiguration.getKey(), configuration.getKey());
        Assertions.assertEquals(returnedConfiguration.getValue(), configuration.getValue());
    }


}
