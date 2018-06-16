package com.castlemock.web.basis.repository.configuration;

import com.castlemock.core.basis.model.configuration.domain.ConfigurationGroup;
import com.castlemock.web.basis.model.configuration.dto.ConfigurationGroupDtoGenerator;
import com.castlemock.web.basis.repository.configuration.ConfigurationRepositoryImpl;
import com.castlemock.web.basis.support.FileRepositorySupport;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.4
 */
public class ConfigurationRepositoryTest {

    @Mock
    private FileRepositorySupport fileRepositorySupport;
    @Spy
    private DozerBeanMapper mapper;
    @InjectMocks
    private ConfigurationRepositoryImpl repository;

    private static final String DIRECTORY = "/directory";
    private static final String EXTENSION = ".extension";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(repository, "configurationFileDirectory", DIRECTORY);
        ReflectionTestUtils.setField(repository, "configurationFileExtension", EXTENSION);
    }

    @Test
    public void testInitialize(){
        List<ConfigurationGroup> configurationGroups = new ArrayList<ConfigurationGroup>();
        ConfigurationGroup configurationGroup = ConfigurationGroupDtoGenerator.generateConfigurationGroup();
        configurationGroups.add(configurationGroup);
        Mockito.when(fileRepositorySupport.load(ConfigurationGroup.class, DIRECTORY, EXTENSION)).thenReturn(configurationGroups);
        repository.initialize();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).load(ConfigurationRepositoryImpl.ConfigurationGroupFile.class, DIRECTORY, EXTENSION);
    }

    @Test
    public void testFindOne(){
        final ConfigurationGroup configurationGroup = save();
        final ConfigurationGroup returnedConfigurationGroup = repository.findOne(configurationGroup.getId());
        Assert.assertEquals(configurationGroup.getId(), returnedConfigurationGroup.getId());
        Assert.assertEquals(configurationGroup.getName(), returnedConfigurationGroup.getName());
    }

    @Test
    public void testFindAll(){
        final ConfigurationGroup configurationGroup = save();
        final List<ConfigurationGroup> configurationGroups = repository.findAll();
        Assert.assertEquals(configurationGroups.size(), 1);
        Assert.assertEquals(configurationGroups.get(0).getId(), configurationGroup.getId());
        Assert.assertEquals(configurationGroups.get(0).getName(), configurationGroup.getName());
    }

    @Test
    public void testSave(){
        final ConfigurationGroup configurationGroup = save();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).save(Mockito.any(ConfigurationGroup.class), Mockito.anyString());
    }

    @Test
    public void testDelete(){
        final ConfigurationGroup configurationGroup = save();
        repository.delete(configurationGroup.getId());
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).delete(DIRECTORY + File.separator + configurationGroup.getId() + EXTENSION);
    }

    @Test
    public void testCount(){
        final ConfigurationGroup configurationGroup = save();
        final Integer count = repository.count();
        Assert.assertEquals(new Integer(1), count);
    }

    private ConfigurationGroup save(){
        final ConfigurationGroup configurationGroup = ConfigurationGroupDtoGenerator.generateConfigurationGroupDto();
        repository.save(configurationGroup);
        return configurationGroup;
    }

}
