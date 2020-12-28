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

package com.castlemock.repository.soap.file.project;


import com.castlemock.model.mock.soap.domain.SoapProject;
import com.castlemock.model.mock.soap.domain.SoapProjectTestBuilder;
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
public class SoapProjectRepositoryTest {

    @Mock
    private FileRepositorySupport fileRepositorySupport;
    @Spy
    private DozerBeanMapper mapper;
    @InjectMocks
    private SoapProjectFileRepository repository;
    private static final String DIRECTORY = "/directory";
    private static final String EXTENSION = ".extension";

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(repository, "soapProjectFileDirectory", DIRECTORY);
        ReflectionTestUtils.setField(repository, "soapProjectFileExtension", EXTENSION);
    }

    @Test
    public void testInitialize(){
        List<SoapProject> soapProjects = new ArrayList<SoapProject>();
        SoapProject soapProject = SoapProjectTestBuilder.builder().build();;
        soapProjects.add(soapProject);
        Mockito.when(fileRepositorySupport.load(SoapProject.class, DIRECTORY, EXTENSION)).thenReturn(soapProjects);
        repository.initialize();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).load(SoapProjectFileRepository.SoapProjectFile.class, DIRECTORY, EXTENSION);
    }

    @Test
    public void testFindOne(){
        final SoapProject soapProject = save();
        final SoapProject returnedSoapEvent = repository.findOne(soapProject.getId());
        Assert.assertEquals(returnedSoapEvent.getId(), soapProject.getId());
        Assert.assertEquals(returnedSoapEvent.getName(), soapProject.getName());
        Assert.assertEquals(returnedSoapEvent.getDescription(), soapProject.getDescription());
        Assert.assertEquals(returnedSoapEvent.getTypeIdentifier(), soapProject.getTypeIdentifier());
    }

    @Test
    public void testFindAll(){
        final SoapProject soapProject = save();
        final List<SoapProject> soapProjects = repository.findAll();
        Assert.assertEquals(soapProjects.size(), 1);
        Assert.assertEquals(soapProjects.get(0).getId(), soapProject.getId());
        Assert.assertEquals(soapProjects.get(0).getName(), soapProject.getName());
        Assert.assertEquals(soapProjects.get(0).getDescription(), soapProject.getDescription());
        Assert.assertEquals(soapProjects.get(0).getTypeIdentifier(), soapProject.getTypeIdentifier());
    }

    @Test
    public void testSave(){
        final SoapProject soapProject = save();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).save(Mockito.any(SoapProjectFileRepository.SoapProjectFile.class), Mockito.anyString());
    }

    @Test
    public void testDelete(){
        final SoapProject soapProject = save();
        repository.delete(soapProject.getId());
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).delete(DIRECTORY + File.separator + soapProject.getId() + EXTENSION);
    }

    @Test
    public void testCount(){
        final SoapProject soapProject = save();
        final Integer count = repository.count();
        Assert.assertEquals(Integer.valueOf(1), count);
    }

    private SoapProject save(){
        SoapProject soapProject = SoapProjectTestBuilder.builder().build();;
        repository.save(soapProject);
        return soapProject;
    }

}
