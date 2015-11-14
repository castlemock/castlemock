package com.fortmocks.mock.soap.model.project.processor.message.output;

import com.fortmocks.core.model.Output;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadSoapMockResponsesOutput implements Output{

    private SoapProjectDto soapProject;

    public SoapProjectDto getSoapProject() {
        return soapProject;
    }

    public void setSoapProject(SoapProjectDto soapProject) {
        this.soapProject = soapProject;
    }
}
