package com.castlemock.model.mock.soap.domain;

import com.castlemock.model.core.http.HttpMethod;

import java.util.List;

public final class SoapOperationTestBuilder {

    private SoapOperationTestBuilder() {

    }

    public static SoapOperation.Builder builder(){
        return SoapOperation.builder()
                .currentResponseSequenceIndex(0)
                .defaultBody("Default body")
                .defaultMockResponseId(null)
                .defaultResponseName(null)
                .forwardedEndpoint("Forwarded event")
                .httpMethod(HttpMethod.POST)
                .id("SOAP OPERATION")
                .identifier("soapoperation")
                .identifyStrategy(SoapOperationIdentifyStrategy.ELEMENT_NAMESPACE)
                .invokeAddress("Invoke address")
                .mockOnFailure(Boolean.FALSE)
                .mockResponses(List.of())
                .name("Soap operation name")
                .networkDelay(1000L)
                .operationIdentifier( SoapOperationIdentifierTestBuilder.builder().build())
                .originalEndpoint("Original endpoint")
                .portId("port id")
                .responseStrategy(SoapResponseStrategy.SEQUENCE)
                .simulateNetworkDelay(Boolean.FALSE)
                .soapVersion(SoapVersion.SOAP11)
                .status(SoapOperationStatus.MOCKED)
                .automaticForward(Boolean.FALSE);
    }

    public static SoapOperation build() {
        return builder().build();

    }

}
