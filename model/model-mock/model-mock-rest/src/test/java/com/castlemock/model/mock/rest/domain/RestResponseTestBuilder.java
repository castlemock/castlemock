package com.castlemock.model.mock.rest.domain;

import java.util.List;

public final class RestResponseTestBuilder {


    private RestResponseTestBuilder() {

    }

    public static RestResponse.Builder builder() {
        return RestResponse.builder()
                .body("")
                .contentType("application/json")
                .contentEncodings(List.of())
                .httpHeaders(List.of())
                .httpStatusCode(200)
                .mockResponseName("Response name");
    }

    public static RestResponse build() {
        return builder().build();
    }

}
