package com.fortmocks.mock.soap.model.project.processor.message.output;

import com.fortmocks.core.model.Output;
import com.fortmocks.mock.soap.model.project.dto.SoapOperationDto;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadSoapOperationWithTypeOutput implements Output{

    private SoapOperationDto soapOperation;

    public ReadSoapOperationWithTypeOutput(SoapOperationDto soapOperation) {
        this.soapOperation = soapOperation;
    }

    public SoapOperationDto getSoapOperation() {
        return soapOperation;
    }

    public void setSoapOperation(SoapOperationDto soapOperation) {
        this.soapOperation = soapOperation;
    }
}
