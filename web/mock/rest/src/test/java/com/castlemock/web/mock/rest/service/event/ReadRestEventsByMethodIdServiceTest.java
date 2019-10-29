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

package com.castlemock.web.mock.rest.service.event;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.rest.model.event.domain.RestEvent;
import com.castlemock.core.mock.rest.model.event.domain.RestEventTestBuilder;
import com.castlemock.core.mock.rest.service.event.input.ReadRestEventWithMethodIdInput;
import com.castlemock.core.mock.rest.service.event.output.ReadRestEventWithMethodIdOutput;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.4
 */
public class ReadRestEventsByMethodIdServiceTest {


    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private RestEventRepository repository;

    @InjectMocks
    private ReadRestEventWithMethodIdService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final List<RestEvent> restEvents = new ArrayList<RestEvent>();
        for(int index = 0; index < 2; index++){
            final RestEvent restEvent = RestEventTestBuilder.builder().build();
            restEvents.add(restEvent);
        }

        restEvents.get(0).setMethodId("OperationId");
        restEvents.get(1).setMethodId("OperationId");

        Mockito.when(repository.findEventsByMethodId(Mockito.anyString())).thenReturn(restEvents);

        final ReadRestEventWithMethodIdInput input = ReadRestEventWithMethodIdInput.builder()
                .restMethodId("OperationId")
                .build();
        final ServiceTask<ReadRestEventWithMethodIdInput> serviceTask = new ServiceTask<ReadRestEventWithMethodIdInput>(input);
        final ServiceResult<ReadRestEventWithMethodIdOutput> serviceResult = service.process(serviceTask);
        final ReadRestEventWithMethodIdOutput output = serviceResult.getOutput();


        Assert.assertEquals(2, output.getRestEvents().size());

        for(int index = 0; index < 2; index++){
            final RestEvent restEvent = restEvents.get(index);
            final RestEvent returnedRestEvent = output.getRestEvents().get(index);

            Assert.assertEquals(restEvent.getId(), returnedRestEvent.getId());
            Assert.assertEquals(restEvent.getResourceId(), returnedRestEvent.getResourceId());
            Assert.assertEquals(restEvent.getMethodId(), returnedRestEvent.getMethodId());
            Assert.assertEquals(restEvent.getProjectId(), returnedRestEvent.getProjectId());
            Assert.assertEquals(restEvent.getApplicationId(), returnedRestEvent.getApplicationId());
        }
    }
}
