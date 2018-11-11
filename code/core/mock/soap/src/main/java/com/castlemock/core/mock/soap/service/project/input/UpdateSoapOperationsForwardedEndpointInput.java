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
import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class UpdateSoapOperationsForwardedEndpointInput implements Input {

    @NotNull
    private final String projectId;
    @NotNull
    private final String portId;
    @NotNull
    private final List<SoapOperation> operations;
    @NotNull
    private final String forwardedEndpoint;

    public UpdateSoapOperationsForwardedEndpointInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId);
        this.portId = Objects.requireNonNull(builder.portId);
        this.operations = Objects.requireNonNull(builder.operations);
        this.forwardedEndpoint = Objects.requireNonNull(builder.forwardedEndpoint);
    }

    public String getProjectId() {
        return projectId;
    }

    public String getPortId() {
        return portId;
    }

    public List<SoapOperation> getOperations() {
        return operations;
    }

    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }


    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String projectId;
        private String portId;
        private List<SoapOperation> operations;
        private String forwardedEndpoint;

        private Builder(){

        }

        public Builder projectId(final String projectId){
            this.projectId = projectId;
            return this;
        }

        public Builder portId(final String portId){
            this.portId = portId;
            return this;
        }

        public Builder operations(final List<SoapOperation> operations){
            this.operations = operations;
            return this;
        }

        public Builder forwardedEndpoint(final String forwardedEndpoint){
            this.forwardedEndpoint = forwardedEndpoint;
            return this;
        }

        public UpdateSoapOperationsForwardedEndpointInput build(){
            return new UpdateSoapOperationsForwardedEndpointInput(this);
        }
    }

}
