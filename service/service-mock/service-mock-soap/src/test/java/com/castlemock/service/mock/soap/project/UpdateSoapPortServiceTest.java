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
import com.castlemock.model.mock.soap.domain.SoapPort;
import com.castlemock.model.mock.soap.domain.SoapPortTestBuilder;
import com.castlemock.repository.soap.project.SoapPortRepository;
import com.castlemock.service.mock.soap.project.input.UpdateSoapPortInput;
import com.castlemock.service.mock.soap.project.output.UpdateSoapPortOutput;
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
public class UpdateSoapPortServiceTest {


    @Mock
    private SoapPortRepository soapPortRepository;

    @InjectMocks
    private UpdateSoapPortService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final String projectId = "ProjectId";
        final String newUri = "newUri";
        final SoapPort port = SoapPortTestBuilder.builder().build();
        final UpdateSoapPortInput input = UpdateSoapPortInput.builder()
                .projectId(projectId)
                .portId(port.getId())
                .uri(newUri)
                .build();
        final ServiceTask<UpdateSoapPortInput> serviceTask = ServiceTask.of(input, "user");

        Mockito.when(soapPortRepository.findOne(port.getId())).thenReturn(Optional.of(port));
        Mockito.when(soapPortRepository.update(Mockito.anyString(), Mockito.any(SoapPort.class))).thenReturn(port);

        final ServiceResult<UpdateSoapPortOutput> result = service.process(serviceTask);
        final UpdateSoapPortOutput output = result.getOutput();
        final SoapPort returnedSoapPort = output.getPort()
                        .orElse(null);

        Assertions.assertNotNull(returnedSoapPort);
        Assertions.assertEquals(port.getId(), returnedSoapPort.getId());
        Assertions.assertEquals(port.getName(), returnedSoapPort.getName());
        Assertions.assertEquals(port.getUri(), returnedSoapPort.getUri());
        Assertions.assertEquals(port.getProjectId(), returnedSoapPort.getProjectId());

        Mockito.verify(soapPortRepository, Mockito.times(1)).findOne(port.getId());
        Mockito.verify(soapPortRepository, Mockito.times(1)).update(port.getId(), port.toBuilder()
                .uri(newUri)
                .build());
    }
}
