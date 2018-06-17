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

package com.castlemock.web.mock.rest.service.project;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponse;
import com.castlemock.core.mock.rest.service.project.input.CreateRestMockResponseInput;
import com.castlemock.core.mock.rest.service.project.output.CreateRestMockResponseOutput;
import com.castlemock.web.mock.rest.model.project.RestMockResponseGenerator;
import com.castlemock.web.mock.rest.repository.project.RestMockResponseRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateRestMockResponseServiceTest {

    @Mock
    private RestMockResponseRepository mockResponseRepository;

    @InjectMocks
    private CreateRestMockResponseService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final String projectId = "ProjectId";
        final String applicationId = "ApplicationId";
        final String resourceId = "ResourceId";
        final String methodId = "MethodId";
        final RestMockResponse mockResponse = RestMockResponseGenerator.generateRestMockResponse();
        Mockito.when(mockResponseRepository.save(Mockito.any(RestMockResponse.class))).thenReturn(mockResponse);

        final CreateRestMockResponseInput input =
                new CreateRestMockResponseInput(projectId, applicationId, resourceId, methodId, mockResponse);
        final ServiceTask<CreateRestMockResponseInput> serviceTask = new ServiceTask<CreateRestMockResponseInput>(input);
        final ServiceResult<CreateRestMockResponseOutput> serviceResult = service.process(serviceTask);

        Assert.assertNotNull(serviceResult.getOutput());
        Assert.assertEquals(mockResponse, serviceResult.getOutput().getRestMockResponse());
        Mockito.verify(mockResponseRepository, Mockito.times(1)).save(mockResponse);
    }

}
