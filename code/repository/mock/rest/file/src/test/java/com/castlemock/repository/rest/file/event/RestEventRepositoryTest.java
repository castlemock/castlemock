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

package com.castlemock.repository.rest.file.event;


import com.castlemock.core.mock.rest.model.event.RestEventGenerator;
import com.castlemock.core.mock.rest.model.event.domain.RestEvent;
import com.castlemock.repository.core.file.FileRepositorySupport;
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
public class RestEventRepositoryTest {

    @Mock
    private FileRepositorySupport fileRepositorySupport;
    @Spy
    private DozerBeanMapper mapper;
    @InjectMocks
    private RestEventFileRepository repository;
    private static final String DIRECTORY = "/directory";
    private static final String EXTENSION = ".extension";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(repository, "restEventFileDirectory", DIRECTORY);
        ReflectionTestUtils.setField(repository, "restEventFileExtension", EXTENSION);
    }

    @Test
    public void testInitialize(){
        List<RestEvent> restEvents = new ArrayList<RestEvent>();
        RestEvent restEvent = RestEventGenerator.generateRestEvent();
        restEvents.add(restEvent);
        Mockito.when(fileRepositorySupport.load(RestEvent.class, DIRECTORY, EXTENSION)).thenReturn(restEvents);
        repository.initialize();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).load(RestEventFileRepository.RestEventFile.class, DIRECTORY, EXTENSION);
    }

    @Test
    public void testFindOne(){
        final RestEvent restEvent = save();
        final RestEvent returnedRestEvent = repository.findOne(restEvent.getId());
        Assert.assertEquals(returnedRestEvent.getResourceId(), restEvent.getResourceId());
        Assert.assertEquals(returnedRestEvent.getApplicationId(), restEvent.getApplicationId());
        Assert.assertEquals(returnedRestEvent.getMethodId(), restEvent.getMethodId());
        Assert.assertEquals(returnedRestEvent.getProjectId(), restEvent.getProjectId());
        Assert.assertEquals(returnedRestEvent.getResourceName(), restEvent.getResourceName());
    }

    @Test
    public void testFindAll(){
        final RestEvent restEvent = save();
        final List<RestEvent> restEvents = repository.findAll();
        Assert.assertEquals(restEvents.size(), 1);
        Assert.assertEquals(restEvents.get(0).getResourceId(), restEvent.getResourceId());
        Assert.assertEquals(restEvents.get(0).getApplicationId(), restEvent.getApplicationId());
        Assert.assertEquals(restEvents.get(0).getMethodId(), restEvent.getMethodId());
        Assert.assertEquals(restEvents.get(0).getProjectId(), restEvent.getProjectId());
        Assert.assertEquals(restEvents.get(0).getResourceName(), restEvent.getResourceName());
    }

    @Test
    public void testSave(){
        final RestEvent restEvent = save();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).save(Mockito.any(RestEventFileRepository.RestEventFile.class), Mockito.anyString());
    }

    @Test
    public void testDelete(){
        final RestEvent restEvent = save();
        repository.delete(restEvent.getId());
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).delete(DIRECTORY + File.separator + restEvent.getId() + EXTENSION);
    }

    @Test
    public void testCount(){
        final RestEvent restEvent = save();
        final Integer count = repository.count();
        Assert.assertEquals(new Integer(1), count);
    }

    private RestEvent save(){
        final RestEvent restEvent = RestEventGenerator.generateRestEvent();
        repository.save(restEvent);
        return restEvent;
    }

}
