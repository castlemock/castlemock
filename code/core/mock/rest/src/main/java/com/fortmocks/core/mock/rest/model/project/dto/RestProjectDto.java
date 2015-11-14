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

package com.fortmocks.core.mock.rest.model.project.dto;

import com.fortmocks.core.basis.model.project.dto.ProjectDto;
import org.dozer.Mapping;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class RestProjectDto extends ProjectDto {

    @Mapping("restApplications")
    private List<RestApplicationDto> restApplications = new LinkedList<RestApplicationDto>();

    /**
     * The default REST project constructor
     */
    public RestProjectDto() {
    }

    /**
     * The constructor will create a new REST project DTO based on the provided projectDto
     * @param projectDto The new REST project DTO will be based on the provided project DTO and contain
     *                   the same information as the provided project DTO
     */
    public RestProjectDto(final ProjectDto projectDto){
        super(projectDto);
    }

    public List<RestApplicationDto> getRestApplications() {
        return restApplications;
    }

    public void setRestApplications(List<RestApplicationDto> restApplications) {
        this.restApplications = restApplications;
    }
}
