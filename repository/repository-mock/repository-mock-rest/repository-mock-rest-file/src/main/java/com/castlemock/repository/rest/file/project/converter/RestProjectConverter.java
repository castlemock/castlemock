package com.castlemock.repository.rest.file.project.converter;

import com.castlemock.model.mock.rest.domain.RestProject;
import com.castlemock.repository.core.file.project.converter.ProjectConverter;
import com.castlemock.repository.rest.file.project.model.RestProjectFile;

public final class RestProjectConverter {

    private RestProjectConverter() {

    }

    public static RestProjectFile toRestProjectFile(final RestProject restProject) {
        return ProjectConverter.toProjectFile(restProject, RestProjectFile.builder())
                .build();
    }

}
