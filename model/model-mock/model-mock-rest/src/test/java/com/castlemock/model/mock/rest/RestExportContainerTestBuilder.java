package com.castlemock.model.mock.rest;

import com.castlemock.model.mock.rest.domain.RestApplicationTestBuilder;
import com.castlemock.model.mock.rest.domain.RestMethodTestBuilder;
import com.castlemock.model.mock.rest.domain.RestMockResponseTestBuilder;
import com.castlemock.model.mock.rest.domain.RestProjectTestBuilder;
import com.castlemock.model.mock.rest.domain.RestResourceTestBuilder;

import java.util.List;

public final class RestExportContainerTestBuilder {
    
    private RestExportContainerTestBuilder() {
        
    }

    public static RestExportContainer.Builder builder() {
        return RestExportContainer.builder()
                .project(RestProjectTestBuilder.builder().build())
                .applications(List.of(RestApplicationTestBuilder.builder().build()))
                .methods(List.of(RestMethodTestBuilder.builder().build()))
                .mockResponses(List.of(RestMockResponseTestBuilder.builder().build()))
                .resources(List.of(RestResourceTestBuilder.builder().build()));
    }

    public static RestExportContainer build() {
        return builder().build();
    }
    
}
