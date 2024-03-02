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

import com.castlemock.model.mock.soap.domain.SoapEvent;
import com.castlemock.model.mock.soap.domain.SoapEventTestBuilder;
import com.castlemock.repository.core.file.FileRepositorySupport;
import com.castlemock.repository.soap.file.event.model.SoapEventFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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
    @InjectMocks
    private SoapEventFileRepository repository;
    private static final String DIRECTORY = "/directory";
    private static final String EXTENSION = ".extension";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(repository, "soapEventFileDirectory", DIRECTORY);
        ReflectionTestUtils.setField(repository, "soapEventFileExtension", EXTENSION);
    }

    @Test
    public void testInitialize(){
        List<SoapEvent> soapEvents = new ArrayList<>();
        SoapEvent soapEvent = SoapEventTestBuilder.builder().build();
        soapEvents.add(soapEvent);
        Mockito.when(fileRepositorySupport.load(SoapEvent.class, DIRECTORY, EXTENSION)).thenReturn(soapEvents);
        repository.initialize();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).load(SoapEventFile.class, DIRECTORY, EXTENSION);
    }

    @Test
    public void testFindOne(){
        final SoapEvent soapEvent = save();
        final SoapEvent returnedSoapEvent = repository.findOne(soapEvent.getId()).orElse(null);
        Assertions.assertNotNull(returnedSoapEvent);
        Assertions.assertEquals(returnedSoapEvent.getProjectId(), soapEvent.getProjectId());
        Assertions.assertEquals(returnedSoapEvent.getId(), soapEvent.getId());
        Assertions.assertEquals(returnedSoapEvent.getResourceName(), soapEvent.getResourceName());
        Assertions.assertEquals(returnedSoapEvent.getOperationId(), soapEvent.getOperationId());
        Assertions.assertEquals(returnedSoapEvent.getPortId(), soapEvent.getPortId());
        Assertions.assertEquals(returnedSoapEvent.getResourceName(), soapEvent.getResourceName());
    }

    @Test
    public void testFindAll(){
        final SoapEvent soapEvent = save();
        final List<SoapEvent> soapEvents = repository.findAll();
        Assertions.assertEquals(soapEvents.size(), 1);
        Assertions.assertEquals(soapEvents.getFirst().getProjectId(), soapEvent.getProjectId());
        Assertions.assertEquals(soapEvents.getFirst().getId(), soapEvent.getId());
        Assertions.assertEquals(soapEvents.getFirst().getResourceName(), soapEvent.getResourceName());
        Assertions.assertEquals(soapEvents.getFirst().getOperationId(), soapEvent.getOperationId());
        Assertions.assertEquals(soapEvents.getFirst().getPortId(), soapEvent.getPortId());
        Assertions.assertEquals(soapEvents.getFirst().getResourceName(), soapEvent.getResourceName());
        
    }

    @Test
    public void testSave(){
        save();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).save(Mockito.any(SoapEventFile.class), Mockito.anyString());
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
        Assertions.assertEquals(Integer.valueOf(1), count);
    }

    private SoapEvent save(){
        final SoapEvent soapEvent = SoapEventTestBuilder.builder().build();
        repository.save(soapEvent);
        return soapEvent;
    }

}
