package com.castlemock.web.mock.rest.model;

public final class CreateRestResourceRequestTestBuilder {

    private CreateRestResourceRequestTestBuilder() {

    }

    public static CreateRestResourceRequest.Builder builder() {
        return CreateRestResourceRequest.builder()
                .name("Resource name")
                .uri("/path/to/resource");
    }

    public static CreateRestResourceRequest build() {
        return builder().build();
    }

}
