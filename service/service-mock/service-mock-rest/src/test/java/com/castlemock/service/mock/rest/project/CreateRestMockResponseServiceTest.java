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

package com.castlemock.service.mock.rest.project;

import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.model.mock.rest.domain.RestMockResponseTestBuilder;
import com.castlemock.service.mock.rest.project.input.CreateRestMockResponseInput;
import com.castlemock.service.mock.rest.project.output.CreateRestMockResponseOutput;
import com.castlemock.repository.rest.project.RestMockResponseRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;


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
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final String projectId = "ProjectId";
        final String applicationId = "ApplicationId";
        final String resourceId = "ResourceId";
        final String methodId = "MethodId";
        final RestMockResponse mockResponse = RestMockResponseTestBuilder.builder().build();
        Mockito.when(mockResponseRepository.save(any(RestMockResponse.class))).thenReturn(mockResponse);

        final CreateRestMockResponseInput input = CreateRestMockResponseInput.builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodId(methodId)
                .name(mockResponse.getName())
                .body(mockResponse.getBody())
                .status(mockResponse.getStatus())
                .contentEncodings(mockResponse.getContentEncodings())
                .headerQueries(mockResponse.getHeaderQueries())
                .httpHeaders(mockResponse.getHttpHeaders())
                .httpStatusCode(mockResponse.getHttpStatusCode())
                .jsonPathExpressions(mockResponse.getJsonPathExpressions())
                .parameterQueries(mockResponse.getParameterQueries())
                .usingExpressions(mockResponse.isUsingExpressions())
                .xpathExpressions(mockResponse.getXpathExpressions())
                .build();
        final ServiceTask<CreateRestMockResponseInput> serviceTask = new ServiceTask<CreateRestMockResponseInput>(input);
        final ServiceResult<CreateRestMockResponseOutput> serviceResult = service.process(serviceTask);

        Assert.assertNotNull(serviceResult.getOutput());
        Assert.assertEquals(mockResponse, serviceResult.getOutput().getRestMockResponse());
        Mockito.verify(mockResponseRepository, Mockito.times(1)).save(any());
    }

}
