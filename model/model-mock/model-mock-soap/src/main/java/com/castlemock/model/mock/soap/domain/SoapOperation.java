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

package com.castlemock.model.mock.soap.domain;

import com.castlemock.model.core.http.HttpMethod;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = SoapOperation.Builder.class)
public class SoapOperation {

    @XmlElement
    private final String id;

    @XmlElement
    private final String name;

    @XmlElement
    private final String identifier;

    @XmlElement
    private final SoapOperationIdentifier operationIdentifier;

    @XmlElement
    private final SoapResponseStrategy responseStrategy;

    @XmlElement
    private final SoapOperationStatus status;

    @XmlElement
    private final HttpMethod httpMethod;

    @XmlElement
    private final SoapVersion soapVersion;

    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String defaultBody;

    @XmlElement
    private final Integer currentResponseSequenceIndex;

    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String forwardedEndpoint;

    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String originalEndpoint;

    @XmlElement
    private final Boolean simulateNetworkDelay;

    @XmlElement
    private final Long networkDelay;

    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String defaultMockResponseId;

    @XmlElement
    private final String portId;

    @XmlElement
    private final Boolean mockOnFailure;

    @XmlElement
    private final SoapOperationIdentifyStrategy identifyStrategy;

    @XmlElementWrapper(name = "mockResponses")
    @XmlElement(name = "mockResponse")
    private final List<SoapMockResponse> mockResponses;

    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String invokeAddress;

    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String defaultResponseName;

    @XmlElement
    private final Boolean automaticForward;


    private SoapOperation(final Builder builder){
        this.id = Objects.requireNonNull(builder.id, "id");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.identifier = Objects.requireNonNull(builder.identifier, "identifier");
        this.operationIdentifier = Objects.requireNonNull(builder.operationIdentifier, "operationIdentifier");
        this.responseStrategy = Objects.requireNonNull(builder.responseStrategy, "responseStrategy");
        this.status = Objects.requireNonNull(builder.status, "status");
        this.httpMethod = Objects.requireNonNull(builder.httpMethod, "httpMethod");
        this.soapVersion = Objects.requireNonNull(builder.soapVersion, "soapVersion");
        this.currentResponseSequenceIndex = Objects.requireNonNull(builder.currentResponseSequenceIndex, "currentResponseSequenceIndex");
        this.portId = Objects.requireNonNull(builder.portId, "portId");
        this.identifyStrategy = Objects.requireNonNull(builder.identifyStrategy, "identifyStrategy");

        this.simulateNetworkDelay = builder.simulateNetworkDelay;
        this.defaultBody = builder.defaultBody;
        this.originalEndpoint = builder.originalEndpoint;
        this.forwardedEndpoint = builder.forwardedEndpoint;
        this.networkDelay = builder.networkDelay;
        this.defaultMockResponseId = builder.defaultMockResponseId;
        this.invokeAddress = builder.invokeAddress;
        this.defaultResponseName = builder.defaultResponseName;
        this.mockOnFailure = builder.mockOnFailure;
        this.automaticForward = builder.automaticForward;
        this.mockResponses = Optional.ofNullable(builder.mockResponses).orElseGet(List::of);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public SoapOperationIdentifier getOperationIdentifier() {
        return operationIdentifier;
    }

    public String getPortId() {
        return portId;
    }

    public SoapResponseStrategy getResponseStrategy() {
        return responseStrategy;
    }

    public SoapOperationStatus getStatus() {
        return status;
    }


    public List<SoapMockResponse> getMockResponses() {
        return Optional.ofNullable(mockResponses)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    public Optional<String> getInvokeAddress() {
        return Optional.ofNullable(invokeAddress);
    }

    public Optional<String> getDefaultBody() {
        return Optional.ofNullable(defaultBody);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public SoapVersion getSoapVersion() {
        return soapVersion;
    }

    public Integer getCurrentResponseSequenceIndex() {
        return currentResponseSequenceIndex;
    }

    public Optional<String> getForwardedEndpoint() {
        return Optional.ofNullable(forwardedEndpoint);
    }

    public Optional<String> getOriginalEndpoint() {
        return Optional.ofNullable(originalEndpoint);
    }

    public Optional<Boolean> getSimulateNetworkDelay() {
        return Optional.ofNullable(simulateNetworkDelay);
    }

    public Optional<Long> getNetworkDelay() {
        return Optional.ofNullable(networkDelay);
    }

    public Optional<String> getDefaultResponseName() {
        return Optional.ofNullable(defaultResponseName);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SoapOperation that = (SoapOperation) o;
        return automaticForward == that.automaticForward && Objects.equals(id, that.id)
                && Objects.equals(name, that.name) && Objects.equals(identifier, that.identifier)
                && Objects.equals(operationIdentifier, that.operationIdentifier) && responseStrategy == that.responseStrategy
                && status == that.status && httpMethod == that.httpMethod && soapVersion == that.soapVersion
                && Objects.equals(defaultBody, that.defaultBody) && Objects.equals(currentResponseSequenceIndex, that.currentResponseSequenceIndex)
                && Objects.equals(forwardedEndpoint, that.forwardedEndpoint) && Objects.equals(originalEndpoint, that.originalEndpoint)
                && Objects.equals(simulateNetworkDelay, that.simulateNetworkDelay) && Objects.equals(networkDelay, that.networkDelay)
                && Objects.equals(defaultMockResponseId, that.defaultMockResponseId) && Objects.equals(portId, that.portId)
                && Objects.equals(mockOnFailure, that.mockOnFailure) && identifyStrategy == that.identifyStrategy
                && Objects.equals(mockResponses, that.mockResponses) && Objects.equals(invokeAddress, that.invokeAddress)
                && Objects.equals(defaultResponseName, that.defaultResponseName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, identifier, operationIdentifier, responseStrategy, status, httpMethod,
                soapVersion, defaultBody, currentResponseSequenceIndex, forwardedEndpoint, originalEndpoint,
                simulateNetworkDelay, networkDelay, defaultMockResponseId, portId, mockOnFailure, identifyStrategy,
                mockResponses, invokeAddress, defaultResponseName, automaticForward);
    }

    @Override
    public String toString() {
        return "SoapOperation{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", identifier='" + identifier + '\'' +
                ", operationIdentifier=" + operationIdentifier +
                ", responseStrategy=" + responseStrategy +
                ", status=" + status +
                ", httpMethod=" + httpMethod +
                ", soapVersion=" + soapVersion +
                ", defaultBody='" + defaultBody + '\'' +
                ", currentResponseSequenceIndex=" + currentResponseSequenceIndex +
                ", forwardedEndpoint='" + forwardedEndpoint + '\'' +
                ", originalEndpoint='" + originalEndpoint + '\'' +
                ", simulateNetworkDelay=" + simulateNetworkDelay +
                ", networkDelay=" + networkDelay +
                ", defaultMockResponseId='" + defaultMockResponseId + '\'' +
                ", portId='" + portId + '\'' +
                ", mockOnFailure=" + mockOnFailure +
                ", identifyStrategy=" + identifyStrategy +
                ", mockResponses=" + mockResponses +
                ", invokeAddress='" + invokeAddress + '\'' +
                ", defaultResponseName='" + defaultResponseName + '\'' +
                ", automaticForward=" + automaticForward +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder()
                .id(id)
                .automaticForward(automaticForward)
                .mockOnFailure(mockOnFailure)
                .portId(portId)
                .simulateNetworkDelay(simulateNetworkDelay)
                .identifyStrategy(identifyStrategy)
                .currentResponseSequenceIndex(currentResponseSequenceIndex)
                .defaultBody(defaultBody)
                .defaultResponseName(defaultResponseName)
                .defaultMockResponseId(defaultMockResponseId)
                .name(name)
                .identifier(identifier)
                .operationIdentifier(operationIdentifier)
                .responseStrategy(responseStrategy)
                .invokeAddress(invokeAddress)
                .soapVersion(soapVersion)
                .forwardedEndpoint(forwardedEndpoint)
                .mockResponses(Optional.ofNullable(mockResponses)
                        .map(responses -> responses.stream()
                                .map(SoapMockResponse::toBuilder)
                                .map(SoapMockResponse.Builder::build)
                                .collect(Collectors.toList()))
                        .orElse(null))
                .originalEndpoint(originalEndpoint)
                .status(status)
                .httpMethod(httpMethod)
                .networkDelay(networkDelay);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private String id;
        private String name;
        private String identifier;
        private SoapOperationIdentifier operationIdentifier;
        private SoapResponseStrategy responseStrategy;
        private SoapOperationStatus status;
        private HttpMethod httpMethod;
        private SoapVersion soapVersion;
        private String defaultBody;
        private Integer currentResponseSequenceIndex;
        private String forwardedEndpoint;
        private String originalEndpoint;
        private Boolean simulateNetworkDelay;
        private Long networkDelay;
        private String defaultMockResponseId;
        private String portId;
        private Boolean mockOnFailure;
        private SoapOperationIdentifyStrategy identifyStrategy;
        private List<SoapMockResponse> mockResponses;
        private String invokeAddress;
        private String defaultResponseName;
        private Boolean automaticForward;

        private Builder() {
        }

        public Builder id(final String id) {
            this.id = id;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder identifier(final String identifier) {
            this.identifier = identifier;
            return this;
        }

        public Builder operationIdentifier(final SoapOperationIdentifier operationIdentifier) {
            this.operationIdentifier = operationIdentifier;
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

        public Builder httpMethod(final HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public Builder soapVersion(final SoapVersion soapVersion) {
            this.soapVersion = soapVersion;
            return this;
        }

        public Builder defaultBody(final String defaultBody) {
            this.defaultBody = defaultBody;
            return this;
        }

        public Builder currentResponseSequenceIndex(final Integer currentResponseSequenceIndex) {
            this.currentResponseSequenceIndex = currentResponseSequenceIndex;
            return this;
        }

        public Builder forwardedEndpoint(final String forwardedEndpoint) {
            this.forwardedEndpoint = forwardedEndpoint;
            return this;
        }

        public Builder originalEndpoint(final String originalEndpoint) {
            this.originalEndpoint = originalEndpoint;
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

        public Builder portId(final String portId) {
            this.portId = portId;
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

        public Builder mockResponses(final List<SoapMockResponse> mockResponses) {
            this.mockResponses = mockResponses;
            return this;
        }

        public Builder invokeAddress(final String invokeAddress) {
            this.invokeAddress = invokeAddress;
            return this;
        }

        public Builder defaultResponseName(final String defaultResponseName) {
            this.defaultResponseName = defaultResponseName;
            return this;
        }

        public Builder automaticForward(final Boolean automaticForward) {
            this.automaticForward = automaticForward;
            return this;
        }

        public SoapOperation build() {
            return new SoapOperation(this);
        }
    }
}
