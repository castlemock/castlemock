package com.fortmocks.web.basis.model.configuration.service;

import com.fortmocks.core.basis.model.Repository;
import com.fortmocks.core.basis.model.ServiceResult;
import com.fortmocks.core.basis.model.ServiceTask;
import com.fortmocks.core.basis.model.configuration.domain.Configuration;
import com.fortmocks.core.basis.model.configuration.domain.ConfigurationGroup;
import com.fortmocks.core.basis.model.configuration.domain.ConfigurationType;
import com.fortmocks.core.basis.model.configuration.dto.ConfigurationDto;
import com.fortmocks.core.basis.model.configuration.dto.ConfigurationGroupDto;
import com.fortmocks.core.basis.model.configuration.service.message.input.ReadAllConfigurationGroupsInput;
import com.fortmocks.core.basis.model.configuration.service.message.output.ReadAllConfigurationGroupsOutput;
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
        List<ConfigurationGroup> configurationGroups = new ArrayList<ConfigurationGroup>();
        ConfigurationGroup configurationGroup = new ConfigurationGroup();
        configurationGroup.setId(1L);
        configurationGroup.setName("Configuration group");
        configurationGroup.setConfigurations(new ArrayList<Configuration>());
        Configuration configuration = new Configuration();
        configuration.setKey("Key");
        configuration.setValue("Value");
        configuration.setConfigurationType(ConfigurationType.BOOLEAN);
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
        Assert.assertEquals(returnedConfiguration.getConfigurationType(), configuration.getConfigurationType());
        Assert.assertEquals(returnedConfiguration.getKey(), configuration.getKey());
        Assert.assertEquals(returnedConfiguration.getValue(), configuration.getValue());
    }


}
