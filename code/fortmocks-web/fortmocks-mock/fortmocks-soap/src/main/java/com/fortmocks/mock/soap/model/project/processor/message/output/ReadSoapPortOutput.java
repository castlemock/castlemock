package com.fortmocks.mock.soap.model.project.processor.message.output;

import com.fortmocks.core.model.Output;
import com.fortmocks.mock.soap.model.project.domain.SoapPort;
import com.fortmocks.mock.soap.model.project.dto.SoapPortDto;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadSoapPortOutput implements Output{

    private SoapPortDto soapPort;

    public ReadSoapPortOutput(SoapPortDto soapPort) {
        this.soapPort = soapPort;
    }

    public SoapPortDto getSoapPort() {
        return soapPort;
    }

    public void setSoapPort(SoapPortDto soapPort) {
        this.soapPort = soapPort;
    }
}
