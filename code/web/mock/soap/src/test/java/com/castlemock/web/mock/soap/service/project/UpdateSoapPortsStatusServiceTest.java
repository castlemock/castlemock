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
import com.castlemock.core.mock.soap.model.project.domain.SoapOperationStatus;
import com.castlemock.core.mock.soap.service.project.input.UpdateSoapPortsStatusInput;
import com.castlemock.core.mock.soap.service.project.output.UpdateSoapPortsStatusOutput;
import com.castlemock.web.mock.soap.model.project.SoapOperationGenerator;
import com.castlemock.web.mock.soap.repository.project.SoapOperationRepository;
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
public class UpdateSoapPortsStatusServiceTest {

    @Mock
    private SoapOperationRepository operationRepository;

    @InjectMocks
    private UpdateSoapPortsStatusService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapOperation soapOperation = SoapOperationGenerator.generateSoapOperation();
        final String projectId = "SOAP PROJECT";
        final String portId = "SOAP PORT";

        final UpdateSoapPortsStatusInput input = new UpdateSoapPortsStatusInput(projectId, portId, SoapOperationStatus.MOCKED);
        final ServiceTask<UpdateSoapPortsStatusInput> serviceTask = new ServiceTask<UpdateSoapPortsStatusInput>(input);

        Mockito.when(operationRepository.findWithPortId("SOAP PORT")).thenReturn(Arrays.asList(soapOperation));
        Mockito.when(operationRepository.update(Mockito.anyString(), Mockito.any(SoapOperation.class))).thenReturn(soapOperation);
        final ServiceResult<UpdateSoapPortsStatusOutput> result = service.process(serviceTask);


        Mockito.verify(operationRepository, Mockito.times(1)).findWithPortId(portId);
        Mockito.verify(operationRepository, Mockito.times(1)).update(soapOperation.getId(), soapOperation);

    }
}
