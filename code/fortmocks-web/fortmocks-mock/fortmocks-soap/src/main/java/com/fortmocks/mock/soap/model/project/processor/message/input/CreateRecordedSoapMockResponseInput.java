package com.fortmocks.mock.soap.model.project.processor.message.input;

import com.fortmocks.core.model.Input;
import com.fortmocks.core.model.validation.NotNull;
import com.fortmocks.mock.soap.model.project.dto.SoapMockResponseDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateRecordedSoapMockResponseInput implements Input {

    @NotNull
    private Long soapOperationId;
    @NotNull
    private SoapMockResponseDto soapMockResponseDto;

    public CreateRecordedSoapMockResponseInput(Long soapOperationId, SoapMockResponseDto soapMockResponseDto) {
        this.soapOperationId = soapOperationId;
        this.soapMockResponseDto = soapMockResponseDto;
    }

    public Long getSoapOperationId() {
        return soapOperationId;
    }

    public void setSoapOperationId(Long soapOperationId) {
        this.soapOperationId = soapOperationId;
    }

    public SoapMockResponseDto getSoapMockResponseDto() {
        return soapMockResponseDto;
    }

    public void setSoapMockResponseDto(SoapMockResponseDto soapMockResponseDto) {
        this.soapMockResponseDto = soapMockResponseDto;
    }
}
