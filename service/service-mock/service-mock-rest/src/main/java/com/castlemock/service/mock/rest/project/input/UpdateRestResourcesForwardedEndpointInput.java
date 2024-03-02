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

import java.util.Objects;
import java.util.Set;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class UpdateRestResourcesForwardedEndpointInput implements Input {

    private final String projectId;
    private final String applicationId;
    private final Set<String> resourceIds;
    private final String forwardedEndpoint;

    private UpdateRestResourcesForwardedEndpointInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId);
        this.applicationId = Objects.requireNonNull(builder.applicationId);
        this.resourceIds = Objects.requireNonNull(builder.resourceIds);
        this.forwardedEndpoint = Objects.requireNonNull(builder.forwardedEndpoint);
    }

    public String getProjectId() {
        return projectId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public Set<String> getResourceIds() {
        return resourceIds;
    }

    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    public static Builder builder(){
        return new Builder();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UpdateRestResourcesForwardedEndpointInput that = (UpdateRestResourcesForwardedEndpointInput) o;
        return Objects.equals(projectId, that.projectId) && Objects.equals(applicationId, that.applicationId) &&
                Objects.equals(resourceIds, that.resourceIds) && Objects.equals(forwardedEndpoint, that.forwardedEndpoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, applicationId, resourceIds, forwardedEndpoint);
    }

    @Override
    public String toString() {
        return "UpdateRestResourcesForwardedEndpointInput{" +
                "projectId='" + projectId + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", resourceIds=" + resourceIds +
                ", forwardedEndpoint='" + forwardedEndpoint + '\'' +
                '}';
    }

    public static final class Builder {

        private String projectId;
        private String applicationId;
        private Set<String> resourceIds;
        private String forwardedEndpoint;

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

        public Builder resourceIds(final Set<String> resourceIds){
            this.resourceIds = resourceIds;
            return this;
        }

        public Builder forwardedEndpoint(final String forwardedEndpoint){
            this.forwardedEndpoint = forwardedEndpoint;
            return this;
        }

        public UpdateRestResourcesForwardedEndpointInput build(){
            return new UpdateRestResourcesForwardedEndpointInput(this);
        }

    }


}
