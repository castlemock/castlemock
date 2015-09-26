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

package com.fortmocks.war.base.model.project.service;

import com.fortmocks.core.base.model.ServiceFacade;
import com.fortmocks.core.base.model.project.dto.ProjectDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * The project service facade is used to assembly all the project service layers and interact with them
 * in order to retrieve a unified answer independent of the project type. The class is responsible for keeping
 * tracks of all the project services and providing the basic functionality shared among all the
 * project services, such as get, delete, update.
 * @author Karl Dahlgren
 * @since 1.0
 * @see com.fortmocks.core.base.model.project.Project
 * @see com.fortmocks.core.base.model.project.dto.ProjectDto
 */
public interface ProjectServiceFacade extends ServiceFacade<ProjectDto, Long> {

    /**
     * The method provides the functionality to export a project and convert it to a String
     * @param typeUrl The url for the specific type that the instance belongs to
     * @param id The id of the project that will be converted and exported
     * @return The project with the provided id as a String
     */
    String exportProject(String typeUrl, Long id);

    /**
     * The method provides the functionality to import a project as a String
     * @param type The type value for the specific type that the instance belongs to
     * @param multipartFiles The imported project files
     */
    void importProject(String type, List<MultipartFile> multipartFiles);
}
