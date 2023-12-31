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
import com.castlemock.model.mock.soap.domain.SoapOperationTestBuilder;
import com.castlemock.model.mock.soap.domain.SoapPort;
import com.castlemock.model.mock.soap.domain.SoapPortTestBuilder;
import com.castlemock.repository.soap.project.SoapOperationRepository;
import com.castlemock.service.mock.soap.project.input.UpdateSoapPortsForwardedEndpointInput;
import com.castlemock.service.mock.soap.project.output.UpdateSoapPortsForwardedEndpointOutput;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;

/**
 * @author Karl Dahlgren
 * @since 1.1
 */
public class UpdateSoapPortsForwardedEndpointServiceTest {

    @Mock
    private SoapOperationRepository operationRepository;

    @InjectMocks
    private UpdateSoapPortsForwardedEndpointService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapPort port = SoapPortTestBuilder.builder().build();
        final SoapOperation operation = SoapOperationTestBuilder.builder().build();
        final String projectId = "SOAP PROJECT";

        final UpdateSoapPortsForwardedEndpointInput input = UpdateSoapPortsForwardedEndpointInput.builder()
                .projectId(projectId)
                .portIds(Set.of(port.getId()))
                .forwardedEndpoint("Forward Endpoint")
                .build();
        final ServiceTask<UpdateSoapPortsForwardedEndpointInput> serviceTask = ServiceTask.of(input, "user");

        Mockito.when(operationRepository.findWithPortId(port.getId())).thenReturn(List.of(operation));
        Mockito.when(operationRepository.update(Mockito.anyString(), Mockito.any(SoapOperation.class))).thenReturn(operation);
        final ServiceResult<UpdateSoapPortsForwardedEndpointOutput> result = service.process(serviceTask);

        Mockito.verify(operationRepository, Mockito.times(1)).findWithPortId(port.getId());
        Mockito.verify(operationRepository, Mockito.times(1)).update(operation.getId(), operation.toBuilder()
                .forwardedEndpoint("Forward Endpoint")
                .build());

    }
}
