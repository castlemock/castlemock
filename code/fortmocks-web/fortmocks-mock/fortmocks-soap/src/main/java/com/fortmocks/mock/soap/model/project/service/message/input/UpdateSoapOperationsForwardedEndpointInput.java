package com.fortmocks.mock.soap.model.project.service.message.input;

import com.fortmocks.core.model.Input;
import com.fortmocks.core.model.validation.NotNull;
import com.fortmocks.mock.soap.model.project.dto.SoapOperationDto;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateSoapOperationsForwardedEndpointInput implements Input {

    @NotNull
    private Long soapProjectId;
    @NotNull
    private Long soapPortId;
    @NotNull
    private List<SoapOperationDto> soapOperations;
    @NotNull
    private String forwardedEndpoint;

    public UpdateSoapOperationsForwardedEndpointInput(Long soapProjectId, Long soapPortId, List<SoapOperationDto> soapOperations, String forwardedEndpoint) {
        this.soapProjectId = soapProjectId;
        this.soapPortId = soapPortId;
        this.soapOperations = soapOperations;
        this.forwardedEndpoint = forwardedEndpoint;
    }

    public Long getSoapProjectId() {
        return soapProjectId;
    }

    public void setSoapProjectId(Long soapProjectId) {
        this.soapProjectId = soapProjectId;
    }

    public Long getSoapPortId() {
        return soapPortId;
    }

    public void setSoapPortId(Long soapPortId) {
        this.soapPortId = soapPortId;
    }

    public List<SoapOperationDto> getSoapOperations() {
        return soapOperations;
    }

    public void setSoapOperations(List<SoapOperationDto> soapOperations) {
        this.soapOperations = soapOperations;
    }

    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    public void setForwardedEndpoint(String forwardedEndpoint) {
        this.forwardedEndpoint = forwardedEndpoint;
    }
}
