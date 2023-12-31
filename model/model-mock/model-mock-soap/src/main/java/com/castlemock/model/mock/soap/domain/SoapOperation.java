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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
public class SoapOperation {

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
    private List<SoapMockResponse> mockResponses = new CopyOnWriteArrayList<SoapMockResponse>();
    private String invokeAddress;
    private String defaultResponseName;
    private boolean automaticForward;

    private SoapOperation(){

    }

    private SoapOperation(final Builder builder){
        this.id = Objects.requireNonNull(builder.id);
        this.name = Objects.requireNonNull(builder.name);
        this.identifier = Objects.requireNonNull(builder.identifier);
        this.operationIdentifier = Objects.requireNonNull(builder.operationIdentifier);
        this.responseStrategy = Objects.requireNonNull(builder.responseStrategy);
        this.status = Objects.requireNonNull(builder.status);
        this.httpMethod = Objects.requireNonNull(builder.httpMethod);
        this.soapVersion = Objects.requireNonNull(builder.soapVersion);
        this.defaultBody = Objects.requireNonNull(builder.defaultBody);
        this.currentResponseSequenceIndex = Objects.requireNonNull(builder.currentResponseSequenceIndex);
        this.forwardedEndpoint = builder.forwardedEndpoint;
        this.originalEndpoint = Objects.requireNonNull(builder.originalEndpoint);
        this.simulateNetworkDelay = Objects.requireNonNull(builder.simulateNetworkDelay);
        this.networkDelay = builder.networkDelay;
        this.defaultMockResponseId = builder.defaultMockResponseId;
        this.portId = Objects.requireNonNull(builder.portId);
        this.mockOnFailure = Objects.requireNonNull(builder.mockOnFailure);
        this.identifyStrategy = Objects.requireNonNull(builder.identifyStrategy);
        this.invokeAddress = builder.invokeAddress;
        this.defaultResponseName = builder.defaultResponseName;
        this.mockResponses = Optional.ofNullable(builder.mockResponses).orElseGet(CopyOnWriteArrayList::new);
        this.automaticForward = Objects.requireNonNull(builder.automaticForward);
    }

    @XmlElement
    public String getId() {
        return id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    @XmlElement
    public String getIdentifier() {
        return identifier;
    }

    @XmlElement
    public SoapOperationIdentifier getOperationIdentifier() {
        return operationIdentifier;
    }

    @XmlElement
    public String getPortId() {
        return portId;
    }

    @XmlElement
    public SoapResponseStrategy getResponseStrategy() {
        return responseStrategy;
    }

    @XmlElement
    public SoapOperationStatus getStatus() {
        return status;
    }

    @XmlElementWrapper(name = "mockResponses")
    @XmlElement(name = "mockResponse")
    public List<SoapMockResponse> getMockResponses() {
        return mockResponses;
    }

    @XmlElement
    public String getInvokeAddress() {
        return invokeAddress;
    }

    @XmlElement
    public String getDefaultBody() {
        return defaultBody;
    }

    @XmlElement
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    @XmlElement
    public SoapVersion getSoapVersion() {
        return soapVersion;
    }

    @XmlElement
    public Integer getCurrentResponseSequenceIndex() {
        return currentResponseSequenceIndex;
    }

    @XmlElement
    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    @XmlElement
    public String getOriginalEndpoint() {
        return originalEndpoint;
    }

    @XmlElement
    public Boolean getSimulateNetworkDelay() {
        return simulateNetworkDelay;
    }

    @XmlElement
    public Long getNetworkDelay() {
        return networkDelay;
    }

    @XmlElement
    public String getDefaultResponseName() {
        return defaultResponseName;
    }

    @XmlElement
    public Boolean getMockOnFailure() {
        return mockOnFailure;
    }

    @XmlElement
    public SoapOperationIdentifyStrategy getIdentifyStrategy() {
        return identifyStrategy;
    }

    @XmlElement
    public String getDefaultMockResponseId() {
        return defaultMockResponseId;
    }

    public boolean getAutomaticForward() {
        return automaticForward;
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
        return Objects.hash(id, name, identifier, operationIdentifier, responseStrategy, status, httpMethod, soapVersion, defaultBody, currentResponseSequenceIndex, forwardedEndpoint, originalEndpoint, simulateNetworkDelay, networkDelay, defaultMockResponseId, portId, mockOnFailure, identifyStrategy, mockResponses, invokeAddress, defaultResponseName, automaticForward);
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
                .mockResponses(mockResponses)
                .originalEndpoint(originalEndpoint)
                .status(status)
                .httpMethod(httpMethod)
                .networkDelay(networkDelay);
    }

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
        private List<SoapMockResponse> mockResponses = new CopyOnWriteArrayList<SoapMockResponse>();
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
