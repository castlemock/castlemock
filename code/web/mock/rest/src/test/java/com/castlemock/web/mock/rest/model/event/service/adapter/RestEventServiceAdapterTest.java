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

package com.castlemock.web.mock.rest.model.event.service.adapter;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.basis.model.TypeIdentifier;
import com.castlemock.core.basis.model.event.dto.EventDto;
import com.castlemock.core.mock.rest.model.event.dto.RestEventDto;
import com.castlemock.core.mock.rest.model.event.service.message.input.ReadAllRestEventInput;
import com.castlemock.core.mock.rest.model.event.service.message.input.ReadRestEventInput;
import com.castlemock.core.mock.rest.model.event.service.message.output.ReadAllRestEventOutput;
import com.castlemock.core.mock.rest.model.event.service.message.output.ReadRestEventOutput;
import com.castlemock.web.mock.rest.model.RestTypeIdentifier;
import com.castlemock.web.mock.rest.model.project.RestEventDtoGenerator;
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
        final RestEventDto restEventDto = RestEventDtoGenerator.generateRestEventDto();
        serviceAdapter.create(restEventDto);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testDelete(){
        final RestEventDto restEventDto = RestEventDtoGenerator.generateRestEventDto();
        serviceAdapter.delete(restEventDto.getProjectId());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUpdate(){
        final RestEventDto restEventDto = RestEventDtoGenerator.generateRestEventDto();
        serviceAdapter.update(restEventDto.getProjectId(), restEventDto);
    }

    @Test
    public void testReadAll(){
        final List<RestEventDto> restEventDtos = new ArrayList<RestEventDto>();
        for(int index = 0; index < 3; index++){
            final RestEventDto restEventDto = RestEventDtoGenerator.generateRestEventDto();
            restEventDtos.add(restEventDto);

        }

        final ReadAllRestEventOutput output = new ReadAllRestEventOutput(restEventDtos);
        Mockito.when(serviceProcessor.process(Mockito.any(ReadAllRestEventInput.class))).thenReturn(output);

        final List<RestEventDto> returnedRestEventDtos = serviceAdapter.readAll();

        for(int index = 0; index < 3; index++){
            final RestEventDto restEvent = restEventDtos.get(index);
            final RestEventDto returnedRestEvent = returnedRestEventDtos.get(index);

            Assert.assertEquals(restEvent.getId(), returnedRestEvent.getId());
            Assert.assertEquals(restEvent.getResourceId(), returnedRestEvent.getResourceId());
            Assert.assertEquals(restEvent.getMethodId(), returnedRestEvent.getMethodId());
            Assert.assertEquals(restEvent.getProjectId(), returnedRestEvent.getProjectId());
            Assert.assertEquals(restEvent.getApplicationId(), returnedRestEvent.getApplicationId());
        }
    }

    @Test
    public void testRead(){
        final RestEventDto restEvent = RestEventDtoGenerator.generateRestEventDto();
        final ReadRestEventOutput output = new ReadRestEventOutput(restEvent);
        Mockito.when(serviceProcessor.process(Mockito.any(ReadRestEventInput.class))).thenReturn(output);

        final RestEventDto returnedRestEvent = serviceAdapter.read(restEvent.getId());

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
        EventDto eventDto = RestEventDtoGenerator.generateRestEventDto();
        RestEventDto returnedRestEventDto = serviceAdapter.convertType(eventDto);
        Assert.assertEquals(eventDto.getId(), returnedRestEventDto.getId());
        Assert.assertEquals(eventDto.getEndDate(), returnedRestEventDto.getEndDate());
        Assert.assertEquals(eventDto.getResourceLink(), returnedRestEventDto.getResourceLink());
        Assert.assertEquals(eventDto.getStartDate(), returnedRestEventDto.getStartDate());
        Assert.assertEquals(eventDto.getResourceName(), returnedRestEventDto.getResourceName());
    }

    @Test
    public void testGenerateResourceLink(){
        final RestEventDto restEventDto = RestEventDtoGenerator.generateRestEventDto();
        final String generatedResourceLink = serviceAdapter.generateResourceLink(restEventDto);
        Assert.assertEquals("/web/rest/project/" + restEventDto.getProjectId() + "/application/" + restEventDto.getApplicationId() + "/resource/" + restEventDto.getResourceId() + "/method/" + restEventDto.getMethodId(), generatedResourceLink);
    }
}
