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

package com.castlemock.core.mock.soap.service.project.input;

import com.castlemock.core.basis.model.Input;
import com.castlemock.core.basis.model.validation.NotNull;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class UpdateSoapOperationsForwardedEndpointInput implements Input {

    @NotNull
    private final String soapProjectId;
    @NotNull
    private final String soapPortId;
    @NotNull
    private final List<SoapOperation> soapOperations;
    @NotNull
    private final String forwardedEndpoint;

    public UpdateSoapOperationsForwardedEndpointInput(String soapProjectId, String soapPortId, List<SoapOperation> soapOperations, String forwardedEndpoint) {
        this.soapProjectId = soapProjectId;
        this.soapPortId = soapPortId;
        this.soapOperations = soapOperations;
        this.forwardedEndpoint = forwardedEndpoint;
    }

    public String getSoapProjectId() {
        return soapProjectId;
    }

    public String getSoapPortId() {
        return soapPortId;
    }

    public List<SoapOperation> getSoapOperations() {
        return soapOperations;
    }

    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

}
