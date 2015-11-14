package com.fortmocks.core.mock.rest.model.event.service.adapter;

import com.fortmocks.core.basis.model.TypeIdentifier;
import com.fortmocks.core.basis.model.event.service.EventServiceAdapter;
import com.fortmocks.web.mock.rest.model.RestTypeIdentifier;
import com.fortmocks.core.mock.rest.model.event.dto.RestEventDto;
import com.fortmocks.core.mock.rest.model.event.service.message.input.ReadAllRestEventInput;
import com.fortmocks.core.mock.rest.model.event.service.message.input.ReadRestEventInput;
import com.fortmocks.core.mock.rest.model.event.service.message.output.ReadAllRestEventOutput;
import com.fortmocks.core.mock.rest.model.event.service.message.output.ReadRestEventOutput;
import com.fortmocks.web.basis.service.ServiceProcessor;
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
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RestEventDto update(Long id, RestEventDto dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<RestEventDto> readAll() {
        final ReadAllRestEventOutput output = serviceProcessor.process(new ReadAllRestEventInput());
        return output.getRestEvents();
    }

    @Override
    public RestEventDto read(Long id) {
        final ReadRestEventOutput output = serviceProcessor.process(new ReadRestEventInput(id));
        return output.getRestEvent();
    }

    @Override
    public TypeIdentifier getTypeIdentifier() {
         return REST_TYPE_IDENTIFIER;
    }

    @Override
    public RestEventDto convertType(RestEventDto parent) {
        return new RestEventDto(parent);
    }

}
