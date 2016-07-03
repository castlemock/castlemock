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
import com.castlemock.core.mock.soap.model.project.dto.SoapMockResponseDto;
import com.castlemock.core.mock.soap.model.project.dto.SoapOperationDto;
import com.castlemock.core.mock.soap.model.project.dto.SoapPortDto;
import com.castlemock.core.mock.soap.model.project.dto.SoapProjectDto;
import com.castlemock.core.mock.soap.model.project.service.message.input.DeleteSoapMockResponseInput;
import com.castlemock.core.mock.soap.model.project.service.message.output.DeleteSoapMockResponseOutput;
import com.castlemock.web.mock.soap.model.project.SoapMockResponseDtoGenerator;
import com.castlemock.web.mock.soap.model.project.SoapOperationDtoGenerator;
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
public class DeleteSoapMockResponseServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private SoapProjectRepository repository;

    @InjectMocks
    private DeleteSoapMockResponseService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapProjectDto soapProject = SoapProjectDtoGenerator.generateSoapProjectDto();
        final SoapPortDto soapPort = SoapPortDtoGenerator.generateSoapPortDto();
        final SoapOperationDto soapOperation = SoapOperationDtoGenerator.generateSoapOperationDto();
        final SoapMockResponseDto soapMockResponse = SoapMockResponseDtoGenerator.generateSoapMockResponseDto();

        soapProject.getPorts().add(soapPort);
        soapPort.getOperations().add(soapOperation);
        soapOperation.getMockResponses().add(soapMockResponse);

        final DeleteSoapMockResponseInput input = new DeleteSoapMockResponseInput(soapProject.getId(), soapPort.getId(), soapOperation.getId(), soapMockResponse.getId());
        final ServiceTask<DeleteSoapMockResponseInput> serviceTask = new ServiceTask<DeleteSoapMockResponseInput>(input);
        final ServiceResult<DeleteSoapMockResponseOutput> serviceResult = service.process(serviceTask);
        serviceResult.getOutput();

        Mockito.verify(repository, Mockito.times(1)).deleteSoapMockResponse(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }
}
