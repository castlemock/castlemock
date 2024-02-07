package com.castlemock.web.mock.rest.converter;

import com.castlemock.service.mock.rest.project.input.UpdateRestMockResponseInput;
import com.castlemock.web.mock.rest.model.UpdateRestMockResponseRequest;

public final class UpdateRestMockResponseRequestConverter {

    private UpdateRestMockResponseRequestConverter() {

    }

    public static UpdateRestMockResponseInput toUpdateRestMockResponseInput(final UpdateRestMockResponseRequest request,
                                                                            final String projectId, final String applicationId,
                                                                            final String resourceId, final String methodId,
                                                                            final String responseId) {
        return UpdateRestMockResponseInput.builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodId(methodId)
                .mockResponseId(responseId)
                .body(request.getBody().orElse(null))
                .contentEncodings(request.getContentEncodings())
                .headerQueries(request.getHeaderQueries())
                .httpHeaders(request.getHttpHeaders())
                .httpStatusCode(request.getHttpStatusCode())
                .jsonPathExpressions(request.getJsonPathExpressions())
                .name(request.getName())
                .parameterQueries(request.getParameterQueries())
                .status(request.getStatus())
                .usingExpressions(request.getUsingExpressions()
                        .orElse(null))
                .xpathExpressions(request.getXpathExpressions())
                .build();
    }

}
