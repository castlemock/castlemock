package com.fortmocks.mock.soap.model.project.message;

import com.fortmocks.core.model.Input;
import com.fortmocks.core.model.user.dto.UserDto;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SaveSoapProjectInput implements Input {

    private SoapProjectDto soapProject;

    public SoapProjectDto getSoapProject() {
        return soapProject;
    }

    public void setSoapProject(SoapProjectDto soapProject) {
        this.soapProject = soapProject;
    }
}
