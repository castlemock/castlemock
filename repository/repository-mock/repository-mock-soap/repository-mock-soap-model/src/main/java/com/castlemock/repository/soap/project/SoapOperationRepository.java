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

package com.castlemock.repository.soap.project;

import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapOperationIdentifier;
import com.castlemock.model.mock.soap.domain.SoapVersion;
import com.castlemock.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface SoapOperationRepository extends Repository<SoapOperation, String> {

    void deleteWithPortId(String portId);

    List<SoapOperation> findWithPortId(String portId);

    /**
     * The method provides the functionality to find a SOAP operation with a specific name
     * @param soapPortId The id of the SOAP port.
     * @param soapOperationName The name of the SOAP operation that should be retrieved
     * @return A SOAP operation that matches the search criteria. If no SOAP operation matches the provided
     * name then null will be returned.
     */
    Optional<SoapOperation> findWithName(String soapPortId, String soapOperationName);

    /**
     * Find a {@link SoapOperation} with a provided {@link HttpMethod}, {@link SoapVersion}
     * and an identifier.
     * @param portId The id of the port
     * @param method The HTTP method
     * @param version The SOAP version
     * @param operationIdentifier The identifier
     * @return A {@link SoapOperation} that matches the provided search criteria.
     */
    Optional<SoapOperation> findWithMethodAndVersionAndIdentifier(String portId, HttpMethod method,
                                                        SoapVersion version,
                                                        SoapOperationIdentifier operationIdentifier);

    /**
     * Retrieve the {@link com.castlemock.model.mock.soap.domain.SoapPort} id
     * for the {@link SoapOperation} with the provided id.
     * @param operationId The id of the {@link SoapOperation}.
     * @return The id of the port.
     * @since 1.20
     */
    String getPortId(String operationId);

}
