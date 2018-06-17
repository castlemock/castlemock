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
import com.castlemock.core.mock.rest.model.project.domain.RestApplication;
import com.castlemock.core.mock.rest.service.project.input.CreateRestApplicationInput;
import com.castlemock.core.mock.rest.service.project.output.CreateRestApplicationOutput;
import com.castlemock.web.mock.rest.model.project.RestApplicationGenerator;
import com.castlemock.web.mock.rest.repository.project.RestApplicationRepository;
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
public class CreateRestApplicationServiceTest {

    @Mock
    private RestApplicationRepository applicationRepository;

    @InjectMocks
    private CreateRestApplicationService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final String projectId = "ProjectId";
        final RestApplication application = RestApplicationGenerator.generateRestApplication();
        Mockito.when(applicationRepository.save(Mockito.any(RestApplication.class))).thenReturn(application);

        final CreateRestApplicationInput input = new CreateRestApplicationInput(projectId, application);
        final ServiceTask<CreateRestApplicationInput> serviceTask = new ServiceTask<CreateRestApplicationInput>(input);
        final ServiceResult<CreateRestApplicationOutput> serviceResult = service.process(serviceTask);

        Assert.assertNotNull(serviceResult.getOutput());
        Assert.assertEquals(application, serviceResult.getOutput().getSavedRestApplication());
        Mockito.verify(applicationRepository, Mockito.times(1)).save(application);
    }

}
