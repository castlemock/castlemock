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

}
