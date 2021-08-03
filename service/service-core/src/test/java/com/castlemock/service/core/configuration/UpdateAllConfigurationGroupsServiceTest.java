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
import com.castlemock.service.core.configuration.input.UpdateAllConfigurationGroupsInput;
import com.castlemock.service.core.configuration.output.UpdateAllConfigurationGroupsOutput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
public class UpdateAllConfigurationGroupsServiceTest {

    @Mock
    private ConfigurationRepository repository;

    @InjectMocks
    private UpdateAllConfigurationGroupsService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final List<ConfigurationGroup> configurationGroups = new ArrayList<ConfigurationGroup>();
        final ConfigurationGroup configurationGroup = new ConfigurationGroup();
        configurationGroup.setId("123");
        configurationGroup.setName("Configuration group");
        configurationGroup.setConfigurations(new ArrayList<Configuration>());
        final Configuration configuration = new Configuration();
        configuration.setKey("Key");
        configuration.setValue("New value");
        configuration.setType(ConfigurationType.BOOLEAN);
        configurationGroup.getConfigurations().add(configuration);
        configurationGroups.add(configurationGroup);

        final List<ConfigurationGroup> sourceConfigurationGroups = new ArrayList<ConfigurationGroup>();
        final ConfigurationGroup sourceConfigurationGroup = new ConfigurationGroup();
        sourceConfigurationGroup.setId("123");
        sourceConfigurationGroup.setName("Configuration group");
        sourceConfigurationGroup.setConfigurations(new ArrayList<Configuration>());
        final Configuration sourceConfiguration = new Configuration();
        sourceConfiguration.setKey("Key");
        sourceConfiguration.setValue("Old value");
        sourceConfiguration.setType(ConfigurationType.BOOLEAN);
        sourceConfigurationGroup.getConfigurations().add(sourceConfiguration);
        sourceConfigurationGroups.add(sourceConfigurationGroup);

        final ConfigurationGroup updatedConfigurationGroup = new ConfigurationGroup();
        updatedConfigurationGroup.setId("123");
        updatedConfigurationGroup.setName("Configuration group");
        updatedConfigurationGroup.setConfigurations(new ArrayList<Configuration>());
        final Configuration updatedConfiguration = new Configuration();
        updatedConfiguration.setKey("Key");
        updatedConfiguration.setValue("New value");
        updatedConfiguration.setType(ConfigurationType.BOOLEAN);
        updatedConfigurationGroup.getConfigurations().add(updatedConfiguration);

        Mockito.when(repository.findAll()).thenReturn(sourceConfigurationGroups);
        Mockito.when(repository.save(Mockito.any(ConfigurationGroup.class))).thenReturn(updatedConfigurationGroup);

        final UpdateAllConfigurationGroupsInput input = new UpdateAllConfigurationGroupsInput(configurationGroups);
        final ServiceTask<UpdateAllConfigurationGroupsInput> serviceTask = new ServiceTask<>();
        serviceTask.setInput(input);
        final ServiceResult<UpdateAllConfigurationGroupsOutput> serviceResult = service.process(serviceTask);
        final UpdateAllConfigurationGroupsOutput output = serviceResult.getOutput();

        Assert.assertNotNull(output);
        Assert.assertNotNull(output.getUpdatedConfigurationGroups());
        List<ConfigurationGroup> returnedConfigurationGroups = output.getUpdatedConfigurationGroups();
        Assert.assertEquals(1, returnedConfigurationGroups.size());
        ConfigurationGroup returnedConfigurationGroup = returnedConfigurationGroups.get(0);
        Assert.assertEquals(updatedConfigurationGroup.getId(), returnedConfigurationGroup.getId());
        Assert.assertEquals(updatedConfigurationGroup.getName(), returnedConfigurationGroup.getName());
        Assert.assertEquals(updatedConfigurationGroup.getConfigurations().size(), returnedConfigurationGroup.getConfigurations().size());
        Assert.assertEquals(1, returnedConfigurationGroup.getConfigurations().size());
        Configuration returnedConfiguration = returnedConfigurationGroup.getConfigurations().get(0);
        Assert.assertEquals(returnedConfiguration.getType(), updatedConfiguration.getType());
        Assert.assertEquals(returnedConfiguration.getKey(), updatedConfiguration.getKey());
        Assert.assertEquals(returnedConfiguration.getValue(), updatedConfiguration.getValue());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(ConfigurationGroup.class));
        Mockito.verify(repository, Mockito.times(1)).findAll();
    }


}
