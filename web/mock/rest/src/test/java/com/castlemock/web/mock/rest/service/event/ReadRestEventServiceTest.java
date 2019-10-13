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
import com.castlemock.core.mock.rest.service.event.input.ReadRestEventInput;
import com.castlemock.core.mock.rest.service.event.output.ReadRestEventOutput;
import com.castlemock.repository.Repository;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

/**
 * @author Karl Dahlgren
 * @since 1.4
 */
public class ReadRestEventServiceTest {


    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @InjectMocks
    private ReadRestEventService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final RestEvent restEvent = RestEventTestBuilder.builder().build();
        Mockito.when(repository.findOne(restEvent.getId())).thenReturn(restEvent);

        final ReadRestEventInput input = ReadRestEventInput.builder()
                .restEventId(restEvent.getId())
                .build();
        final ServiceTask<ReadRestEventInput> serviceTask = new ServiceTask<ReadRestEventInput>(input);
        final ServiceResult<ReadRestEventOutput> serviceResult = service.process(serviceTask);
        final ReadRestEventOutput output = serviceResult.getOutput();
        final RestEvent returnedRestEvent = output.getRestEvent();

        Assert.assertEquals(restEvent.getId(), returnedRestEvent.getId());
        Assert.assertEquals(restEvent.getResourceId(), returnedRestEvent.getResourceId());
        Assert.assertEquals(restEvent.getMethodId(), returnedRestEvent.getMethodId());
        Assert.assertEquals(restEvent.getProjectId(), returnedRestEvent.getProjectId());
        Assert.assertEquals(restEvent.getApplicationId(), returnedRestEvent.getApplicationId());
    }
}
