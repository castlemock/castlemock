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

package com.castlemock.core.mock.graphql.model.project.dto;

import com.castlemock.core.basis.model.project.dto.ProjectDto;
import org.dozer.Mapping;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
public class GraphQLProjectDto extends ProjectDto {

    @Mapping("applications")
    private List<GraphQLApplicationDto> applications = new CopyOnWriteArrayList<GraphQLApplicationDto>();

    public GraphQLProjectDto(){

    }

    public List<GraphQLApplicationDto> getApplications() {
        return applications;
    }

    public void setApplications(List<GraphQLApplicationDto> applications) {
        this.applications = applications;
    }
}
