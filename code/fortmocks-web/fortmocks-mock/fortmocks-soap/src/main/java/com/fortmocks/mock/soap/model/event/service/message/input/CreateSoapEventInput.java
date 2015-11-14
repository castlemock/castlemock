package com.fortmocks.mock.soap.model.event.service.message.input;

import com.fortmocks.core.model.Input;
import com.fortmocks.mock.soap.model.event.dto.SoapEventDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateSoapEventInput implements Input {

    private SoapEventDto soapEvent;

    public CreateSoapEventInput(SoapEventDto soapEvent) {
        this.soapEvent = soapEvent;
    }

    public SoapEventDto getSoapEvent() {
        return soapEvent;
    }

    public void setSoapEvent(SoapEventDto soapEvent) {
        this.soapEvent = soapEvent;
    }
}
