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
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;

import java.util.List;


public interface SoapPortRepository extends Repository<SoapPort, String> {

    void deleteWithProjectId(String projectId);

    List<SoapPort> findWithProjectId(String projectId);

    /**
     * The method finds a {@link SoapPort} with the provided name
     * @param soapPortName The name of the {@link SoapPort}
     * @return A {@link SoapPort} that matches the provided search criteria.
     */
    SoapPort findWithName(String projectId, String soapPortName);

    /**
     * The method finds a {@link SoapPort} with the provided uri
     * @param uri The uri used by the {@link SoapPort}
     * @return A {@link SoapPort} that matches the provided search criteria.
     */
    SoapPort findWithUri(String projectId, String uri);

    /**
     * Retrieve the {@link com.castlemock.core.mock.soap.model.project.domain.SoapProject} id
     * for the {@link SoapPort} with the provided id.
     * @param portId The id of the {@link SoapPort}.
     * @return The id of the project.
     * @since 1.20
     */
    String getProjectId(String portId);
}
