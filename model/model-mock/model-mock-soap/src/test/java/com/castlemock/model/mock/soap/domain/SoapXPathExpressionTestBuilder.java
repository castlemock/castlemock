package com.castlemock.model.mock.soap.domain;

public final class SoapXPathExpressionTestBuilder {

    private SoapXPathExpressionTestBuilder() {
    }

    public static SoapXPathExpression.Builder builder() {
        return SoapXPathExpression.builder()
                .expression("//Request/Name[text()='Input1']");
    }

    public static SoapXPathExpression build() {
        return builder().build();
    }

}
