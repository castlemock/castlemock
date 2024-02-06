package com.castlemock.web.mock.rest.model;

import com.castlemock.model.mock.rest.RestDefinitionType;

public final class LinkDefinitionRequestTestBuilder {

    private LinkDefinitionRequestTestBuilder() {

    }

    public static LinkDefinitionRequest.Builder builder() {
        return LinkDefinitionRequest.builder()
                .definitionType(RestDefinitionType.RAML)
                .generateResponse(true)
                .url("/path/to/resource");
    }

    public static LinkDefinitionRequest build() {
        return builder().build();
    }

}
