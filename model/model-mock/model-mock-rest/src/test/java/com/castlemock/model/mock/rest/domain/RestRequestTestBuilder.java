package com.castlemock.model.mock.rest.domain;

import com.castlemock.model.core.http.HttpMethod;

import java.util.List;

public final class RestRequestTestBuilder {

    private RestRequestTestBuilder() {

    }

    public static RestRequest.Builder builder(){
        return RestRequest.builder()
                .uri("/test")
                .body("Rest request body")
                .contentType("application/json")
                .httpMethod(HttpMethod.POST)
                .httpHeaders(List.of())
                .httpParameters(List.of());
    }

    public static RestRequest build() {
        return builder().build();
    }
}
