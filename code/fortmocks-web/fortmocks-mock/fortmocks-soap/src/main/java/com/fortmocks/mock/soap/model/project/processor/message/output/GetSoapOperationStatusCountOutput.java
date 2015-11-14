package com.fortmocks.mock.soap.model.project.processor.message.output;

import com.fortmocks.core.model.Output;
import com.fortmocks.mock.soap.model.project.domain.SoapOperationStatus;
import com.fortmocks.mock.soap.model.project.dto.SoapOperationDto;

import java.util.Map;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class GetSoapOperationStatusCountOutput implements Output{

    private Map<SoapOperationStatus, Integer> soapOperationStatuses;

    public GetSoapOperationStatusCountOutput(Map<SoapOperationStatus, Integer> soapOperationStatuses) {
        this.soapOperationStatuses = soapOperationStatuses;
    }

    public Map<SoapOperationStatus, Integer> getSoapOperationStatuses() {
        return soapOperationStatuses;
    }

    public void setSoapOperationStatuses(Map<SoapOperationStatus, Integer> soapOperationStatuses) {
        this.soapOperationStatuses = soapOperationStatuses;
    }
}
