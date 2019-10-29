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

import com.castlemock.core.mock.rest.model.project.domain.RestMockResponse;
import com.castlemock.repository.Repository;

import java.util.List;

public interface RestMockResponseRepository extends Repository<RestMockResponse, String> {

    /**
     * Delete all {@link RestMockResponse} that matches the provided
     * <code>methodId</code>.
     * @param methodId The id of the method.
     */
    void deleteWithMethodId(String methodId);

    /**
     * Find all {@link RestMockResponse} that matches the provided
     * <code>methodId</code>.
     * @param methodId The id of the method.
     * @return A list of {@link RestMockResponse}.
     */
    List<RestMockResponse> findWithMethodId(String methodId);

    /**
     * Retrieve the {@link com.castlemock.core.mock.rest.model.project.domain.RestMethod} id
     * for the {@link RestMockResponse} with the provided id.
     * @param mockResponseId The id of the {@link RestMockResponse}.
     * @return The id of the method.
     * @since 1.20
     */
    String getMethodId(String mockResponseId);
}
