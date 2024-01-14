package com.castlemock.model.mock.rest.domain;

public final class RestParameterQueryTestBuilder {


    private RestParameterQueryTestBuilder() {
    }

    public static RestParameterQuery.Builder builder(){
        return RestParameterQuery.builder()
                .parameter("user")
                .query("karl")
                .matchCase(false)
                .matchAny(false)
                .matchRegex(false)
                .urlEncoded(false);
    }

    public static RestParameterQuery build() {
        return builder().build();
    }

}
