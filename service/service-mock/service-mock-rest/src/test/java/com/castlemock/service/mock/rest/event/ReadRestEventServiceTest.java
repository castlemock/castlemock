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

package com.castlemock.service.mock.rest.event;

import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.rest.domain.RestEvent;
import com.castlemock.model.mock.rest.domain.RestEventTestBuilder;
import com.castlemock.repository.rest.event.RestEventRepository;
import com.castlemock.service.mock.rest.event.input.ReadRestEventInput;
import com.castlemock.service.mock.rest.event.output.ReadRestEventOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.4
 */
public class ReadRestEventServiceTest {

    @Mock
    private RestEventRepository repository;

    @InjectMocks
    private ReadRestEventService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final RestEvent restEvent = RestEventTestBuilder.builder().build();
        Mockito.when(repository.findOne(restEvent.getId())).thenReturn(Optional.of(restEvent));

        final ReadRestEventInput input = ReadRestEventInput.builder()
                .restEventId(restEvent.getId())
                .build();
        final ServiceTask<ReadRestEventInput> serviceTask = ServiceTask.of(input, "user");
        final ServiceResult<ReadRestEventOutput> serviceResult = service.process(serviceTask);
        final ReadRestEventOutput output = serviceResult.getOutput();
        final RestEvent returnedRestEvent = output.getRestEvent()
                .orElse(null);

        Assertions.assertNotNull(returnedRestEvent);
        Assertions.assertEquals(restEvent.getId(), returnedRestEvent.getId());
        Assertions.assertEquals(restEvent.getResourceId(), returnedRestEvent.getResourceId());
        Assertions.assertEquals(restEvent.getMethodId(), returnedRestEvent.getMethodId());
        Assertions.assertEquals(restEvent.getProjectId(), returnedRestEvent.getProjectId());
        Assertions.assertEquals(restEvent.getApplicationId(), returnedRestEvent.getApplicationId());
    }
}
