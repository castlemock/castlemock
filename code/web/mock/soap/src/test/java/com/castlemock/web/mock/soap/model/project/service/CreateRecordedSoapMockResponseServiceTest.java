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

import com.castlemock.core.basis.model.Repository;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponse;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.core.mock.soap.model.project.dto.SoapMockResponseDto;
import com.castlemock.core.mock.soap.model.project.service.message.input.CreateRecordedSoapMockResponseInput;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateRecordedSoapMockResponseServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @InjectMocks
    private CreateRecordedSoapMockResponseService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final SoapProject soapProject = new SoapProject();
        final SoapPort soapPort = new SoapPort();
        final SoapOperation soapOperation = new SoapOperation();
        soapOperation.setId(new String());

        soapProject.setPorts(new ArrayList<SoapPort>());
        soapPort.setOperations(new ArrayList<SoapOperation>());
        soapOperation.setMockResponses(new ArrayList<SoapMockResponse>());

        soapProject.getPorts().add(soapPort);
        soapPort.getOperations().add(soapOperation);

        List<SoapProject> soapProjects = new ArrayList<SoapProject>();
        soapProjects.add(soapProject);

        Mockito.when(repository.findAll()).thenReturn(soapProjects);

    }

    @Test
    public void testProcess(){
        final String SoapMethodId = new String();
        final SoapMockResponseDto SoapMockResponseDto = Mockito.mock(SoapMockResponseDto.class);
        final CreateRecordedSoapMockResponseInput input = new CreateRecordedSoapMockResponseInput(SoapMethodId, SoapMockResponseDto);
        final ServiceTask<CreateRecordedSoapMockResponseInput> serviceTask = new ServiceTask<>(input);
        service.process(serviceTask);
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(SoapProject.class));
    }
}
