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

package com.castlemock.web.mock.soap.service.event;

import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.model.event.domain.SoapEvent;
import com.castlemock.core.mock.soap.model.event.domain.SoapEventTestBuilder;
import com.castlemock.core.mock.soap.service.event.input.ReadSoapEventInput;
import com.castlemock.core.mock.soap.service.event.output.ReadSoapEventOutput;
import com.castlemock.repository.Repository;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

/**
 * @author Karl Dahlgren
 * @since 1.4
 */
public class ReadSoapEventServiceTest {


    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @InjectMocks
    private ReadSoapEventService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        final SoapEvent soapEvent = SoapEventTestBuilder.builder().build();
        Mockito.when(repository.findOne(soapEvent.getId())).thenReturn(soapEvent);

        final ReadSoapEventInput input = ReadSoapEventInput.builder()
                .soapEventId(soapEvent.getId())
                .build();
        final ServiceTask<ReadSoapEventInput> serviceTask = new ServiceTask<ReadSoapEventInput>(input);
        final ServiceResult<ReadSoapEventOutput> serviceResult = service.process(serviceTask);
        final ReadSoapEventOutput output = serviceResult.getOutput();
        final SoapEvent returnedSoapEvent = output.getSoapEvent();

        Assert.assertEquals(soapEvent.getId(), returnedSoapEvent.getId());
        Assert.assertEquals(soapEvent.getOperationId(), returnedSoapEvent.getOperationId());
        Assert.assertEquals(soapEvent.getPortId(), returnedSoapEvent.getPortId());
        Assert.assertEquals(soapEvent.getProjectId(), returnedSoapEvent.getProjectId());
    }
}
