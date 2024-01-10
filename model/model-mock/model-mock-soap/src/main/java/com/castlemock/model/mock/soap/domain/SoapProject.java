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

import com.castlemock.model.core.project.Project;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The DTO class for the Project class
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement(name = "soapProject")
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = SoapProject.Builder.class)
public class SoapProject extends Project {

    @XmlElementWrapper(name = "ports")
    @XmlElement(name = "port")
    private final List<SoapPort> ports;

    @XmlElementWrapper(name = "resources")
    @XmlElement(name = "resource")
    private final List<SoapResource> resources;

    @XmlTransient
    private final Map<SoapOperationStatus, Integer> statusCount;

    private SoapProject(final Builder builder){
        super(builder);
        this.ports = Optional.ofNullable(builder.ports).orElseGet(CopyOnWriteArrayList::new);
        this.resources = Optional.ofNullable(builder.resources).orElseGet(CopyOnWriteArrayList::new);
        this.statusCount = Optional.ofNullable(builder.statusCount).orElseGet(HashMap::new);
    }

    /**
     * Returns all the SOAP ports
     * @return The SOAP ports for the SOAP project
     */
    public List<SoapPort> getPorts() {
        return ports;
    }

    /**
     * Returns all the SOAP resources
     * @return The SOAP resources for the SOAP resources
     */
    public List<SoapResource> getResources() {
        return resources;
    }

    /**
     * The status count is used in the GUI to information the user on the SOAP operation status distribution.
     * @return The status counts.
     */

    public Map<SoapOperationStatus, Integer> getStatusCount() {
        return statusCount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder()
                .id(id)
                .name(name)
                .created(created)
                .updated(updated)
                .description(description)
                .ports(ports)
                .resources(resources)
                .statusCount(statusCount);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder extends Project.Builder<Builder> {

        private List<SoapPort> ports = new CopyOnWriteArrayList<>();
        private List<SoapResource> resources = new CopyOnWriteArrayList<>();
        private Map<SoapOperationStatus, Integer> statusCount = new HashMap<>();

        private Builder() {
        }


        public Builder ports(final List<SoapPort> ports) {
            this.ports = ports;
            return this;
        }

        public Builder resources(final List<SoapResource> resources) {
            this.resources = resources;
            return this;
        }

        public Builder statusCount(final Map<SoapOperationStatus, Integer> statusCount) {
            this.statusCount = statusCount;
            return this;
        }

        public SoapProject build() {
            return new SoapProject(this);
        }
    }
}
