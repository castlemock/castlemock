/*
 * Copyright 2018 Karl Dahlgren
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
import com.castlemock.model.mock.soap.domain.SoapOperationTestBuilder;
import com.castlemock.repository.soap.project.SoapOperationRepository;
import com.castlemock.service.mock.soap.project.input.UpdateSoapOperationInput;
import com.castlemock.service.mock.soap.project.output.UpdateSoapOperationOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

/**
 * @author Karl Dahlgren
 */
public class UpdateSoapOperationServiceTest {


    @Mock
    private SoapOperationRepository operationRepository;

    @InjectMocks
    private UpdateSoapOperationService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final String projectId = "ProjectId";
        final String portId = "PortId";
        final SoapOperation operation = SoapOperationTestBuilder.builder().build();
        final UpdateSoapOperationInput input = UpdateSoapOperationInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operation.getId())
                .status(operation.getStatus())
                .responseStrategy(operation.getResponseStrategy())
                .simulateNetworkDelay(operation.getSimulateNetworkDelay().orElse(null))
                .networkDelay(operation.getNetworkDelay().orElse(null))
                .mockOnFailure(operation.getMockOnFailure()
                        .orElse(null))
                .identifyStrategy(operation.getIdentifyStrategy())
                .forwardedEndpoint(operation.getForwardedEndpoint().orElse(null))
                .defaultMockResponseId(operation.getDefaultMockResponseId().orElse(null))
                .automaticForward(operation.getAutomaticForward().orElse(null))
                .build();
        final ServiceTask<UpdateSoapOperationInput> serviceTask = ServiceTask.of(input, "user");


        Mockito.when(operationRepository.findOne(operation.getId())).thenReturn(Optional.of(operation));
        Mockito.when(operationRepository.update(Mockito.anyString(), Mockito.any(SoapOperation.class))).thenReturn(operation);

        final ServiceResult<UpdateSoapOperationOutput> result = service.process(serviceTask);
        final UpdateSoapOperationOutput output = result.getOutput();
        final SoapOperation returnedSoapOperation = output.getOperation()
                .orElse(null);

        Assertions.assertNotNull(returnedSoapOperation);
        Mockito.verify(operationRepository, Mockito.times(1)).findOne(operation.getId());
        Mockito.verify(operationRepository, Mockito.times(1)).update(operation.getId(), operation);
        Assertions.assertEquals(operation.getId(), returnedSoapOperation.getId());
        Assertions.assertEquals(operation.getName(), returnedSoapOperation.getName());
        Assertions.assertEquals(operation.getStatus(), returnedSoapOperation.getStatus());
        Assertions.assertEquals(operation.getForwardedEndpoint(), returnedSoapOperation.getForwardedEndpoint());
        Assertions.assertEquals(operation.getResponseStrategy(), returnedSoapOperation.getResponseStrategy());
        Assertions.assertEquals(operation.getSimulateNetworkDelay(), returnedSoapOperation.getSimulateNetworkDelay());
        Assertions.assertEquals(operation.getNetworkDelay(), returnedSoapOperation.getNetworkDelay());
        Assertions.assertEquals(operation.getCurrentResponseSequenceIndex(), returnedSoapOperation.getCurrentResponseSequenceIndex());
        Assertions.assertEquals(operation.getMockOnFailure(), returnedSoapOperation.getMockOnFailure());
    }
}
