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

package com.castlemock.service.mock.soap.event;

import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.soap.domain.SoapEvent;
import com.castlemock.model.mock.soap.domain.SoapEventTestBuilder;
import com.castlemock.repository.soap.event.SoapEventRepository;
import com.castlemock.service.mock.soap.event.input.ReadAllSoapEventInput;
import com.castlemock.service.mock.soap.event.output.ReadAllSoapEventOutput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.4
 */
public class ReadAllSoapEventServiceTest {

    @Mock
    private SoapEventRepository repository;

    @InjectMocks
    private ReadAllSoapEventService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final List<SoapEvent> soapEvents = new ArrayList<SoapEvent>();
        for(int index = 0; index < 3; index++){
            final SoapEvent soapEvent = SoapEventTestBuilder.builder().build();
            soapEvents.add(soapEvent);
        }

        Mockito.when(repository.findAll()).thenReturn(soapEvents);

        final ReadAllSoapEventInput input = ReadAllSoapEventInput.builder().build();
        final ServiceTask<ReadAllSoapEventInput> serviceTask = ServiceTask.of(input, "user");
        final ServiceResult<ReadAllSoapEventOutput> serviceResult = service.process(serviceTask);
        final ReadAllSoapEventOutput output = serviceResult.getOutput();

        Assert.assertEquals(soapEvents.size(), output.getSoapEvents().size());

        for(int index = 0; index < 3; index++){
            final SoapEvent soapEvent = soapEvents.get(index);
            final SoapEvent returnedSoapEvent = output.getSoapEvents().get(index);

            Assert.assertEquals(soapEvent.getId(), returnedSoapEvent.getId());
            Assert.assertEquals(soapEvent.getOperationId(), returnedSoapEvent.getOperationId());
            Assert.assertEquals(soapEvent.getPortId(), returnedSoapEvent.getPortId());
            Assert.assertEquals(soapEvent.getProjectId(), returnedSoapEvent.getProjectId());
        }
    }
}
