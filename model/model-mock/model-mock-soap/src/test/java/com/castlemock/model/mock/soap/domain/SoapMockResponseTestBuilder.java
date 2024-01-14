package com.castlemock.model.mock.soap.domain;

import java.util.List;

public final class SoapMockResponseTestBuilder {

    private SoapMockResponseTestBuilder() {

    }

    public static SoapMockResponse.Builder builder(){
        return SoapMockResponse.builder()
                .body("Soap mock response body")
                .contentEncodings(List.of())
                .httpHeaders(List.of())
                .httpStatusCode(200)
                .id("SOAP MOCK RESPONSE")
                .name("Soap mock response name")
                .operationId("OperationId")
                .status(SoapMockResponseStatus.ENABLED)
                .usingExpressions(Boolean.TRUE)
                .xpathExpressions(List.of(SoapXPathExpression.builder()
                        .expression("//Request/Name[text()='Input1']")
                        .build(), SoapXPathExpression.builder()
                        .expression("//Request/Name[text()='Input2']")
                        .build()));
    }


    public static SoapMockResponse build() {
        return builder().build();
    }

}
