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
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.core.mock.rest.model.project.domain.RestProjectTestBuilder;
import com.castlemock.core.mock.rest.service.project.input.CreateRestProjectInput;
import com.castlemock.core.mock.rest.service.project.output.CreateRestProjectOutput;
import com.castlemock.repository.rest.project.RestProjectRepository;
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
public class CreateRestProjectServiceTest {

    @Mock
    private RestProjectRepository repository;

    @InjectMocks
    private CreateRestProjectService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final RestProject project = RestProjectTestBuilder.builder().build();
        Mockito.when(repository.save(Mockito.any(RestProject.class))).thenReturn(project);

        final CreateRestProjectInput input = CreateRestProjectInput.builder()
                .restProject(project)
                .build();
        final ServiceTask<CreateRestProjectInput> serviceTask = new ServiceTask<CreateRestProjectInput>(input);
        final ServiceResult<CreateRestProjectOutput> serviceResult = service.process(serviceTask);

        Assert.assertNotNull(serviceResult.getOutput());
        Assert.assertEquals(project, serviceResult.getOutput().getSavedRestProject());
        Mockito.verify(repository, Mockito.times(1)).save(project);
    }
}
