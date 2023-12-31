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
import com.castlemock.repository.soap.project.SoapOperationRepository;
import com.castlemock.service.mock.soap.project.input.UpdateSoapOperationsForwardedEndpointInput;
import com.castlemock.service.mock.soap.project.output.UpdateSoapOperationsForwardedEndpointOutput;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Set;

/**
 * @author Karl Dahlgren
 * @since 1.1
 */
public class UpdateSoapOperationsForwardedEndpointServiceTest {

    @Mock
    private SoapOperationRepository operationRepository;

    @InjectMocks
    private UpdateSoapOperationsForwardedEndpointService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapOperation operation = SoapOperationTestBuilder.builder().build();

        final String projectId = "SOAP PROJECT";
        final String portId = "SOAP PORT";

        final UpdateSoapOperationsForwardedEndpointInput input = UpdateSoapOperationsForwardedEndpointInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationIds(Set.of(operation.getId()))
                .forwardedEndpoint("Forward Endpoint")
                .build();

        final ServiceTask<UpdateSoapOperationsForwardedEndpointInput> serviceTask = ServiceTask.of(input, "user");

        Mockito.when(operationRepository.findOne(operation.getId())).thenReturn(operation);
        Mockito.when(operationRepository.update(Mockito.anyString(), Mockito.any(SoapOperation.class))).thenReturn(operation);
        final ServiceResult<UpdateSoapOperationsForwardedEndpointOutput> result = service.process(serviceTask);

        Mockito.verify(operationRepository, Mockito.times(1)).findOne(operation.getId());
        Mockito.verify(operationRepository, Mockito.times(1)).update(operation.getId(), operation
                .toBuilder()
                .forwardedEndpoint("Forward Endpoint")
                .build());

    }
}
