package com.castlemock.repository.rest.file.project.converter;

import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.repository.rest.file.project.model.RestMethodFile;

public final class RestMethodConverter {

    private RestMethodConverter() {

    }

    public static RestMethodFile toRestMethod(final RestMethod restMethod) {
        return RestMethodFile.builder()
                .id(restMethod.getId())
                .name(restMethod.getName())
                .httpMethod(restMethod.getHttpMethod())
                .status(restMethod.getStatus())
                .resourceId(restMethod.getResourceId())
                .currentResponseSequenceIndex(restMethod.getCurrentResponseSequenceIndex())
                .responseStrategy(restMethod.getResponseStrategy())
                .simulateNetworkDelay(restMethod.getSimulateNetworkDelay()
                        .orElse(null))
                .automaticForward(restMethod.getAutomaticForward()
                        .orElse(null))
                .forwardedEndpoint(restMethod.getForwardedEndpoint()
                        .orElse(null))
                .networkDelay(restMethod.getNetworkDelay()
                        .orElse(null))
                .defaultMockResponseId(restMethod.getDefaultMockResponseId()
                        .orElse(null))
                .defaultBody(restMethod.getDefaultBody()
                        .orElse(null))
                .build();
    }

}
