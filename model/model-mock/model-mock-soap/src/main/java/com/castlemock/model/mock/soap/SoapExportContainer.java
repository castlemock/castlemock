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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
@XmlAccessorType(XmlAccessType.NONE)
public class SoapExportContainer extends ExportContainer {

    @XmlElement
    private SoapProject project;

    @XmlElementWrapper(name = "ports")
    @XmlElement(name = "port")
    private List<SoapPort> ports;

    @XmlElementWrapper(name = "resources")
    @XmlElement(name = "resource")
    private List<SoapResource> resources;

    @XmlElementWrapper(name = "operations")
    @XmlElement(name = "operation")
    private List<SoapOperation> operations;

    @XmlElementWrapper(name = "mockResponses")
    @XmlElement(name = "mockResponse")
    private List<SoapMockResponse> mockResponses;

    private SoapExportContainer() {

    }

    private SoapExportContainer(final Builder builder) {
        this.project = Objects.requireNonNull(builder.project, "project");
        this.ports = Objects.requireNonNull(builder.ports, "ports");
        this.resources = Objects.requireNonNull(builder.resources, "resources");
        this.operations = Objects.requireNonNull(builder.operations, "operations");
        this.mockResponses = Objects.requireNonNull(builder.mockResponses, "mockResponses");
    }

    public SoapProject getProject() {
        return project;
    }


    public List<SoapPort> getPorts() {
        return ports;
    }


    public List<SoapResource> getResources() {
        return resources;
    }


    public List<SoapOperation> getOperations() {
        return operations;
    }


    public List<SoapMockResponse> getMockResponses() {
        return mockResponses;
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final SoapExportContainer that = (SoapExportContainer) o;
        return Objects.equals(project, that.project) && Objects.equals(ports, that.ports)
                && Objects.equals(resources, that.resources) && Objects.equals(operations, that.operations)
                && Objects.equals(mockResponses, that.mockResponses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(project, ports, resources, operations, mockResponses);
    }

    @Override
    public String toString() {
        return "SoapExportContainer{" +
                "project=" + project +
                ", ports=" + ports +
                ", resources=" + resources +
                ", operations=" + operations +
                ", mockResponses=" + mockResponses +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private SoapProject project;
        private List<SoapPort> ports;
        private List<SoapResource> resources;
        private List<SoapOperation> operations;
        private List<SoapMockResponse> mockResponses;

        private Builder() {
        }

        public Builder project(final SoapProject project) {
            this.project = project;
            return this;
        }

        public Builder ports(final List<SoapPort> ports) {
            this.ports = ports;
            return this;
        }

        public Builder resources(final List<SoapResource> resources) {
            this.resources = resources;
            return this;
        }

        public Builder operations(final List<SoapOperation> operations) {
            this.operations = operations;
            return this;
        }

        public Builder mockResponses(final List<SoapMockResponse> mockResponses) {
            this.mockResponses = mockResponses;
            return this;
        }

        public SoapExportContainer build() {
            return new SoapExportContainer(this);
        }
    }
}
