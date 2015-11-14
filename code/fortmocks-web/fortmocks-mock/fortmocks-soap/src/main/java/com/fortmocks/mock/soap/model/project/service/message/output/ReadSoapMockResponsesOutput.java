package com.fortmocks.mock.soap.model.project.service.message.output;

import com.fortmocks.core.model.Output;
import com.fortmocks.mock.soap.model.project.dto.SoapMockResponseDto;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadSoapMockResponsesOutput implements Output{

    private List<SoapMockResponseDto> soapMockResponses;

    public ReadSoapMockResponsesOutput(List<SoapMockResponseDto> soapMockResponses) {
        this.soapMockResponses = soapMockResponses;
    }

    public List<SoapMockResponseDto> getSoapMockResponses() {
        return soapMockResponses;
    }

    public void setSoapMockResponses(List<SoapMockResponseDto> soapMockResponses) {
        this.soapMockResponses = soapMockResponses;
    }
}
