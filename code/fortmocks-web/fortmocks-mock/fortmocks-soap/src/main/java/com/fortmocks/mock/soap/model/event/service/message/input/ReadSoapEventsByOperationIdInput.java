package com.fortmocks.mock.soap.model.event.service.message.input;

import com.fortmocks.core.basis.model.Input;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadSoapEventsByOperationIdInput implements Input {

    private Long operationId;

    public ReadSoapEventsByOperationIdInput(Long operationId) {
        this.operationId = operationId;
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }
}
