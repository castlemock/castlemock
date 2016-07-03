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

package com.castlemock.web.mock.soap.model.project.service;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.model.project.dto.SoapPortDto;
import com.castlemock.core.mock.soap.model.project.dto.SoapProjectDto;
import com.castlemock.core.mock.soap.model.project.service.message.input.DeleteSoapPortInput;
import com.castlemock.core.mock.soap.model.project.service.message.output.DeleteSoapPortOutput;
import com.castlemock.web.mock.soap.model.project.SoapPortDtoGenerator;
import com.castlemock.web.mock.soap.model.project.SoapProjectDtoGenerator;
import com.castlemock.web.mock.soap.model.project.repository.SoapProjectRepository;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class DeleteSoapPortServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private SoapProjectRepository repository;

    @InjectMocks
    private DeleteSoapPortService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapProjectDto soapProject = SoapProjectDtoGenerator.generateSoapProjectDto();
        final SoapPortDto soapPort = SoapPortDtoGenerator.generateSoapPortDto();

        soapProject.getPorts().add(soapPort);
        Mockito.when(repository.findOne(soapProject.getId())).thenReturn(soapProject);

        final DeleteSoapPortInput input = new DeleteSoapPortInput(soapProject.getId(), soapPort.getId());
        final ServiceTask<DeleteSoapPortInput> serviceTask = new ServiceTask<DeleteSoapPortInput>(input);
        final ServiceResult<DeleteSoapPortOutput> serviceResult = service.process(serviceTask);
        serviceResult.getOutput();

        Mockito.verify(repository, Mockito.times(1)).deleteSoapPort(Mockito.anyString(), Mockito.anyString());
    }
}
