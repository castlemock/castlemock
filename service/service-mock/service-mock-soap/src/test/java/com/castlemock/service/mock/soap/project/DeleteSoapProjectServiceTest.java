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
import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.model.mock.soap.domain.SoapMockResponseTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapOperationTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapPort;
import com.castlemock.model.mock.soap.domain.SoapPortTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapProject;
import com.castlemock.model.mock.soap.domain.SoapProjectTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapResource;
import com.castlemock.model.mock.soap.domain.SoapResourceTestBuilder;
import com.castlemock.repository.soap.project.SoapMockResponseRepository;
import com.castlemock.repository.soap.project.SoapOperationRepository;
import com.castlemock.repository.soap.project.SoapPortRepository;
import com.castlemock.repository.soap.project.SoapProjectRepository;
import com.castlemock.repository.soap.project.SoapResourceRepository;
import com.castlemock.service.mock.soap.project.input.DeleteSoapProjectInput;
import com.castlemock.service.mock.soap.project.output.DeleteSoapProjectOutput;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapProject soapProject = SoapProjectTestBuilder.builder().build();
        final SoapPort soapPort = SoapPortTestBuilder.builder().build();
        final SoapOperation soapOperation = SoapOperationTestBuilder.builder().build();
        final SoapResource soapResource = SoapResourceTestBuilder.builder().build();
        final SoapMockResponse soapMockResponse = SoapMockResponseTestBuilder.builder().build();

        Mockito.when(repository.delete(soapProject.getId())).thenReturn(soapProject);
        Mockito.when(portRepository.findWithProjectId(soapProject.getId())).thenReturn(List.of(soapPort));
        Mockito.when(resourceRepository.findWithProjectId(soapProject.getId())).thenReturn(Collections.singletonList(soapResource));
        Mockito.when(operationRepository.findWithPortId(soapPort.getId())).thenReturn(List.of(soapOperation));
        Mockito.when(mockResponseRepository.findWithOperationId(soapOperation.getId())).thenReturn(List.of(soapMockResponse));

        final DeleteSoapProjectInput input = DeleteSoapProjectInput.builder()
                .projectId(soapProject.getId())
                .build();
        final ServiceTask<DeleteSoapProjectInput> serviceTask = ServiceTask.of(input, "user");
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
