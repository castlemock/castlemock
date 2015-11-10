package com.fortmocks.mock.soap.model.project.message;

import com.fortmocks.core.model.Input;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class FindSoapProjectInput implements Input {

    private Long soapProjectId;

    public Long getSoapProjectId() {
        return soapProjectId;
    }

    public void setSoapProjectId(Long soapProjectId) {
        this.soapProjectId = soapProjectId;
    }
}
