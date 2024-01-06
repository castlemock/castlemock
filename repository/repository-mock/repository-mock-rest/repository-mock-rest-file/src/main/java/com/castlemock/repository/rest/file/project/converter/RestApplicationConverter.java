package com.castlemock.repository.rest.file.project.converter;

import com.castlemock.model.mock.rest.domain.RestApplication;
import com.castlemock.repository.rest.file.project.model.RestApplicationFile;

public final class RestApplicationConverter {

    private RestApplicationConverter() {

    }

    public static RestApplicationFile toRestApplicationFile(final RestApplication restApplication) {
        return RestApplicationFile.builder()
                .id(restApplication.getId())
                .name(restApplication.getName())
                .projectId(restApplication.getProjectId())
                .build();
    }
}
