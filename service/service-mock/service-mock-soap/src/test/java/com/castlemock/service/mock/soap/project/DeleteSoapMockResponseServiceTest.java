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

import com.castlemock.model.core.model.ServiceResult;
import com.castlemock.model.core.model.ServiceTask;
import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.model.mock.soap.domain.SoapMockResponseTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapOperationTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapPort;
import com.castlemock.model.mock.soap.domain.SoapPortTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapProject;
import com.castlemock.model.mock.soap.domain.SoapProjectTestBuilder;
import com.castlemock.service.mock.soap.project.input.DeleteSoapMockResponseInput;
import com.castlemock.service.mock.soap.project.output.DeleteSoapMockResponseOutput;
import com.castlemock.repository.soap.project.SoapMockResponseRepository;
import org.dozer.DozerBeanMapper;
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
public class DeleteSoapMockResponseServiceTest {

    @Spy
    private DozerBeanMapper mapper;

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
        final SoapProject soapProject = SoapProjectTestBuilder.builder().build();
        final SoapPort soapPort = SoapPortTestBuilder.builder().build();
        final SoapOperation soapOperation = SoapOperationTestBuilder.builder().build();
        final SoapMockResponse soapMockResponse = SoapMockResponseTestBuilder.builder().build();

        soapProject.getPorts().add(soapPort);
        soapPort.getOperations().add(soapOperation);
        soapOperation.getMockResponses().add(soapMockResponse);

        Mockito.when(mockResponseRepository.delete(Mockito.any())).thenReturn(soapMockResponse);

        final DeleteSoapMockResponseInput input = DeleteSoapMockResponseInput.builder()
                .projectId(soapProject.getId())
                .portId(soapPort.getId())
                .operationId(soapOperation.getId())
                .mockResponseId(soapMockResponse.getId())
                .build();
        final ServiceTask<DeleteSoapMockResponseInput> serviceTask = new ServiceTask<DeleteSoapMockResponseInput>(input);
        final ServiceResult<DeleteSoapMockResponseOutput> serviceResult = service.process(serviceTask);
        serviceResult.getOutput();

        Mockito.verify(mockResponseRepository, Mockito.times(1)).delete(Mockito.anyString());
    }
}
