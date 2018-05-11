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

package com.castlemock.web.mock.soap.model.project.repository;

import com.castlemock.core.basis.model.Repository;
import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponse;

import java.util.List;

public interface SoapMockResponseRepository extends Repository<SoapMockResponse, String> {

    void deleteWithOperationId(String operationId);

    List<SoapMockResponse> findWithOperationId(String operationId);

    /**
     * Retrieve the {@link com.castlemock.core.mock.soap.model.project.domain.SoapOperation} id
     * for the {@link SoapMockResponse} with the provided id.
     * @param mockResponseId The id of the {@link SoapMockResponse}.
     * @return The id of the operation.
     * @since 1.20
     */
    String getOperationId(String mockResponseId);

}
