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

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponse;
import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponseTestBuilder;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperationTestBuilder;
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.model.project.domain.SoapPortTestBuilder;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.core.mock.soap.model.project.domain.SoapProjectTestBuilder;
import com.castlemock.core.mock.soap.service.project.input.DeleteSoapPortsInput;
import com.castlemock.core.mock.soap.service.project.output.DeleteSoapPortsOutput;
import com.castlemock.repository.soap.project.SoapMockResponseRepository;
import com.castlemock.repository.soap.project.SoapOperationRepository;
import com.castlemock.repository.soap.project.SoapPortRepository;
import com.google.common.collect.ImmutableList;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Arrays;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class DeleteSoapPortsServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private SoapPortRepository portRepository;

    @Mock
    private SoapOperationRepository operationRepository;

    @Mock
    private SoapMockResponseRepository mockResponseRepository;

    @InjectMocks
    private DeleteSoapPortsService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapProject soapProject = SoapProjectTestBuilder.builder().build();
        final SoapPort soapPort = SoapPortTestBuilder.builder().build();
        final SoapOperation soapOperation = SoapOperationTestBuilder.builder().build();
        final SoapMockResponse soapMockResponse = SoapMockResponseTestBuilder.builder().build();

        Mockito.when(operationRepository.findWithPortId(soapPort.getId())).thenReturn(Arrays.asList(soapOperation));
        Mockito.when(mockResponseRepository.findWithOperationId(soapOperation.getId())).thenReturn(Arrays.asList(soapMockResponse));

        final DeleteSoapPortsInput input = DeleteSoapPortsInput.builder()
                .projectId(soapProject.getId())
                .ports(ImmutableList.of(soapPort))
                .build();
        final ServiceTask<DeleteSoapPortsInput> serviceTask = new ServiceTask<DeleteSoapPortsInput>(input);
        final ServiceResult<DeleteSoapPortsOutput> serviceResult = service.process(serviceTask);

        Mockito.verify(portRepository, Mockito.times(1)).delete(soapPort.getId());
        Mockito.verify(operationRepository, Mockito.times(1)).delete(soapOperation.getId());
        Mockito.verify(mockResponseRepository, Mockito.times(1)).delete(soapMockResponse.getId());
    }
}
