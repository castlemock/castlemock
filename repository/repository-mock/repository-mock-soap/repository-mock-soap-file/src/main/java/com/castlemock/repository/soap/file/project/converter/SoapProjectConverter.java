package com.castlemock.repository.soap.file.project.converter;

import com.castlemock.model.mock.soap.domain.SoapProject;
import com.castlemock.repository.core.file.project.converter.ProjectConverter;
import com.castlemock.repository.soap.file.project.model.SoapProjectFile;

public final class SoapProjectConverter {

    private SoapProjectConverter() {

    }

    public static SoapProjectFile toSoapProjectFile(final SoapProject SoapProject) {
        return ProjectConverter.toProjectFile(SoapProject, SoapProjectFile.builder())
                .build();
    }

}
