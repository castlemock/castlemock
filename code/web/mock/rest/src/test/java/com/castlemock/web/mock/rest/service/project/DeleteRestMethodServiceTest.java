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

package com.castlemock.web.mock.rest.service.project;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponse;
import com.castlemock.core.mock.rest.service.project.input.DeleteRestMethodInput;
import com.castlemock.core.mock.rest.service.project.output.DeleteRestMethodOutput;
import com.castlemock.web.mock.rest.model.project.RestMockResponseGenerator;
import com.castlemock.web.mock.rest.repository.project.RestMethodRepository;
import com.castlemock.web.mock.rest.repository.project.RestMockResponseRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class DeleteRestMethodServiceTest {

    @Mock
    private RestMethodRepository methodRepository;

    @Mock
    private RestMockResponseRepository mockResponseRepository;

    @InjectMocks
    private DeleteRestMethodService service;

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

        Mockito.when(mockResponseRepository.findWithMethodId(methodId)).thenReturn(Arrays.asList(mockResponse));

        final DeleteRestMethodInput input = DeleteRestMethodInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .restResourceId(resourceId)
                .restMethodId(methodId)
                .build();
        final ServiceTask<DeleteRestMethodInput> serviceTask = new ServiceTask<DeleteRestMethodInput>(input);
        final ServiceResult<DeleteRestMethodOutput> serviceResult = service.process(serviceTask);

        Mockito.verify(methodRepository, Mockito.times(1)).delete(methodId);
        Mockito.verify(mockResponseRepository, Mockito.times(1)).delete(mockResponse.getId());

        Mockito.verify(mockResponseRepository, Mockito.times(1)).findWithMethodId(methodId);
    }
}
