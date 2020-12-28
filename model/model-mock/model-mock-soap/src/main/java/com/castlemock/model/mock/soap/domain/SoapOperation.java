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
    @Deprecated
    private String defaultXPathMockResponseId;
    private String defaultMockResponseId;
    private String portId;
    private Boolean mockOnFailure;
    private SoapOperationIdentifyStrategy identifyStrategy;

    private List<SoapMockResponse> mockResponses = new CopyOnWriteArrayList<SoapMockResponse>();

    private String invokeAddress;

    private String defaultResponseName;

    public SoapOperation(){

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
        this.forwardedEndpoint = Objects.requireNonNull(builder.forwardedEndpoint);
        this.originalEndpoint = Objects.requireNonNull(builder.originalEndpoint);
        this.simulateNetworkDelay = Objects.requireNonNull(builder.simulateNetworkDelay);
        this.networkDelay = Objects.requireNonNull(builder.networkDelay);
        this.defaultXPathMockResponseId = builder.defaultXPathMockResponseId;
        this.defaultMockResponseId = builder.defaultMockResponseId;
        this.portId = Objects.requireNonNull(builder.portId);
        this.mockOnFailure = Objects.requireNonNull(builder.mockOnFailure);
        this.identifyStrategy = Objects.requireNonNull(builder.identifyStrategy);
        this.invokeAddress = Objects.requireNonNull(builder.invokeAddress);
        this.defaultResponseName = builder.defaultResponseName;
        this.mockResponses = Optional.ofNullable(builder.mockResponses).orElseGet(CopyOnWriteArrayList::new);
    }

    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @XmlElement
    public SoapOperationIdentifier getOperationIdentifier() {
        return operationIdentifier;
    }

    public void setOperationIdentifier(SoapOperationIdentifier operationIdentifier) {
        this.operationIdentifier = operationIdentifier;
    }

    @XmlElement
    public String getPortId() {
        return portId;
    }

    public void setPortId(String portId) {
        this.portId = portId;
    }

    @XmlElement
    public SoapResponseStrategy getResponseStrategy() {
        return responseStrategy;
    }

    public void setResponseStrategy(SoapResponseStrategy responseStrategy) {
        this.responseStrategy = responseStrategy;
    }

    @XmlElement
    public SoapOperationStatus getStatus() {
        return status;
    }

    public void setStatus(SoapOperationStatus status) {
        this.status = status;
    }

    @XmlElementWrapper(name = "mockResponses")
    @XmlElement(name = "mockResponse")
    public List<SoapMockResponse> getMockResponses() {
        return mockResponses;
    }

    public void setMockResponses(List<SoapMockResponse> mockResponses) {
        this.mockResponses = mockResponses;
    }

    @XmlElement
    public String getInvokeAddress() {
        return invokeAddress;
    }

    public void setInvokeAddress(String invokeAddress) {
        this.invokeAddress = invokeAddress;
    }

    @XmlElement
    public String getDefaultBody() {
        return defaultBody;
    }

    public void setDefaultBody(String defaultBody) {
        this.defaultBody = defaultBody;
    }

    @XmlElement
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    @XmlElement
    public SoapVersion getSoapVersion() {
        return soapVersion;
    }

    public void setSoapVersion(SoapVersion soapVersion) {
        this.soapVersion = soapVersion;
    }

    @XmlElement
    public Integer getCurrentResponseSequenceIndex() {
        return currentResponseSequenceIndex;
    }

    public void setCurrentResponseSequenceIndex(Integer currentResponseSequenceIndex) {
        this.currentResponseSequenceIndex = currentResponseSequenceIndex;
    }

    @XmlElement
    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    public void setForwardedEndpoint(String forwardedEndpoint) {
        this.forwardedEndpoint = forwardedEndpoint;
    }

    @XmlElement
    public String getOriginalEndpoint() {
        return originalEndpoint;
    }

    public void setOriginalEndpoint(String originalEndpoint) {
        this.originalEndpoint = originalEndpoint;
    }

    @XmlElement
    public Boolean getSimulateNetworkDelay() {
        return simulateNetworkDelay;
    }

    public void setSimulateNetworkDelay(Boolean simulateNetworkDelay) {
        this.simulateNetworkDelay = simulateNetworkDelay;
    }

    @XmlElement
    public Long getNetworkDelay() {
        return networkDelay;
    }

    public void setNetworkDelay(Long networkDelay) {
        this.networkDelay = networkDelay;
    }

    @XmlElement
    @Deprecated
    public String getDefaultXPathMockResponseId() {
        return defaultXPathMockResponseId;
    }

    public void setDefaultXPathMockResponseId(String defaultXPathMockResponseId) {
        this.defaultXPathMockResponseId = defaultXPathMockResponseId;
    }

    @XmlElement
    public String getDefaultResponseName() {
        return defaultResponseName;
    }

    public void setDefaultResponseName(String defaultResponseName) {
        this.defaultResponseName = defaultResponseName;
    }

    @XmlElement
    public Boolean getMockOnFailure() {
        return mockOnFailure;
    }

    public void setMockOnFailure(Boolean mockOnFailure) {
        this.mockOnFailure = mockOnFailure;
    }

    @XmlElement
    public SoapOperationIdentifyStrategy getIdentifyStrategy() {
        return identifyStrategy;
    }

    public void setIdentifyStrategy(SoapOperationIdentifyStrategy identifyStrategy) {
        this.identifyStrategy = identifyStrategy;
    }

    @XmlElement
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
        private String defaultXPathMockResponseId;
        private String defaultMockResponseId;
        private String portId;
        private Boolean mockOnFailure;
        private SoapOperationIdentifyStrategy identifyStrategy;
        private List<SoapMockResponse> mockResponses = new CopyOnWriteArrayList<SoapMockResponse>();
        private String invokeAddress;
        private String defaultResponseName;

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

        public Builder defaultXPathMockResponseId(final String defaultXPathMockResponseId) {
            this.defaultXPathMockResponseId = defaultXPathMockResponseId;
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

        public SoapOperation build() {
            return new SoapOperation(this);
        }
    }
}
