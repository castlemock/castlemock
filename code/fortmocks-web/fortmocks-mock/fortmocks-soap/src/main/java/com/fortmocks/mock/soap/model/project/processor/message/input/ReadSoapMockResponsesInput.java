package com.fortmocks.mock.soap.model.project.processor.message.input;

import com.fortmocks.core.model.Input;
import com.fortmocks.core.model.validation.NotNull;
import com.fortmocks.mock.soap.model.project.domain.SoapMockResponseStatus;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadSoapMockResponsesInput implements Input {

    @NotNull
    private Long soapOperationId;
    @NotNull
    private SoapMockResponseStatus status;

    public Long getSoapOperationId() {
        return soapOperationId;
    }

    public void setSoapOperationId(Long soapOperationId) {
        this.soapOperationId = soapOperationId;
    }

    public SoapMockResponseStatus getStatus() {
        return status;
    }

    public void setStatus(SoapMockResponseStatus status) {
        this.status = status;
    }
}
