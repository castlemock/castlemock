package com.castlemock.repository.rest.file.project.converter;

import com.castlemock.model.mock.rest.domain.RestProject;
import com.castlemock.repository.core.file.project.converter.ProjectFileConverter;
import com.castlemock.repository.rest.file.project.model.RestProjectFile;

public final class RestProjectFileConverter {

    private RestProjectFileConverter() {

    }

    public static RestProject toRestProject(final RestProjectFile restProjectFile) {
        return ProjectFileConverter.toProject(restProjectFile, RestProject.builder())
                .build();
    }

}
