package com.fortmocks.mock.soap.model.event.service.message.output;

import com.fortmocks.core.basis.model.Output;
import com.fortmocks.mock.soap.model.event.dto.SoapEventDto;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadSoapEventsByOperationIdOutput implements Output {

    private List<SoapEventDto> soapEvents;

    public ReadSoapEventsByOperationIdOutput(List<SoapEventDto> soapEvents) {
        this.soapEvents = soapEvents;
    }

    public List<SoapEventDto> getSoapEvents() {
        return soapEvents;
    }

    public void setSoapEvents(List<SoapEventDto> soapEvents) {
        this.soapEvents = soapEvents;
    }
}
