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

package com.castlemock.core.mock.rest.model;

import com.castlemock.core.basis.model.ExportContainer;
import com.castlemock.core.mock.rest.model.project.domain.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

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
}
