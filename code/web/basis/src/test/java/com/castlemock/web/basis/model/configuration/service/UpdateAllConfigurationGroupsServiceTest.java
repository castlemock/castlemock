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

package com.castlemock.web.basis.model.configuration.service;

import com.castlemock.core.basis.model.Repository;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.basis.model.configuration.domain.ConfigurationGroup;
import com.castlemock.core.basis.model.configuration.domain.ConfigurationType;
import com.castlemock.core.basis.model.configuration.dto.ConfigurationDto;
import com.castlemock.core.basis.model.configuration.dto.ConfigurationGroupDto;
import com.castlemock.core.basis.model.configuration.service.message.input.UpdateAllConfigurationGroupsInput;
import com.castlemock.core.basis.model.configuration.service.message.output.UpdateAllConfigurationGroupsOutput;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateAllConfigurationGroupsServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @InjectMocks
    private UpdateAllConfigurationGroupsService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final List<ConfigurationGroupDto> configurationGroups = new ArrayList<ConfigurationGroupDto>();
        final ConfigurationGroupDto configurationGroup = new ConfigurationGroupDto();
        configurationGroup.setId(new String());
        configurationGroup.setName("Configuration group");
        configurationGroup.setConfigurations(new ArrayList<ConfigurationDto>());
        final ConfigurationDto configuration = new ConfigurationDto();
        configuration.setKey("Key");
        configuration.setValue("New value");
        configuration.setType(ConfigurationType.BOOLEAN);
        configurationGroup.getConfigurations().add(configuration);
        configurationGroups.add(configurationGroup);

        final List<ConfigurationGroupDto> sourceConfigurationGroups = new ArrayList<ConfigurationGroupDto>();
        final ConfigurationGroupDto sourceConfigurationGroup = new ConfigurationGroupDto();
        sourceConfigurationGroup.setId(new String());
        sourceConfigurationGroup.setName("Configuration group");
        sourceConfigurationGroup.setConfigurations(new ArrayList<ConfigurationDto>());
        final ConfigurationDto sourceConfiguration = new ConfigurationDto();
        sourceConfiguration.setKey("Key");
        sourceConfiguration.setValue("Old value");
        sourceConfiguration.setType(ConfigurationType.BOOLEAN);
        sourceConfigurationGroup.getConfigurations().add(sourceConfiguration);
        sourceConfigurationGroups.add(sourceConfigurationGroup);

        final ConfigurationGroupDto updatedConfigurationGroup = new ConfigurationGroupDto();
        updatedConfigurationGroup.setId(new String());
        updatedConfigurationGroup.setName("Configuration group");
        updatedConfigurationGroup.setConfigurations(new ArrayList<ConfigurationDto>());
        final ConfigurationDto updatedConfiguration = new ConfigurationDto();
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
        List<ConfigurationGroupDto> returnedConfigurationGroups = output.getUpdatedConfigurationGroups();
        Assert.assertEquals(1, returnedConfigurationGroups.size());
        ConfigurationGroupDto returnedConfigurationGroup = returnedConfigurationGroups.get(0);
        Assert.assertEquals(updatedConfigurationGroup.getId(), returnedConfigurationGroup.getId());
        Assert.assertEquals(updatedConfigurationGroup.getName(), returnedConfigurationGroup.getName());
        Assert.assertEquals(updatedConfigurationGroup.getConfigurations().size(), returnedConfigurationGroup.getConfigurations().size());
        Assert.assertEquals(1, returnedConfigurationGroup.getConfigurations().size());
        ConfigurationDto returnedConfiguration = returnedConfigurationGroup.getConfigurations().get(0);
        Assert.assertEquals(returnedConfiguration.getType(), updatedConfiguration.getType());
        Assert.assertEquals(returnedConfiguration.getKey(), updatedConfiguration.getKey());
        Assert.assertEquals(returnedConfiguration.getValue(), updatedConfiguration.getValue());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(ConfigurationGroup.class));
        Mockito.verify(repository, Mockito.times(1)).findAll();
    }


}
