package com.castlemock.repository.core.file.project.converter;

import com.castlemock.model.core.project.Project;
import com.castlemock.repository.core.file.project.model.ProjectFile;

public final class ProjectFileConverter {

    private ProjectFileConverter() {

    }

    public static <B extends Project.Builder<B>> B toProject(final ProjectFile projectFile, final B builder) {
        return builder
                .id(projectFile.getId())
                .name(projectFile.getName())
                .created(projectFile.getCreated())
                .updated(projectFile.getUpdated())
                .description(projectFile.getDescription());
    }

}
