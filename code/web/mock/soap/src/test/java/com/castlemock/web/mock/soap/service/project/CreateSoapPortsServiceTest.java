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

import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.web.mock.soap.model.project.SoapPortGenerator;
import com.castlemock.web.mock.soap.model.project.SoapProjectGenerator;
import com.castlemock.web.mock.soap.repository.project.SoapProjectRepository;
import com.castlemock.web.mock.soap.service.project.CreateSoapPortsService;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateSoapPortsServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private SoapProjectRepository repository;

    @InjectMocks
    private CreateSoapPortsService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Ignore
    public void testProcess(){
        final SoapProject soapProject = SoapProjectGenerator.generateSoapProject();
        final List<SoapPort> soapPorts = new ArrayList<SoapPort>();
        for(int index = 0; index < 3; index++){
            final SoapPort soapPort = SoapPortGenerator.generateSoapPort();
            soapPorts.add(soapPort);
        }

        Mockito.when(repository.findOne(soapProject.getId())).thenReturn(soapProject);

        /*
        final CreateSoapPortsInput input = new CreateSoapPortsInput(soapProject.getId(), soapPorts);
        final ServiceTask<CreateSoapPortsInput> serviceTask = new ServiceTask<CreateSoapPortsInput>(input);
        final ServiceResult<CreateSoapPortsOutput> serviceResult = service.process(serviceTask);
        final CreateSoapPortsOutput output = serviceResult.getOutput();

        Assert.assertNotNull(output);

        Mockito.verify(repository, Mockito.times(3)).saveSoapPort(Mockito.anyString(), Mockito.any(SoapPort.class));
        */
    }
}
