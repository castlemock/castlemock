package com.castlemock.web.mock.rest.model;

import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.mock.rest.domain.RestMethodStatus;
import com.castlemock.model.mock.rest.domain.RestResponseStrategy;

public final class UpdateRestMethodRequestTestBuilder {

    private UpdateRestMethodRequestTestBuilder() {

    }

    public static UpdateRestMethodRequest.Builder builder() {
        return UpdateRestMethodRequest.builder()
                .name("getUserByName")
                .httpMethod(HttpMethod.GET)
                .forwardedEndpoint("")
                .status(RestMethodStatus.MOCKED)
                .responseStrategy(RestResponseStrategy.RANDOM)
                .simulateNetworkDelay(Boolean.FALSE)
                .networkDelay(0L)
                .defaultMockResponseId("")
                .automaticForward(Boolean.FALSE);
    }

    public static UpdateRestMethodRequest build() {
        return builder().build();
    }

}
