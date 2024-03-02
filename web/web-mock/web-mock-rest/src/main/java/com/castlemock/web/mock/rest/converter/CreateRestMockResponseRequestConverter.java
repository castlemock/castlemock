package com.castlemock.web.mock.rest.converter;

import com.castlemock.service.mock.rest.project.input.CreateRestMockResponseInput;
import com.castlemock.web.mock.rest.model.CreateRestMockResponseRequest;

public final class CreateRestMockResponseRequestConverter {

    private CreateRestMockResponseRequestConverter() {

    }

    public static CreateRestMockResponseInput toCreateRestMockResponseInput(final CreateRestMockResponseRequest request,
                                                                            final String projectId, final String applicationId,
                                                                            final String resourceId, final String methodId) {
        return CreateRestMockResponseInput.builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodId(methodId)
                .body(request.getBody().orElse(null))
                .contentEncodings(request.getContentEncodings())
                .headerQueries(request.getHeaderQueries())
                .httpHeaders(request.getHttpHeaders())
                .httpStatusCode(request.getHttpStatusCode()
                        .orElse(null))
                .jsonPathExpressions(request.getJsonPathExpressions())
                .name(request.getName())
                .parameterQueries(request.getParameterQueries())
                .status(request.getStatus()
                        .orElse(null))
                .usingExpressions(request.getUsingExpressions()
                        .orElse(null))
                .xpathExpressions(request.getXpathExpressions())
                .build();
    }

}
