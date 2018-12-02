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

package com.castlemock.repository.rest.project;

import com.castlemock.core.mock.rest.model.project.domain.RestApplication;
import com.castlemock.repository.Repository;

import java.util.List;

public interface RestApplicationRepository extends Repository<RestApplication, String> {

    /**
     * Delete all {@link RestApplication} that matches the provided
     * <code>projectId</code>.
     * @param projectId The id of the project.
     */
    void deleteWithProjectId(String projectId);

    /**
     * Find all {@link RestApplication} that matches the provided
     * <code>projectId</code>.
     * @param projectId The id of the project.
     * @return A list of {@link RestApplication}.
     */
    List<RestApplication> findWithProjectId(String projectId);

    /**
     * Retrieve the {@link com.castlemock.core.mock.rest.model.project.domain.RestProject} id
     * for the {@link RestApplication} with the provided id.
     * @param applicationId The id of the {@link RestApplication}.
     * @return The id of the project.
     * @since 1.20
     */
    String getProjectId(String applicationId);

}
