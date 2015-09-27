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

import com.fortmocks.core.mock.rest.model.project.RestApplication;
import com.fortmocks.core.mock.rest.model.project.RestProject;
import com.fortmocks.core.mock.rest.model.project.dto.RestApplicationDto;
import com.fortmocks.core.mock.rest.model.project.dto.RestProjectDto;
import com.fortmocks.war.base.model.project.service.ProjectService;

import java.util.List;

/**
 * The class is the project operation layer class responsible for communicating
 * with the project repository and managing projects.
 * @author Karl Dahlgren
 * @since 1.0
 */
public interface RestProjectService extends ProjectService<RestProject, RestProjectDto> {

    /**
     * Finds a project by a given name
     * @param name The name of the project that should be retrieved
     * @return Returns a project with the provided name
     */
    RestProjectDto findRestProject(String name);

    RestApplicationDto findRestApplication(Long restProjectId, Long restApplicationId);

    RestApplicationDto saveRestApplication(Long restProjectId, RestApplicationDto restApplication);

    /**
     * Deletes an application from a specific REST project. Both the project and the application are identified with provided
     * identifiers.
     * @param restProjectId The identifier of the REST project that the port belongs to
     * @param restApplicationId The identifier of the application that will be removed
     */
    void deleteRestApplication(Long restProjectId, Long restApplicationId);

    /**
     * Takes a list of applications and delete them from a project
     * @param restProjectId The id of the project that the application belong to
     * @param restApplications The list of applications that will be deleted
     */
    void deleteRestApplications(Long restProjectId, List<RestApplicationDto> restApplications);
}
