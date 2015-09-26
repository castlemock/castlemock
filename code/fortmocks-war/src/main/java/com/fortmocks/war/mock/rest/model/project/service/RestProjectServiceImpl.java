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

package com.fortmocks.war.mock.rest.model.project.service;

import com.fortmocks.core.base.model.TypeIdentifier;
import com.fortmocks.core.base.model.project.dto.ProjectDto;
import com.fortmocks.core.mock.rest.model.RestTypeIdentifier;
import com.fortmocks.core.mock.rest.model.project.RestProject;
import com.fortmocks.core.mock.rest.model.project.dto.RestProjectDto;
import com.fortmocks.war.base.model.project.service.ProjectServiceImpl;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;


/**
 * The class is the project operation layer class responsible for communicating
 * with the project repository and managing projects.
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class RestProjectServiceImpl extends ProjectServiceImpl<RestProject, RestProjectDto> implements RestProjectService {

    private static final RestTypeIdentifier REST_TYPE_IDENTIFIER = new RestTypeIdentifier();

    /**
     * Finds a project by a given name
     * @param name The name of the project that should be retrieved
     * @return Returns a project with the provided name
     */
    @Override
    public RestProjectDto findRestProject(final String name) {
        Preconditions.checkNotNull(name, "Project name cannot be null");
        Preconditions.checkArgument(!name.isEmpty(), "Project name cannot be empty");
        for(RestProject restProject : findAllTypes()){
            if(restProject.getName().equalsIgnoreCase(name)) {
                return toDto(restProject);
            }
        }
        return null;
    }

    /**
     * Updates a project with new information
     * @param restProjectId The id of the project that will be updated
     * @param updatedProject The updated version of the project
     * @return The updated version project
     */
    @Override
    public RestProjectDto update(final Long restProjectId, final RestProjectDto updatedProject){
        Preconditions.checkNotNull(restProjectId, "Project id be null");
        Preconditions.checkArgument(restProjectId >= 0, "Project id cannot be negative");
        Preconditions.checkNotNull(updatedProject, "Project cannot be null");
        Preconditions.checkArgument(!updatedProject.getName().isEmpty(), "Invalid project name. Project name cannot be empty");
        final RestProjectDto projectWithNameDto = findRestProject(updatedProject.getName());
        Preconditions.checkArgument(projectWithNameDto == null || projectWithNameDto.getId().equals(restProjectId), "Project name is already taken");
        final RestProjectDto project = findOne(restProjectId);
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        return super.save(project);
    }



    /**
     * Returns the REST type identifier.
     * @return The REST identifier
     */
    @Override
    public TypeIdentifier getTypeIdentifier() {
        return REST_TYPE_IDENTIFIER;
    }

    /**
     * The method is responsible for converting a project dto instance into a project dto subclass.
     * This is used when the {@link com.fortmocks.war.base.model.project.service.ProjectServiceFacadeImpl} needs
     * to manage base project class, but wants to be able to convert it into a specific subclass, for example when
     * creating or updating a project instance.
     * @param projectDto The project dto instance that will be converted into a project dto subclass
     * @return The converted project dto subclass
     * @throws NullPointerException Throws NullPointerException in case if the provided project dto instance is null.
     * @see com.fortmocks.war.base.model.project.service.ProjectServiceFacade
     */
    @Override
    public RestProjectDto convertType(ProjectDto projectDto) {
        Preconditions.checkNotNull(projectDto, "Project cannot be null");
        return new RestProjectDto(projectDto);
    }
}
