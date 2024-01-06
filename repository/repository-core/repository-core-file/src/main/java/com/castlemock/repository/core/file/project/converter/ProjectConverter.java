package com.castlemock.repository.core.file.project.converter;

import com.castlemock.model.core.project.Project;
import com.castlemock.repository.core.file.project.model.ProjectFile;

public final class ProjectConverter {

    private ProjectConverter() {

    }

    public static <B extends ProjectFile.Builder<B>> B toProjectFile(final Project project, final B builder) {
        return builder
                .id(project.getId())
                .name(project.getName())
                .created(project.getCreated())
                .updated(project.getUpdated())
                .description(project.getDescription()
                        .orElse(null));
    }

}
