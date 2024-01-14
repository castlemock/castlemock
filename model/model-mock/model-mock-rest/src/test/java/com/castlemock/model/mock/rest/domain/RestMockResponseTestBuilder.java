package com.castlemock.model.mock.rest.domain;

import java.util.List;

public final class RestMockResponseTestBuilder {

    private RestMockResponseTestBuilder() {

    }

    public static RestMockResponse.Builder builder(){
        return RestMockResponse
                .builder()
                .id("r1eXT3")
                .name("Response: successful operation (application/xml)")
                .body("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><User><id>${RANDOM_LONG()}</id><username>${RANDOM_STRING()}</username><firstName>${RANDOM_STRING()}</firstName><lastName>${RANDOM_STRING()}</lastName><email>${RANDOM_STRING()}</email><password>${RANDOM_STRING()}</password><phone>${RANDOM_STRING()}</phone><userStatus>${RANDOM_INTEGER()}</userStatus></User>")
                .methodId("OSIFJL")
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

    public static RestMockResponse build(){
        return builder().build();
    }

}
