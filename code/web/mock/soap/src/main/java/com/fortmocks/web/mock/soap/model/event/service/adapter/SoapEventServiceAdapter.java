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

package com.fortmocks.web.mock.soap.model.event.service.adapter;

import com.fortmocks.core.basis.model.ServiceProcessor;
import com.fortmocks.core.basis.model.TypeIdentifier;
import com.fortmocks.core.basis.model.event.dto.EventDto;
import com.fortmocks.core.basis.model.event.service.EventServiceAdapter;
import com.fortmocks.core.mock.soap.model.event.dto.SoapEventDto;
import com.fortmocks.core.mock.soap.model.event.service.message.input.ReadAllSoapEventInput;
import com.fortmocks.core.mock.soap.model.event.service.message.input.ReadSoapEventInput;
import com.fortmocks.core.mock.soap.model.event.service.message.output.ReadAllSoapEventOutput;
import com.fortmocks.core.mock.soap.model.event.service.message.output.ReadSoapEventOutput;
import com.fortmocks.web.mock.soap.model.SoapTypeIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class SoapEventServiceAdapter implements EventServiceAdapter<SoapEventDto> {

    @Autowired
    private ServiceProcessor serviceProcessor;
    private SoapTypeIdentifier SOAP_TYPE_IDENTIFIER = new SoapTypeIdentifier();

    @Override
    public SoapEventDto create(SoapEventDto dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SoapEventDto update(String id, SoapEventDto dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<SoapEventDto> readAll() {
        final ReadAllSoapEventOutput output = serviceProcessor.process(new ReadAllSoapEventInput());
        return output.getSoapEvents();
    }

    @Override
    public SoapEventDto read(String id) {
        final ReadSoapEventOutput output = serviceProcessor.process(new ReadSoapEventInput(id));
        return output.getSoapEvent();
    }

    @Override
    public TypeIdentifier getTypeIdentifier() {
         return SOAP_TYPE_IDENTIFIER;
    }

    @Override
    public SoapEventDto convertType(EventDto parent) {
        return new SoapEventDto(parent);
    }

}
