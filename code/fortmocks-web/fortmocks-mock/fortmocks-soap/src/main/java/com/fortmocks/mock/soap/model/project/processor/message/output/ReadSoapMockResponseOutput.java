package com.fortmocks.mock.soap.model.project.processor.message.output;

import com.fortmocks.core.model.Output;
import com.fortmocks.mock.soap.model.project.dto.SoapMockResponseDto;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadSoapMockResponseOutput implements Output{

    private SoapMockResponseDto soapMockResponse;

    public ReadSoapMockResponseOutput(SoapMockResponseDto soapMockResponse) {
        this.soapMockResponse = soapMockResponse;
    }

    public SoapMockResponseDto getSoapMockResponse() {
        return soapMockResponse;
    }

    public void setSoapMockResponse(SoapMockResponseDto soapMockResponse) {
        this.soapMockResponse = soapMockResponse;
    }
}
