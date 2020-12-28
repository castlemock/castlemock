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

package com.castlemock.service.mock.soap.project.input;

import com.castlemock.model.core.model.Input;
import com.castlemock.model.core.model.validation.NotNull;
import com.castlemock.model.mock.soap.domain.SoapOperationIdentifyStrategy;
import com.castlemock.model.mock.soap.domain.SoapOperationStatus;
import com.castlemock.model.mock.soap.domain.SoapResponseStrategy;

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class UpdateSoapOperationInput implements Input {

    @NotNull
    private final String projectId;
    @NotNull
    private final String portId;
    @NotNull
    private final String operationId;
    @NotNull
    private SoapResponseStrategy responseStrategy;
    @NotNull
    private SoapOperationStatus status;
    @NotNull
    private String forwardedEndpoint;
    @NotNull
    private Boolean simulateNetworkDelay;
    @NotNull
    private Long networkDelay;
    private String defaultMockResponseId;
    @NotNull
    private Boolean mockOnFailure;
    @NotNull
    private SoapOperationIdentifyStrategy identifyStrategy;

    public UpdateSoapOperationInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId);
        this.portId = Objects.requireNonNull(builder.portId);
        this.operationId = Objects.requireNonNull(builder.operationId);
        this.responseStrategy = Objects.requireNonNull(builder.responseStrategy);
        this.status = Objects.requireNonNull(builder.status);
        this.forwardedEndpoint = Objects.requireNonNull(builder.forwardedEndpoint);
        this.simulateNetworkDelay = Objects.requireNonNull(builder.simulateNetworkDelay);
        this.networkDelay = Objects.requireNonNull(builder.networkDelay);
        this.defaultMockResponseId = builder.defaultMockResponseId;
        this.mockOnFailure = Objects.requireNonNull(builder.mockOnFailure);
        this.identifyStrategy = Objects.requireNonNull(builder.identifyStrategy);
    }

    public String getProjectId() {
        return projectId;
    }

    public String getPortId() {
        return portId;
    }

    public String getOperationId() {
        return operationId;
    }

    public SoapResponseStrategy getResponseStrategy() {
        return responseStrategy;
    }

    public SoapOperationStatus getStatus() {
        return status;
    }

    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    public Boolean getSimulateNetworkDelay() {
        return simulateNetworkDelay;
    }

    public Long getNetworkDelay() {
        return networkDelay;
    }

    public String getDefaultMockResponseId() {
        return defaultMockResponseId;
    }

    public Boolean getMockOnFailure() {
        return mockOnFailure;
    }

    public SoapOperationIdentifyStrategy getIdentifyStrategy() {
        return identifyStrategy;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String projectId;
        private String portId;
        private String operationId;
        private SoapResponseStrategy responseStrategy;
        private SoapOperationStatus status;
        private String forwardedEndpoint;
        private Boolean simulateNetworkDelay;
        private Long networkDelay;
        private String defaultMockResponseId;
        private Boolean mockOnFailure;
        private SoapOperationIdentifyStrategy identifyStrategy;

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

        public Builder operationId(final String operationId){
            this.operationId = operationId;
            return this;
        }


        public Builder responseStrategy(final SoapResponseStrategy responseStrategy) {
            this.responseStrategy = responseStrategy;
            return this;
        }

        public Builder status(final SoapOperationStatus status) {
            this.status = status;
            return this;
        }

        public Builder forwardedEndpoint(final String forwardedEndpoint) {
            this.forwardedEndpoint = forwardedEndpoint;
            return this;
        }

        public Builder simulateNetworkDelay(final Boolean simulateNetworkDelay) {
            this.simulateNetworkDelay = simulateNetworkDelay;
            return this;
        }

        public Builder networkDelay(final Long networkDelay) {
            this.networkDelay = networkDelay;
            return this;
        }

        public Builder defaultMockResponseId(final String defaultMockResponseId) {
            this.defaultMockResponseId = defaultMockResponseId;
            return this;
        }

        public Builder mockOnFailure(final Boolean mockOnFailure) {
            this.mockOnFailure = mockOnFailure;
            return this;
        }

        public Builder identifyStrategy(final SoapOperationIdentifyStrategy identifyStrategy) {
            this.identifyStrategy = identifyStrategy;
            return this;
        }
        public UpdateSoapOperationInput build(){
            return new UpdateSoapOperationInput(this);
        }
    }
}
