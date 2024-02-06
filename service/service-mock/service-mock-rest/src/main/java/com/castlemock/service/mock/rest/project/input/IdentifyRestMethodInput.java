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
import com.castlemock.model.core.http.HttpParameter;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class IdentifyRestMethodInput implements Input {

    private final String projectId;
    private final String applicationId;
    private final String resourceUri;
    private final HttpMethod httpMethod;
    private final Set<HttpParameter> httpParameters;

    private IdentifyRestMethodInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.applicationId = Objects.requireNonNull(builder.applicationId, "applicationId");
        this.resourceUri = Objects.requireNonNull(builder.resourceUri, "resourceUri");
        this.httpMethod = Objects.requireNonNull(builder.httpMethod, "httpMethod");
        this.httpParameters = Optional.ofNullable(builder.httpParameters).orElseGet(Set::of);
    }

    public String getProjectId() {
        return projectId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getResourceUri() {
        return resourceUri;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Set<HttpParameter> getHttpParameters(){ return Optional.ofNullable(httpParameters)
            .map(Set::copyOf)
            .orElseGet(Set::of); }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final IdentifyRestMethodInput that = (IdentifyRestMethodInput) o;
        return Objects.equals(projectId, that.projectId) && Objects.equals(applicationId, that.applicationId) &&
                Objects.equals(resourceUri, that.resourceUri) && httpMethod == that.httpMethod &&
                Objects.equals(httpParameters, that.httpParameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, applicationId, resourceUri, httpMethod, httpParameters);
    }

    @Override
    public String toString() {
        return "IdentifyRestMethodInput{" +
                "projectId='" + projectId + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", resourceUri='" + resourceUri + '\'' +
                ", httpMethod=" + httpMethod +
                ", httpParameters=" + httpParameters +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private String projectId;
        private String applicationId;
        private String resourceUri;
        private HttpMethod httpMethod;
        private Set<HttpParameter> httpParameters;

        public Builder projectId(final String projectId){
            this.projectId = projectId;
            return this;
        }

        public Builder applicationId(final String applicationId){
            this.applicationId = applicationId;
            return this;
        }

        public Builder resourceUri(final String resourceUri){
            this.resourceUri = resourceUri;
            return this;
        }

        public Builder httpMethod(final HttpMethod httpMethod){
            this.httpMethod = httpMethod;
            return this;
        }

        public Builder httpParameters(final Set<HttpParameter> httpParameters){
            this.httpParameters = httpParameters;
            return this;
        }

        public IdentifyRestMethodInput build(){
            return new IdentifyRestMethodInput(this);
        }

    }
}
