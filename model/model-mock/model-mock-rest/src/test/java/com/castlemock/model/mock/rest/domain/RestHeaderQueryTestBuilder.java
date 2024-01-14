package com.castlemock.model.mock.rest.domain;

public final class RestHeaderQueryTestBuilder {

    private RestHeaderQueryTestBuilder() {
    }

    public static RestHeaderQuery.Builder builder(){
        return RestHeaderQuery.builder()
                .header("Content-Type")
                .query("json/application")
                .matchCase(false)
                .matchAny(false)
                .matchRegex(false);
    }

    public static RestHeaderQuery build() {
        return builder().build();
    }
}
