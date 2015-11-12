package com.fortmocks.mock.soap.model.project.message;

import com.fortmocks.core.model.Output;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SaveSoapProjectOutput implements Output {

    private SoapProjectDto savedSoapProject;

    public SoapProjectDto getSavedSoapProject() {
        return savedSoapProject;
    }

    public void setSavedSoapProject(SoapProjectDto savedSoapProject) {
        this.savedSoapProject = savedSoapProject;
    }
}
