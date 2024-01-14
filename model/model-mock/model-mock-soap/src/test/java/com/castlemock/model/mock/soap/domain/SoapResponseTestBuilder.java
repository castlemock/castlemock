package com.castlemock.model.mock.soap.domain;

import java.util.List;

public final class SoapResponseTestBuilder {

    private SoapResponseTestBuilder() {

    }

    public static SoapResponse.Builder builder() {
        return SoapResponse.builder()
                .body("")
                .contentType("application/json")
                .httpStatusCode(200)
                .mockResponseName("")
                .httpHeaders(List.of())
                .contentEncodings(List.of());
    }

    public static SoapResponse build() {
        return builder().build();
    }

}
