package com.castlemock.repository.soap.file.project.converter;

import com.castlemock.model.mock.soap.domain.SoapProject;
import com.castlemock.repository.core.file.project.converter.ProjectFileConverter;
import com.castlemock.repository.soap.file.project.model.SoapProjectFile;

public final class SoapProjectFileConverter {

    private SoapProjectFileConverter() {

    }

    public static SoapProject toSoapProject(final SoapProjectFile SoapProjectFile) {
        return ProjectFileConverter.toProject(SoapProjectFile, SoapProject.builder())
                .build();
    }

}
