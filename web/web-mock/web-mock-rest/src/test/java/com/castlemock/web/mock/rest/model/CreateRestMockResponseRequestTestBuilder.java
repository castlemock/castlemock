package com.castlemock.web.mock.rest.model;

import com.castlemock.model.mock.rest.domain.RestMockResponseStatus;

import java.util.List;

public final class CreateRestMockResponseRequestTestBuilder {

    private CreateRestMockResponseRequestTestBuilder() {

    }

    public static CreateRestMockResponseRequest.Builder builder() {
        return CreateRestMockResponseRequest
                .builder()
                .name("Response: successful operation (application/xml)")
                .body("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><User><id>${RANDOM_LONG()}</id><username>${RANDOM_STRING()}</username><firstName>${RANDOM_STRING()}</firstName><lastName>${RANDOM_STRING()}</lastName><email>${RANDOM_STRING()}</email><password>${RANDOM_STRING()}</password><phone>${RANDOM_STRING()}</phone><userStatus>${RANDOM_INTEGER()}</userStatus></User>")
                .status(RestMockResponseStatus.ENABLED)
                .usingExpressions(true)
                .httpStatusCode(200)
                .httpHeaders(List.of())
                .contentEncodings(List.of())
                .parameterQueries(List.of())
                .xpathExpressions(List.of())
                .jsonPathExpressions(List.of())
                .headerQueries(List.of());
    }

    public static CreateRestMockResponseRequest build() {
        return builder().build();
    }

}
