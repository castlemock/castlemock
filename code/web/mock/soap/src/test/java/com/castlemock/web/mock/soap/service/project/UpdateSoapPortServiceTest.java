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
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.service.project.input.UpdateSoapPortInput;
import com.castlemock.core.mock.soap.service.project.output.UpdateSoapPortOutput;
import com.castlemock.web.mock.soap.model.project.SoapPortGenerator;
import com.castlemock.web.mock.soap.repository.project.SoapPortRepository;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Karl Dahlgren
 */
public class UpdateSoapPortServiceTest {


    @Mock
    private SoapPortRepository soapPortRepository;

    @InjectMocks
    private UpdateSoapPortService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final String projectId = "ProjectId";
        final SoapPort port = SoapPortGenerator.generateSoapPort();
        final UpdateSoapPortInput input = UpdateSoapPortInput.builder()
                .projectId(projectId)
                .portId(port.getId())
                .port(port)
                .build();
        final ServiceTask<UpdateSoapPortInput> serviceTask = new ServiceTask<UpdateSoapPortInput>(input);


        Mockito.when(soapPortRepository.findOne(port.getId())).thenReturn(port);
        Mockito.when(soapPortRepository.update(Mockito.anyString(), Mockito.any(SoapPort.class))).thenReturn(port);

        final ServiceResult<UpdateSoapPortOutput> result = service.process(serviceTask);
        final UpdateSoapPortOutput output = result.getOutput();
        final SoapPort returnedSoapPort = output.getPort();

        Mockito.verify(soapPortRepository, Mockito.times(1)).findOne(port.getId());
        Mockito.verify(soapPortRepository, Mockito.times(1)).update(port.getId(), port);
        Assert.assertEquals(port.getId(), returnedSoapPort.getId());
        Assert.assertEquals(port.getName(), returnedSoapPort.getName());
        Assert.assertEquals(port.getUri(), returnedSoapPort.getUri());
        Assert.assertEquals(port.getProjectId(), returnedSoapPort.getProjectId());

    }
}
