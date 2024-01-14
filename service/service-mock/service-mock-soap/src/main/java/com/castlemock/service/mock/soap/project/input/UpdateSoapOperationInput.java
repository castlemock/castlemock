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

import com.castlemock.model.core.Input;
import com.castlemock.model.mock.soap.domain.SoapOperationIdentifyStrategy;
import com.castlemock.model.mock.soap.domain.SoapOperationStatus;
import com.castlemock.model.mock.soap.domain.SoapResponseStrategy;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class UpdateSoapOperationInput implements Input {

    private final String projectId;
    private final String portId;
    private final String operationId;
    private final SoapResponseStrategy responseStrategy;
    private final SoapOperationStatus status;
    private final String forwardedEndpoint;
    private final Boolean simulateNetworkDelay;
    private final Long networkDelay;
    private final String defaultMockResponseId;
    private final Boolean mockOnFailure;
    private final SoapOperationIdentifyStrategy identifyStrategy;

    private final Boolean automaticForward;

    public UpdateSoapOperationInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.portId = Objects.requireNonNull(builder.portId, "portId");
        this.operationId = Objects.requireNonNull(builder.operationId, "operationId");
        this.responseStrategy = Objects.requireNonNull(builder.responseStrategy, "responseStrategy");
        this.status = Objects.requireNonNull(builder.status, "status");
        this.identifyStrategy = Objects.requireNonNull(builder.identifyStrategy, "identifyStrategy");

        this.automaticForward = builder.automaticForward;
        this.simulateNetworkDelay = builder.simulateNetworkDelay;
        this.defaultMockResponseId = builder.defaultMockResponseId;
        this.networkDelay = builder.networkDelay;
        this.forwardedEndpoint = builder.forwardedEndpoint;
        this.mockOnFailure = builder.mockOnFailure;
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

    public Optional<String> getForwardedEndpoint() {
        return Optional.ofNullable(forwardedEndpoint);
    }

    public Optional<Boolean> getSimulateNetworkDelay() {
        return Optional.ofNullable(simulateNetworkDelay);
    }

    public Optional<Long> getNetworkDelay() {
        return Optional.ofNullable(networkDelay);
    }

    public Optional<String> getDefaultMockResponseId() {
        return Optional.ofNullable(defaultMockResponseId);
    }

    public Optional<Boolean> getMockOnFailure() {
        return Optional.ofNullable(mockOnFailure);
    }

    public SoapOperationIdentifyStrategy getIdentifyStrategy() {
        return identifyStrategy;
    }

    public Optional<Boolean> getAutomaticForward() {
        return Optional.ofNullable(automaticForward);
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
        private Boolean automaticForward;

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

        public Builder automaticForward(final Boolean automaticForward) {
            this.automaticForward = automaticForward;
            return this;
        }

        public UpdateSoapOperationInput build(){
            return new UpdateSoapOperationInput(this);
        }
    }
}
