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
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;

import java.util.List;

public interface RestMethodRepository extends Repository<RestMethod, String> {

    /**
     * Updates the current response sequence index.
     * @param restMethodId The method id.
     * @param index The new response sequence index.
     * @since 1.17
     */
    void setCurrentResponseSequenceIndex(String restMethodId, Integer index);


    /**
     * Delete all {@link RestMethod} that matches the provided
     * <code>resourceId</code>.
     * @param resourceId The id of the resource.
     */
    void deleteWithResourceId(String resourceId);

    /**
     * Find all {@link RestMethod} that matches the provided
     * <code>resourceId</code>.
     * @param resourceId The id of the resource.
     * @return A list of {@link RestMethod}.
     * @since 1.20
     */
    List<RestMethod> findWithResourceId(String resourceId);

    /**
     * Find all {@link RestMethod} ids that matches the provided
     * <code>resourceId</code>.
     * @param resourceId The id of the resource.
     * @return A list of {@link RestMethod} ids.
     * @since 1.20
     */
    List<String> findIdsWithResourceId(String resourceId);


    /**
     * Retrieve the {@link com.castlemock.core.mock.rest.model.project.domain.RestResource} id
     * for the {@link RestMethod} with the provided id.
     * @param methodId The id of the {@link RestMethod}.
     * @return The id of the resource.
     * @since 1.20
     */
    String getResourceId(String methodId);
}
