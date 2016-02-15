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

package com.fortmocks.web.mock.rest.model.event.service;

import com.fortmocks.core.basis.model.Repository;
import com.fortmocks.core.basis.model.ServiceResult;
import com.fortmocks.core.basis.model.ServiceTask;
import com.fortmocks.core.mock.rest.model.event.domain.RestEvent;
import com.fortmocks.core.mock.rest.model.event.dto.RestEventDto;
import com.fortmocks.core.mock.rest.model.event.service.message.input.ReadAllRestEventInput;
import com.fortmocks.core.mock.rest.model.event.service.message.output.ReadAllRestEventOutput;
import com.fortmocks.web.mock.rest.model.project.RestEventDtoGenerator;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.4
 */
public class ReadAllRestEventServiceTest {


    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @InjectMocks
    private ReadAllRestEventService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final List<RestEvent> restEvents = new ArrayList<RestEvent>();
        for(int index = 0; index < 3; index++){
            final RestEvent restEvent = RestEventDtoGenerator.generateRestEvent();
            restEvents.add(restEvent);
        }

        Mockito.when(repository.findAll()).thenReturn(restEvents);

        final ReadAllRestEventInput input = new ReadAllRestEventInput();
        final ServiceTask<ReadAllRestEventInput> serviceTask = new ServiceTask<ReadAllRestEventInput>(input);
        final ServiceResult<ReadAllRestEventOutput> serviceResult = service.process(serviceTask);
        final ReadAllRestEventOutput output = serviceResult.getOutput();

        Assert.assertEquals(restEvents.size(), output.getRestEvents().size());

        for(int index = 0; index < 3; index++){
            final RestEvent restEvent = restEvents.get(index);
            final RestEventDto returnedRestEvent = output.getRestEvents().get(index);

            Assert.assertEquals(restEvent.getId(), returnedRestEvent.getId());
            Assert.assertEquals(restEvent.getResourceId(), returnedRestEvent.getResourceId());
            Assert.assertEquals(restEvent.getMethodId(), returnedRestEvent.getMethodId());
            Assert.assertEquals(restEvent.getProjectId(), returnedRestEvent.getProjectId());
            Assert.assertEquals(restEvent.getApplicationId(), returnedRestEvent.getApplicationId());
        }
    }
}
