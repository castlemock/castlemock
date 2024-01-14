package com.castlemock.model.mock.rest.domain;

public final class RestJsonPathExpressionTestBuilder {

    private RestJsonPathExpressionTestBuilder() {
    }


    public static RestJsonPathExpression.Builder builder(){
        return RestJsonPathExpression.builder()
                .expression("$.store.book[?(@.price < 10)]");
    }

    public static RestJsonPathExpression build() {
        return builder().build();
    }

}
