package com.castlemock.repository.rest.file.project.converter;

import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.repository.rest.file.project.model.RestMethodFile;

public final class RestMethodFileConverter {

    private RestMethodFileConverter() {

    }

    public static RestMethod toRestMethod(final RestMethodFile restMethodFile) {
        return RestMethod.builder()
                .id(restMethodFile.getId())
                .name(restMethodFile.getName())
                .httpMethod(restMethodFile.getHttpMethod())
                .status(restMethodFile.getStatus())
                .resourceId(restMethodFile.getResourceId())
                .forwardedEndpoint(restMethodFile.getForwardedEndpoint())
                .networkDelay(restMethodFile.getNetworkDelay())
                .defaultMockResponseId(restMethodFile.getDefaultMockResponseId())
                .currentResponseSequenceIndex(restMethodFile.getCurrentResponseSequenceIndex())
                .defaultBody(restMethodFile.getDefaultBody())
                .responseStrategy(restMethodFile.getResponseStrategy())
                .simulateNetworkDelay(restMethodFile.getSimulateNetworkDelay())
                .automaticForward(restMethodFile.getAutomaticForward())
                .uri(null)
                .build();
    }

}
