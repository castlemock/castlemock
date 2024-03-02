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
import com.castlemock.model.mock.soap.domain.SoapEvent;
import com.castlemock.model.mock.soap.domain.SoapEventTestBuilder;
import com.castlemock.service.mock.soap.event.input.ClearAllSoapEventInput;
import com.castlemock.service.mock.soap.event.input.ReadAllSoapEventInput;
import com.castlemock.service.mock.soap.event.output.ReadAllSoapEventOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReadAll(){
        final List<SoapEvent> soapEvents = new ArrayList<>();
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

            Assertions.assertEquals(soapEvent.getId(), returnedSoapEvent.getId());
            Assertions.assertEquals(soapEvent.getOperationId(), returnedSoapEvent.getOperationId());
            Assertions.assertEquals(soapEvent.getPortId(), returnedSoapEvent.getPortId());
            Assertions.assertEquals(soapEvent.getProjectId(), returnedSoapEvent.getProjectId());
        }
    }

    @Test
    public void testClearAll(){
        serviceAdapter.clearAll();
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(ClearAllSoapEventInput.class));
    }
}
