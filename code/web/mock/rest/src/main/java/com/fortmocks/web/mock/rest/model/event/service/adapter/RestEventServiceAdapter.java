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

package com.fortmocks.web.mock.rest.model.event.service.adapter;

import com.fortmocks.core.basis.model.ServiceProcessor;
import com.fortmocks.core.basis.model.TypeIdentifier;
import com.fortmocks.core.basis.model.event.dto.EventDto;
import com.fortmocks.core.basis.model.event.service.EventServiceAdapter;
import com.fortmocks.core.mock.rest.model.event.dto.RestEventDto;
import com.fortmocks.core.mock.rest.model.event.service.message.input.ReadAllRestEventInput;
import com.fortmocks.core.mock.rest.model.event.service.message.input.ReadRestEventInput;
import com.fortmocks.core.mock.rest.model.event.service.message.output.ReadAllRestEventOutput;
import com.fortmocks.core.mock.rest.model.event.service.message.output.ReadRestEventOutput;
import com.fortmocks.web.mock.rest.model.RestTypeIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class RestEventServiceAdapter implements EventServiceAdapter<RestEventDto> {

    @Autowired
    private ServiceProcessor serviceProcessor;
    private RestTypeIdentifier REST_TYPE_IDENTIFIER = new RestTypeIdentifier();

    @Override
    public RestEventDto create(RestEventDto dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RestEventDto update(String id, RestEventDto dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<RestEventDto> readAll() {
        final ReadAllRestEventOutput output = serviceProcessor.process(new ReadAllRestEventInput());
        return output.getRestEvents();
    }

    @Override
    public RestEventDto read(String id) {
        final ReadRestEventOutput output = serviceProcessor.process(new ReadRestEventInput(id));
        return output.getRestEvent();
    }

    @Override
    public TypeIdentifier getTypeIdentifier() {
         return REST_TYPE_IDENTIFIER;
    }

    @Override
    public RestEventDto convertType(EventDto parent) {
        return new RestEventDto(parent);
    }

}
