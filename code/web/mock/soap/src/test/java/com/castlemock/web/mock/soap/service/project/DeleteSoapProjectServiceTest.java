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

import com.castlemock.core.mock.soap.model.project.domain.*;
import com.castlemock.core.mock.soap.service.project.input.DeleteSoapPortInput;
import com.castlemock.core.mock.soap.service.project.output.DeleteSoapPortOutput;
import com.castlemock.web.basis.repository.Repository;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.service.project.input.DeleteSoapProjectInput;
import com.castlemock.core.mock.soap.service.project.output.DeleteSoapProjectOutput;
import com.castlemock.web.mock.soap.model.project.*;
import com.castlemock.web.mock.soap.repository.project.*;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.*;

import java.util.Arrays;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class DeleteSoapProjectServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private SoapPortRepository portRepository;

    @Mock
    private SoapProjectRepository repository;

    @Mock
    private SoapOperationRepository operationRepository;

    @Mock
    private SoapMockResponseRepository mockResponseRepository;

    @Mock
    private SoapResourceRepository resourceRepository;

    @InjectMocks
    private DeleteSoapProjectService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapProject soapProject = SoapProjectGenerator.generateSoapProject();
        final SoapPort soapPort = SoapPortGenerator.generateSoapPort();
        final SoapOperation soapOperation = SoapOperationGenerator.generateSoapOperation();
        final SoapResource soapResource = SoapResourceGenerator.generateSoapResource();
        final SoapMockResponse soapMockResponse = SoapMockResponseGenerator.generateSoapMockResponse();

        Mockito.when(portRepository.findWithProjectId(soapProject.getId())).thenReturn(Arrays.asList(soapPort));
        Mockito.when(resourceRepository.findWithProjectId(soapProject.getId())).thenReturn(Arrays.asList(soapResource));
        Mockito.when(operationRepository.findWithPortId(soapPort.getId())).thenReturn(Arrays.asList(soapOperation));
        Mockito.when(mockResponseRepository.findWithOperationId(soapOperation.getId())).thenReturn(Arrays.asList(soapMockResponse));

        final DeleteSoapProjectInput input = new DeleteSoapProjectInput(soapProject.getId());
        final ServiceTask<DeleteSoapProjectInput> serviceTask = new ServiceTask<DeleteSoapProjectInput>(input);
        final ServiceResult<DeleteSoapProjectOutput> serviceResult = service.process(serviceTask);

        Mockito.verify(repository, Mockito.times(1)).delete(soapProject.getId());
        Mockito.verify(portRepository, Mockito.times(1)).delete(soapPort.getId());
        Mockito.verify(resourceRepository, Mockito.times(1)).delete(soapResource.getId());
        Mockito.verify(operationRepository, Mockito.times(1)).delete(soapOperation.getId());
        Mockito.verify(mockResponseRepository, Mockito.times(1)).delete(soapMockResponse.getId());

        Mockito.verify(portRepository, Mockito.times(1)).findWithProjectId(soapProject.getId());
        Mockito.verify(resourceRepository, Mockito.times(1)).findWithProjectId(soapProject.getId());
        Mockito.verify(operationRepository, Mockito.times(1)).findWithPortId(soapPort.getId());
        Mockito.verify(mockResponseRepository, Mockito.times(1)).findWithOperationId(soapOperation.getId());
    }
}
