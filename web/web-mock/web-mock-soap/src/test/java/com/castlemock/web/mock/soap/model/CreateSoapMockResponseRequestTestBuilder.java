package com.castlemock.web.mock.soap.model;

import com.castlemock.model.mock.soap.domain.SoapMockResponseStatus;
import com.castlemock.model.mock.soap.domain.SoapXPathExpression;

import java.util.List;

public final class CreateSoapMockResponseRequestTestBuilder {

    private CreateSoapMockResponseRequestTestBuilder() {

    }

    public static CreateSoapMockResponseRequest.Builder builder() {
        return CreateSoapMockResponseRequest.builder()
                .body("Soap mock response body")
                .httpHeaders(List.of())
                .httpStatusCode(200)
                .name("Soap mock response name")
                .status(SoapMockResponseStatus.ENABLED)
                .usingExpressions(Boolean.TRUE)
                .xpathExpressions(List.of(SoapXPathExpression.builder()
                        .expression("//Request/Name[text()='Input1']")
                        .build(), SoapXPathExpression.builder()
                        .expression("//Request/Name[text()='Input2']")
                        .build()));
    }

    public static CreateSoapMockResponseRequest build() {
        return builder().build();
    }

}
