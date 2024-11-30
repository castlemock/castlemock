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

package com.castlemock.repository.rest.file.event.model;

import com.castlemock.repository.core.file.event.model.EventFile;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "restEvent")
@XmlAccessorType(XmlAccessType.NONE)
public class RestEventFile extends EventFile {

    @XmlElement
    private RestRequestFile request;
    @XmlElement
    private RestResponseFile response;
    @XmlElement
    private String projectId;
    @XmlElement
    private String applicationId;
    @XmlElement
    private String resourceId;
    @XmlElement
    private String methodId;

    private RestEventFile() {

    }

    private RestEventFile(final Builder builder) {
        super(builder);
        this.request = Objects.requireNonNull(builder.request, "request");
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.applicationId = Objects.requireNonNull(builder.applicationId, "applicationId");
        this.resourceId = Objects.requireNonNull(builder.resourceId, "resourceId");
        this.methodId = Objects.requireNonNull(builder.methodId, "methodId");
        this.response = builder.response;
    }

    public RestRequestFile getRequest() {
        return request;
    }

    public RestResponseFile getResponse() {
        return response;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EventFile.Builder<Builder> {

        private RestRequestFile request;
        private RestResponseFile response;
        private String projectId;
        private String applicationId;
        private String resourceId;
        private String methodId;

        private Builder() {
        }

        public Builder request(final RestRequestFile request) {
            this.request = request;
            return this;
        }

        public Builder response(final RestResponseFile response) {
            this.response = response;
            return this;
        }

        public Builder projectId(final String projectId) {
            this.projectId = projectId;
            return this;
        }

        public Builder applicationId(final String applicationId) {
            this.applicationId = applicationId;
            return this;
        }

        public Builder resourceId(final String resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        public Builder methodId(final String methodId) {
            this.methodId = methodId;
            return this;
        }

        public RestEventFile build() {
            return new RestEventFile(this);
        }

    }

}