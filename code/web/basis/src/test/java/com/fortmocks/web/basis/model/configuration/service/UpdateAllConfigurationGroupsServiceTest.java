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

package com.fortmocks.web.basis.model.configuration.service;

import com.fortmocks.core.basis.model.Repository;
import com.fortmocks.core.basis.model.ServiceResult;
import com.fortmocks.core.basis.model.ServiceTask;
import com.fortmocks.core.basis.model.configuration.domain.Configuration;
import com.fortmocks.core.basis.model.configuration.domain.ConfigurationGroup;
import com.fortmocks.core.basis.model.configuration.domain.ConfigurationType;
import com.fortmocks.core.basis.model.configuration.dto.ConfigurationDto;
import com.fortmocks.core.basis.model.configuration.dto.ConfigurationGroupDto;
import com.fortmocks.core.basis.model.configuration.service.message.input.UpdateAllConfigurationGroupsInput;
import com.fortmocks.core.basis.model.configuration.service.message.output.UpdateAllConfigurationGroupsOutput;
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
        configuration.setConfigurationType(ConfigurationType.BOOLEAN);
        configurationGroup.getConfigurations().add(configuration);
        configurationGroups.add(configurationGroup);

        final List<ConfigurationGroup> sourceConfigurationGroups = new ArrayList<ConfigurationGroup>();
        final ConfigurationGroup sourceConfigurationGroup = new ConfigurationGroup();
        sourceConfigurationGroup.setId(new String());
        sourceConfigurationGroup.setName("Configuration group");
        sourceConfigurationGroup.setConfigurations(new ArrayList<Configuration>());
        final Configuration sourceConfiguration = new Configuration();
        sourceConfiguration.setKey("Key");
        sourceConfiguration.setValue("Old value");
        sourceConfiguration.setConfigurationType(ConfigurationType.BOOLEAN);
        sourceConfigurationGroup.getConfigurations().add(sourceConfiguration);
        sourceConfigurationGroups.add(sourceConfigurationGroup);

        final ConfigurationGroup updatedConfigurationGroup = new ConfigurationGroup();
        updatedConfigurationGroup.setId(new String());
        updatedConfigurationGroup.setName("Configuration group");
        updatedConfigurationGroup.setConfigurations(new ArrayList<Configuration>());
        final Configuration updatedConfiguration = new Configuration();
        updatedConfiguration.setKey("Key");
        updatedConfiguration.setValue("New value");
        updatedConfiguration.setConfigurationType(ConfigurationType.BOOLEAN);
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
        Assert.assertEquals(returnedConfiguration.getConfigurationType(), updatedConfiguration.getConfigurationType());
        Assert.assertEquals(returnedConfiguration.getKey(), updatedConfiguration.getKey());
        Assert.assertEquals(returnedConfiguration.getValue(), updatedConfiguration.getValue());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(ConfigurationGroup.class));
        Mockito.verify(repository, Mockito.times(1)).findAll();
    }


}
