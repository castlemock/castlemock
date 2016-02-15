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

package com.fortmocks.web.mock.soap.model.event.service.adapter;

import com.fortmocks.core.basis.model.ServiceProcessor;
import com.fortmocks.core.basis.model.TypeIdentifier;
import com.fortmocks.core.basis.model.event.dto.EventDto;
import com.fortmocks.core.mock.soap.model.event.dto.SoapEventDto;
import com.fortmocks.core.mock.soap.model.event.service.message.input.ReadAllSoapEventInput;
import com.fortmocks.core.mock.soap.model.event.service.message.input.ReadSoapEventInput;
import com.fortmocks.core.mock.soap.model.event.service.message.output.ReadAllSoapEventOutput;
import com.fortmocks.core.mock.soap.model.event.service.message.output.ReadSoapEventOutput;
import com.fortmocks.web.mock.soap.model.SoapTypeIdentifier;
import com.fortmocks.web.mock.soap.model.event.SoapEventDtoGenerator;
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
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCreate(){
        final SoapEventDto soapEventDto = SoapEventDtoGenerator.generateSoapEventDto();
        serviceAdapter.create(soapEventDto);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testDelete(){
        final SoapEventDto soapEventDto = SoapEventDtoGenerator.generateSoapEventDto();
        serviceAdapter.delete(soapEventDto.getProjectId());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUpdate(){
        final SoapEventDto soapEventDto = SoapEventDtoGenerator.generateSoapEventDto();
        serviceAdapter.update(soapEventDto.getProjectId(), soapEventDto);
    }

    @Test
    public void testReadAll(){
        final List<SoapEventDto> soapEventDtos = new ArrayList<SoapEventDto>();
        for(int index = 0; index < 3; index++){
            final SoapEventDto soapEventDto = SoapEventDtoGenerator.generateSoapEventDto();
            soapEventDtos.add(soapEventDto);

        }

        final ReadAllSoapEventOutput output = new ReadAllSoapEventOutput(soapEventDtos);
        Mockito.when(serviceProcessor.process(Mockito.any(ReadAllSoapEventInput.class))).thenReturn(output);

        final List<SoapEventDto> returnedSoapEventDtos = serviceAdapter.readAll();

        for(int index = 0; index < 3; index++){
            final SoapEventDto soapEvent = soapEventDtos.get(index);
            final SoapEventDto returnedSoapEvent = returnedSoapEventDtos.get(index);

            Assert.assertEquals(soapEvent.getId(), returnedSoapEvent.getId());
            Assert.assertEquals(soapEvent.getOperationId(), returnedSoapEvent.getOperationId());
            Assert.assertEquals(soapEvent.getPortId(), returnedSoapEvent.getPortId());
            Assert.assertEquals(soapEvent.getProjectId(), returnedSoapEvent.getProjectId());
        }
    }

    @Test
    public void testRead(){
        final SoapEventDto soapEvent = SoapEventDtoGenerator.generateSoapEventDto();
        final ReadSoapEventOutput output = new ReadSoapEventOutput(soapEvent);
        Mockito.when(serviceProcessor.process(Mockito.any(ReadSoapEventInput.class))).thenReturn(output);

        final SoapEventDto returnedSoapEvent = serviceAdapter.read(soapEvent.getId());

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
        EventDto eventDto = SoapEventDtoGenerator.generateSoapEventDto();
        SoapEventDto returnedSoapEventDto = serviceAdapter.convertType(eventDto);
        Assert.assertEquals(eventDto.getId(), returnedSoapEventDto.getId());
        Assert.assertEquals(eventDto.getEndDate(), returnedSoapEventDto.getEndDate());
        Assert.assertEquals(eventDto.getResourceLink(), returnedSoapEventDto.getResourceLink());
        Assert.assertEquals(eventDto.getStartDate(), returnedSoapEventDto.getStartDate());
        Assert.assertEquals(eventDto.getResourceName(), returnedSoapEventDto.getResourceName());
    }

    @Test
    public void testGenerateResourceLink(){
        final SoapEventDto soapEventDto = SoapEventDtoGenerator.generateSoapEventDto();
        final String generatedResourceLink = serviceAdapter.generateResourceLink(soapEventDto);
        Assert.assertEquals("/web/soap/project/" + soapEventDto.getProjectId() + "/port/" + soapEventDto.getPortId() + "/operation/" + soapEventDto.getOperationId(), generatedResourceLink);
    }
}
