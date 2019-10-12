package com.castlemock.core.mock.rest.model.project.domain;

public final class RestXPathExpressionTestBuilder {

    private String expression;

    private RestXPathExpressionTestBuilder() {
        this.expression = "//GetWhoISResponse/GetWhoISResult[text()='google.com is a good domain']";
    }

    public RestXPathExpressionTestBuilder expression(final String expression) {
        this.expression = expression;
        return this;
    }

    public static RestXPathExpressionTestBuilder builder(){
        return new RestXPathExpressionTestBuilder();
    }

    public RestXPathExpression build() {
        return RestXPathExpression
                .builder()
                .expression(expression)
                .build();
    }
}
