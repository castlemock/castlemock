package com.castlemock.web.mock.rest.model;

import com.castlemock.model.core.http.HttpMethod;

public final class CreateRestMethodRequestTestBuilder {

    private CreateRestMethodRequestTestBuilder() {

    }

    public static CreateRestMethodRequest.Builder builder() {
        return CreateRestMethodRequest.builder()
                .name("Rest Method")
                .httpMethod(HttpMethod.GET);
    }

    public static CreateRestMethodRequest build() {
        return builder().build();
    }

}
