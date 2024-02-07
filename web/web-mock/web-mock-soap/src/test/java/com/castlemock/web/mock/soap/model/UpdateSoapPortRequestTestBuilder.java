package com.castlemock.web.mock.soap.model;

public final class UpdateSoapPortRequestTestBuilder {

    private UpdateSoapPortRequestTestBuilder() {

    }

    public static UpdateSoapPortRequest.Builder builder() {
        return UpdateSoapPortRequest.builder()
                .uri("http://localhost:8080/");
    }

    public static UpdateSoapPortRequest build() {
        return builder().build();
    }

}
