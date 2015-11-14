package com.fortmocks.mock.soap.model.project.service.message.input;

import com.fortmocks.core.model.Input;
import com.fortmocks.core.model.validation.NotNull;
import com.fortmocks.mock.soap.model.project.dto.SoapOperationDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateSoapOperationInput implements Input {

    @NotNull
    private Long soapProjectId;
    @NotNull
    private Long soapPortId;
    @NotNull
    private Long soapOperationId;
    @NotNull
    private SoapOperationDto updatedSoapOperation;

    public UpdateSoapOperationInput(Long soapProjectId, Long soapPortId, Long soapOperationId, SoapOperationDto updatedSoapOperation) {
        this.soapProjectId = soapProjectId;
        this.soapPortId = soapPortId;
        this.soapOperationId = soapOperationId;
        this.updatedSoapOperation = updatedSoapOperation;
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

    public SoapOperationDto getUpdatedSoapOperation() {
        return updatedSoapOperation;
    }

    public void setUpdatedSoapOperation(SoapOperationDto updatedSoapOperation) {
        this.updatedSoapOperation = updatedSoapOperation;
    }
}
