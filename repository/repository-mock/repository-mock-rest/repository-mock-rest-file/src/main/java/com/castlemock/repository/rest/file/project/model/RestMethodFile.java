package com.castlemock.repository.rest.file.project.model;

import com.castlemock.model.core.Saveable;
import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.mock.rest.domain.RestMethodStatus;
import com.castlemock.model.mock.rest.domain.RestResponseStrategy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "restMethod")
@XmlAccessorType(XmlAccessType.NONE)
public class RestMethodFile implements Saveable<String> {

    @XmlElement
    private String id;
    @XmlElement
    private String name;
    @XmlElement
    private String resourceId;
    @XmlElement
    private String defaultBody;
    @XmlElement
    private HttpMethod httpMethod;
    @XmlElement
    private String forwardedEndpoint;
    @XmlElement
    private RestMethodStatus status;
    @XmlElement
    private RestResponseStrategy responseStrategy;
    @XmlElement
    private Integer currentResponseSequenceIndex;
    @XmlElement
    private Boolean simulateNetworkDelay;
    @XmlElement
    private Long networkDelay;
    @XmlElement
    private String defaultMockResponseId;
    @XmlElement
    private Boolean automaticForward;

    private RestMethodFile() {

    }

    private RestMethodFile(final Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "id");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.status = Objects.requireNonNull(builder.status, "status");
        this.resourceId = Objects.requireNonNull(builder.resourceId, "resourceId");
        this.httpMethod = Objects.requireNonNull(builder.httpMethod, "httpMethod");
        this.responseStrategy = Objects.requireNonNull(builder.responseStrategy, "responseStrategy");
        this.currentResponseSequenceIndex = Objects.requireNonNull(builder.currentResponseSequenceIndex, "currentResponseSequenceIndex");
        this.defaultBody = builder.defaultBody;
        this.forwardedEndpoint = builder.forwardedEndpoint;
        this.simulateNetworkDelay = builder.simulateNetworkDelay;
        this.networkDelay = builder.networkDelay;
        this.defaultMockResponseId = builder.defaultMockResponseId;
        this.automaticForward = builder.automaticForward;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getDefaultBody() {
        return defaultBody;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    public RestMethodStatus getStatus() {
        return status;
    }

    public RestResponseStrategy getResponseStrategy() {
        return responseStrategy;
    }

    public Integer getCurrentResponseSequenceIndex() {
        return currentResponseSequenceIndex;
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

    public Boolean getAutomaticForward() {
        return automaticForward;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String name;
        private String resourceId;
        private String defaultBody;
        private HttpMethod httpMethod;
        private String forwardedEndpoint;
        private RestMethodStatus status;
        private RestResponseStrategy responseStrategy;
        private Integer currentResponseSequenceIndex;
        private Boolean simulateNetworkDelay;
        private Long networkDelay;
        private String defaultMockResponseId;
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

        public Builder resourceId(final String resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        public Builder defaultBody(final String defaultBody) {
            this.defaultBody = defaultBody;
            return this;
        }

        public Builder httpMethod(final HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public Builder forwardedEndpoint(final String forwardedEndpoint) {
            this.forwardedEndpoint = forwardedEndpoint;
            return this;
        }

        public Builder status(final RestMethodStatus status) {
            this.status = status;
            return this;
        }

        public Builder responseStrategy(final RestResponseStrategy responseStrategy) {
            this.responseStrategy = responseStrategy;
            return this;
        }

        public Builder currentResponseSequenceIndex(final Integer currentResponseSequenceIndex) {
            this.currentResponseSequenceIndex = currentResponseSequenceIndex;
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

        public Builder automaticForward(final Boolean automaticForward) {
            this.automaticForward = automaticForward;
            return this;
        }

        public RestMethodFile build() {
            return new RestMethodFile(this);
        }
    }
}