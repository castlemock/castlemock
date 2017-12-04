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

package com.castlemock.web.mock.rest.model.project.repository;


import com.castlemock.core.basis.model.Repository;
import com.castlemock.core.mock.rest.model.project.domain.*;
import com.castlemock.core.mock.rest.model.project.dto.*;

/**
 * The REST project file repository provides the functionality to interact with the file system.
 * The repository is responsible for loading and REST project to the file system. Each
 * REST project is stored as a separate file.
 * @author Karl Dahlgren
 * @since 1.0
 * @see RestProject
 * @see Repository
 */
public interface RestProjectRepository extends Repository<RestProject, RestProjectDto, String> {

    /*
     * FIND OPERATIONS
     */

    /**
     * Finds a {@link RestProjectDto} with a provided REST project name.
     * @param restProjectName The name of the REST project that will be retrieved.
     * @return A {@link RestProjectDto} that matches the provided name.
     * @see RestProject
     * @see RestProjectDto
     */
    RestProjectDto findRestProjectWithName(String restProjectName);

    /**
     * Finds a {@link RestApplicationDto} with the provided ids.
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @return A {@link RestApplicationDto} that matches the search criteria.
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     */
    RestApplicationDto findRestApplication(String restProjectId, String restApplicationId);

    /**
     * Finds a {@link RestResourceDto} with the provided ids.
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceId The id of the {@link RestResource}
     * @return A {@link RestResourceDto} that matches the search criteria.
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     * @see RestResource
     * @see RestResourceDto
     */
    RestResourceDto findRestResource(String restProjectId, String restApplicationId, String restResourceId);

    /**
     * Finds a {@link RestMethodDto} with the provided ids.
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceId The id of the {@link RestResource}
     * @param restMethodId The id of the {@link RestMethod}
     * @return A {@link RestMethod} that matches the search criteria.
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     * @see RestResource
     * @see RestResourceDto
     * @see RestMethod
     * @see RestMethodDto
     */
    RestMethodDto findRestMethod(String restProjectId, String restApplicationId, String restResourceId, String restMethodId);

    /**
     * Finds a {@link RestMockResponseDto} with the provided ids.
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceId The id of the {@link RestResource}
     * @param restMethodId The id of the {@link RestMethod}
     * @param restMockResponseId The id of the {@link RestMockResponse}
     * @return A {@link RestMockResponseDto} that matches the search criteria.
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     * @see RestResource
     * @see RestResourceDto
     * @see RestMethod
     * @see RestMethodDto
     * @see RestMockResponse
     * @see RestMockResponseDto
     */
    RestMockResponseDto findRestMockResponse(String restProjectId, String restApplicationId, String restResourceId, String restMethodId, String restMockResponseId);

    /**
     * Finds a {@link RestResourceDto} with a URI
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceUri The URI of a {@link RestResource}
     * @return A {@link RestResourceDto} that matches the search criteria.
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     * @see RestResource
     * @see RestResourceDto
     */
    RestResourceDto findRestResourceByUri(String restProjectId, String restApplicationId, String restResourceUri);

    /*
     * SAVE OPERATIONS
     */

    /**
     * The method adds a new {@link RestApplication} to a {@link RestProject} and then saves
     * the {@link RestProject} to the file system.
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationDto The dto instance of {@link RestApplication} that will be added to the {@link RestProject}
     * @return The saved {@link RestApplicationDto}
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     */
    RestApplicationDto saveRestApplication(String restProjectId, RestApplicationDto restApplicationDto);

    /**
     * The method adds a new {@link RestResource} to a {@link RestApplication} and then saves
     * the {@link RestProject} to the file system.
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceDto The dto instance of {@link RestApplication} that will be added to the {@link RestApplication}
     * @return The saved {@link RestResourceDto}
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     * @see RestResource
     * @see RestResourceDto
     */
    RestResourceDto saveRestResource(String restProjectId, String restApplicationId, RestResourceDto restResourceDto);

    /**
     * The method adds a new {@link RestMethod} to a {@link RestResource} and then saves
     * the {@link RestProject} to the file system.
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceId The id of the {@link RestResource}
     * @param restMethodDto The dto instance of {@link RestMethod} that will be added to the {@link RestResource}
     * @return The saved {@link RestMethodDto}
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     * @see RestResource
     * @see RestResourceDto
     * @see RestMethod
     * @see RestMethodDto
     */
    RestMethodDto saveRestMethod(String restProjectId, String restApplicationId, String restResourceId, RestMethodDto restMethodDto);

    /**
     * The method adds a new {@link RestMockResponse} to a {@link RestMethod} and then saves
     * the {@link RestProject} to the file system.
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceId The id of the {@link RestResource}
     * @param restMethodId The id of the {@link RestMethod}
     * @param restMockResponseDto The dto instance of {@link RestMockResponse} that will be added to the {@link RestMethod}
     * @return The saved {@link RestMockResponseDto}
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     * @see RestResource
     * @see RestResourceDto
     * @see RestMethod
     * @see RestMethodDto
     * @see RestMockResponse
     * @see RestMockResponseDto
     */
    RestMockResponseDto saveRestMockResponse(String restProjectId, String restApplicationId, String restResourceId, String restMethodId, RestMockResponseDto restMockResponseDto);

    /*
     * UPDATE OPERATIONS
     */

    /**
     * The method updates an already existing {@link RestApplication}
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication} that will be updated
     * @param restApplicationDto The updated {@link RestApplicationDto}
     * @return The updated version of the {@link RestApplicationDto}
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     */
    RestApplicationDto updateRestApplication(String restProjectId, String restApplicationId, RestApplicationDto restApplicationDto);

    /**
     * The method updates an already existing {@link RestResource}
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceId The id of the {@link RestResource} that will be updated
     * @param restResourceDto The updated {@link RestResourceDto}
     * @return The updated version of the {@link RestResourceDto}
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     * @see RestResource
     * @see RestResourceDto
     */
    RestResourceDto updateRestResource(String restProjectId, String restApplicationId, String restResourceId, RestResourceDto restResourceDto);

    /**
     * The method updates an already existing {@link RestMethod}
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceId The id of the {@link RestResource}
     * @param restMethodId The id of the {@link RestMethod} that will be updated
     * @param restMethodDto The updated {@link RestMethodDto}
     * @return The updated version of the {@link RestMethodDto}
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     * @see RestResource
     * @see RestResourceDto
     * @see RestMethod
     * @see RestMethodDto
     */
    RestMethodDto updateRestMethod(String restProjectId, String restApplicationId, String restResourceId, String restMethodId, RestMethodDto restMethodDto);

    /**
     * The method updates an already existing {@link RestMockResponse}
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceId The id of the {@link RestResource}
     * @param restMethodId The id of the {@link RestMethod}
     * @param restMockResponseId The id of the {@link RestMockResponse} that will be updated
     * @param restMockResponseDto The updated {@link RestMockResponseDto}
     * @return The updated version of the {@link RestMockResponseDto}
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     * @see RestResource
     * @see RestResourceDto
     * @see RestMethod
     * @see RestMethodDto
     */
    RestMockResponseDto updateRestMockResponse(String restProjectId, String restApplicationId, String restResourceId, String restMethodId, String restMockResponseId, RestMockResponseDto restMockResponseDto);

    /*
     * DELETE OPERATIONS
     */

    /**
     * The method deletes a {@link RestApplication}
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication} that will be deleted
     * @return The deleted {@link RestApplicationDto}
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     */
    RestApplicationDto deleteRestApplication(String restProjectId, String restApplicationId);

    /**
     * The method deletes a {@link RestResource}
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceId The id of the {@link RestResource} that will be deleted
     * @return The deleted {@link RestResourceDto}
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     * @see RestResource
     * @see RestResourceDto
     */
    RestResourceDto deleteRestResource(String restProjectId, String restApplicationId, String restResourceId);

    /**
     * The method deletes a {@link RestMethod}
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceId The id of the {@link RestResource}
     * @param restMethodId The id of the {@link RestMethod} that will be deleted
     * @return The deleted {@link RestMethodDto}
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     * @see RestResource
     * @see RestResourceDto
     * @see RestMethod
     * @see RestMethodDto
     */
    RestMethodDto deleteRestMethod(String restProjectId, String restApplicationId, String restResourceId, String restMethodId);

    /**
     * The method deletes a {@link RestMockResponse}
     * @param restProjectId The id of the {@link RestProject}
     * @param restApplicationId The id of the {@link RestApplication}
     * @param restResourceId The id of the {@link RestResource}
     * @param restMethodId The id of the {@link RestMethod}
     * @param restMockResponseId The id of the {@link RestMockResponse} that will be deleted
     * @return The deleted {@link RestMockResponseDto}
     * @see RestProject
     * @see RestProjectDto
     * @see RestApplication
     * @see RestApplicationDto
     * @see RestResource
     * @see RestResourceDto
     * @see RestMethod
     * @see RestMethodDto
     * @see RestMockResponse
     * @see RestMockResponseDto
     */
    RestMockResponseDto deleteRestMockResponse(String restProjectId, String restApplicationId, String restResourceId, String restMethodId, String restMockResponseId);

    /**
     * Updates the current response sequence index.
     * @param restProjectId The project id.
     * @param restApplicationId The application id.
     * @param restResourceId The resource id.
     * @param restMethodId The method id.
     * @param index The new response sequence index.
     * @since 1.17
     */
    void setCurrentResponseSequenceIndex(String restProjectId, String restApplicationId,
                                         String restResourceId, String restMethodId, Integer index);

}
