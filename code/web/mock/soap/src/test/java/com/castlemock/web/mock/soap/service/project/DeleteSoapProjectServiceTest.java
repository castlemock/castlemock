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

package com.castlemock.web.mock.soap.service.project;

import com.castlemock.web.basis.repository.Repository;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.core.mock.soap.service.project.input.DeleteSoapProjectInput;
import com.castlemock.core.mock.soap.service.project.output.DeleteSoapProjectOutput;
import com.castlemock.web.mock.soap.model.project.SoapProjectGenerator;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.*;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class DeleteSoapProjectServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @InjectMocks
    private DeleteSoapProjectService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Ignore
    public void testProcess(){
        final SoapProject soapProject = SoapProjectGenerator.generateSoapProject();

        Mockito.when(repository.findOne(soapProject.getId())).thenReturn(soapProject);

        final DeleteSoapProjectInput input = new DeleteSoapProjectInput(soapProject.getId());
        final ServiceTask<DeleteSoapProjectInput> serviceTask = new ServiceTask<DeleteSoapProjectInput>(input);
        final ServiceResult<DeleteSoapProjectOutput> serviceResult = service.process(serviceTask);
        serviceResult.getOutput();

        Mockito.verify(repository, Mockito.times(1)).delete(soapProject.getId());
    }
}
