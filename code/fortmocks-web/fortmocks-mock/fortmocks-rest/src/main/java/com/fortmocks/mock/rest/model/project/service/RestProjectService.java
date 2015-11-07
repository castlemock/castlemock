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

package com.fortmocks.mock.rest.model.project.service;

import com.fortmocks.core.model.project.service.ProjectService;
import com.fortmocks.mock.rest.model.project.RestMethod;
import com.fortmocks.mock.rest.model.project.RestMethodType;
import com.fortmocks.mock.rest.model.project.RestMockResponseStatus;
import com.fortmocks.mock.rest.model.project.RestProject;
import com.fortmocks.mock.rest.model.project.dto.*;

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

    RestResourceDto findRestResource(Long restProjectId, Long restApplicationId, Long restResourceId);

    RestResourceDto findRestResource(Long restProjectId, Long restApplicationId, String restResourceUri);

    RestMethodDto findRestMethod(Long restProjectId, Long restApplicationId, Long restResourceId, Long restMethodId);

    RestMethodDto findRestMethod(Long restProjectId, Long restApplicationId, String restResourceUri, RestMethodType restMethodType);

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

    /**
     * The method provides the functionality to update an existing application
     * @param restProjectId The id of the project that the application belongs to
     * @param restApplicationId The id of the application that will be updated
     * @param restApplicationDto The new application values
     */
    void updateRestApplication(Long restProjectId, Long restApplicationId, RestApplicationDto restApplicationDto);

    RestResourceDto saveRestResource(Long restProjectId, Long restApplicationId, RestResourceDto restResourceDto);

    void deleteRestResource(Long restProjectId, Long restApplicationId, Long restResourceId);

    void deleteRestResources(Long restProjectId, Long restApplicationId, List<RestResourceDto> restResources);

    void updateRestResource(Long restProjectId, Long restApplicationId, Long restResourceId, RestResourceDto restResourceDto);

    RestMethodDto saveRestMethod(Long restProjectId, Long restApplicationId, Long restResourceId, RestMethodDto restMethodDto);

    void deleteRestMethod(Long restProjectId, Long restApplicationId, Long restResourceId, Long restMethodId);

    void deleteRestMethods(Long restProjectId, Long restApplicationId, Long restResourceId, List<RestMethodDto> restMethods);

    void updateRestMethod(Long restProjectId, Long restApplicationId, Long restResourceId, Long restMethodId, RestMethodDto restMethodDto);

    void saveRestMockResponse(Long restProjectId, Long restApplicationId, Long restResourceId, Long restMethodId, RestMockResponseDto restMockResponseDto);

    RestMockResponseDto findRestMockResponse(Long restProjectId, Long restApplicationId, Long restResourceId, Long restMethodId, Long restMockResponseId);

    void updateRestMockResponse(Long restProjectId, Long restApplicationId, Long restResourceId, Long restMethodId, Long restMockResponseId, RestMockResponseDto updatedRestMockResponseDto);

    void deleteRestMockResponse(Long restProjectId, Long restApplicationId, Long restResourceId, Long restMethodId, Long restMockResponseId);

    void deleteRestMockResponses(Long restProjectId, Long restApplicationId, Long restResourceId, Long restMethodId, List<RestMockResponseDto> restMockResponses);

    void updateStatus(Long restProjectId, Long restApplicationId, Long restResourceId, Long restMethodId, Long restMockResponseId, RestMockResponseStatus status);

    /**
     * The method is responsible for updating the current response sequence index for
     * a specific method
     * @param restMethodId The id of the method that will be updated
     * @param currentResponseSequenceIndex The new current response sequence index
     * @see RestMethod
     * @see RestMethodDto
     */
    void updateCurrentResponseSequenceIndex(Long restMethodId, Integer currentResponseSequenceIndex);

}
