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
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapOperationStatus;
import com.castlemock.model.mock.soap.domain.SoapOperationTestBuilder;
import com.castlemock.repository.soap.project.SoapOperationRepository;
import com.castlemock.service.mock.soap.project.input.UpdateSoapPortsStatusInput;
import com.castlemock.service.mock.soap.project.output.UpdateSoapPortsStatusOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.1
 */
public class UpdateSoapPortsStatusServiceTest {

    @Mock
    private SoapOperationRepository operationRepository;

    @InjectMocks
    private UpdateSoapPortsStatusService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapOperation soapOperation = SoapOperationTestBuilder.builder().build();
        final String projectId = "SOAP PROJECT";
        final String portId = "SOAP PORT";

        final UpdateSoapPortsStatusInput input = UpdateSoapPortsStatusInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationStatus(SoapOperationStatus.MOCKED)
                .build();
        final ServiceTask<UpdateSoapPortsStatusInput> serviceTask = ServiceTask.of(input, "user");

        Mockito.when(operationRepository.findWithPortId("SOAP PORT")).thenReturn(List.of(soapOperation));
        Mockito.when(operationRepository.update(Mockito.anyString(), Mockito.any(SoapOperation.class))).thenReturn(soapOperation);
        final ServiceResult<UpdateSoapPortsStatusOutput> result = service.process(serviceTask);


        Mockito.verify(operationRepository, Mockito.times(1)).findWithPortId(portId);
        Mockito.verify(operationRepository, Mockito.times(1)).update(soapOperation.getId(), soapOperation);

    }
}
