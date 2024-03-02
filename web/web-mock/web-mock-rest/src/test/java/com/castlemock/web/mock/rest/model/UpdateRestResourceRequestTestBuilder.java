package com.castlemock.web.mock.rest.model;

public final class UpdateRestResourceRequestTestBuilder {

    private UpdateRestResourceRequestTestBuilder() {

    }

    public static UpdateRestResourceRequest.Builder builder() {
        return UpdateRestResourceRequest.builder()
                .name("Resource name")
                .uri("http://localhost/resource.xml");
    }

    public static UpdateRestResourceRequest build() {
        return builder().build();
    }

}
