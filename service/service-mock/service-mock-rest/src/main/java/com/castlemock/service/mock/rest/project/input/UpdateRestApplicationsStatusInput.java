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
public final class UpdateRestApplicationsStatusInput implements Input {

    private final String projectId;
    private final String applicationId;
    private final RestMethodStatus methodStatus;

    private UpdateRestApplicationsStatusInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.applicationId = Objects.requireNonNull(builder.applicationId, "applicationId");
        this.methodStatus = Objects.requireNonNull(builder.methodStatus, "methodStatus");
    }

    public String getProjectId() {
        return projectId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public RestMethodStatus getMethodStatus() {
        return methodStatus;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UpdateRestApplicationsStatusInput that = (UpdateRestApplicationsStatusInput) o;
        return Objects.equals(projectId, that.projectId) && Objects.equals(applicationId, that.applicationId) &&
                methodStatus == that.methodStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, applicationId, methodStatus);
    }

    @Override
    public String toString() {
        return "UpdateRestApplicationsStatusInput{" +
                "projectId='" + projectId + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", methodStatus=" + methodStatus +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private String projectId;
        private String applicationId;
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

        public Builder methodStatus(final RestMethodStatus methodStatus){
            this.methodStatus = methodStatus;
            return this;
        }

        public UpdateRestApplicationsStatusInput build(){
            return new UpdateRestApplicationsStatusInput(this);
        }

    }


}
