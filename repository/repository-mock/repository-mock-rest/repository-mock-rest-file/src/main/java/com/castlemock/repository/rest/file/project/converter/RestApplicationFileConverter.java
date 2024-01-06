package com.castlemock.repository.rest.file.project.converter;

import com.castlemock.model.mock.rest.domain.RestApplication;
import com.castlemock.repository.rest.file.project.model.RestApplicationFile;

public final class RestApplicationFileConverter {

    private RestApplicationFileConverter() {

    }

    public static RestApplication toRestApplication(final RestApplicationFile restApplicationFile) {
        return RestApplication.builder()
                .id(restApplicationFile.getId())
                .name(restApplicationFile.getName())
                .projectId(restApplicationFile.getProjectId())
                .build();
    }
}
