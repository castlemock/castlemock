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
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponse;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
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

    RestProjectDto findRestProjectWithName(String restProjectName);

    RestApplicationDto findRestApplication(String restProjectId, String restApplicationId);

    RestResourceDto findRestResource(String restProjectId, String restApplicationId, String restResourceId);

    RestMethodDto findRestMethod(String restProjectId, String restApplicationId, String restResourceId, String restMethodId);

    RestMockResponseDto findRestMockResponse(String restProjectId, String restApplicationId, String restResourceId, String restMethodId, String restMockResponseId);

    RestResourceDto findRestResourceByUri(String restProjectId, String restApplicationId, String restResourceUri);

    /*
     * SAVE OPERATIONS
     */

    RestApplicationDto saveRestApplication(String restProjectId, RestApplicationDto restApplicationDto);

    RestResourceDto saveRestResource(String restProjectId, String restApplicationId, RestResourceDto restResourceDto);

    RestMethodDto saveRestMethod(String restProjectId, String restApplicationId, String restResourceId, RestMethodDto restMethodDto);

    RestMockResponseDto saveRestMockResponse(String restProjectId, String restApplicationId, String restResourceId, String restMethodId, RestMockResponseDto restMockResponseDto);

    /*
     * UPDATE OPERATIONS
     */

    RestApplicationDto updateRestApplication(String restProjectId, String restApplicationId, RestApplicationDto restApplicationDto);

    RestResourceDto updateRestResource(String restProjectId, String restApplicationId, String restResourceId, RestResourceDto restResourceDto);

    RestMethodDto updateRestMethod(String restProjectId, String restApplicationId, String restResourceId, String restMethodId, RestMethodDto restMethodDto);

    RestMockResponseDto updateRestMockResponse(String restProjectId, String restApplicationId, String restResourceId, String restMethodId, String restMockResponseId, RestMockResponseDto restMockResponseDto);

    /*
     * DELETE OPERATIONS
     */

    RestApplicationDto deleteRestApplication(String restProjectId, String restApplicationId);

    RestResourceDto deleteRestResource(String restProjectId, String restApplicationId, String restResourceId);

    RestMethodDto deleteRestMethod(String restProjectId, String restApplicationId, String restResourceId, String restMethodId);

    RestMockResponseDto deleteRestMockResponse(String restProjectId, String restApplicationId, String restResourceId, String restMethodId, String restMockResponseId);

}
