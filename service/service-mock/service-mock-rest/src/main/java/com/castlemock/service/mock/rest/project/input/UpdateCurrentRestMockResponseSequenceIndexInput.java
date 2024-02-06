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

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class UpdateCurrentRestMockResponseSequenceIndexInput implements Input {

    private final String projectId;
    private final String applicationId;
    private final String resourceId;
    private final String methodId;
    private final Integer currentRestMockResponseSequenceIndex;

    private UpdateCurrentRestMockResponseSequenceIndexInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.applicationId = Objects.requireNonNull(builder.applicationId, "applicationId");
        this.resourceId = Objects.requireNonNull(builder.resourceId, "resourceId");
        this.methodId = Objects.requireNonNull(builder.methodId, "methodId");
        this.currentRestMockResponseSequenceIndex = Objects.requireNonNull(builder.currentRestMockResponseSequenceIndex, "currentRestMockResponseSequenceIndex");
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

    public Integer getCurrentRestMockResponseSequenceIndex() {
        return currentRestMockResponseSequenceIndex;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UpdateCurrentRestMockResponseSequenceIndexInput that = (UpdateCurrentRestMockResponseSequenceIndexInput) o;
        return Objects.equals(projectId, that.projectId) && Objects.equals(applicationId, that.applicationId) &&
                Objects.equals(resourceId, that.resourceId) && Objects.equals(methodId, that.methodId) &&
                Objects.equals(currentRestMockResponseSequenceIndex, that.currentRestMockResponseSequenceIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, applicationId, resourceId, methodId, currentRestMockResponseSequenceIndex);
    }

    @Override
    public String toString() {
        return "UpdateCurrentRestMockResponseSequenceIndexInput{" +
                "projectId='" + projectId + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", resourceId='" + resourceId + '\'' +
                ", methodId='" + methodId + '\'' +
                ", currentRestMockResponseSequenceIndex=" + currentRestMockResponseSequenceIndex +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private String projectId;
        private String applicationId;
        private String resourceId;
        private String methodId;
        private Integer currentRestMockResponseSequenceIndex;

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

        public Builder currentRestMockResponseSequenceIndex(final Integer currentRestMockResponseSequenceIndex){
            this.currentRestMockResponseSequenceIndex = currentRestMockResponseSequenceIndex;
            return this;
        }

        public UpdateCurrentRestMockResponseSequenceIndexInput build(){
            return new UpdateCurrentRestMockResponseSequenceIndexInput(this);
        }

    }


}
