/*
 * Copyright 2016 Karl Dahlgren
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

package com.castlemock.web.mock.rest.service.event.adapter;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.basis.model.TypeIdentifier;
import com.castlemock.core.basis.model.event.domain.Event;
import com.castlemock.core.mock.rest.model.event.domain.RestEvent;
import com.castlemock.core.mock.rest.service.event.input.ClearAllRestEventInput;
import com.castlemock.core.mock.rest.service.event.input.ReadAllRestEventInput;
import com.castlemock.core.mock.rest.service.event.input.ReadRestEventInput;
import com.castlemock.core.mock.rest.service.event.output.ReadAllRestEventOutput;
import com.castlemock.core.mock.rest.service.event.output.ReadRestEventOutput;
import com.castlemock.web.mock.rest.model.RestTypeIdentifier;
import com.castlemock.web.mock.rest.model.project.RestEventGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dalhgren
 * @since 1.4
 */
public class RestEventServiceAdapterTest {

    @Mock
    private ServiceProcessor serviceProcessor;

    @InjectMocks
    private RestEventServiceAdapter serviceAdapter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCreate(){
        final RestEvent restEvent = RestEventGenerator.generateRestEvent();
        serviceAdapter.create(restEvent);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testDelete(){
        final RestEvent restEvent = RestEventGenerator.generateRestEvent();
        serviceAdapter.delete(restEvent.getProjectId());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUpdate(){
        final RestEvent restEvent = RestEventGenerator.generateRestEvent();
        serviceAdapter.update(restEvent.getProjectId(), restEvent);
    }

    @Test
    public void testReadAll(){
        final List<RestEvent> restEvents = new ArrayList<RestEvent>();
        for(int index = 0; index < 3; index++){
            final RestEvent restEvent = RestEventGenerator.generateRestEvent();
            restEvents.add(restEvent);

        }

        final ReadAllRestEventOutput output = ReadAllRestEventOutput.builder().restEvents(restEvents).build();
        Mockito.when(serviceProcessor.process(Mockito.any(ReadAllRestEventInput.class))).thenReturn(output);

        final List<RestEvent> returnedRestEvents = serviceAdapter.readAll();

        for(int index = 0; index < 3; index++){
            final RestEvent restEvent = restEvents.get(index);
            final RestEvent returnedRestEvent = returnedRestEvents.get(index);

            Assert.assertEquals(restEvent.getId(), returnedRestEvent.getId());
            Assert.assertEquals(restEvent.getResourceId(), returnedRestEvent.getResourceId());
            Assert.assertEquals(restEvent.getMethodId(), returnedRestEvent.getMethodId());
            Assert.assertEquals(restEvent.getProjectId(), returnedRestEvent.getProjectId());
            Assert.assertEquals(restEvent.getApplicationId(), returnedRestEvent.getApplicationId());
        }
    }

    @Test
    public void testRead(){
        final RestEvent restEvent = RestEventGenerator.generateRestEvent();
        final ReadRestEventOutput output = ReadRestEventOutput.builder().restEvent(restEvent).build();
        Mockito.when(serviceProcessor.process(Mockito.any(ReadRestEventInput.class))).thenReturn(output);

        final RestEvent returnedRestEvent = serviceAdapter.read(restEvent.getId());

        Assert.assertEquals(restEvent.getId(), returnedRestEvent.getId());
        Assert.assertEquals(restEvent.getResourceId(), returnedRestEvent.getResourceId());
        Assert.assertEquals(restEvent.getMethodId(), returnedRestEvent.getMethodId());
        Assert.assertEquals(restEvent.getProjectId(), returnedRestEvent.getProjectId());
        Assert.assertEquals(restEvent.getApplicationId(), returnedRestEvent.getApplicationId());
    }

    @Test
    public void testGetTypeIdentifier(){
        final RestTypeIdentifier restTypeIdentifier = new RestTypeIdentifier();
        final TypeIdentifier returnedRestTypeIdentifier = serviceAdapter.getTypeIdentifier();

        Assert.assertEquals(restTypeIdentifier.getType(), returnedRestTypeIdentifier.getType());
        Assert.assertEquals(restTypeIdentifier.getTypeUrl(), returnedRestTypeIdentifier.getTypeUrl());
    }

    @Test
    public void testConvertType(){
        Event event = RestEventGenerator.generateRestEvent();
        RestEvent returnedRestEvent = serviceAdapter.convertType(event);
        Assert.assertEquals(event.getId(), returnedRestEvent.getId());
        Assert.assertEquals(event.getEndDate(), returnedRestEvent.getEndDate());
        Assert.assertEquals(event.getResourceLink(), returnedRestEvent.getResourceLink());
        Assert.assertEquals(event.getStartDate(), returnedRestEvent.getStartDate());
        Assert.assertEquals(event.getResourceName(), returnedRestEvent.getResourceName());
    }

    @Test
    public void testGenerateResourceLink(){
        final RestEvent restEvent = RestEventGenerator.generateRestEvent();
        final String generatedResourceLink = serviceAdapter.generateResourceLink(restEvent);
        Assert.assertEquals("/web/rest/project/" + restEvent.getProjectId() + "/application/" + restEvent.getApplicationId() + "/resource/" + restEvent.getResourceId() + "/method/" + restEvent.getMethodId(), generatedResourceLink);
    }

    @Test
    public void testClearAll(){
        serviceAdapter.clearAll();
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(ClearAllRestEventInput.class));
    }
}
