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

import com.castlemock.model.core.event.Event;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = RestEvent.Builder.class)
public class RestEvent extends Event {

    @XmlElement
    private final RestRequest request;

    @XmlElement
    private final RestResponse response;

    @XmlElement
    private final String projectId;

    @XmlElement
    private final String applicationId;

    @XmlElement
    private final String resourceId;

    @XmlElement
    private final String methodId;

    private RestEvent(final Builder builder){
        super(builder);
        this.request = Objects.requireNonNull(builder.request, "request");
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.applicationId = Objects.requireNonNull(builder.applicationId, "applicationId");
        this.resourceId = Objects.requireNonNull(builder.resourceId, "resourceId");
        this.methodId = Objects.requireNonNull(builder.methodId, "methodId");
        this.response = builder.response;
    }

    public RestRequest getRequest() {
        return request;
    }

    public Optional<RestResponse> getResponse() {
        return Optional.ofNullable(response);
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof RestEvent restEvent)) return false;
        return Objects.equals(request, restEvent.request) &&
                Objects.equals(response, restEvent.response) &&
                Objects.equals(projectId, restEvent.projectId) &&
                Objects.equals(applicationId, restEvent.applicationId) &&
                Objects.equals(resourceId, restEvent.resourceId) &&
                Objects.equals(methodId, restEvent.methodId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(request, response, projectId, applicationId, resourceId, methodId);
    }

    @Override
    public String toString() {
        return "RestEvent{" +
                "request=" + request +
                ", response=" + response +
                ", projectId='" + projectId + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", resourceId='" + resourceId + '\'' +
                ", methodId='" + methodId + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder()
                .id(this.id)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .resourceName(this.resourceName)
                .request(this.request)
                .response(this.response)
                .methodId(this.methodId)
                .resourceId(this.resourceId)
                .applicationId(this.applicationId)
                .projectId(this.projectId);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder extends Event.Builder<Builder> {
        private RestRequest request;
        private RestResponse response;
        private String projectId;
        private String applicationId;
        private String resourceId;
        private String methodId;


        private Builder() {
        }

        public Builder request(final RestRequest request) {
            this.request = request;
            return this;
        }

        public Builder response(final RestResponse response) {
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

        public RestEvent build() {
            return new RestEvent(this);
        }
    }
}
