package com.castlemock.repository.rest.file.project.converter;

import com.castlemock.model.mock.rest.domain.RestResource;
import com.castlemock.repository.rest.file.project.model.RestResourceFile;

public final class RestResourceFileConverter {

    private RestResourceFileConverter() {

    }

    public static RestResource toRestResource(final RestResourceFile restResourceFile) {
        return RestResource.builder()
                .id(restResourceFile.getId())
                .name(restResourceFile.getName())
                .uri(restResourceFile.getUri())
                .applicationId(restResourceFile.getApplicationId())
                .build();
    }
}
