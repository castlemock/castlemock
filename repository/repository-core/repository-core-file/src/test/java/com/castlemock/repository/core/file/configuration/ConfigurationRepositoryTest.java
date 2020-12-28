/*
 * Copyright 2018 Karl Dahlgren
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

package com.castlemock.repository.core.file.configuration;

import com.castlemock.model.core.configuration.ConfigurationGroup;
import com.castlemock.model.core.configuration.ConfigurationGroupTestBuilder;
import com.castlemock.repository.core.file.FileRepositorySupport;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
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
    private ConfigurationFileRepository repository;

    private static final String DIRECTORY = "/directory";
    private static final String EXTENSION = ".extension";

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(repository, "configurationFileDirectory", DIRECTORY);
        ReflectionTestUtils.setField(repository, "configurationFileExtension", EXTENSION);
    }

    @Test
    public void testInitialize(){
        List<ConfigurationGroup> configurationGroups = new ArrayList<ConfigurationGroup>();
        ConfigurationGroup configurationGroup = ConfigurationGroupTestBuilder.builder().build();
        configurationGroups.add(configurationGroup);
        Mockito.when(fileRepositorySupport.load(ConfigurationGroup.class, DIRECTORY, EXTENSION)).thenReturn(configurationGroups);
        repository.initialize();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).load(ConfigurationFileRepository.ConfigurationGroupFile.class, DIRECTORY, EXTENSION);
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
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).save(Mockito.any(ConfigurationFileRepository.ConfigurationGroupFile.class), Mockito.anyString());
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
        Assert.assertEquals(Integer.valueOf(1), count);
    }

    private ConfigurationGroup save(){
        final ConfigurationGroup configurationGroup = ConfigurationGroupTestBuilder.builder().build();
        repository.save(configurationGroup);
        return configurationGroup;
    }

}
