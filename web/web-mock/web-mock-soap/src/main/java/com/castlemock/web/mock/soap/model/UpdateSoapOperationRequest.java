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

package com.castlemock.web.mock.soap.model;

import com.castlemock.model.mock.soap.domain.SoapOperationIdentifyStrategy;
import com.castlemock.model.mock.soap.domain.SoapOperationStatus;
import com.castlemock.model.mock.soap.domain.SoapResponseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.52
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = UpdateSoapOperationRequest.Builder.class)
public class UpdateSoapOperationRequest {

    private final SoapResponseStrategy responseStrategy;
    private final SoapOperationStatus status;
    private final String forwardedEndpoint;
    private final Boolean simulateNetworkDelay;
    private final Long networkDelay;
    private final String defaultMockResponseId;
    private final Boolean mockOnFailure;
    private final SoapOperationIdentifyStrategy identifyStrategy;
    private final Boolean automaticForward;

    private UpdateSoapOperationRequest(final Builder builder){
        this.responseStrategy = Objects.requireNonNull(builder.responseStrategy, "responseStrategy");
        this.status = Objects.requireNonNull(builder.status, "status");
        this.identifyStrategy = Objects.requireNonNull(builder.identifyStrategy, "identifyStrategy");
        this.forwardedEndpoint = builder.forwardedEndpoint;
        this.simulateNetworkDelay = builder.simulateNetworkDelay;
        this.networkDelay = builder.networkDelay;
        this.defaultMockResponseId = builder.defaultMockResponseId;
        this.mockOnFailure = builder.mockOnFailure;
        this.automaticForward = builder.automaticForward;
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

    public Optional<Boolean> getMockOnFailure() {
        return Optional.ofNullable(mockOnFailure);
    }

    public SoapOperationIdentifyStrategy getIdentifyStrategy() {
        return identifyStrategy;
    }

    public Optional<String> getDefaultMockResponseId() {
        return Optional.ofNullable(defaultMockResponseId);
    }

    public Optional<Boolean> getAutomaticForward() {
        return Optional.ofNullable(automaticForward);
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {

        private SoapResponseStrategy responseStrategy;
        private SoapOperationStatus status;
        private String forwardedEndpoint;
        private Boolean simulateNetworkDelay;
        private Long networkDelay;
        private String defaultMockResponseId;
        private Boolean mockOnFailure;
        private SoapOperationIdentifyStrategy identifyStrategy;
        private Boolean automaticForward;

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

        public Builder automaticForward(final Boolean automaticForward) {
            this.automaticForward = automaticForward;
            return this;
        }

        public UpdateSoapOperationRequest build() {
            return new UpdateSoapOperationRequest(this);
        }
    }
}
