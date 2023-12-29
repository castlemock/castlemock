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
import com.castlemock.model.mock.rest.domain.RestResource;
import com.castlemock.model.mock.rest.domain.RestResourceTestBuilder;
import com.castlemock.repository.rest.project.RestResourceRepository;
import com.castlemock.service.mock.rest.project.input.CreateRestResourceInput;
import com.castlemock.service.mock.rest.project.output.CreateRestResourceOutput;
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
public class CreateRestResourceServiceTest {

    @Mock
    private RestResourceRepository resourceRepository;

    @InjectMocks
    private CreateRestResourceService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final String projectId = "ProjectId";
        final String applicationId = "ApplicationId";
        final RestResource resource = RestResourceTestBuilder.builder().build();
        Mockito.when(resourceRepository.save(any(RestResource.class))).thenReturn(resource);

        final CreateRestResourceInput input = CreateRestResourceInput.builder()
                .restProjectId(projectId)
                .restApplicationId(applicationId)
                .name(resource.getName())
                .uri(resource.getUri())
                .build();
        final ServiceTask<CreateRestResourceInput> serviceTask = ServiceTask.of(input, "user");
        final ServiceResult<CreateRestResourceOutput> serviceResult = service.process(serviceTask);

        Assert.assertNotNull(serviceResult.getOutput());
        Assert.assertEquals(resource, serviceResult.getOutput().getCreatedRestResource());
        Mockito.verify(resourceRepository, Mockito.times(1)).save(any());
    }

}
