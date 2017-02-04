package com.castlemock.web.mock.rest.converter;


import com.castlemock.core.mock.rest.model.project.domain.RestMockResponseStatus;
import com.castlemock.core.mock.rest.model.project.dto.RestMockResponseDto;

abstract class AbstractRestDefinitionConverter implements RestDefinitionConverter {

    protected static final String AUTO_GENERATED_MOCK_RESPONSE_DEFAULT_NAME = "Auto-generated mocked response";

    /**
     * The method generates a default response.
     * @return The newly generated {@link RestMockResponseDto}.
     */
    protected RestMockResponseDto generateResponse(){
        RestMockResponseDto restMockResponse = new RestMockResponseDto();
        restMockResponse.setName(AUTO_GENERATED_MOCK_RESPONSE_DEFAULT_NAME);
        restMockResponse.setHttpStatusCode(200);
        restMockResponse.setStatus(RestMockResponseStatus.ENABLED);
        return restMockResponse;
    }

}
