package com.castlemock.web.mock.rest.model;

import java.util.Set;

public final class DuplicateRestMockOperationsRequestTestBuilder {

    private DuplicateRestMockOperationsRequestTestBuilder() {

    }

    public static DuplicateRestMockResponsesRequest.Builder builder() {
        return DuplicateRestMockResponsesRequest.builder()
                .mockResponseIds(Set.of("123", "456"));
    }

    public static DuplicateRestMockResponsesRequest build() {
        return builder().build();
    }

}
