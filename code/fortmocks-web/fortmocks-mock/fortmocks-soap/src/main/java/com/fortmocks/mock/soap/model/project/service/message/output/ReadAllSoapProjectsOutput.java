package com.fortmocks.mock.soap.model.project.service.message.output;

import com.fortmocks.core.model.Output;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadAllSoapProjectsOutput implements Output {

    private List<SoapProjectDto> soapProjects;

    public List<SoapProjectDto> getSoapProjects() {
        return soapProjects;
    }

    public void setSoapProjects(List<SoapProjectDto> soapProjects) {
        this.soapProjects = soapProjects;
    }
}
