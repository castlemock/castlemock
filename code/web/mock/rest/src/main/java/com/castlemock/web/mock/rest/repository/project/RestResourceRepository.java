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

package com.castlemock.web.mock.rest.repository.project;

import com.castlemock.web.basis.repository.Repository;
import com.castlemock.core.mock.rest.model.project.domain.RestApplication;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.core.mock.rest.model.project.domain.RestResource;

import java.util.List;

public interface RestResourceRepository extends Repository<RestResource, String> {

    /**
     * Delete all {@link RestResource} that matches the provided
     * <code>applicationId</code>.
     * @param applicationId The id of the applicationId.
     */
    void deleteWithApplicationId(String applicationId);

    /**
     * Find all {@link RestResource} that matches the provided
     * <code>applicationId</code>.
     * @param applicationId The id of the applicationId.
     * @return A list of {@link RestResource}.
     * @since 1.20
     */
    List<RestResource> findWithApplicationId(String applicationId);

    /**
     * Find all {@link RestResource} ids that matches the provided
     * <code>applicationId</code>.
     * @param applicationId The id of the applicationId.
     * @return A list of {@link RestResource} ids.
     * @since 1.20
     */
    List<String> findIdsWithApplicationId(String applicationId);


    /**
     * Finds a {@link RestResource} with a URI
     * @param applicationId The id of the {@link RestApplication}
     * @param resourceUri The URI of a {@link RestResource}
     * @return A {@link RestResource} that matches the search criteria.
     * @see RestProject
     * @see RestApplication
     * @see RestResource
     */
    RestResource findRestResourceByUri(String applicationId, String resourceUri);

    /**
     * Retrieve the {@link com.castlemock.core.mock.rest.model.project.domain.RestApplication} id
     * for the {@link RestResource} with the provided id.
     * @param resourceId The id of the {@link RestResource}.
     * @return The id of the application.
     * @since 1.20
     */
    String getApplicationId(String resourceId);

}
