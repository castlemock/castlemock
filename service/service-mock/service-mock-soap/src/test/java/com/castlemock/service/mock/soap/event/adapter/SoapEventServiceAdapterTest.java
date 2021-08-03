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

package com.castlemock.service.mock.soap.event.adapter;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.core.TypeIdentifier;
import com.castlemock.model.core.event.Event;
import com.castlemock.model.mock.soap.domain.SoapEvent;
import com.castlemock.model.mock.soap.domain.SoapEventTestBuilder;
import com.castlemock.service.mock.soap.SoapTypeIdentifier;
import com.castlemock.service.mock.soap.event.input.ClearAllSoapEventInput;
import com.castlemock.service.mock.soap.event.input.ReadAllSoapEventInput;
import com.castlemock.service.mock.soap.event.input.ReadSoapEventInput;
import com.castlemock.service.mock.soap.event.output.ReadAllSoapEventOutput;
import com.castlemock.service.mock.soap.event.output.ReadSoapEventOutput;
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
 * @author Karl Dalhgren
 * @since 1.4
 */
public class SoapEventServiceAdapterTest {

    @Mock
    private ServiceProcessor serviceProcessor;

    @InjectMocks
    private SoapEventServiceAdapter serviceAdapter;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCreate(){
        final SoapEvent soapEvent = SoapEventTestBuilder.builder().build();
        serviceAdapter.create(soapEvent);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testDelete(){
        final SoapEvent soapEvent = SoapEventTestBuilder.builder().build();
        serviceAdapter.delete(soapEvent.getProjectId());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUpdate(){
        final SoapEvent soapEvent = SoapEventTestBuilder.builder().build();
        serviceAdapter.update(soapEvent.getProjectId(), soapEvent);
    }

    @Test
    public void testReadAll(){
        final List<SoapEvent> soapEvents = new ArrayList<SoapEvent>();
        for(int index = 0; index < 3; index++){
            final SoapEvent soapEvent = SoapEventTestBuilder.builder().build();
            soapEvents.add(soapEvent);

        }

        final ReadAllSoapEventOutput output = ReadAllSoapEventOutput.builder().soapEvents(soapEvents).build();
        Mockito.when(serviceProcessor.process(Mockito.any(ReadAllSoapEventInput.class))).thenReturn(output);

        final List<SoapEvent> returnedSoapEvents = serviceAdapter.readAll();

        for(int index = 0; index < 3; index++){
            final SoapEvent soapEvent = soapEvents.get(index);
            final SoapEvent returnedSoapEvent = returnedSoapEvents.get(index);

            Assert.assertEquals(soapEvent.getId(), returnedSoapEvent.getId());
            Assert.assertEquals(soapEvent.getOperationId(), returnedSoapEvent.getOperationId());
            Assert.assertEquals(soapEvent.getPortId(), returnedSoapEvent.getPortId());
            Assert.assertEquals(soapEvent.getProjectId(), returnedSoapEvent.getProjectId());
        }
    }

    @Test
    public void testRead(){
        final SoapEvent soapEvent = SoapEventTestBuilder.builder().build();
        final ReadSoapEventOutput output = ReadSoapEventOutput.builder()
                .soapEvent(soapEvent)
                .build();
        Mockito.when(serviceProcessor.process(Mockito.any(ReadSoapEventInput.class))).thenReturn(output);

        final SoapEvent returnedSoapEvent = serviceAdapter.read(soapEvent.getId());

        Assert.assertEquals(soapEvent.getId(), returnedSoapEvent.getId());
        Assert.assertEquals(soapEvent.getOperationId(), returnedSoapEvent.getOperationId());
        Assert.assertEquals(soapEvent.getPortId(), returnedSoapEvent.getPortId());
        Assert.assertEquals(soapEvent.getProjectId(), returnedSoapEvent.getProjectId());
    }

    @Test
    public void testGetTypeIdentifier(){
        final SoapTypeIdentifier soapTypeIdentifier = new SoapTypeIdentifier();
        final TypeIdentifier returnedSoapTypeIdentifier = serviceAdapter.getTypeIdentifier();

        Assert.assertEquals(soapTypeIdentifier.getType(), returnedSoapTypeIdentifier.getType());
        Assert.assertEquals(soapTypeIdentifier.getTypeUrl(), returnedSoapTypeIdentifier.getTypeUrl());
    }

    @Test
    public void testConvertType(){
        Event event = SoapEventTestBuilder.builder().build();
        SoapEvent returnedSoapEvent = serviceAdapter.convertType(event);
        Assert.assertEquals(event.getId(), returnedSoapEvent.getId());
        Assert.assertEquals(event.getEndDate(), returnedSoapEvent.getEndDate());
        Assert.assertEquals(event.getResourceLink(), returnedSoapEvent.getResourceLink());
        Assert.assertEquals(event.getStartDate(), returnedSoapEvent.getStartDate());
        Assert.assertEquals(event.getResourceName(), returnedSoapEvent.getResourceName());
    }

    @Test
    public void testGenerateResourceLink(){
        final SoapEvent soapEvent = SoapEventTestBuilder.builder().build();
        final String generatedResourceLink = serviceAdapter.generateResourceLink(soapEvent);
        Assert.assertEquals("/web/soap/project/" + soapEvent.getProjectId() + "/port/" + soapEvent.getPortId() + "/operation/" + soapEvent.getOperationId(), generatedResourceLink);
    }

    @Test
    public void testClearAll(){
        serviceAdapter.clearAll();
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(ClearAllSoapEventInput.class));
    }
}
