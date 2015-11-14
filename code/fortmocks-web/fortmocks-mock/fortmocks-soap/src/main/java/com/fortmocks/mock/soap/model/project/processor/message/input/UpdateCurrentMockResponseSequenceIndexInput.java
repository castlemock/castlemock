package com.fortmocks.mock.soap.model.project.processor.message.input;

import com.fortmocks.core.model.Input;
import com.fortmocks.core.model.validation.NotNull;
import com.fortmocks.mock.soap.model.project.dto.SoapMockResponseDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateCurrentMockResponseSequenceIndexInput implements Input {

    @NotNull
    private Long soapOperationId;
    @NotNull
    private Integer currentResponseSequenceIndex;

    public UpdateCurrentMockResponseSequenceIndexInput(Long soapOperationId, Integer currentResponseSequenceIndex) {
        this.soapOperationId = soapOperationId;
        this.currentResponseSequenceIndex = currentResponseSequenceIndex;
    }

    public Long getSoapOperationId() {
        return soapOperationId;
    }

    public void setSoapOperationId(Long soapOperationId) {
        this.soapOperationId = soapOperationId;
    }

    public Integer getCurrentResponseSequenceIndex() {
        return currentResponseSequenceIndex;
    }

    public void setCurrentResponseSequenceIndex(Integer currentResponseSequenceIndex) {
        this.currentResponseSequenceIndex = currentResponseSequenceIndex;
    }
}
