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

package com.castlemock.model.mock.rest.domain;

import com.castlemock.model.core.http.HttpMethod;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.xml.bind.annotation.*;
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
@JsonDeserialize(builder = RestMethod.Builder.class)
public class RestMethod {

    @XmlElement
    private final String id;

    @XmlElement
    private final String name;

    @XmlElement
    private final String resourceId;

    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String defaultBody;

    @XmlElement
    private final HttpMethod httpMethod;

    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String forwardedEndpoint;

    @XmlElement
    private final RestMethodStatus status;

    @XmlElement
    private final RestResponseStrategy responseStrategy;

    @XmlElement
    private final Integer currentResponseSequenceIndex;

    @XmlElement
    private final Boolean simulateNetworkDelay;

    @XmlElement
    private final Long networkDelay;

    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String defaultMockResponseId;

    @XmlElementWrapper(name = "mockResponses")
    @XmlElement(name = "mockResponse")
    private final List<RestMockResponse> mockResponses;

    @XmlElement
    private final String uri;

    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String defaultResponseName;

    @XmlElement
    private final Boolean automaticForward;

    private RestMethod(final Builder builder){
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
        this.uri = builder.uri;
        this.defaultResponseName = builder.defaultResponseName;
        this.automaticForward = builder.automaticForward;
        this.mockResponses = Optional.ofNullable(builder.mockResponses).orElseGet(List::of);
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getDefaultBody() {
        return Optional.ofNullable(defaultBody);
    }

    public String getResourceId() {
        return resourceId;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public List<RestMockResponse> getMockResponses() {
        return Optional.ofNullable(mockResponses)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    public Optional<String> getForwardedEndpoint() {
        return Optional.ofNullable(forwardedEndpoint);
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

    public String getUri() {
        return uri;
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
        RestMethod that = (RestMethod) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(resourceId, that.resourceId) && Objects.equals(defaultBody, that.defaultBody) && httpMethod == that.httpMethod && Objects.equals(forwardedEndpoint, that.forwardedEndpoint) && status == that.status && responseStrategy == that.responseStrategy && Objects.equals(currentResponseSequenceIndex, that.currentResponseSequenceIndex) && Objects.equals(simulateNetworkDelay, that.simulateNetworkDelay) && Objects.equals(networkDelay, that.networkDelay) && Objects.equals(defaultMockResponseId, that.defaultMockResponseId) && Objects.equals(mockResponses, that.mockResponses) && Objects.equals(uri, that.uri) && Objects.equals(defaultResponseName, that.defaultResponseName) && Objects.equals(automaticForward, that.automaticForward);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, resourceId, defaultBody, httpMethod, forwardedEndpoint, status, responseStrategy, currentResponseSequenceIndex, simulateNetworkDelay, networkDelay, defaultMockResponseId, mockResponses, uri, defaultResponseName, automaticForward);
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
                ", defaultMockResponseId='" + defaultMockResponseId + '\'' +
                ", mockResponses=" + mockResponses +
                ", uri='" + uri + '\'' +
                ", defaultResponseName='" + defaultResponseName + '\'' +
                ", automaticForward='" + automaticForward + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder()
                .id(this.id)
                .name(this.name)
                .automaticForward(this.automaticForward)
                .simulateNetworkDelay(this.simulateNetworkDelay)
                .networkDelay(this.networkDelay)
                .resourceId(this.resourceId)
                .defaultBody(this.defaultBody)
                .responseStrategy(this.responseStrategy)
                .status(this.status)
                .httpMethod(this.httpMethod)
                .mockResponses(Optional.ofNullable(this.mockResponses)
                        .map(respones -> respones.stream()
                                .map(RestMockResponse::toBuilder)
                                .map(RestMockResponse.Builder::build)
                                .collect(Collectors.toList()))
                        .orElse(null))
                .forwardedEndpoint(this.forwardedEndpoint)
                .currentResponseSequenceIndex(this.currentResponseSequenceIndex)
                .defaultMockResponseId(this.defaultMockResponseId)
                .defaultResponseName(this.defaultResponseName)
                .uri(this.uri);
    }

    @JsonPOJOBuilder(withPrefix = "")
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
        private String defaultMockResponseId;
        private List<RestMockResponse> mockResponses;
        private String uri;
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

        public Builder mockResponses(final List<RestMockResponse> mockResponses) {
            this.mockResponses = mockResponses;
            return this;
        }

        public Builder uri(final String uri) {
            this.uri = uri;
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

        public RestMethod build() {
            return new RestMethod(this);
        }
    }
}
