package com.fortmocks.mock.soap.model.project.processor.message.input;

import com.fortmocks.core.model.Input;
import com.fortmocks.core.model.validation.NotNull;
import com.fortmocks.mock.soap.model.project.domain.SoapOperationStatus;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateSoapOperationsStatusInput implements Input {

    @NotNull
    private Long soapProjectId;
    @NotNull
    private Long soapPortId;
    @NotNull
    private Long soapOperationId;
    @NotNull
    private SoapOperationStatus soapOperationStatus;

    public UpdateSoapOperationsStatusInput(Long soapProjectId, Long soapPortId, Long soapOperationId, SoapOperationStatus soapOperationStatus) {
        this.soapProjectId = soapProjectId;
        this.soapPortId = soapPortId;
        this.soapOperationId = soapOperationId;
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

    public Long getSoapOperationId() {
        return soapOperationId;
    }

    public void setSoapOperationId(Long soapOperationId) {
        this.soapOperationId = soapOperationId;
    }

    public SoapOperationStatus getSoapOperationStatus() {
        return soapOperationStatus;
    }

    public void setSoapOperationStatus(SoapOperationStatus soapOperationStatus) {
        this.soapOperationStatus = soapOperationStatus;
    }
}
