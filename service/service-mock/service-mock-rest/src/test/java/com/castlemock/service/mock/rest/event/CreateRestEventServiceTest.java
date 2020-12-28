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

package com.castlemock.service.mock.rest.event;

import com.castlemock.model.core.model.ServiceResult;
import com.castlemock.model.core.model.ServiceTask;
import com.castlemock.model.mock.rest.domain.RestEvent;
import com.castlemock.model.mock.rest.domain.RestEventTestBuilder;
import com.castlemock.service.mock.rest.event.input.CreateRestEventInput;
import com.castlemock.service.mock.rest.event.output.CreateRestEventOutput;
import com.castlemock.repository.rest.event.RestEventRepository;
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

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateRestEventServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private RestEventRepository repository;

    @InjectMocks
    private CreateRestEventService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(service, "restMaxEventCount", 5);
    }

    @Test
    public void testProcess(){
        final RestEvent restEvent = RestEventTestBuilder.builder().build();
        Mockito.when(repository.save(Mockito.any(RestEvent.class))).thenReturn(RestEventTestBuilder.builder().build());

        final CreateRestEventInput input = CreateRestEventInput.builder().restEvent(restEvent).build();
        final ServiceTask<CreateRestEventInput> serviceTask = new ServiceTask<CreateRestEventInput>(input);
        final ServiceResult<CreateRestEventOutput> serviceResult = service.process(serviceTask);
        final CreateRestEventOutput createRestApplicationOutput = serviceResult.getOutput();
        final RestEvent returnedRestEvent = createRestApplicationOutput.getCreatedRestEvent();

        Assert.assertEquals(restEvent.getApplicationId(), returnedRestEvent.getApplicationId());
        Assert.assertEquals(restEvent.getMethodId(), returnedRestEvent.getMethodId());
        Assert.assertEquals(restEvent.getProjectId(), returnedRestEvent.getProjectId());
        Assert.assertEquals(restEvent.getResourceId(), returnedRestEvent.getResourceId());
    }


    @Test
    public void testMaxCountReached(){
        final RestEvent restEvent = RestEventTestBuilder.builder().build();
        Mockito.when(repository.save(Mockito.any(RestEvent.class))).thenReturn(restEvent);
        Mockito.when(repository.count()).thenReturn(6);

        final CreateRestEventInput input = CreateRestEventInput.builder().restEvent(restEvent).build();
        final ServiceTask<CreateRestEventInput> serviceTask = new ServiceTask<CreateRestEventInput>(input);
        final ServiceResult<CreateRestEventOutput> serviceResult = service.process(serviceTask);
        final CreateRestEventOutput output = serviceResult.getOutput();
        final RestEvent returnedSoapEvent = output.getCreatedRestEvent();

        Mockito.verify(repository, Mockito.times(1)).deleteOldestEvent();
        Mockito.verify(repository, Mockito.times(1)).save(restEvent);

        Assert.assertEquals(restEvent.getProjectId(), returnedSoapEvent.getProjectId());
    }

}
