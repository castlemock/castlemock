package com.fortmocks.mock.soap.model.project.processor.message.input;

import com.fortmocks.core.model.Input;
import com.fortmocks.core.model.validation.NotNull;
import com.fortmocks.mock.soap.model.project.domain.SoapOperationStatus;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateSoapPortsStatusInput implements Input {

    @NotNull
    private Long soapProjectId;
    @NotNull
    private Long soapPortId;
    @NotNull
    private SoapOperationStatus soapOperationStatus;

    public UpdateSoapPortsStatusInput(Long soapProjectId, Long soapPortId, SoapOperationStatus soapOperationStatus) {
        this.soapProjectId = soapProjectId;
        this.soapPortId = soapPortId;
        this.soapOperationStatus = soapOperationStatus;
    }

    public Long getSoapProjectId() {
        return soapProjectId;
    }

    public void setSoapProjectId(Long soapProjectId) {
        this.soapProjectId = soapProjectId;
    }

    public Long getSoapPortId() {
        return soapPortId;
    }

    public void setSoapPortId(Long soapPortId) {
        this.soapPortId = soapPortId;
    }

    public SoapOperationStatus getSoapOperationStatus() {
        return soapOperationStatus;
    }

    public void setSoapOperationStatus(SoapOperationStatus soapOperationStatus) {
        this.soapOperationStatus = soapOperationStatus;
    }
}
