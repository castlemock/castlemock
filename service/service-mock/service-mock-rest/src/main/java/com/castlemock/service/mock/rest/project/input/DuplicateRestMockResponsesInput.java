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
public final class DuplicateRestMockResponsesInput implements Input {

    private final String projectId;
    private final String applicationId;
    private final String resourceId;
    private final String methodId;
    private final Set<String> mockResponseIds;

    public DuplicateRestMockResponsesInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId);
        this.applicationId = Objects.requireNonNull(builder.applicationId);
        this.resourceId = Objects.requireNonNull(builder.resourceId);
        this.methodId = Objects.requireNonNull(builder.methodId);
        this.mockResponseIds = Objects.requireNonNull(builder.mockResponseIds);
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

    public Set<String> getMockResponseIds() {
        return mockResponseIds;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DuplicateRestMockResponsesInput that = (DuplicateRestMockResponsesInput) o;
        return Objects.equals(projectId, that.projectId) && Objects.equals(applicationId, that.applicationId) && Objects.equals(resourceId, that.resourceId) && Objects.equals(methodId, that.methodId) && Objects.equals(mockResponseIds, that.mockResponseIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, applicationId, resourceId, methodId, mockResponseIds);
    }

    @Override
    public String toString() {
        return "DuplicateRestMockResponsesInput{" +
                "projectId='" + projectId + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", resourceId='" + resourceId + '\'' +
                ", methodId='" + methodId + '\'' +
                ", mockResponseIds=" + mockResponseIds +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {

        private String projectId;
        private String applicationId;
        private String resourceId;
        private String methodId;
        private Set<String> mockResponseIds;

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

        public Builder mockResponseIds(final Set<String> mockResponseIds){
            this.mockResponseIds = mockResponseIds;
            return this;
        }

        public DuplicateRestMockResponsesInput build(){
            return new DuplicateRestMockResponsesInput(this);
        }
    }
}
