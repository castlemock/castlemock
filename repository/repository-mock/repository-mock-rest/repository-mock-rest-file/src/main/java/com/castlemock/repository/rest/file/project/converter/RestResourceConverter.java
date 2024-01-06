package com.castlemock.repository.rest.file.project.converter;

import com.castlemock.model.mock.rest.domain.RestResource;
import com.castlemock.repository.rest.file.project.model.RestResourceFile;

public final class RestResourceConverter {

    private RestResourceConverter() {

    }

    public static RestResourceFile toRestResourceFile(final RestResource restResource) {
        return RestResourceFile.builder()
                .id(restResource.getId())
                .name(restResource.getName())
                .uri(restResource.getUri())
                .applicationId(restResource.getApplicationId())
                .build();
    }
}
