package com.fortmocks.mock.soap.model.project.processor.message.input;

import com.fortmocks.core.model.Input;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadSoapProjectInput implements Input {

    private Long soapProjectId;

    public Long getSoapProjectId() {
        return soapProjectId;
    }

    public void setSoapProjectId(Long soapProjectId) {
        this.soapProjectId = soapProjectId;
    }
}
