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
public final class UpdateRestApplicationsForwardedEndpointInput implements Input {

    private final String projectId;
    private final Set<String> applicationIds;
    private final String forwardedEndpoint;

    private UpdateRestApplicationsForwardedEndpointInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.applicationIds = Objects.requireNonNull(builder.applicationIds, "applicationIds");
        this.forwardedEndpoint = Objects.requireNonNull(builder.forwardedEndpoint, "forwardedEndpoint");
    }

    public String getProjectId() {
        return projectId;
    }

    public Set<String> getApplicationIds() {
        return applicationIds;
    }

    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UpdateRestApplicationsForwardedEndpointInput that = (UpdateRestApplicationsForwardedEndpointInput) o;
        return Objects.equals(projectId, that.projectId) && Objects.equals(applicationIds, that.applicationIds) &&
                Objects.equals(forwardedEndpoint, that.forwardedEndpoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, applicationIds, forwardedEndpoint);
    }

    @Override
    public String toString() {
        return "UpdateRestApplicationsForwardedEndpointInput{" +
                "projectId='" + projectId + '\'' +
                ", applicationIds=" + applicationIds +
                ", forwardedEndpoint='" + forwardedEndpoint + '\'' +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private String projectId;
        private String forwardedEndpoint;
        private Set<String> applicationIds;

        private Builder() {
        }

        public Builder projectId(final String projectId){
            this.projectId = projectId;
            return this;
        }

        public Builder forwardedEndpoint(final String forwardedEndpoint){
            this.forwardedEndpoint = forwardedEndpoint;
            return this;
        }

        public Builder applicationIds(final Set<String> applicationIds){
            this.applicationIds = applicationIds;
            return this;
        }

        public UpdateRestApplicationsForwardedEndpointInput build(){
            return new UpdateRestApplicationsForwardedEndpointInput(this);
        }

    }


}
