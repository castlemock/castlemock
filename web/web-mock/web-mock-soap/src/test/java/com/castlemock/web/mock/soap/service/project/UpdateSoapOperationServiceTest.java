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

package com.castlemock.web.mock.soap.service.project;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperationTestBuilder;
import com.castlemock.core.mock.soap.service.project.input.UpdateSoapOperationInput;
import com.castlemock.core.mock.soap.service.project.output.UpdateSoapOperationOutput;
import com.castlemock.repository.soap.project.SoapOperationRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Karl Dahlgren
 */
public class UpdateSoapOperationServiceTest {


    @Mock
    private SoapOperationRepository operationRepository;

    @InjectMocks
    private UpdateSoapOperationService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @SuppressWarnings("deprecation")
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
                .simulateNetworkDelay(operation.getSimulateNetworkDelay())
                .networkDelay(operation.getNetworkDelay())
                .mockOnFailure(operation.getMockOnFailure())
                .identifyStrategy(operation.getIdentifyStrategy())
                .forwardedEndpoint(operation.getForwardedEndpoint())
                .defaultMockResponseId(operation.getDefaultMockResponseId())
                .build();
        final ServiceTask<UpdateSoapOperationInput> serviceTask = new ServiceTask<UpdateSoapOperationInput>(input);


        Mockito.when(operationRepository.findOne(operation.getId())).thenReturn(operation);
        Mockito.when(operationRepository.update(Mockito.anyString(), Mockito.any(SoapOperation.class))).thenReturn(operation);

        final ServiceResult<UpdateSoapOperationOutput> result = service.process(serviceTask);
        final UpdateSoapOperationOutput output = result.getOutput();
        final SoapOperation returnedSoapOperation = output.getOperation();

        Mockito.verify(operationRepository, Mockito.times(1)).findOne(operation.getId());
        Mockito.verify(operationRepository, Mockito.times(1)).update(operation.getId(), operation);
        Assert.assertEquals(operation.getId(), returnedSoapOperation.getId());
        Assert.assertEquals(operation.getName(), returnedSoapOperation.getName());
        Assert.assertEquals(operation.getStatus(), returnedSoapOperation.getStatus());
        Assert.assertEquals(operation.getForwardedEndpoint(), returnedSoapOperation.getForwardedEndpoint());
        Assert.assertEquals(operation.getResponseStrategy(), returnedSoapOperation.getResponseStrategy());
        Assert.assertEquals(operation.getSimulateNetworkDelay(), returnedSoapOperation.getSimulateNetworkDelay());
        Assert.assertEquals(operation.getNetworkDelay(), returnedSoapOperation.getNetworkDelay());
        Assert.assertEquals(operation.getCurrentResponseSequenceIndex(), returnedSoapOperation.getCurrentResponseSequenceIndex());
        Assert.assertEquals(operation.getDefaultXPathMockResponseId(), returnedSoapOperation.getDefaultXPathMockResponseId());
        Assert.assertEquals(operation.getMockOnFailure(), returnedSoapOperation.getMockOnFailure());
    }
}
