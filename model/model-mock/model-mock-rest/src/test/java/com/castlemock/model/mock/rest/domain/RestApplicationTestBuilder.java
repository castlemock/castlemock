package com.castlemock.model.mock.rest.domain;

import java.util.List;
import java.util.Map;

public final class RestApplicationTestBuilder {

    private RestApplicationTestBuilder() {

    }

    public static RestApplication.Builder builder(){
        return RestApplication.builder()
                .id("QLP2Nk")
                .name("Swagger Petstore")
                .projectId("EqbLQU")
                .resources(List.of())
                .statusCount(Map.of());
    }

    public static RestApplication build() {
        return builder().build();
    }
}
