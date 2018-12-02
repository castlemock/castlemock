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
import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponse;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.core.mock.soap.service.project.input.CreateSoapMockResponseInput;
import com.castlemock.core.mock.soap.service.project.output.CreateSoapMockResponseOutput;
import com.castlemock.repository.soap.project.SoapMockResponseRepository;
import com.castlemock.core.mock.soap.model.project.SoapMockResponseGenerator;
import com.castlemock.core.mock.soap.model.project.SoapOperationGenerator;
import com.castlemock.core.mock.soap.model.project.SoapPortGenerator;
import com.castlemock.core.mock.soap.model.project.SoapProjectGenerator;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateSoapMockResponseServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private SoapMockResponseRepository repository;

    @InjectMocks
    private CreateSoapMockResponseService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapProject soapProject = SoapProjectGenerator.generateSoapProject();
        final SoapPort soapPort = SoapPortGenerator.generateSoapPort();
        final SoapOperation soapOperation = SoapOperationGenerator.generateSoapOperation();
        final SoapMockResponse soapMockResponse = SoapMockResponseGenerator.generateSoapMockResponse();


        final CreateSoapMockResponseInput input = CreateSoapMockResponseInput.builder()
                .projectId(soapProject.getId())
                .portId(soapPort.getId())
                .operationId(soapOperation.getId())
                .mockResponse(soapMockResponse)
                .build();
        final ServiceTask<CreateSoapMockResponseInput> serviceTask = new ServiceTask<>(input);
        final ServiceResult<CreateSoapMockResponseOutput> serviceResult = service.process(serviceTask);
        final CreateSoapMockResponseOutput output = serviceResult.getOutput();

        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(SoapMockResponse.class));
    }
}
