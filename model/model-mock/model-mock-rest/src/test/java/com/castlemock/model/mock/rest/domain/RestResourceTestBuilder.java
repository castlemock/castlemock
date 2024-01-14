package com.castlemock.model.mock.rest.domain;

import java.util.List;
import java.util.Map;

public final class RestResourceTestBuilder {


    private RestResourceTestBuilder(){

    }

    public static RestResource.Builder builder(){
        return RestResource
                .builder()
                .id("9gsIpq")
                .name("Users")
                .uri("/user/{username}")
                .applicationId("QLP2Nk")
                .invokeAddress("http://localhost:8080/mock/rest/project/EqbLQU/application/QLP2Nk/user/{username}")
                .methods(List.of(RestMethodTestBuilder.build()))
                .statusCount(Map.of());
    }

    public static RestResource build() {
        return builder().build();
    }
}
