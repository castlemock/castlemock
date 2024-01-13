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
import com.castlemock.repository.soap.project.SoapMockResponseRepository;
import com.castlemock.repository.soap.project.SoapOperationRepository;
import com.castlemock.repository.soap.project.SoapPortRepository;
import com.castlemock.service.mock.soap.project.input.DeleteSoapPortInput;
import com.castlemock.service.mock.soap.project.output.DeleteSoapPortOutput;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class DeleteSoapPortServiceTest {


    @Mock
    private SoapPortRepository portRepository;

    @Mock
    private SoapOperationRepository operationRepository;

    @Mock
    private SoapMockResponseRepository mockResponseRepository;

    @InjectMocks
    private DeleteSoapPortService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapProject soapProject = SoapProjectTestBuilder.builder().build();
        final SoapPort soapPort = SoapPortTestBuilder.builder().build();
        final SoapOperation soapOperation = SoapOperationTestBuilder.builder().build();
        final SoapMockResponse soapMockResponse = SoapMockResponseTestBuilder.builder().build();

        Mockito.when(operationRepository.findWithPortId(soapPort.getId())).thenReturn(List.of(soapOperation));
        Mockito.when(mockResponseRepository.findWithOperationId(soapOperation.getId())).thenReturn(List.of(soapMockResponse));
        Mockito.when(portRepository.delete(soapPort.getId())).thenReturn(soapPort);

        final DeleteSoapPortInput input = DeleteSoapPortInput.builder()
                .projectId(soapProject.getId())
                .portId(soapPort.getId())
                .build();
        final ServiceTask<DeleteSoapPortInput> serviceTask = ServiceTask.of(input, "user");
        final ServiceResult<DeleteSoapPortOutput> serviceResult = service.process(serviceTask);

        assertNotNull(serviceResult);
        assertNotNull(serviceResult.getOutput());
        assertEquals(soapPort, serviceResult.getOutput().getPort());

        Mockito.verify(portRepository, Mockito.times(1)).delete(soapPort.getId());
        Mockito.verify(operationRepository, Mockito.times(1)).delete(soapOperation.getId());
        Mockito.verify(mockResponseRepository, Mockito.times(1)).delete(soapMockResponse.getId());
    }
}
