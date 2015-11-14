package com.fortmocks.mock.soap.model.project.processor.message.input;

import com.fortmocks.core.model.Input;
import com.fortmocks.core.model.validation.NotNull;
import com.fortmocks.mock.soap.model.project.dto.SoapMockResponseDto;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class DeleteSoapMockResponsesInput implements Input{

    @NotNull
    private Long soapProjectId;
    @NotNull
    private Long soapPortId;
    @NotNull
    private Long soapOperationId;
    @NotNull
    private List<SoapMockResponseDto> mockResponses;

    public DeleteSoapMockResponsesInput(Long soapProjectId, Long soapPortId, Long soapOperationId, List<SoapMockResponseDto> mockResponses) {
        this.soapProjectId = soapProjectId;
        this.soapPortId = soapPortId;
        this.soapOperationId = soapOperationId;
        this.mockResponses = mockResponses;
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

    public List<SoapMockResponseDto> getMockResponses() {
        return mockResponses;
    }

    public void setMockResponses(List<SoapMockResponseDto> mockResponses) {
        this.mockResponses = mockResponses;
    }
}
