package com.fortmocks.mock.soap.model.project.service.message.input;

import com.fortmocks.core.model.Input;
import com.fortmocks.core.model.validation.NotNull;
import com.fortmocks.mock.soap.model.project.dto.SoapPortDto;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateSoapPortsForwardedEndpointInput implements Input {

    @NotNull
    private Long soapProjectId;
    @NotNull
    private List<SoapPortDto> soapPorts;
    @NotNull
    private String forwardedEndpoint;

    public UpdateSoapPortsForwardedEndpointInput(Long soapProjectId, List<SoapPortDto> soapPorts, String forwardedEndpoint) {
        this.soapProjectId = soapProjectId;
        this.soapPorts = soapPorts;
        this.forwardedEndpoint = forwardedEndpoint;
    }

    public Long getSoapProjectId() {
        return soapProjectId;
    }

    public void setSoapProjectId(Long soapProjectId) {
        this.soapProjectId = soapProjectId;
    }

    public List<SoapPortDto> getSoapPorts() {
        return soapPorts;
    }

    public void setSoapPorts(List<SoapPortDto> soapPorts) {
        this.soapPorts = soapPorts;
    }

    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    public void setForwardedEndpoint(String forwardedEndpoint) {
        this.forwardedEndpoint = forwardedEndpoint;
    }
}
