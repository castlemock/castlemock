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

package com.castlemock.model.mock.soap.domain;

import com.castlemock.model.core.model.TypeIdentifier;
import com.castlemock.model.core.model.project.domain.Project;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The DTO class for the Project class
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement(name = "soapProject")
public class SoapProject extends Project {

    private List<SoapPort> ports = new CopyOnWriteArrayList<SoapPort>();
    private List<SoapResource> resources = new CopyOnWriteArrayList<SoapResource>();

    private Map<SoapOperationStatus, Integer> statusCount = new HashMap<SoapOperationStatus, Integer>();

    /**
     * The default SOAP project constructor
     */
    public SoapProject() {
    }

    private SoapProject(final Builder builder){
        this.id = Objects.requireNonNull(builder.id);
        this.name = Objects.requireNonNull(builder.name);
        this.updated = Objects.requireNonNull(builder.updated);
        this.created = Objects.requireNonNull(builder.created);
        this.description = Objects.requireNonNull(builder.description);
        this.ports = Optional.ofNullable(builder.ports).orElseGet(CopyOnWriteArrayList::new);
        this.resources = Optional.ofNullable(builder.resources).orElseGet(CopyOnWriteArrayList::new);
        this.statusCount = Optional.ofNullable(builder.statusCount).orElseGet(HashMap::new);
    }

    /**
     * The constructor will create a new SOAP project DTO based on the provided projectDto
     * @param projectDto The new SOAP project DTO will be based on the provided project DTO and contain
     *                   the same information as the provided project DTO
     */
    public SoapProject(final Project projectDto){
        super(projectDto);
    }

    /**
     * Returns all the SOAP ports
     * @return The SOAP ports for the SOAP project
     */
    @XmlElementWrapper(name = "ports")
    @XmlElement(name = "port")
    public List<SoapPort> getPorts() {
        return ports;
    }

    /**
     * Set new value to the variable ports
     * @param ports The new value to ports
     */
    public void setPorts(List<SoapPort> ports) {
        this.ports = ports;
    }

    /**
     * Returns all the SOAP resources
     * @return The SOAP resources for the SOAP resources
     */
    @XmlElementWrapper(name = "resources")
    @XmlElement(name = "resource")
    public List<SoapResource> getResources() {
        return resources;
    }

    /**
     * Set new value to the variable resources
     * @param resources The new value to resources
     */
    public void setResources(List<SoapResource> resources) {
        this.resources = resources;
    }

    /**
     * The status count is used in the GUI to information the user on the SOAP operation status distribution.
     * @return The status counts.
     */
    @XmlTransient
    public Map<SoapOperationStatus, Integer> getStatusCount() {
        return statusCount;
    }

    /**
     * Sets a new value to the statusCount variable
     * @param statusCount The new value to statusCount
     */
    public void setStatusCount(Map<SoapOperationStatus, Integer> statusCount) {
        this.statusCount = statusCount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String name;
        private Date updated;
        private Date created;
        private String description;
        private TypeIdentifier typeIdentifier;
        private List<SoapPort> ports = new CopyOnWriteArrayList<SoapPort>();
        private List<SoapResource> resources = new CopyOnWriteArrayList<SoapResource>();
        private Map<SoapOperationStatus, Integer> statusCount = new HashMap<SoapOperationStatus, Integer>();

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder updated(Date updated) {
            this.updated = updated;
            return this;
        }

        public Builder ports(List<SoapPort> ports) {
            this.ports = ports;
            return this;
        }

        public Builder created(Date created) {
            this.created = created;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder resources(List<SoapResource> resources) {
            this.resources = resources;
            return this;
        }

        public Builder typeIdentifier(TypeIdentifier typeIdentifier) {
            this.typeIdentifier = typeIdentifier;
            return this;
        }

        public Builder statusCount(Map<SoapOperationStatus, Integer> statusCount) {
            this.statusCount = statusCount;
            return this;
        }

        public SoapProject build() {
            return new SoapProject(this);
        }
    }
}
