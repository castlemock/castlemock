package com.fortmocks.mock.rest.model.project.processor.message.output;

import com.fortmocks.core.model.Output;
import com.fortmocks.core.model.validation.NotNull;
import com.fortmocks.mock.rest.model.project.dto.RestMethodDto;
import com.fortmocks.mock.rest.model.project.dto.RestMockResponseDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateRestMockResponseOutput implements Output {

    @NotNull
    private RestMockResponseDto restMockResponse;

    public CreateRestMockResponseOutput(RestMockResponseDto restMockResponse) {
        this.restMockResponse = restMockResponse;
    }

    public RestMockResponseDto getRestMockResponse() {
        return restMockResponse;
    }

    public void setRestMockResponse(RestMockResponseDto restMockResponse) {
        this.restMockResponse = restMockResponse;
    }
}
