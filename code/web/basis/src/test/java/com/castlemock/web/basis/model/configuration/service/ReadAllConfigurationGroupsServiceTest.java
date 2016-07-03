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
import com.castlemock.core.basis.model.configuration.domain.ConfigurationType;
import com.castlemock.core.basis.model.configuration.dto.ConfigurationDto;
import com.castlemock.core.basis.model.configuration.dto.ConfigurationGroupDto;
import com.castlemock.core.basis.model.configuration.service.message.input.ReadAllConfigurationGroupsInput;
import com.castlemock.core.basis.model.configuration.service.message.output.ReadAllConfigurationGroupsOutput;
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
public class ReadAllConfigurationGroupsServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @InjectMocks
    private ReadAllConfigurationGroupsService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        List<ConfigurationGroupDto> configurationGroups = new ArrayList<ConfigurationGroupDto>();
        ConfigurationGroupDto configurationGroup = new ConfigurationGroupDto();
        configurationGroup.setId(new String());
        configurationGroup.setName("Configuration group");
        configurationGroup.setConfigurations(new ArrayList<ConfigurationDto>());
        ConfigurationDto configuration = new ConfigurationDto();
        configuration.setKey("Key");
        configuration.setValue("Value");
        configuration.setType(ConfigurationType.BOOLEAN);
        configurationGroup.getConfigurations().add(configuration);
        configurationGroups.add(configurationGroup);

        Mockito.when(repository.findAll()).thenReturn(configurationGroups);
        final ReadAllConfigurationGroupsInput input = Mockito.mock(ReadAllConfigurationGroupsInput.class);
        final ServiceTask<ReadAllConfigurationGroupsInput> serviceTask = new ServiceTask<ReadAllConfigurationGroupsInput>();
        serviceTask.setInput(input);
        final ServiceResult<ReadAllConfigurationGroupsOutput> serviceResult = service.process(serviceTask);
        final ReadAllConfigurationGroupsOutput output = serviceResult.getOutput();

        final List<ConfigurationGroupDto> returnedConfigurationGroups = output.getConfigurationGroups();
        Assert.assertEquals(returnedConfigurationGroups.size(), configurationGroups.size());
        final ConfigurationGroupDto returnedConfigurationGroup = returnedConfigurationGroups.get(0);
        Assert.assertEquals(returnedConfigurationGroup.getId(), configurationGroup.getId());
        Assert.assertEquals(returnedConfigurationGroup.getName(), configurationGroup.getName());
        Assert.assertEquals(returnedConfigurationGroup.getConfigurations().size(), configurationGroup.getConfigurations().size());
        final ConfigurationDto returnedConfiguration = returnedConfigurationGroup.getConfigurations().get(0);
        Assert.assertEquals(returnedConfiguration.getType(), configuration.getType());
        Assert.assertEquals(returnedConfiguration.getKey(), configuration.getKey());
        Assert.assertEquals(returnedConfiguration.getValue(), configuration.getValue());
    }


}
