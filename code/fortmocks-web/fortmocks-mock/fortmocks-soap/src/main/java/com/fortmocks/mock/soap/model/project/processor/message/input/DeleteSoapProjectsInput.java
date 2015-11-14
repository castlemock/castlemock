package com.fortmocks.mock.soap.model.project.processor.message.input;

import com.fortmocks.core.model.Input;
import com.fortmocks.core.model.validation.NotNull;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class DeleteSoapProjectsInput implements Input{

    @NotNull
    private List<SoapProjectDto> soapProjects;

    public List<SoapProjectDto> getSoapProjects() {
        return soapProjects;
    }

    public void setSoapProjects(List<SoapProjectDto> soapProjects) {
        this.soapProjects = soapProjects;
    }
}
