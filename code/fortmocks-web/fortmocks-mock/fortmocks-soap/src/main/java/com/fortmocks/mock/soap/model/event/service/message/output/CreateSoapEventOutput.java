package com.fortmocks.mock.soap.model.event.service.message.output;

import com.fortmocks.core.model.Output;
import com.fortmocks.mock.soap.model.event.dto.SoapEventDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateSoapEventOutput implements Output {

    private SoapEventDto createdSoapEvent;

    public CreateSoapEventOutput(SoapEventDto createdSoapEvent) {
        this.createdSoapEvent = createdSoapEvent;
    }

    public SoapEventDto getCreatedSoapEvent() {
        return createdSoapEvent;
    }

    public void setCreatedSoapEvent(SoapEventDto createdSoapEvent) {
        this.createdSoapEvent = createdSoapEvent;
    }
}
