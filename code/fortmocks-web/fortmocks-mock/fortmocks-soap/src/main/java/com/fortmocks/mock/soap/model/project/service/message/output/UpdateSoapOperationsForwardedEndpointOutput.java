package com.fortmocks.mock.soap.model.project.service.message.output;

import com.fortmocks.core.model.Output;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateSoapOperationsForwardedEndpointOutput implements Output {

    private SoapProjectDto updatedSoapProject;

    public SoapProjectDto getUpdatedSoapProject() {
        return updatedSoapProject;
    }

    public void setUpdatedSoapProject(SoapProjectDto updatedSoapProject) {
        this.updatedSoapProject = updatedSoapProject;
    }
}
