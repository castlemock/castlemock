package com.fortmocks.mock.soap.model.project.processor.message.input;

import com.fortmocks.core.model.Input;
import com.fortmocks.core.model.validation.NotNull;
import com.fortmocks.mock.soap.model.project.domain.SoapMockResponseStatus;
import com.fortmocks.mock.soap.model.project.domain.SoapOperationStatus;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateSoapMockResponseStatusInput implements Input {

    @NotNull
    private Long soapProjectId;
    @NotNull
    private Long soapPortId;
    @NotNull
    private Long soapOperationId;
    @NotNull
    private Long soapMockResponseId;
    @NotNull
    private SoapMockResponseStatus soapMockResponseStatus;

    public UpdateSoapMockResponseStatusInput(Long soapProjectId, Long soapPortId, Long soapOperationId, Long soapMockResponseId, SoapMockResponseStatus soapMockResponseStatus) {
        this.soapProjectId = soapProjectId;
        this.soapPortId = soapPortId;
        this.soapOperationId = soapOperationId;
        this.soapMockResponseId = soapMockResponseId;
        this.soapMockResponseStatus = soapMockResponseStatus;
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

    public Long getSoapMockResponseId() {
        return soapMockResponseId;
    }

    public void setSoapMockResponseId(Long soapMockResponseId) {
        this.soapMockResponseId = soapMockResponseId;
    }

    public SoapMockResponseStatus getSoapMockResponseStatus() {
        return soapMockResponseStatus;
    }

    public void setSoapMockResponseStatus(SoapMockResponseStatus soapMockResponseStatus) {
        this.soapMockResponseStatus = soapMockResponseStatus;
    }
}
