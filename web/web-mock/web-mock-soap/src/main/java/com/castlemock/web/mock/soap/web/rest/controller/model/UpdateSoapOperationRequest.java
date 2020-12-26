/*
 * Copyright 2020 Karl Dahlgren
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

package com.castlemock.web.mock.soap.web.rest.controller.model;

import com.castlemock.core.mock.soap.model.project.domain.SoapOperationIdentifyStrategy;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperationStatus;
import com.castlemock.core.mock.soap.model.project.domain.SoapResponseStrategy;

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.52
 */
public class UpdateSoapOperationRequest {

    private SoapResponseStrategy responseStrategy;
    private SoapOperationStatus status;
    private String forwardedEndpoint;
    private Boolean simulateNetworkDelay;
    private Long networkDelay;
    private String defaultMockResponseId;
    private Boolean mockOnFailure;
    private SoapOperationIdentifyStrategy identifyStrategy;

    public UpdateSoapOperationRequest(){

    }

    private UpdateSoapOperationRequest(final Builder builder){
        this.responseStrategy = Objects.requireNonNull(builder.responseStrategy);
        this.status = Objects.requireNonNull(builder.status);
        this.forwardedEndpoint = Objects.requireNonNull(builder.forwardedEndpoint);
        this.simulateNetworkDelay = Objects.requireNonNull(builder.simulateNetworkDelay);
        this.networkDelay = Objects.requireNonNull(builder.networkDelay);
        this.defaultMockResponseId = builder.defaultMockResponseId;
        this.mockOnFailure = Objects.requireNonNull(builder.mockOnFailure);
        this.identifyStrategy = Objects.requireNonNull(builder.identifyStrategy);
    }

    public SoapResponseStrategy getResponseStrategy() {
        return responseStrategy;
    }

    public void setResponseStrategy(SoapResponseStrategy responseStrategy) {
        this.responseStrategy = responseStrategy;
    }

    public SoapOperationStatus getStatus() {
        return status;
    }

    public void setStatus(SoapOperationStatus status) {
        this.status = status;
    }

    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    public void setForwardedEndpoint(String forwardedEndpoint) {
        this.forwardedEndpoint = forwardedEndpoint;
    }

    public Boolean getSimulateNetworkDelay() {
        return simulateNetworkDelay;
    }

    public void setSimulateNetworkDelay(Boolean simulateNetworkDelay) {
        this.simulateNetworkDelay = simulateNetworkDelay;
    }

    public Long getNetworkDelay() {
        return networkDelay;
    }

    public void setNetworkDelay(Long networkDelay) {
        this.networkDelay = networkDelay;
    }

    public Boolean getMockOnFailure() {
        return mockOnFailure;
    }

    public void setMockOnFailure(Boolean mockOnFailure) {
        this.mockOnFailure = mockOnFailure;
    }

    public SoapOperationIdentifyStrategy getIdentifyStrategy() {
        return identifyStrategy;
    }

    public void setIdentifyStrategy(SoapOperationIdentifyStrategy identifyStrategy) {
        this.identifyStrategy = identifyStrategy;
    }

    public String getDefaultMockResponseId() {
        return defaultMockResponseId;
    }

    public void setDefaultMockResponseId(String defaultMockResponseId) {
        this.defaultMockResponseId = defaultMockResponseId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private SoapResponseStrategy responseStrategy;
        private SoapOperationStatus status;
        private String forwardedEndpoint;
        private Boolean simulateNetworkDelay;
        private Long networkDelay;
        private String defaultMockResponseId;
        private Boolean mockOnFailure;
        private SoapOperationIdentifyStrategy identifyStrategy;

        private Builder() {
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

        public UpdateSoapOperationRequest build() {
            return new UpdateSoapOperationRequest(this);
        }
    }
}
