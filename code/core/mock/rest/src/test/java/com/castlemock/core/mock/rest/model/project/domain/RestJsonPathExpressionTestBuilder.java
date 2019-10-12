package com.castlemock.core.mock.rest.model.project.domain;

public final class RestJsonPathExpressionTestBuilder {

    private String expression;

    private RestJsonPathExpressionTestBuilder() {
        this.expression = "$.store.book[?(@.price < 10)]";
    }

    public RestJsonPathExpressionTestBuilder expression(final String expression) {
        this.expression = expression;
        return this;
    }

    public static RestJsonPathExpressionTestBuilder builder(){
        return new RestJsonPathExpressionTestBuilder();
    }

    public RestJsonPathExpression build() {
        return RestJsonPathExpression.builder()
                .expression(expression)
                .build();
    }
}
