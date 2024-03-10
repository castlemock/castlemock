/*
 * Copyright 2024 Karl Dahlgren
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

package com.castlemock.repository.soap.file.project.model;

import com.castlemock.model.core.Saveable;
import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.mock.soap.domain.SoapOperationIdentifyStrategy;
import com.castlemock.model.mock.soap.domain.SoapOperationStatus;
import com.castlemock.model.mock.soap.domain.SoapResponseStrategy;
import com.castlemock.model.mock.soap.domain.SoapVersion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "soapOperation")
@XmlAccessorType(XmlAccessType.NONE)
public class SoapOperationFile implements Saveable<String> {

    @XmlElement
    private String id;
    @XmlElement
    private String name;
    @XmlElement
    private String portId;
    @XmlElement
    private SoapResponseStrategy responseStrategy;
    @XmlElement
    private String identifier;
    @XmlElement
    private SoapOperationIdentifierFile operationIdentifier;
    @XmlElement
    private SoapOperationStatus status;
    @XmlElement
    private HttpMethod httpMethod;
    @XmlElement
    private SoapVersion soapVersion;
    @XmlElement
    private String defaultBody;
    @XmlElement
    private Integer currentResponseSequenceIndex;
    @XmlElement
    private String forwardedEndpoint;
    @XmlElement
    private String originalEndpoint;
    @XmlElement
    private String defaultMockResponseId;
    @XmlElement
    private Boolean simulateNetworkDelay;
    @XmlElement
    private Long networkDelay;
    @XmlElement
    private Boolean mockOnFailure;
    @XmlElement
    private SoapOperationIdentifyStrategy identifyStrategy;
    @XmlElement
    private Boolean automaticForward;

    private SoapOperationFile() {

    }

    private SoapOperationFile(final Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "id");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.operationIdentifier = Objects.requireNonNull(builder.operationIdentifier, "operationIdentifier");
        this.responseStrategy = Objects.requireNonNull(builder.responseStrategy, "responseStrategy");
        this.status = Objects.requireNonNull(builder.status, "status");
        this.httpMethod = Objects.requireNonNull(builder.httpMethod, "httpMethod");
        this.soapVersion = Objects.requireNonNull(builder.soapVersion, "soapVersion");
        this.currentResponseSequenceIndex = Objects.requireNonNull(builder.currentResponseSequenceIndex, "currentResponseSequenceIndex");
        this.portId = Objects.requireNonNull(builder.portId, "portId");
        this.identifyStrategy = Objects.requireNonNull(builder.identifyStrategy, "identifyStrategy");

        this.mockOnFailure = builder.mockOnFailure;
        this.automaticForward = builder.automaticForward;
        this.simulateNetworkDelay = builder.simulateNetworkDelay;
        this.defaultBody = builder.defaultBody;
        this.originalEndpoint = builder.originalEndpoint;
        this.forwardedEndpoint = builder.forwardedEndpoint;
        this.networkDelay = builder.networkDelay;
        this.defaultMockResponseId = builder.defaultMockResponseId;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public SoapOperationIdentifierFile getOperationIdentifier() {
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

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public SoapVersion getSoapVersion() {
        return soapVersion;
    }

    public String getDefaultBody() {
        return defaultBody;
    }

    public Integer getCurrentResponseSequenceIndex() {
        return currentResponseSequenceIndex;
    }

    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    public String getOriginalEndpoint() {
        return originalEndpoint;
    }

    public Boolean getSimulateNetworkDelay() {
        return simulateNetworkDelay;
    }

    public Long getNetworkDelay() {
        return networkDelay;
    }

    public Boolean getMockOnFailure() {
        return mockOnFailure;
    }

    public SoapOperationIdentifyStrategy getIdentifyStrategy() {
        return identifyStrategy;
    }

    public String getDefaultMockResponseId() {
        return defaultMockResponseId;
    }

    public Boolean getAutomaticForward() {
        return automaticForward;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private String id;
        private String name;
        private String portId;
        private SoapResponseStrategy responseStrategy;
        private SoapOperationIdentifierFile operationIdentifier;
        private SoapOperationStatus status;
        private HttpMethod httpMethod;
        private SoapVersion soapVersion;
        private String defaultBody;
        private Integer currentResponseSequenceIndex;
        private String forwardedEndpoint;
        private String originalEndpoint;
        private String defaultMockResponseId;
        private Boolean simulateNetworkDelay;
        private Long networkDelay;
        private Boolean mockOnFailure;
        private SoapOperationIdentifyStrategy identifyStrategy;
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

        public Builder portId(final String portId) {
            this.portId = portId;
            return this;
        }

        public Builder responseStrategy(final SoapResponseStrategy responseStrategy) {
            this.responseStrategy = responseStrategy;
            return this;
        }

        public Builder operationIdentifier(final SoapOperationIdentifierFile operationIdentifier) {
            this.operationIdentifier = operationIdentifier;
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

        public Builder defaultMockResponseId(final String defaultMockResponseId) {
            this.defaultMockResponseId = defaultMockResponseId;
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

        public SoapOperationFile build() {
            return new SoapOperationFile(this);
        }
    }
}