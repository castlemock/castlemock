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

package com.castlemock.core.mock.rest.model.project.domain;

import com.castlemock.core.basis.model.http.domain.HttpMethod;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
public class RestMethod {

    private String id;
    private String name;
    private String resourceId;
    private String defaultBody;
    private HttpMethod httpMethod;
    private String forwardedEndpoint;
    private RestMethodStatus status;
    private RestResponseStrategy responseStrategy;
    private Integer currentResponseSequenceIndex = 0;
    private boolean simulateNetworkDelay;
    private long networkDelay;
    @Deprecated
    private String defaultQueryMockResponseId;
    private String defaultMockResponseId;

    private List<RestMockResponse> mockResponses = new CopyOnWriteArrayList<RestMockResponse>();
    private String invokeAddress;
    private String defaultResponseName;

    public RestMethod(){

    }

    private RestMethod(final Builder builder){
        this.id = Objects.requireNonNull(builder.id);
        this.name = Objects.requireNonNull(builder.name);
        this.resourceId = Objects.requireNonNull(builder.resourceId);
        this.defaultBody = Objects.requireNonNull(builder.defaultBody);
        this.httpMethod = Objects.requireNonNull(builder.httpMethod);
        this.forwardedEndpoint = Objects.requireNonNull(builder.forwardedEndpoint);
        this.status = Objects.requireNonNull(builder.status);
        this.responseStrategy = Objects.requireNonNull(builder.responseStrategy);
        this.currentResponseSequenceIndex = Objects.requireNonNull(builder.currentResponseSequenceIndex);
        this.simulateNetworkDelay = Objects.requireNonNull(builder.simulateNetworkDelay);
        this.networkDelay = Objects.requireNonNull(builder.networkDelay);
        this.defaultQueryMockResponseId = Objects.requireNonNull(builder.defaultQueryMockResponseId);
        this.defaultMockResponseId = Objects.requireNonNull(builder.defaultMockResponseId);
        this.mockResponses = Objects.requireNonNull(builder.mockResponses);
        this.invokeAddress = Objects.requireNonNull(builder.invokeAddress);
        this.defaultResponseName = Objects.requireNonNull(builder.defaultResponseName);
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
    public String getDefaultBody() {
        return defaultBody;
    }

    public void setDefaultBody(String defaultBody) {
        this.defaultBody = defaultBody;
    }

    @XmlElement
    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    @XmlElement
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    @XmlElementWrapper(name = "mockResponses")
    @XmlElement(name = "mockResponse")
    public List<RestMockResponse> getMockResponses() {
        return mockResponses;
    }

    public void setMockResponses(List<RestMockResponse> mockResponses) {
        this.mockResponses = mockResponses;
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
    public String getInvokeAddress() {
        return invokeAddress;
    }

    public void setInvokeAddress(String invokeAddress) {
        this.invokeAddress = invokeAddress;
    }

    @XmlElement
    public boolean getSimulateNetworkDelay() {
        return simulateNetworkDelay;
    }

    public void setSimulateNetworkDelay(boolean simulateNetworkDelay) {
        this.simulateNetworkDelay = simulateNetworkDelay;
    }

    @XmlElement
    public long getNetworkDelay() {
        return networkDelay;
    }

    public void setNetworkDelay(long networkDelay) {
        this.networkDelay = networkDelay;
    }

    @XmlElement
    @Deprecated
    public String getDefaultQueryMockResponseId() {
        return defaultQueryMockResponseId;
    }

    public void setDefaultQueryMockResponseId(String defaultQueryMockResponseId) {
        this.defaultQueryMockResponseId = defaultQueryMockResponseId;
    }

    @XmlElement
    public String getDefaultResponseName() {
        return defaultResponseName;
    }

    public void setDefaultResponseName(String defaultResponseName) {
        this.defaultResponseName = defaultResponseName;
    }

    @XmlElement
    public String getDefaultMockResponseId() {
        return defaultMockResponseId;
    }

    public void setDefaultMockResponseId(String defaultMockResponseId) {
        this.defaultMockResponseId = defaultMockResponseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestMethod that = (RestMethod) o;
        return simulateNetworkDelay == that.simulateNetworkDelay &&
                networkDelay == that.networkDelay &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(resourceId, that.resourceId) &&
                Objects.equals(defaultBody, that.defaultBody) &&
                httpMethod == that.httpMethod &&
                Objects.equals(forwardedEndpoint, that.forwardedEndpoint) &&
                status == that.status &&
                responseStrategy == that.responseStrategy &&
                Objects.equals(currentResponseSequenceIndex, that.currentResponseSequenceIndex) &&
                Objects.equals(defaultQueryMockResponseId, that.defaultQueryMockResponseId) &&
                Objects.equals(defaultMockResponseId, that.defaultMockResponseId) &&
                Objects.equals(mockResponses, that.mockResponses) &&
                Objects.equals(invokeAddress, that.invokeAddress) &&
                Objects.equals(defaultResponseName, that.defaultResponseName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, resourceId, defaultBody, httpMethod, forwardedEndpoint, status, responseStrategy, currentResponseSequenceIndex, simulateNetworkDelay, networkDelay, defaultQueryMockResponseId, defaultMockResponseId, mockResponses, invokeAddress, defaultResponseName);
    }

    @Override
    public String toString() {
        return "RestMethod{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", resourceId='" + resourceId + '\'' +
                ", defaultBody='" + defaultBody + '\'' +
                ", httpMethod=" + httpMethod +
                ", forwardedEndpoint='" + forwardedEndpoint + '\'' +
                ", status=" + status +
                ", responseStrategy=" + responseStrategy +
                ", currentResponseSequenceIndex=" + currentResponseSequenceIndex +
                ", simulateNetworkDelay=" + simulateNetworkDelay +
                ", networkDelay=" + networkDelay +
                ", defaultQueryMockResponseId='" + defaultQueryMockResponseId + '\'' +
                ", defaultMockResponseId='" + defaultMockResponseId + '\'' +
                ", mockResponses=" + mockResponses +
                ", invokeAddress='" + invokeAddress + '\'' +
                ", defaultResponseName='" + defaultResponseName + '\'' +
                '}';
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
        private Integer currentResponseSequenceIndex = 0;
        private Boolean simulateNetworkDelay;
        private Long networkDelay;
        private String defaultQueryMockResponseId;
        private String defaultMockResponseId;
        private List<RestMockResponse> mockResponses = new CopyOnWriteArrayList<RestMockResponse>();
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

        public Builder defaultQueryMockResponseId(final String defaultQueryMockResponseId) {
            this.defaultQueryMockResponseId = defaultQueryMockResponseId;
            return this;
        }

        public Builder defaultMockResponseId(final String defaultMockResponseId) {
            this.defaultMockResponseId = defaultMockResponseId;
            return this;
        }

        public Builder mockResponses(final List<RestMockResponse> mockResponses) {
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

        public RestMethod build() {
            return new RestMethod(this);
        }
    }
}
