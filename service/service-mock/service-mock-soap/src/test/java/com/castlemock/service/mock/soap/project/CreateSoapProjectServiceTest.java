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

package com.castlemock.service.mock.soap.project;

import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.soap.domain.SoapProject;
import com.castlemock.model.mock.soap.domain.SoapProjectTestBuilder;
import com.castlemock.repository.soap.project.SoapProjectRepository;
import com.castlemock.service.mock.soap.project.input.CreateSoapProjectInput;
import com.castlemock.service.mock.soap.project.output.CreateSoapProjectOutput;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateSoapProjectServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private SoapProjectRepository repository;

    @InjectMocks
    private CreateSoapProjectService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapProject soapProject = SoapProjectTestBuilder.builder().build();

        Mockito.when(repository.save(Mockito.any(SoapProject.class))).thenReturn(soapProject);

        final CreateSoapProjectInput input = CreateSoapProjectInput.builder()
                .name("New name")
                .description("New description")
                .build();
        final ServiceTask<CreateSoapProjectInput> serviceTask = ServiceTask.of(input, "user");
        final ServiceResult<CreateSoapProjectOutput> serviceResult = service.process(serviceTask);
        final CreateSoapProjectOutput output = serviceResult.getOutput();
        final SoapProject returnedSoapProject = output.getProject();

        Assert.assertEquals(soapProject.getName(), returnedSoapProject.getName());
        Assert.assertEquals(soapProject.getDescription(), returnedSoapProject.getDescription());

        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(SoapProject.class));
    }
}
