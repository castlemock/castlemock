package com.fortmocks.mock.rest.model.project.processor.message.output;

import com.fortmocks.core.model.Output;
import com.fortmocks.mock.rest.model.project.domain.RestMockResponse;
import com.fortmocks.mock.rest.model.project.dto.RestMockResponseDto;
import com.fortmocks.mock.rest.model.project.dto.RestProjectDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadRestMockResponseOutput implements Output{

    private RestMockResponseDto restMockResponse;

    public ReadRestMockResponseOutput(RestMockResponseDto restMockResponse) {
        this.restMockResponse = restMockResponse;
    }

    public RestMockResponseDto getRestMockResponse() {
        return restMockResponse;
    }

    public void setRestMockResponse(RestMockResponseDto restMockResponse) {
        this.restMockResponse = restMockResponse;
    }
}
