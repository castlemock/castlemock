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
import com.castlemock.model.mock.rest.domain.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import jakarta.xml.bind.annotation.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.20
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = RestExportContainer.Builder.class)
public class RestExportContainer extends ExportContainer {

    @XmlElement
    private final RestProject project;

    @XmlElementWrapper(name = "applications")
    @XmlElement(name = "application")
    private final List<RestApplication> applications;

    @XmlElementWrapper(name = "resources")
    @XmlElement(name = "resource")
    private final List<RestResource> resources;

    @XmlElementWrapper(name = "methods")
    @XmlElement(name = "method")
    private final List<RestMethod> methods;

    @XmlElementWrapper(name = "mockResponses")
    @XmlElement(name = "mockResponse")
    private final List<RestMockResponse> mockResponses;

    private RestExportContainer(final Builder builder){
        this.project = Objects.requireNonNull(builder.project, "project");
        this.applications = Optional.ofNullable(builder.applications).orElseGet(List::of);
        this.resources = Optional.ofNullable(builder.resources).orElseGet(List::of);
        this.methods = Optional.ofNullable(builder.methods).orElseGet(List::of);
        this.mockResponses = Optional.ofNullable(builder.mockResponses).orElseGet(List::of);
    }


    public RestProject getProject() {
        return project;
    }

    public List<RestApplication> getApplications() {
        return Optional.ofNullable(applications)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    public List<RestResource> getResources() {
        return Optional.ofNullable(resources)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    public List<RestMethod> getMethods() {
        return Optional.ofNullable(methods)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    public List<RestMockResponse> getMockResponses() {
        return Optional.ofNullable(mockResponses)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RestExportContainer that = (RestExportContainer) o;
        return Objects.equals(project, that.project) && Objects.equals(applications, that.applications)
                && Objects.equals(resources, that.resources) && Objects.equals(methods, that.methods)
                && Objects.equals(mockResponses, that.mockResponses);
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

    @JsonPOJOBuilder(withPrefix = "")
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
