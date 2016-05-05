/*
 * Copyright 2015 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.castlemock.core.mock.soap.model.project.service.message.input;

import com.castlemock.core.basis.model.Input;
import com.castlemock.core.basis.model.validation.NotNull;
import com.castlemock.core.mock.soap.model.project.dto.SoapOperationDto;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateSoapOperationsForwardedEndpointInput implements Input {

    @NotNull
    private String soapProjectId;
    @NotNull
    private String soapPortId;
    @NotNull
    private List<SoapOperationDto> soapOperations;
    @NotNull
    private String forwardedEndpoint;

    public UpdateSoapOperationsForwardedEndpointInput(String soapProjectId, String soapPortId, List<SoapOperationDto> soapOperations, String forwardedEndpoint) {
        this.soapProjectId = soapProjectId;
        this.soapPortId = soapPortId;
        this.soapOperations = soapOperations;
        this.forwardedEndpoint = forwardedEndpoint;
    }

    public String getSoapProjectId() {
        return soapProjectId;
    }

    public void setSoapProjectId(String soapProjectId) {
        this.soapProjectId = soapProjectId;
    }

    public String getSoapPortId() {
        return soapPortId;
    }

    public void setSoapPortId(String soapPortId) {
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
