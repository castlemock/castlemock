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

package com.castlemock.model.mock.soap;

import com.castlemock.model.core.ExportContainer;
import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapPort;
import com.castlemock.model.mock.soap.domain.SoapProject;
import com.castlemock.model.mock.soap.domain.SoapResource;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.20
 */
@XmlRootElement
public class SoapExportContainer extends ExportContainer {

    private SoapProject project;
    private List<SoapPort> ports;
    private List<SoapResource> resources;
    private List<SoapOperation> operations;
    private List<SoapMockResponse> mockResponses;

    @XmlElement
    public SoapProject getProject() {
        return project;
    }

    public void setProject(SoapProject project) {
        this.project = project;
    }

    @XmlElementWrapper(name = "ports")
    @XmlElement(name = "port")
    public List<SoapPort> getPorts() {
        return ports;
    }

    public void setPorts(List<SoapPort> ports) {
        this.ports = ports;
    }

    @XmlElementWrapper(name = "resources")
    @XmlElement(name = "resource")
    public List<SoapResource> getResources() {
        return resources;
    }

    public void setResources(List<SoapResource> resources) {
        this.resources = resources;
    }

    @XmlElementWrapper(name = "operations")
    @XmlElement(name = "operation")
    public List<SoapOperation> getOperations() {
        return operations;
    }

    public void setOperations(List<SoapOperation> operations) {
        this.operations = operations;
    }

    @XmlElementWrapper(name = "mockResponses")
    @XmlElement(name = "mockResponse")
    public List<SoapMockResponse> getMockResponses() {
        return mockResponses;
    }

    public void setMockResponses(List<SoapMockResponse> mockResponses) {
        this.mockResponses = mockResponses;
    }
}
