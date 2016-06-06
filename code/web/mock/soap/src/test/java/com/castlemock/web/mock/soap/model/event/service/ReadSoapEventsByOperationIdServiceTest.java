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

package com.castlemock.web.mock.soap.model.event.service;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.model.event.dto.SoapEventDto;
import com.castlemock.core.mock.soap.model.event.service.message.input.ReadSoapEventsByOperationIdInput;
import com.castlemock.core.mock.soap.model.event.service.message.output.ReadSoapEventsByOperationIdOutput;
import com.castlemock.web.mock.soap.model.event.SoapEventDtoGenerator;
import com.castlemock.web.mock.soap.model.event.repository.SoapEventRepository;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.4
 */
public class ReadSoapEventsByOperationIdServiceTest {


    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private SoapEventRepository repository;

    @InjectMocks
    private ReadSoapEventsByOperationIdService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final List<SoapEventDto> soapEvents = new ArrayList<SoapEventDto>();
        for(int index = 0; index < 2; index++){
            final SoapEventDto soapEvent = SoapEventDtoGenerator.generateSoapEventDto();
            soapEvents.add(soapEvent);
        }

        soapEvents.get(0).setOperationId("OperationId");
        soapEvents.get(1).setOperationId("OperationId");

        Mockito.when(repository.findEventsByOperationId(Mockito.anyString())).thenReturn(soapEvents);

        final ReadSoapEventsByOperationIdInput input = new ReadSoapEventsByOperationIdInput("OperationId");
        final ServiceTask<ReadSoapEventsByOperationIdInput> serviceTask = new ServiceTask<ReadSoapEventsByOperationIdInput>(input);
        final ServiceResult<ReadSoapEventsByOperationIdOutput> serviceResult = service.process(serviceTask);
        final ReadSoapEventsByOperationIdOutput output = serviceResult.getOutput();


        Assert.assertEquals(2, output.getSoapEvents().size());

        for(int index = 0; index < 2; index++){
            final SoapEventDto soapEvent = soapEvents.get(index);
            final SoapEventDto returnedSoapEvent = output.getSoapEvents().get(index);

            Assert.assertEquals(soapEvent.getId(), returnedSoapEvent.getId());
            Assert.assertEquals(soapEvent.getOperationId(), returnedSoapEvent.getOperationId());
            Assert.assertEquals(soapEvent.getPortId(), returnedSoapEvent.getPortId());
            Assert.assertEquals(soapEvent.getProjectId(), returnedSoapEvent.getProjectId());
        }
    }
}
