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

package com.castlemock.service.mock.rest.project.input;

import com.castlemock.model.core.Input;
import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.mock.rest.domain.RestMethodStatus;
import com.castlemock.model.mock.rest.domain.RestResponseStrategy;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class UpdateRestMethodInput implements Input {

    private final String projectId;
    private final String applicationId;
    private final String resourceId;
    private final String methodId;
    private final String name;
    private final HttpMethod httpMethod;
    private final String forwardedEndpoint;
    private final RestMethodStatus status;
    private final RestResponseStrategy responseStrategy;
    private final Boolean simulateNetworkDelay;
    private final Long networkDelay;
    private final String defaultMockResponseId;
    private final Boolean automaticForward;

    private UpdateRestMethodInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.applicationId = Objects.requireNonNull(builder.applicationId, "applicationId");
        this.resourceId = Objects.requireNonNull(builder.resourceId, "resourceId");
        this.methodId = Objects.requireNonNull(builder.methodId, "methodId");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.httpMethod = Objects.requireNonNull(builder.httpMethod, "httpMethod");
        this.status = Objects.requireNonNull(builder.status, "status");
        this.responseStrategy = Objects.requireNonNull(builder.responseStrategy, "responseStrategy");

        this.simulateNetworkDelay = builder.simulateNetworkDelay;
        this.forwardedEndpoint = builder.forwardedEndpoint;
        this.networkDelay = builder.networkDelay;
        this.defaultMockResponseId = builder.defaultMockResponseId;
        this.automaticForward = builder.automaticForward;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getMethodId() {
        return methodId;
    }

    public String getName() {
        return name;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
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

    public Optional<Boolean> getSimulateNetworkDelay() {
        return Optional.ofNullable(simulateNetworkDelay);
    }

    public Optional<Long> getNetworkDelay() {
        return Optional.ofNullable(networkDelay);
    }

    public Optional<String> getDefaultMockResponseId() {
        return Optional.ofNullable(defaultMockResponseId);
    }

    public Optional<Boolean> getAutomaticForward() {
        return Optional.ofNullable(automaticForward);
    }

    public static Builder builder(){
        return new Builder();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UpdateRestMethodInput that = (UpdateRestMethodInput) o;
        return Objects.equals(projectId, that.projectId) && Objects.equals(applicationId, that.applicationId) &&
                Objects.equals(resourceId, that.resourceId) && Objects.equals(methodId, that.methodId) &&
                Objects.equals(name, that.name) && httpMethod == that.httpMethod &&
                Objects.equals(forwardedEndpoint, that.forwardedEndpoint) &&
                status == that.status && responseStrategy == that.responseStrategy &&
                Objects.equals(simulateNetworkDelay, that.simulateNetworkDelay) &&
                Objects.equals(networkDelay, that.networkDelay) &&
                Objects.equals(defaultMockResponseId, that.defaultMockResponseId) &&
                Objects.equals(automaticForward, that.automaticForward);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, applicationId, resourceId, methodId, name, httpMethod,
                forwardedEndpoint, status, responseStrategy, simulateNetworkDelay, networkDelay,
                defaultMockResponseId, automaticForward);
    }

    @Override
    public String toString() {
        return "UpdateRestMethodInput{" +
                "projectId='" + projectId + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", resourceId='" + resourceId + '\'' +
                ", methodId='" + methodId + '\'' +
                ", name='" + name + '\'' +
                ", httpMethod=" + httpMethod +
                ", forwardedEndpoint='" + forwardedEndpoint + '\'' +
                ", status=" + status +
                ", responseStrategy=" + responseStrategy +
                ", simulateNetworkDelay=" + simulateNetworkDelay +
                ", networkDelay=" + networkDelay +
                ", defaultMockResponseId='" + defaultMockResponseId + '\'' +
                ", automaticForward=" + automaticForward +
                '}';
    }

    public static final class Builder {

        private String projectId;
        private String applicationId;
        private String resourceId;
        private String methodId;
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

        public Builder projectId(final String projectId){
            this.projectId = projectId;
            return this;
        }

        public Builder applicationId(final String applicationId){
            this.applicationId = applicationId;
            return this;
        }

        public Builder resourceId(final String resourceId){
            this.resourceId = resourceId;
            return this;
        }

        public Builder methodId(final String methodId){
            this.methodId = methodId;
            return this;
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


        public UpdateRestMethodInput build(){
            return new UpdateRestMethodInput(this);
        }

    }


}
