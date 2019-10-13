package com.castlemock.core.mock.soap.model.project.domain;

public final class SoapXPathExpressionTestBuilder {

    private String expression;

    private SoapXPathExpressionTestBuilder() {
        this.expression = "//Request/Name[text()='Input1']";
    }

    public SoapXPathExpressionTestBuilder expression(String expression) {
        this.expression = expression;
        return this;
    }

    public SoapXPathExpression build() {
        return SoapXPathExpression.builder()
                .expression(expression)
                .build();
    }

}
