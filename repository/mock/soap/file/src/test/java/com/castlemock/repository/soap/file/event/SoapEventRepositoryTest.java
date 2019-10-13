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

package com.castlemock.repository.soap.file.event;

import com.castlemock.core.mock.soap.model.event.domain.SoapEvent;
import com.castlemock.core.mock.soap.model.event.domain.SoapEventTestBuilder;
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
public class SoapEventRepositoryTest {

    @Mock
    private FileRepositorySupport fileRepositorySupport;
    @Spy
    private DozerBeanMapper mapper;
    @InjectMocks
    private SoapEventFileRepository repository;
    private static final String DIRECTORY = "/directory";
    private static final String EXTENSION = ".extension";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(repository, "soapEventFileDirectory", DIRECTORY);
        ReflectionTestUtils.setField(repository, "soapEventFileExtension", EXTENSION);
    }

    @Test
    public void testInitialize(){
        List<SoapEvent> soapEvents = new ArrayList<SoapEvent>();
        SoapEvent soapEvent = SoapEventTestBuilder.builder().build();
        soapEvents.add(soapEvent);
        Mockito.when(fileRepositorySupport.load(SoapEvent.class, DIRECTORY, EXTENSION)).thenReturn(soapEvents);
        repository.initialize();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).load(SoapEventFileRepository.SoapEventFile.class, DIRECTORY, EXTENSION);
    }

    @Test
    public void testFindOne(){
        final SoapEvent soapEvent = save();
        final SoapEvent returnedSoapEvent = repository.findOne(soapEvent.getId());
        Assert.assertEquals(returnedSoapEvent.getProjectId(), soapEvent.getProjectId());
        Assert.assertEquals(returnedSoapEvent.getId(), soapEvent.getId());
        Assert.assertEquals(returnedSoapEvent.getResourceName(), soapEvent.getResourceName());
        Assert.assertEquals(returnedSoapEvent.getOperationId(), soapEvent.getOperationId());
        Assert.assertEquals(returnedSoapEvent.getPortId(), soapEvent.getPortId());
        Assert.assertEquals(returnedSoapEvent.getResourceName(), soapEvent.getResourceName());


        //Assert.assertEquals(returnedSoapEvent.getResourceLink(), soapEvent.getResourceLink());
        //Assert.assertEquals(returnedSoapEvent.getTypeIdentifier(), soapEvent.getTypeIdentifier());
    }

    @Test
    public void testFindAll(){
        final SoapEvent soapEvent = save();
        final List<SoapEvent> soapEvents = repository.findAll();
        Assert.assertEquals(soapEvents.size(), 1);
        Assert.assertEquals(soapEvents.get(0).getProjectId(), soapEvent.getProjectId());
        Assert.assertEquals(soapEvents.get(0).getId(), soapEvent.getId());
        Assert.assertEquals(soapEvents.get(0).getResourceName(), soapEvent.getResourceName());
        Assert.assertEquals(soapEvents.get(0).getOperationId(), soapEvent.getOperationId());
        Assert.assertEquals(soapEvents.get(0).getPortId(), soapEvent.getPortId());
        Assert.assertEquals(soapEvents.get(0).getResourceName(), soapEvent.getResourceName());

        //Assert.assertEquals(soapEvents.get(0).getResourceLink(), soapEvent.getResourceLink());
        //Assert.assertEquals(soapEvents.get(0).getTypeIdentifier(), soapEvent.getTypeIdentifier());
    }

    @Test
    public void testSave(){
        final SoapEvent soapEvent = save();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).save(Mockito.any(SoapEventFileRepository.SoapEventFile.class), Mockito.anyString());
    }

    @Test
    public void testDelete(){
        final SoapEvent soapEvent = save();
        repository.delete(soapEvent.getId());
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).delete(DIRECTORY + File.separator + soapEvent.getId() + EXTENSION);
    }

    @Test
    public void testCount(){
        final SoapEvent soapEvent = save();
        final Integer count = repository.count();
        Assert.assertEquals(new Integer(1), count);
    }

    private SoapEvent save(){
        final SoapEvent soapEvent = SoapEventTestBuilder.builder().build();
        repository.save(soapEvent);
        return soapEvent;
    }

}
