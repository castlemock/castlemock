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

package com.castlemock.web.mock.rest.model.event.service;

import com.castlemock.core.basis.model.Repository;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.rest.model.event.dto.RestEventDto;
import com.castlemock.core.mock.rest.model.event.service.message.input.CreateRestEventInput;
import com.castlemock.core.mock.rest.model.event.service.message.output.CreateRestEventOutput;
import com.castlemock.web.mock.rest.model.project.RestEventDtoGenerator;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateRestEventServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @InjectMocks
    private CreateRestEventService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(service, "restMaxEventCount", 5);
    }

    @Test
    public void testProcess(){
        final RestEventDto restEventDto = RestEventDtoGenerator.generateRestEventDto();
        Mockito.when(repository.save(Mockito.any(RestEventDto.class))).thenReturn(RestEventDtoGenerator.generateRestEventDto());

        final CreateRestEventInput input = new CreateRestEventInput(restEventDto);
        input.setRestEvent(restEventDto);

        final ServiceTask<CreateRestEventInput> serviceTask = new ServiceTask<CreateRestEventInput>(input);
        final ServiceResult<CreateRestEventOutput> serviceResult = service.process(serviceTask);
        final CreateRestEventOutput createRestApplicationOutput = serviceResult.getOutput();
        final RestEventDto returnedRestEventDto = createRestApplicationOutput.getCreatedRestEvent();

        Assert.assertEquals(restEventDto.getApplicationId(), returnedRestEventDto.getApplicationId());
        Assert.assertEquals(restEventDto.getMethodId(), returnedRestEventDto.getMethodId());
        Assert.assertEquals(restEventDto.getProjectId(), returnedRestEventDto.getProjectId());
        Assert.assertEquals(restEventDto.getResourceId(), returnedRestEventDto.getResourceId());
    }

}
