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
import com.castlemock.service.mock.soap.project.input.DeleteSoapMockResponseInput;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class DeleteSoapMockResponseServiceTest {


    @Mock
    private SoapMockResponseRepository mockResponseRepository;

    @InjectMocks
    private DeleteSoapMockResponseService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapMockResponse soapMockResponse = SoapMockResponseTestBuilder.builder().build();
        final SoapOperation soapOperation = SoapOperationTestBuilder.builder()
                .mockResponses(List.of(soapMockResponse))
                .build();
        final SoapPort soapPort = SoapPortTestBuilder.builder()
                .operations(List.of(soapOperation))
                .build();
        final SoapProject soapProject = SoapProjectTestBuilder.builder()
                .ports(List.of(soapPort))
                .build();

        Mockito.when(mockResponseRepository.delete(Mockito.any())).thenReturn(Optional.of(soapMockResponse));

        final DeleteSoapMockResponseInput input = DeleteSoapMockResponseInput.builder()
                .projectId(soapProject.getId())
                .portId(soapPort.getId())
                .operationId(soapOperation.getId())
                .mockResponseId(soapMockResponse.getId())
                .build();
        final ServiceTask<DeleteSoapMockResponseInput> serviceTask = ServiceTask.of(input, "user");
        service.process(serviceTask);

        Mockito.verify(mockResponseRepository, Mockito.times(1)).delete(Mockito.anyString());
    }
}
