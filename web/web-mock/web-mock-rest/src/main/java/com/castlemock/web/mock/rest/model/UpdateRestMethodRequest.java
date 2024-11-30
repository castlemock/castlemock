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

package com.castlemock.web.mock.rest.model;

import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.mock.rest.domain.RestMethodStatus;
import com.castlemock.model.mock.rest.domain.RestResponseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.52
 */
@XmlRootElement
@JsonDeserialize(builder = UpdateRestMethodRequest.Builder.class)
public class UpdateRestMethodRequest {

    private final String name;
    private final HttpMethod httpMethod;
    private final String forwardedEndpoint;
    private final RestMethodStatus status;
    private final RestResponseStrategy responseStrategy;
    private final Boolean simulateNetworkDelay;
    private final Long networkDelay;
    private final String defaultMockResponseId;
    private final Boolean automaticForward;

    private UpdateRestMethodRequest(final Builder builder){
        this.name = Objects.requireNonNull(builder.name, "name");
        this.httpMethod = Objects.requireNonNull(builder.httpMethod, "httpMethod");
        this.status = Objects.requireNonNull(builder.status, "status");
        this.responseStrategy = Objects.requireNonNull(builder.responseStrategy, "responseStrategy");

        this.forwardedEndpoint = builder.forwardedEndpoint;
        this.simulateNetworkDelay = builder.simulateNetworkDelay;
        this.networkDelay = builder.networkDelay;
        this.defaultMockResponseId = builder.defaultMockResponseId;
        this.automaticForward = builder.automaticForward;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    @XmlElement
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    @XmlElement
    public Optional<String> getForwardedEndpoint() {
        return Optional.ofNullable(forwardedEndpoint);
    }

    @XmlElement
    public RestMethodStatus getStatus() {
        return status;
    }

    @XmlElement
    public RestResponseStrategy getResponseStrategy() {
        return responseStrategy;
    }

    @XmlElement
    public Optional<Boolean> getSimulateNetworkDelay() {
        return Optional.ofNullable(simulateNetworkDelay);
    }

    @XmlElement
    public Optional<Long> getNetworkDelay() {
        return Optional.ofNullable(networkDelay);
    }

    @XmlElement
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
        UpdateRestMethodRequest that = (UpdateRestMethodRequest) o;
        return automaticForward == that.automaticForward && simulateNetworkDelay == that.simulateNetworkDelay && Objects.equals(networkDelay, that.networkDelay) && Objects.equals(name, that.name) && httpMethod == that.httpMethod && Objects.equals(forwardedEndpoint, that.forwardedEndpoint) && status == that.status && responseStrategy == that.responseStrategy && Objects.equals(defaultMockResponseId, that.defaultMockResponseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, httpMethod, forwardedEndpoint, status, responseStrategy, simulateNetworkDelay, networkDelay, defaultMockResponseId, automaticForward);
    }

    @Override
    public String toString() {
        return "UpdateRestMethodRequest{" +
                "name='" + name + '\'' +
                ", httpMethod=" + httpMethod +
                ", forwardedEndpoint='" + forwardedEndpoint + '\'' +
                ", status=" + status +
                ", responseStrategy=" + responseStrategy +
                ", simulateNetworkDelay=" + simulateNetworkDelay +
                ", networkDelay=" + networkDelay +
                ", defaultMockResponseId='" + defaultMockResponseId + '\'' +
                ", automaticForward='" + automaticForward + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private String name;
        private HttpMethod httpMethod;
        private String forwardedEndpoint;
        private RestMethodStatus status;
        private RestResponseStrategy responseStrategy;
        private Boolean simulateNetworkDelay;
        private Long networkDelay;
        private String defaultMockResponseId;
        private Boolean automaticForward;

        private Builder() {
        }

        public Builder name(final String name) {
            this.name = name;
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

        public UpdateRestMethodRequest build() {
            return new UpdateRestMethodRequest(this);
        }
    }
}
