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
import com.castlemock.service.mock.soap.event.input.ReadSoapEventInput;
import com.castlemock.service.mock.soap.event.output.ReadSoapEventOutput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.4
 */
public class ReadSoapEventServiceTest {

    @Mock
    private SoapEventRepository repository;

    @InjectMocks
    private ReadSoapEventService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapEvent soapEvent = SoapEventTestBuilder.builder().build();
        Mockito.when(repository.findOne(soapEvent.getId())).thenReturn(Optional.of(soapEvent));

        final ReadSoapEventInput input = ReadSoapEventInput.builder()
                .soapEventId(soapEvent.getId())
                .build();
        final ServiceTask<ReadSoapEventInput> serviceTask = ServiceTask.of(input, "user");
        final ServiceResult<ReadSoapEventOutput> serviceResult = service.process(serviceTask);
        final ReadSoapEventOutput output = serviceResult.getOutput();
        final SoapEvent returnedSoapEvent = output.getSoapEvent()
                        .orElse(null);

        Assert.assertNotNull(returnedSoapEvent);
        Assert.assertEquals(soapEvent.getId(), returnedSoapEvent.getId());
        Assert.assertEquals(soapEvent.getOperationId(), returnedSoapEvent.getOperationId());
        Assert.assertEquals(soapEvent.getPortId(), returnedSoapEvent.getPortId());
        Assert.assertEquals(soapEvent.getProjectId(), returnedSoapEvent.getProjectId());
    }
}
