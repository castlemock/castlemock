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
import com.castlemock.model.mock.rest.domain.RestApplication;
import com.castlemock.model.mock.rest.domain.RestApplicationTestBuilder;
import com.castlemock.repository.rest.project.RestApplicationRepository;
import com.castlemock.service.mock.rest.project.input.CreateRestApplicationInput;
import com.castlemock.service.mock.rest.project.output.CreateRestApplicationOutput;
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
public class CreateRestApplicationServiceTest {

    @Mock
    private RestApplicationRepository applicationRepository;

    @InjectMocks
    private CreateRestApplicationService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final String projectId = "ProjectId";
        final RestApplication application = RestApplicationTestBuilder.builder().build();
        Mockito.when(applicationRepository.save(any(RestApplication.class))).thenReturn(application);

        final CreateRestApplicationInput input = CreateRestApplicationInput.builder()
                .projectId(projectId)
                .name(application.getName())
                .build();
        final ServiceTask<CreateRestApplicationInput> serviceTask = ServiceTask.of(input, "user");
        final ServiceResult<CreateRestApplicationOutput> serviceResult = service.process(serviceTask);

        Assert.assertNotNull(serviceResult.getOutput());
        Assert.assertEquals(application, serviceResult.getOutput().getSavedRestApplication());
        Mockito.verify(applicationRepository, Mockito.times(1)).save(any());
    }

}
