package com.castlemock.web.mock.soap.model;

import com.castlemock.model.mock.soap.domain.SoapOperationIdentifyStrategy;
import com.castlemock.model.mock.soap.domain.SoapOperationStatus;
import com.castlemock.model.mock.soap.domain.SoapResponseStrategy;

public final class UpdateSoapOperationRequestTestBuilder {

    private UpdateSoapOperationRequestTestBuilder() {

    }

    public static UpdateSoapOperationRequest.Builder builder() {
        return UpdateSoapOperationRequest.builder()
                .defaultMockResponseId(null)
                .forwardedEndpoint("Forwarded event")
                .identifyStrategy(SoapOperationIdentifyStrategy.ELEMENT_NAMESPACE)
                .mockOnFailure(Boolean.FALSE)
                .networkDelay(1000L)
                .responseStrategy(SoapResponseStrategy.SEQUENCE)
                .simulateNetworkDelay(Boolean.FALSE)
                .status(SoapOperationStatus.MOCKED)
                .automaticForward(Boolean.FALSE);
    }

    public static UpdateSoapOperationRequest build() {
        return builder().build();
    }

}
