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

package com.castlemock.core.mock.graphql.model.project.domain;

import com.castlemock.core.basis.model.project.domain.Project;

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
 * @since 1.19
 */
@XmlRootElement
public class GraphQLProject extends Project {

    private List<GraphQLApplication> applications = new CopyOnWriteArrayList<GraphQLApplication>();
    private Map<GraphQLOperationStatus, Integer> statusCount = new HashMap<GraphQLOperationStatus, Integer>();

    public GraphQLProject(){

    }

    /**
     * The constructor will create a new REST project DTO based on the provided projectDto
     * @param projectDto The new REST project DTO will be based on the provided project DTO and contain
     *                   the same information as the provided project DTO
     */
    public GraphQLProject(final Project projectDto){
        super(projectDto);
    }

    @XmlElementWrapper(name = "applications")
    @XmlElement(name = "application")
    public List<GraphQLApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<GraphQLApplication> applications) {
        this.applications = applications;
    }

    @XmlTransient
    public Map<GraphQLOperationStatus, Integer> getStatusCount() {
        return statusCount;
    }

    public void setStatusCount(Map<GraphQLOperationStatus, Integer> statusCount) {
        this.statusCount = statusCount;
    }
}
