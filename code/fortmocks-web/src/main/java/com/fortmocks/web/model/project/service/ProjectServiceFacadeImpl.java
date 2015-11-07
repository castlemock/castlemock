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

package com.fortmocks.web.model.project.service;

import com.fortmocks.core.model.TypeIdentifiable;
import com.fortmocks.core.model.TypeIdentifier;
import com.fortmocks.core.model.project.Project;
import com.fortmocks.core.model.project.dto.ProjectDto;
import com.fortmocks.core.model.project.service.ProjectServiceFacade;
import com.fortmocks.web.model.ServiceFacadeImpl;
import org.springframework.stereotype.Service;

/**
 * The project service component is used to assembly all the project service layers and interact with them
 * in order to retrieve a unified answer independent of the project type. The class is responsible for keeping
 * tracks of all the project services and providing the basic functionality shared among all the
 * project services, such as get, delete, update.
 * @author Karl Dahlgren
 * @since 1.0
 * @see Project
 * @see ProjectDto
 */
@Service
public class ProjectServiceFacadeImpl extends ServiceFacadeImpl<Project, ProjectDto, Long, ProjectServiceImpl<Project, ProjectDto>> implements ProjectServiceFacade {

    /**
     * The initiate method is responsible for for locating all the service instances for a specific module
     * and organizing them depending on the type.
     * @see com.fortmocks.core.model.Service
     * @see TypeIdentifier
     * @see TypeIdentifiable
     */
    @Override
    public void initiate(){
        initiate(ProjectServiceImpl.class);
    }

    /**
     * The method provides the functionality to export a project and convert it to a String
     * @param typeUrl The url for the specific type that the instance belongs to
     * @param id The id of the project that will be converted and exported
     * @return The project with the provided id as a String
     */
    @Override
    public String exportProject(final String typeUrl, final Long id){
        final ProjectServiceImpl<Project, ProjectDto> service = findByTypeUrl(typeUrl);
        return service.exportProject(id);
    }

    /**
     * The method provides the functionality to import a project as a String
     * @param type The type value for the specific type that the instance belongs to
     * @param rawProject The imported project file
     */
    @Override
    public void importProject(String type, String rawProject) {
        final ProjectServiceImpl<Project, ProjectDto> service = findByType(type);
        service.importProject(rawProject);
    }

}
