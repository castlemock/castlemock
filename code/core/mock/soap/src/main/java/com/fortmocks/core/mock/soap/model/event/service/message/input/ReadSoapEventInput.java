package com.fortmocks.core.mock.soap.model.event.service.message.input;

import com.fortmocks.core.basis.model.Input;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadSoapEventInput implements Input {

    private Long soapEventId;

    public ReadSoapEventInput(Long soapEventId) {
        this.soapEventId = soapEventId;
    }

    public Long getSoapEventId() {
        return soapEventId;
    }

    public void setSoapEventId(Long soapEventId) {
        this.soapEventId = soapEventId;
    }

}
