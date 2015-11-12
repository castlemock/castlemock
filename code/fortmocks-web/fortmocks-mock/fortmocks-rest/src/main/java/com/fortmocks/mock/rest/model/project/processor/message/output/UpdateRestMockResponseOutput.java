package com.fortmocks.mock.rest.model.project.processor.message.output;

import com.fortmocks.core.model.Output;
import com.fortmocks.core.model.validation.NotNull;
import com.fortmocks.mock.rest.model.project.dto.RestMethodDto;
import com.fortmocks.mock.rest.model.project.dto.RestMockResponseDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateRestMockResponseOutput implements Output {

    @NotNull
    private RestMockResponseDto updatedRestMockResponse;

    public RestMockResponseDto getUpdatedRestMockResponse() {
        return updatedRestMockResponse;
    }

    public void setUpdatedRestMockResponse(RestMockResponseDto updatedRestMockResponse) {
        this.updatedRestMockResponse = updatedRestMockResponse;
    }
}
