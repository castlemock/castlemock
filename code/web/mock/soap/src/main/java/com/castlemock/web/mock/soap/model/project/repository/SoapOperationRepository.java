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
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.model.project.dto.SoapOperationDto;

import java.util.List;


public interface SoapOperationRepository extends Repository<SoapOperation, SoapOperationDto, String> {

    void deleteWithPortId(String portId);

    List<SoapOperationDto> findWithPortId(String portId);

    /**
     * The method provides the functionality to find a SOAP operation with a specific name
     * @param soapPortId The id of the SOAP port.
     * @param soapOperationName The name of the SOAP operation that should be retrieved
     * @return A SOAP operation that matches the search criteria. If no SOAP operation matches the provided
     * name then null will be returned.
     */
    SoapOperationDto findWithName(String soapPortId, String soapOperationName);

    /**
     * Updates the current response sequence index.
     * @param soapOperationId The operation id.
     * @param index The new response sequence index.
     * @since 1.17
     */
    void setCurrentResponseSequenceIndex(String soapOperationId, Integer index);

}
