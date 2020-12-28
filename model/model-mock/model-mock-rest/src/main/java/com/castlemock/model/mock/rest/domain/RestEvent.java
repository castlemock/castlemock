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

import com.castlemock.model.core.model.TypeIdentifier;
import com.castlemock.model.core.model.event.domain.Event;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
public class RestEvent extends Event {

    private RestRequest request;
    private RestResponse response;
    private String projectId;
    private String applicationId;
    private String resourceId;
    private String methodId;

    /**
     * Default constructor for the REST event DTO
     */
    public RestEvent() {
    }

    /**
     * Default constructor for the REST event DTO
     */
    public RestEvent(final Event eventDto) {
        super(eventDto);
    }

    public RestEvent(final Builder builder){
        this.id = Objects.requireNonNull(builder.id);
        this.resourceName = Objects.requireNonNull(builder.resourceName);
        this.startDate = Objects.requireNonNull(builder.startDate);
        this.endDate = Objects.requireNonNull(builder.endDate);
        this.typeIdentifier = Objects.requireNonNull(builder.typeIdentifier);
        this.resourceLink = Objects.requireNonNull(builder.resourceLink);
        this.request = Objects.requireNonNull(builder.request);
        this.response = Objects.requireNonNull(builder.response);
        this.projectId = Objects.requireNonNull(builder.projectId);
        this.applicationId = Objects.requireNonNull(builder.applicationId);
        this.resourceId = Objects.requireNonNull(builder.resourceId);
        this.methodId = Objects.requireNonNull(builder.methodId);
    }

    /**
     * Constructor for the REST event DTO
     * @param request The REST request that the event is representing
     * @param projectId The project id that the event belongs to
     * @param applicationId The application id that the event belongs to
     * @param resourceId The resource id that the event belongs to
     * @param methodId The id of the REST operation that is affected by the provided REST request
     */
    public RestEvent(final String resourceName, final RestRequest request, final String projectId, final String applicationId, final String resourceId, final String methodId) {
        super(resourceName);
        this.request = request;
        this.projectId = projectId;
        this.applicationId = applicationId;
        this.resourceId = resourceId;
        this.methodId = methodId;
    }

    /**
     * The finish method is used to sent the response that was sent back, but was also
     * to set the date/time for when the event ended.
     * @param restResponse
     */
    public void finish(final RestResponse restResponse) {
        this.response = restResponse;
        setEndDate(new Date());
    }

    @XmlElement
    public RestRequest getRequest() {
        return request;
    }

    public void setRequest(RestRequest request) {
        this.request = request;
    }

    @XmlElement
    public RestResponse getResponse() {
        return response;
    }

    public void setResponse(RestResponse response) {
        this.response = response;
    }

    @XmlElement
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @XmlElement
    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    @XmlElement
    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    @XmlElement
    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RestEvent)) return false;
        RestEvent restEvent = (RestEvent) o;
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

    public static final class Builder {
        private RestRequest request;
        private RestResponse response;
        private String projectId;
        private String applicationId;
        private String id;
        private String resourceId;
        private String resourceName;
        private String methodId;
        private Date startDate;
        private Date endDate;
        private TypeIdentifier typeIdentifier;
        private String resourceLink;

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

        public Builder id(final String id) {
            this.id = id;
            return this;
        }

        public Builder resourceId(final String resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        public Builder resourceName(final String resourceName) {
            this.resourceName = resourceName;
            return this;
        }

        public Builder methodId(final String methodId) {
            this.methodId = methodId;
            return this;
        }

        public Builder startDate(final Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(final Date endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder typeIdentifier(final TypeIdentifier typeIdentifier) {
            this.typeIdentifier = typeIdentifier;
            return this;
        }

        public Builder resourceLink(final String resourceLink) {
            this.resourceLink = resourceLink;
            return this;
        }

        public RestEvent build() {
            return new RestEvent(this);
        }
    }
}
