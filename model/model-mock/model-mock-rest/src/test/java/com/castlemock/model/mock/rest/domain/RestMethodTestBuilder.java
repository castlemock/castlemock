package com.castlemock.model.mock.rest.domain;

import com.castlemock.model.core.http.HttpMethod;

import java.util.List;

public final class RestMethodTestBuilder {

    private RestMethodTestBuilder(){

    }

    public static RestMethod.Builder builder() {
        return RestMethod.builder()
                .id("OSIFJL")
                .name("getUserByName")
                .resourceId("9gsIpq")
                .defaultBody("")
                .httpMethod(HttpMethod.GET)
                .forwardedEndpoint("")
                .status(RestMethodStatus.MOCKED)
                .responseStrategy(RestResponseStrategy.RANDOM)
                .currentResponseSequenceIndex(0)
                .simulateNetworkDelay(Boolean.FALSE)
                .networkDelay(0L)
                .defaultMockResponseId("")
                .mockResponses(List.of())
                .uri("/")
                .defaultResponseName("")
                .automaticForward(Boolean.FALSE)
                .mockResponses(List.of(RestMockResponseTestBuilder.builder().build()));
    }

    public static RestMethod build() {
        return builder().build();
    }
}
