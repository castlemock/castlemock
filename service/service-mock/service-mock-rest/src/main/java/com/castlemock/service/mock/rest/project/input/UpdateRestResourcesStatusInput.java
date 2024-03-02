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
import com.castlemock.model.mock.rest.domain.RestMethodStatus;

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class UpdateRestResourcesStatusInput implements Input {

    private final String projectId;
    private final String applicationId;
    private final String resourceId;
    private final RestMethodStatus methodStatus;

    private UpdateRestResourcesStatusInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.applicationId = Objects.requireNonNull(builder.applicationId, "applicationId");
        this.resourceId = Objects.requireNonNull(builder.resourceId, "resourceId");
        this.methodStatus = Objects.requireNonNull(builder.methodStatus, "methodStatus");
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

    public RestMethodStatus getMethodStatus() {
        return methodStatus;
    }

    public static Builder builder(){
        return new Builder();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UpdateRestResourcesStatusInput that = (UpdateRestResourcesStatusInput) o;
        return Objects.equals(projectId, that.projectId) && Objects.equals(applicationId, that.applicationId) &&
                Objects.equals(resourceId, that.resourceId) && methodStatus == that.methodStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, applicationId, resourceId, methodStatus);
    }

    @Override
    public String toString() {
        return "UpdateRestResourcesStatusInput{" +
                "projectId='" + projectId + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", resourceId='" + resourceId + '\'' +
                ", methodStatus=" + methodStatus +
                '}';
    }

    public static final class Builder {

        private String projectId;
        private String applicationId;
        private String resourceId;
        private RestMethodStatus methodStatus;

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

        public Builder methodStatus(final RestMethodStatus methodStatus){
            this.methodStatus = methodStatus;
            return this;
        }

        public UpdateRestResourcesStatusInput build(){
            return new UpdateRestResourcesStatusInput(this);
        }

    }


}
