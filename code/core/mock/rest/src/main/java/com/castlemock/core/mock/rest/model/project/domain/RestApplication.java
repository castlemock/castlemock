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

package com.castlemock.core.mock.rest.model.project.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
public class RestApplication {

    private String id;
    private String name;
    private String projectId;

    private List<RestResource> resources = new CopyOnWriteArrayList<RestResource>();

    private Map<RestMethodStatus, Integer> statusCount = new HashMap<RestMethodStatus, Integer>();

    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @XmlElementWrapper(name = "resources")
    @XmlElement(name = "resource")
    public List<RestResource> getResources() {
        return resources;
    }

    public void setResources(List<RestResource> resources) {
        this.resources = resources;
    }

    @XmlTransient
    public Map<RestMethodStatus, Integer> getStatusCount() {
        return statusCount;
    }

    public void setStatusCount(Map<RestMethodStatus, Integer> statusCount) {
        this.statusCount = statusCount;
    }
}
