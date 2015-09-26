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

package com.fortmocks.war.mock.soap.model.project.service;

import com.fortmocks.core.mock.soap.model.project.*;
import com.fortmocks.core.mock.soap.model.project.dto.SoapMockResponseDto;
import com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto;
import com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto;
import com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.war.base.model.project.service.ProjectService;

import java.util.List;
import java.util.Map;

/**
 * The class is the project operation layer class responsible for communicating
 * with the project repository and managing projects.
 * @author Karl Dahlgren
 * @since 1.0
 */
public interface SoapProjectService extends ProjectService<SoapProject, SoapProjectDto> {

    /**
     * Finds a project by a given name
     * @param name The name of the project that should be retrieved
     * @return Returns a project with the provided name
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     */
    SoapProjectDto findSoapProject(String name);

    /**
     * Deletes a list of projects
     * @param soapProjects The list of projects that will be deleted
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     */
    void delete(List<SoapProjectDto> soapProjects);


    /**
     * Count the operation statuses. Each status will be a key in the map and the corresponding status
     * count will be the value.
     * @return The result of the status count
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapPort
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapOperation
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto
     */
    Map<SoapOperationStatus, Integer> getOperationStatusCount(Long soapProjectId, Long soapPortId);

    /**
     * Count the operation statuses. Each status will be a key in the map and the corresponding status
     * count will be the value.
     * @return The result of the status count
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapPort
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapOperation
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto
     */
    Map<SoapOperationStatus, Integer> getOperationStatusCount(SoapPortDto soapPortDto);

    /**
     * Update the status of all operations that has a port with the provided port id
     * @param soapProjectId The id of the project that the operations belongs to
     * @param soapPortId The id of the port that the operations belongs to
     * @param soapOperationStatus The new status that will be assigned to all operations
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapPort
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapOperation
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto
     */
    void updateStatus(Long soapProjectId, Long soapPortId, SoapOperationStatus soapOperationStatus);

    /**
     * Update the status of the provided operation
     * @param soapProjectId
     * @param soapPortId
     * @param soapOperationId The id of the operation that will be updated
     * @param soapOperationStatus The new status that will be assigned to the operation
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapPort
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapOperation
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto
     */
    void updateStatus(Long soapProjectId, Long soapPortId, Long soapOperationId, SoapOperationStatus soapOperationStatus);


    /**
     * The method updates a status for a specific MockResponse
     * @param soapProjectId The id of the project that the mocked response belongs to
     * @param soapPortId The id of the port that the mocked response belongs to
     * @param soapOperationId The id of the operation that the mocked response belongs to
     * @param soapMockResponseId The id of the mocked response that will be updated with the new status
     * @param soapMockResponseStatus The new status that should be assigned to the MockResponse
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapPort
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapOperation
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto
     */
    void updateStatus(Long soapProjectId, Long soapPortId, Long soapOperationId, Long soapMockResponseId, SoapMockResponseStatus soapMockResponseStatus);

    /**
     * The method provides the functionality to update an operation for a specific port for a SOAP project.
     * @param soapProjectId The id of the project that the operation belongs to
     * @param soapPortId The id of the port that the operation belongs to
     * @param soapOperationId The id of the operation that will be updated
     * @param updatedSoapOperation The soapOperationDto contains the new values that the operation with the provided id
     *                         will receive.
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapPort
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapOperation
     */
    void updateOperation(Long soapProjectId, Long soapPortId, Long soapOperationId, SoapOperationDto updatedSoapOperation);

    /**
     * The method provides the functionality to update the forward address for all operations for the provided ports
     * @param soapProjectId The id of the project that the ports belongs to
     * @param soapPorts The list of port that will be assigned the new forward address
     * @param forwardedEndpoint The updated forward endpoint address
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto
     */
    void updateForwardedEndpoint(Long soapProjectId, List<SoapPortDto> soapPorts, String forwardedEndpoint);

    /**
     * The method updates the forwarded endpoint for a provided list with operations
     * @param soapProjectId The id of the project that the operation belongs to
     * @param soapPortId The id of the port that the operation belongs to
     * @param soapOperations The list of operation ids that will updated with the provided forwarded endpoint
     * @param forwardedEndpoint The new value forwarded endpoint
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapPort
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto
     */
    void updateForwardedEndpoint(Long soapProjectId, Long soapPortId, List<SoapOperationDto> soapOperations, String forwardedEndpoint);

    /**
     * The method retrieves a operation with a specific project id, portss id and operation id
     * @param soapProjectId The id of the project that the operation belongs to
     * @param soapPortId The id of the port that the operation belongs to
     * @param soapOperationId The id of the operation that will be retrieved
     * @return A operation that matches the search criteria. Returns null if no operation matches the search criteria
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapPort
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto
     */
    SoapOperationDto findSoapOperation(Long soapProjectId, Long soapPortId, Long soapOperationId);

    /**
     * The method find an ports with project id and ports id
     * @param soapProjectId The id of the project which the port belongs to
     * @param soapPortId The id of the port that will be retrieved
     * @return Returns an port that matches the search criteria. Returns null if no port matches.
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapPort
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto
     */
    SoapPortDto findSoapPort(Long soapProjectId, Long soapPortId);

    /**
     * The method provides the functionality to save a list of ports to a SOAP project. The project is identified
     * With the provided soapProjectId
     * @param soapProjectId The identifier for the SOAP project that the ports will be added to
     * @param soapPorts The ports that will be added and saved to the SOAP project
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto
     */
    void saveSoapPorts(Long soapProjectId, List<SoapPortDto> soapPorts);

    /**
     * Deletes a port from a specific SOAP project. Both the project and the port are identified with provided
     * identifiers.
     * @param soapProjectId The identifier of the SOAP project that the port belongs to
     * @param soapPortId The identifier of the port that will be removed
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapPort
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto
     */
    void deleteSoapPort(Long soapProjectId, Long soapPortId);

    /**
     * Takes a list of ports and delete them from the database
     * @param soapProjectId The id of the project that the ports belong to
     * @param soapPorts The list of ports that will be deleted
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapPort
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto
     */
    void deleteSoapPorts(Long soapProjectId, List<SoapPortDto> soapPorts);

    /**
     * The method provides the functionality to retrieve a mocked response with project id, ports id,
     * operation id and mock response id
     * @param soapProjectId The id of the project that the mocked response belongs to
     * @param soapPortId The id of the ports that the mocked response belongs to
     * @param soapOperationId The id of the operation that the mocked response belongs to
     * @param soapMockResponseId The id of the mocked response that will be retrieved
     * @return Mocked response that match the provided parameters
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapPort
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapOperation
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapMockResponse
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapMockResponseDto
     */
    SoapMockResponseDto findSoapMockResponse(Long soapProjectId, Long soapPortId, Long soapOperationId, Long soapMockResponseId);

    /**
     * The method provides the functionality to save a SOAP mocked response to a SOAP operation. The SOAP operation is
     * identified with project id, port id and operation id.
     * @param soapProjectId The id of the project that operation belongs to
     * @param soapPortId The id of the port that the operation belongs to
     * @param soapOperationId The id of the operation that the mock SOAP response will be added to
     * @param soapMockResponseDto The mock response that will be added to the operation
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapPort
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapOperation
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto
     */
    void saveSoapMockResponse(Long soapProjectId, Long soapPortId, Long soapOperationId, SoapMockResponseDto soapMockResponseDto);

    /**
     * The method provides the functionality to save a SOAP mocked response to a SOAP operation. The SOAP operation is
     * identified with operation id
     * @param soapOperationId The id of the operation that the mock SOAP response will be added to
     * @param soapMockResponseDto The mock response that will be added to the operation
     * @see com.fortmocks.core.mock.soap.model.project.SoapOperation
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto
     */
    void saveSoapMockResponse(Long soapOperationId, SoapMockResponseDto soapMockResponseDto);

    /**
     * The method provides the functionality to delete a mock response from a specific operation
     * @param soapProjectId The id of the project that operation belongs to
     * @param soapPortId The id of the port that the operation belongs to
     * @param soapOperationId The id of the operation that the mock SOAP response belongs to
     * @param soapMockResponseId The id of the of the SOAP mock response that will be deleted
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapPort
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapOperation
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapMockResponse
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapMockResponseDto
     */
    void deleteSoapMockResponse(Long soapProjectId, Long soapPortId, Long soapOperationId, Long soapMockResponseId);

    /**
     * The method provides the functionality to delete a list of mock responses from a specific operation
     * @param soapProjectId The id of the project that operation belongs to
     * @param soapPortId The id of the port that the operation belongs to
     * @param soapOperationId The id of the operation that the mock SOAP responses belongs to
     * @param mockResponses The list of mock responses that will be deleted
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapPort
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapOperation
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto
     */
    void deleteSoapMockResponses(Long soapProjectId, Long soapPortId, Long soapOperationId, List<SoapMockResponseDto> mockResponses);

    /**
     *
     * @param soapProjectId The id of the project that the mock response belongs to
     * @param soapPortId The id of the port that the mock response belongs to
     * @param soapOperationId The id of the operation that the mock response belongs to
     * @param soapMockResponseId The id of the SOAP mock response that will be updated
     * @param soapMockResponseDto The SOAP mock response contains all the updated values that will be assigned
     *                         to the mock response with the id equal to the provided soapMockResponseId
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapPort
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapOperation
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto
     */
    void updateSoapMockResponse(Long soapProjectId, Long soapPortId, Long soapOperationId, Long soapMockResponseId, SoapMockResponseDto soapMockResponseDto);

    /**
     * Find a operation that matches the project id, name, url and method
     * @param soapProjectId The id of the project that the operation belongs to
     * @param name The operation name
     * @param uri The operation url
     * @param soapOperationMethod The operation method
     * @return A operation that matches the search criteria. Returns null if no operation matches the search criteria
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapOperation
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto
     */
    SoapOperationDto findSoapOperation(Long soapProjectId, String name, String uri, SoapOperationMethod soapOperationMethod, SoapOperationType type);

    /**
     * The method finds a mock responses from an operation that has a specific response status
     * @param soapOperationId The id of the operation that the SOAP mock response has to belong to
     * @param status The status that the SOAP mock responses has to have
     * @return A list of {@link com.fortmocks.core.mock.soap.model.project.dto.SoapMockResponseDto} that match the provided
     * search criteria.
     * @see com.fortmocks.core.mock.soap.model.project.SoapOperation
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto
     */
    List<SoapMockResponseDto> findSoapMockResponses(Long soapOperationId, SoapMockResponseStatus status);

    /**
     * The method is responsible for updating the current response sequence index for
     * a specific operation
     * @param soapOperationId The id of the operation that will be updated
     * @param currentResponseSequenceIndex The new current response sequence index
     * @see com.fortmocks.core.mock.soap.model.project.SoapOperation
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto
     */
    void updateCurrentResponseSequenceIndex(Long soapOperationId, Integer currentResponseSequenceIndex);



}
