/*
 * Copyright 2015 Karl Dahlgren
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

package com.castlemock.web.mock.soap.model.event.service;

import com.castlemock.core.basis.model.Repository;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.model.event.dto.SoapEventDto;
import com.castlemock.core.mock.soap.model.event.service.message.input.CreateSoapEventInput;
import com.castlemock.core.mock.soap.model.event.service.message.output.CreateSoapEventOutput;
import com.castlemock.web.mock.soap.model.event.SoapEventDtoGenerator;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateSoapEventServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @InjectMocks
    private CreateSoapEventService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(service, "soapMaxEventCount", 5);
    }

    @Test
    public void testProcess(){
        final SoapEventDto soapEventDto = SoapEventDtoGenerator.generateSoapEventDto();
        Mockito.when(repository.save(Mockito.any(SoapEventDto.class))).thenReturn(soapEventDto);

        final CreateSoapEventInput input = new CreateSoapEventInput(soapEventDto);
        input.setSoapEvent(soapEventDto);

        final ServiceTask<CreateSoapEventInput> serviceTask = new ServiceTask<CreateSoapEventInput>(input);
        final ServiceResult<CreateSoapEventOutput> serviceResult = service.process(serviceTask);
        final CreateSoapEventOutput createRestApplicationOutput = serviceResult.getOutput();
        final SoapEventDto returnedSoapEventDto = createRestApplicationOutput.getCreatedSoapEvent();

        Assert.assertEquals(soapEventDto.getOperationId(), returnedSoapEventDto.getOperationId());
        Assert.assertEquals(soapEventDto.getPortId(), returnedSoapEventDto.getPortId());
        Assert.assertEquals(soapEventDto.getProjectId(), returnedSoapEventDto.getProjectId());
    }

}
