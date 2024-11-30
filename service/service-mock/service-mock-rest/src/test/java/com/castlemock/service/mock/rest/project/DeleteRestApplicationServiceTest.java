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

package com.castlemock.service.mock.rest.project;

import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.rest.domain.*;
import com.castlemock.repository.rest.project.RestApplicationRepository;
import com.castlemock.repository.rest.project.RestMethodRepository;
import com.castlemock.repository.rest.project.RestMockResponseRepository;
import com.castlemock.repository.rest.project.RestResourceRepository;
import com.castlemock.service.mock.rest.project.input.DeleteRestApplicationInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class DeleteRestApplicationServiceTest {

    @Mock
    private RestApplicationRepository applicationRepository;

    @Mock
    private RestResourceRepository resourceRepository;

    @Mock
    private RestMethodRepository methodRepository;

    @Mock
    private RestMockResponseRepository mockResponseRepository;

    @InjectMocks
    private DeleteRestApplicationService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final String projectId = "ProjectId";
        final String applicationId = "ApplicationId";
        final RestApplication application = RestApplicationTestBuilder.builder().build();
        final RestResource resource = RestResourceTestBuilder.builder().build();
        final RestMethod method = RestMethodTestBuilder.builder().build();
        final RestMockResponse mockResponse = RestMockResponseTestBuilder.builder().build();

        Mockito.when(applicationRepository.delete(Mockito.any())).thenReturn(Optional.of(application));
        Mockito.when(resourceRepository.findWithApplicationId(applicationId)).thenReturn(List.of(resource));
        Mockito.when(methodRepository.findWithResourceId(resource.getId())).thenReturn(List.of(method));
        Mockito.when(mockResponseRepository.findWithMethodId(method.getId())).thenReturn(List.of(mockResponse));

        final DeleteRestApplicationInput input = DeleteRestApplicationInput.builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .build();
        final ServiceTask<DeleteRestApplicationInput> serviceTask = ServiceTask.of(input, "user");
        service.process(serviceTask);

        Mockito.verify(applicationRepository, Mockito.times(1)).delete(applicationId);
        Mockito.verify(resourceRepository, Mockito.times(1)).delete(resource.getId());
        Mockito.verify(methodRepository, Mockito.times(1)).delete(method.getId());
        Mockito.verify(mockResponseRepository, Mockito.times(1)).delete(mockResponse.getId());

        Mockito.verify(resourceRepository, Mockito.times(1)).findWithApplicationId(applicationId);
        Mockito.verify(methodRepository, Mockito.times(1)).findWithResourceId(resource.getId());
        Mockito.verify(mockResponseRepository, Mockito.times(1)).findWithMethodId(method.getId());
    }
}
