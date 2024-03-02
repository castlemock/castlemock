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

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class CreateRestMethodInput implements Input {

    private final String projectId;
    private final String applicationId;
    private final String resourceId;
    private final String name;
    private final HttpMethod httpMethod;

    private CreateRestMethodInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.applicationId = Objects.requireNonNull(builder.applicationId, "applicationId");
        this.resourceId = Objects.requireNonNull(builder.resourceId, "resourceId");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.httpMethod = Objects.requireNonNull(builder.httpMethod, "httpMethod");
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

    public String getName() {
        return name;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public static Builder builder(){
        return new Builder();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CreateRestMethodInput that = (CreateRestMethodInput) o;
        return Objects.equals(projectId, that.projectId) && Objects.equals(applicationId, that.applicationId) && Objects.equals(resourceId, that.resourceId) && Objects.equals(name, that.name) && httpMethod == that.httpMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, applicationId, resourceId, name, httpMethod);
    }

    @Override
    public String toString() {
        return "CreateRestMethodInput{" +
                "projectId='" + projectId + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", resourceId='" + resourceId + '\'' +
                ", name='" + name + '\'' +
                ", httpMethod=" + httpMethod +
                '}';
    }

    public static class Builder {
        private String projectId;
        private String applicationId;
        private String resourceId;
        private String name;
        private HttpMethod httpMethod;

        private Builder() {
        }

        public Builder projectId(final String restProjectId){
            this.projectId = restProjectId;
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

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder httpMethod(final HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public CreateRestMethodInput build(){
            return new CreateRestMethodInput(this);
        }
    }

}
