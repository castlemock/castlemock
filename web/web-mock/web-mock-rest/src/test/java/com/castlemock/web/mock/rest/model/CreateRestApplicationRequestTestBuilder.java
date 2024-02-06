package com.castlemock.web.mock.rest.model;

public final class CreateRestApplicationRequestTestBuilder {

    private CreateRestApplicationRequestTestBuilder() {

    }

    public static CreateRestApplicationRequest.Builder builder() {
        return CreateRestApplicationRequest.builder()
                .name("Rest application");
    }

    public static CreateRestApplicationRequest build() {
        return builder().build();
    }

}
