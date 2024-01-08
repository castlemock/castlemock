package com.castlemock.repository.soap.file.project.model;

import com.castlemock.model.core.Saveable;
import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.mock.soap.domain.SoapOperationIdentifyStrategy;
import com.castlemock.model.mock.soap.domain.SoapOperationStatus;
import com.castlemock.model.mock.soap.domain.SoapResponseStrategy;
import com.castlemock.model.mock.soap.domain.SoapVersion;
import org.dozer.Mapping;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "soapOperation")
public class SoapOperationFile implements Saveable<String> {

    @Mapping("id")
    private String id;
    @Mapping("name")
    private String name;
    @Mapping("portId")
    private String portId;
    @Mapping("responseStrategy")
    private SoapResponseStrategy responseStrategy;
    @Mapping("identifier")
    private String identifier;
    @Mapping("operationIdentifier")
    private SoapOperationIdentifierFile operationIdentifier;
    @Mapping("status")
    private SoapOperationStatus status;
    @Mapping("httpMethod")
    private HttpMethod httpMethod;
    @Mapping("soapVersion")
    private SoapVersion soapVersion;
    @Mapping("defaultBody")
    private String defaultBody;
    @Mapping("currentResponseSequenceIndex")
    private Integer currentResponseSequenceIndex;
    @Mapping("forwardedEndpoint")
    private String forwardedEndpoint;
    @Mapping("originalEndpoint")
    private String originalEndpoint;
    @Mapping("defaultMockResponseId")
    private String defaultMockResponseId;
    @Mapping("simulateNetworkDelay")
    private Boolean simulateNetworkDelay;
    @Mapping("networkDelay")
    private Long networkDelay;
    @Mapping("mockOnFailure")
    private Boolean mockOnFailure;
    @Mapping("identifyStrategy")
    private SoapOperationIdentifyStrategy identifyStrategy;
    @Mapping("automaticForward")
    private Boolean automaticForward;

    private SoapOperationFile() {

    }

    private SoapOperationFile(final Builder builder) {
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

        this.mockOnFailure = builder.mockOnFailure;
        this.automaticForward = builder.automaticForward;
        this.simulateNetworkDelay = builder.simulateNetworkDelay;
        this.defaultBody = builder.defaultBody;
        this.originalEndpoint = builder.originalEndpoint;
        this.forwardedEndpoint = builder.forwardedEndpoint;
        this.networkDelay = builder.networkDelay;
        this.defaultMockResponseId = builder.defaultMockResponseId;
    }

    @XmlElement
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(final String id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @XmlElement
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(final String identifier) {
        this.identifier = identifier;
    }

    @XmlElement
    public SoapOperationIdentifierFile getOperationIdentifier() {
        return operationIdentifier;
    }

    public void setOperationIdentifier(final SoapOperationIdentifierFile operationIdentifier) {
        this.operationIdentifier = operationIdentifier;
    }

    @XmlElement
    public String getPortId() {
        return portId;
    }

    public void setPortId(final String portId) {
        this.portId = portId;
    }

    @XmlElement
    public SoapResponseStrategy getResponseStrategy() {
        return responseStrategy;
    }

    public void setResponseStrategy(final SoapResponseStrategy responseStrategy) {
        this.responseStrategy = responseStrategy;
    }

    @XmlElement
    public SoapOperationStatus getStatus() {
        return status;
    }

    public void setStatus(final SoapOperationStatus status) {
        this.status = status;
    }

    @XmlElement
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(final HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    @XmlElement
    public SoapVersion getSoapVersion() {
        return soapVersion;
    }

    public void setSoapVersion(final SoapVersion soapVersion) {
        this.soapVersion = soapVersion;
    }

    @XmlElement
    public String getDefaultBody() {
        return defaultBody;
    }

    public void setDefaultBody(final String defaultBody) {
        this.defaultBody = defaultBody;
    }

    @XmlElement
    public Integer getCurrentResponseSequenceIndex() {
        return currentResponseSequenceIndex;
    }

    public void setCurrentResponseSequenceIndex(final Integer currentResponseSequenceIndex) {
        this.currentResponseSequenceIndex = currentResponseSequenceIndex;
    }

    @XmlElement
    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    public void setForwardedEndpoint(final String forwardedEndpoint) {
        this.forwardedEndpoint = forwardedEndpoint;
    }

    @XmlElement
    public String getOriginalEndpoint() {
        return originalEndpoint;
    }

    public void setOriginalEndpoint(final String originalEndpoint) {
        this.originalEndpoint = originalEndpoint;
    }

    @XmlElement
    public Boolean getSimulateNetworkDelay() {
        return simulateNetworkDelay;
    }

    public void setSimulateNetworkDelay(final boolean simulateNetworkDelay) {
        this.simulateNetworkDelay = simulateNetworkDelay;
    }

    @XmlElement
    public Long getNetworkDelay() {
        return networkDelay;
    }

    public void setNetworkDelay(final long networkDelay) {
        this.networkDelay = networkDelay;
    }

    @XmlElement
    public Boolean getMockOnFailure() {
        return mockOnFailure;
    }

    public void setMockOnFailure(final boolean mockOnFailure) {
        this.mockOnFailure = mockOnFailure;
    }

    @XmlElement
    public SoapOperationIdentifyStrategy getIdentifyStrategy() {
        return identifyStrategy;
    }

    public void setIdentifyStrategy(final SoapOperationIdentifyStrategy identifyStrategy) {
        this.identifyStrategy = identifyStrategy;
    }

    @XmlElement
    public String getDefaultMockResponseId() {
        return defaultMockResponseId;
    }

    public void setDefaultMockResponseId(final String defaultMockResponseId) {
        this.defaultMockResponseId = defaultMockResponseId;
    }

    @XmlElement
    public Boolean getAutomaticForward() {
        return automaticForward;
    }

    public void setAutomaticForward(boolean automaticForward) {
        this.automaticForward = automaticForward;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private String id;
        private String name;
        private String portId;
        private SoapResponseStrategy responseStrategy;
        private String identifier;
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

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder portId(String portId) {
            this.portId = portId;
            return this;
        }

        public Builder responseStrategy(SoapResponseStrategy responseStrategy) {
            this.responseStrategy = responseStrategy;
            return this;
        }

        public Builder identifier(String identifier) {
            this.identifier = identifier;
            return this;
        }

        public Builder operationIdentifier(SoapOperationIdentifierFile operationIdentifier) {
            this.operationIdentifier = operationIdentifier;
            return this;
        }

        public Builder status(SoapOperationStatus status) {
            this.status = status;
            return this;
        }

        public Builder httpMethod(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public Builder soapVersion(SoapVersion soapVersion) {
            this.soapVersion = soapVersion;
            return this;
        }

        public Builder defaultBody(String defaultBody) {
            this.defaultBody = defaultBody;
            return this;
        }

        public Builder currentResponseSequenceIndex(Integer currentResponseSequenceIndex) {
            this.currentResponseSequenceIndex = currentResponseSequenceIndex;
            return this;
        }

        public Builder forwardedEndpoint(String forwardedEndpoint) {
            this.forwardedEndpoint = forwardedEndpoint;
            return this;
        }

        public Builder originalEndpoint(String originalEndpoint) {
            this.originalEndpoint = originalEndpoint;
            return this;
        }

        public Builder defaultMockResponseId(String defaultMockResponseId) {
            this.defaultMockResponseId = defaultMockResponseId;
            return this;
        }

        public Builder simulateNetworkDelay(Boolean simulateNetworkDelay) {
            this.simulateNetworkDelay = simulateNetworkDelay;
            return this;
        }

        public Builder networkDelay(Long networkDelay) {
            this.networkDelay = networkDelay;
            return this;
        }

        public Builder mockOnFailure(Boolean mockOnFailure) {
            this.mockOnFailure = mockOnFailure;
            return this;
        }

        public Builder identifyStrategy(SoapOperationIdentifyStrategy identifyStrategy) {
            this.identifyStrategy = identifyStrategy;
            return this;
        }

        public Builder automaticForward(Boolean automaticForward) {
            this.automaticForward = automaticForward;
            return this;
        }

        public SoapOperationFile build() {
            return new SoapOperationFile(this);
        }
    }
}