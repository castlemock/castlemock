/*
 * Copyright 2018 Karl Dahlgren
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

package com.castlemock.model.mock.rest;

import com.castlemock.model.core.ExportContainer;
import com.castlemock.model.mock.rest.domain.RestApplication;
import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.model.mock.rest.domain.RestProject;
import com.castlemock.model.mock.rest.domain.RestResource;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.20
 */
@XmlRootElement
public class RestExportContainer extends ExportContainer {

    private RestProject project;
    private List<RestApplication> applications;
    private List<RestResource> resources;
    private List<RestMethod> methods;
    private List<RestMockResponse> mockResponses;

    public RestExportContainer(){

    }

    private RestExportContainer(final Builder builder){
        this.project = Objects.requireNonNull(builder.project);
        this.applications = Objects.requireNonNull(builder.applications);
        this.resources = Objects.requireNonNull(builder.resources);
        this.methods = Objects.requireNonNull(builder.methods);
        this.mockResponses = Objects.requireNonNull(builder.mockResponses);
    }

    @XmlElement
    public RestProject getProject() {
        return project;
    }

    public void setProject(RestProject project) {
        this.project = project;
    }

    @XmlElementWrapper(name = "applications")
    @XmlElement(name = "application")
    public List<RestApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<RestApplication> applications) {
        this.applications = applications;
    }

    @XmlElementWrapper(name = "resources")
    @XmlElement(name = "resource")
    public List<RestResource> getResources() {
        return resources;
    }

    public void setResources(List<RestResource> resources) {
        this.resources = resources;
    }

    @XmlElementWrapper(name = "methods")
    @XmlElement(name = "method")
    public List<RestMethod> getMethods() {
        return methods;
    }

    public void setMethods(List<RestMethod> methods) {
        this.methods = methods;
    }

    @XmlElementWrapper(name = "mockResponses")
    @XmlElement(name = "mockResponse")
    public List<RestMockResponse> getMockResponses() {
        return mockResponses;
    }

    public void setMockResponses(List<RestMockResponse> mockResponses) {
        this.mockResponses = mockResponses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RestExportContainer)) return false;
        RestExportContainer that = (RestExportContainer) o;
        return Objects.equals(project, that.project) &&
                Objects.equals(applications, that.applications) &&
                Objects.equals(resources, that.resources) &&
                Objects.equals(methods, that.methods) &&
                Objects.equals(mockResponses, that.mockResponses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(project, applications, resources, methods, mockResponses);
    }

    @Override
    public String toString() {
        return "RestExportContainer{" +
                "project=" + project +
                ", applications=" + applications +
                ", resources=" + resources +
                ", methods=" + methods +
                ", mockResponses=" + mockResponses +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private RestProject project;
        private List<RestApplication> applications;
        private List<RestResource> resources;
        private List<RestMethod> methods;
        private List<RestMockResponse> mockResponses;

        private Builder() {
        }

        public Builder project(final RestProject project) {
            this.project = project;
            return this;
        }

        public Builder applications(final List<RestApplication> applications) {
            this.applications = applications;
            return this;
        }

        public Builder resources(final List<RestResource> resources) {
            this.resources = resources;
            return this;
        }

        public Builder methods(final List<RestMethod> methods) {
            this.methods = methods;
            return this;
        }

        public Builder mockResponses(final List<RestMockResponse> mockResponses) {
            this.mockResponses = mockResponses;
            return this;
        }

        public RestExportContainer build() {
            return new RestExportContainer(this);
        }
    }
}
