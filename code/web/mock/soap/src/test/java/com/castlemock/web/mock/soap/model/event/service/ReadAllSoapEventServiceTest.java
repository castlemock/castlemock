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

import com.castlemock.core.basis.model.Repository;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.model.event.dto.SoapEventDto;
import com.castlemock.core.mock.soap.model.event.service.message.input.ReadAllSoapEventInput;
import com.castlemock.core.mock.soap.model.event.service.message.output.ReadAllSoapEventOutput;
import com.castlemock.web.mock.soap.model.event.SoapEventDtoGenerator;
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
public class ReadAllSoapEventServiceTest {


    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @InjectMocks
    private ReadAllSoapEventService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final List<SoapEventDto> soapEvents = new ArrayList<SoapEventDto>();
        for(int index = 0; index < 3; index++){
            final SoapEventDto soapEvent = SoapEventDtoGenerator.generateSoapEventDto();
            soapEvents.add(soapEvent);
        }

        Mockito.when(repository.findAll()).thenReturn(soapEvents);

        final ReadAllSoapEventInput input = new ReadAllSoapEventInput();
        final ServiceTask<ReadAllSoapEventInput> serviceTask = new ServiceTask<ReadAllSoapEventInput>(input);
        final ServiceResult<ReadAllSoapEventOutput> serviceResult = service.process(serviceTask);
        final ReadAllSoapEventOutput output = serviceResult.getOutput();

        Assert.assertEquals(soapEvents.size(), output.getSoapEvents().size());

        for(int index = 0; index < 3; index++){
            final SoapEventDto soapEvent = soapEvents.get(index);
            final SoapEventDto returnedSoapEvent = output.getSoapEvents().get(index);

            Assert.assertEquals(soapEvent.getId(), returnedSoapEvent.getId());
            Assert.assertEquals(soapEvent.getOperationId(), returnedSoapEvent.getOperationId());
            Assert.assertEquals(soapEvent.getPortId(), returnedSoapEvent.getPortId());
            Assert.assertEquals(soapEvent.getProjectId(), returnedSoapEvent.getProjectId());
        }
    }
}
