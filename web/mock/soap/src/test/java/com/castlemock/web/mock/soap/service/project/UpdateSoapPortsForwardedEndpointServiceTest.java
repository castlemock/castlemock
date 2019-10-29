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
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperationTestBuilder;
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.model.project.domain.SoapPortTestBuilder;
import com.castlemock.core.mock.soap.service.project.input.UpdateSoapPortsForwardedEndpointInput;
import com.castlemock.core.mock.soap.service.project.output.UpdateSoapPortsForwardedEndpointOutput;
import com.castlemock.repository.soap.project.SoapOperationRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

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
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapPort port = SoapPortTestBuilder.builder().build();
        final SoapOperation operation = SoapOperationTestBuilder.builder().build();
        final String projectId = "SOAP PROJECT";

        final UpdateSoapPortsForwardedEndpointInput input = UpdateSoapPortsForwardedEndpointInput.builder()
                .projectId(projectId)
                .ports(Arrays.asList(port))
                .forwardedEndpoint("Forward Endpoint")
                .build();
        final ServiceTask<UpdateSoapPortsForwardedEndpointInput> serviceTask = new ServiceTask<UpdateSoapPortsForwardedEndpointInput>(input);

        Mockito.when(operationRepository.findWithPortId(port.getId())).thenReturn(Arrays.asList(operation));
        Mockito.when(operationRepository.update(Mockito.anyString(), Mockito.any(SoapOperation.class))).thenReturn(operation);
        final ServiceResult<UpdateSoapPortsForwardedEndpointOutput> result = service.process(serviceTask);

        Mockito.verify(operationRepository, Mockito.times(1)).findWithPortId(port.getId());
        Mockito.verify(operationRepository, Mockito.times(1)).update(operation.getId(), operation);

    }
}
