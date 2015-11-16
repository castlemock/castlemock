package com.fortmocks.web.mock.soap.model.event.service.adapter;

import com.fortmocks.core.basis.model.TypeIdentifier;
import com.fortmocks.core.basis.model.event.dto.EventDto;
import com.fortmocks.core.basis.model.event.service.EventServiceAdapter;
import com.fortmocks.core.mock.soap.model.event.dto.SoapEventDto;
import com.fortmocks.core.mock.soap.model.event.service.message.output.ReadAllSoapEventOutput;
import com.fortmocks.web.mock.soap.model.SoapTypeIdentifier;
import com.fortmocks.core.mock.soap.model.event.service.message.input.ReadAllSoapEventInput;
import com.fortmocks.core.mock.soap.model.event.service.message.input.ReadSoapEventInput;
import com.fortmocks.core.mock.soap.model.event.service.message.output.ReadSoapEventOutput;
import com.fortmocks.web.basis.service.ServiceProcessor;
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
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SoapEventDto update(Long id, SoapEventDto dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<SoapEventDto> readAll() {
        final ReadAllSoapEventOutput output = serviceProcessor.process(new ReadAllSoapEventInput());
        return output.getSoapEvents();
    }

    @Override
    public SoapEventDto read(Long id) {
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
