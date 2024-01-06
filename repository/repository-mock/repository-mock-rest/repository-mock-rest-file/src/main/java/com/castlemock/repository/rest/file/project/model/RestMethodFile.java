package com.castlemock.repository.rest.file.project.model;

import com.castlemock.model.core.Saveable;
import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.mock.rest.domain.RestMethodStatus;
import com.castlemock.model.mock.rest.domain.RestResponseStrategy;
import org.dozer.Mapping;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "restMethod")
public class RestMethodFile implements Saveable<String> {

    @Mapping("id")
    private String id;
    @Mapping("name")
    private String name;
    @Mapping("resourceId")
    private String resourceId;
    @Mapping("defaultBody")
    private String defaultBody;
    @Mapping("httpMethod")
    private HttpMethod httpMethod;
    @Mapping("forwardedEndpoint")
    private String forwardedEndpoint;
    @Mapping("status")
    private RestMethodStatus status;
    @Mapping("responseStrategy")
    private RestResponseStrategy responseStrategy;
    @Mapping("currentResponseSequenceIndex")
    private Integer currentResponseSequenceIndex;
    @Mapping("simulateNetworkDelay")
    private Boolean simulateNetworkDelay;
    @Mapping("networkDelay")
    private Long networkDelay;
    @Mapping("defaultMockResponseId")
    private String defaultMockResponseId;
    @Mapping("automaticForward")
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
    @XmlElement
    public String getId() {
        return id;
    }

    @Override
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
    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
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
    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    public void setForwardedEndpoint(String forwardedEndpoint) {
        this.forwardedEndpoint = forwardedEndpoint;
    }

    @XmlElement
    public RestMethodStatus getStatus() {
        return status;
    }

    public void setStatus(RestMethodStatus status) {
        this.status = status;
    }

    @XmlElement
    public RestResponseStrategy getResponseStrategy() {
        return responseStrategy;
    }

    public void setResponseStrategy(RestResponseStrategy responseStrategy) {
        this.responseStrategy = responseStrategy;
    }

    @XmlElement
    public Integer getCurrentResponseSequenceIndex() {
        return currentResponseSequenceIndex;
    }

    public void setCurrentResponseSequenceIndex(Integer currentResponseSequenceIndex) {
        this.currentResponseSequenceIndex = currentResponseSequenceIndex;
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
    public String getDefaultMockResponseId() {
        return defaultMockResponseId;
    }

    public void setDefaultMockResponseId(String defaultMockResponseId) {
        this.defaultMockResponseId = defaultMockResponseId;
    }

    @XmlElement
    public Boolean getAutomaticForward() {
        return automaticForward;
    }

    public void setAutomaticForward(Boolean automaticForward) {
        this.automaticForward = automaticForward;
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


        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder resourceId(String resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        public Builder defaultBody(String defaultBody) {
            this.defaultBody = defaultBody;
            return this;
        }

        public Builder httpMethod(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public Builder forwardedEndpoint(String forwardedEndpoint) {
            this.forwardedEndpoint = forwardedEndpoint;
            return this;
        }

        public Builder status(RestMethodStatus status) {
            this.status = status;
            return this;
        }

        public Builder responseStrategy(RestResponseStrategy responseStrategy) {
            this.responseStrategy = responseStrategy;
            return this;
        }

        public Builder currentResponseSequenceIndex(Integer currentResponseSequenceIndex) {
            this.currentResponseSequenceIndex = currentResponseSequenceIndex;
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

        public Builder defaultMockResponseId(String defaultMockResponseId) {
            this.defaultMockResponseId = defaultMockResponseId;
            return this;
        }

        public Builder automaticForward(Boolean automaticForward) {
            this.automaticForward = automaticForward;
            return this;
        }

        public RestMethodFile build() {
            return new RestMethodFile(this);
        }
    }
}