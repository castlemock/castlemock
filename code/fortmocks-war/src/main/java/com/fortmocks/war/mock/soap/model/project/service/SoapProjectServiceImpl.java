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

import com.fortmocks.core.base.model.TypeIdentifier;
import com.fortmocks.core.base.model.project.dto.ProjectDto;
import com.fortmocks.core.mock.soap.model.project.*;
import com.fortmocks.core.mock.soap.model.project.dto.SoapMockResponseDto;
import com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto;
import com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto;
import com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.war.base.model.project.service.ProjectServiceImpl;
import com.fortmocks.war.mock.soap.model.SoapTypeIdentifier;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * The class is the project operation layer class responsible for communicating
 * with the project repository and managing projects.
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class SoapProjectServiceImpl extends ProjectServiceImpl<SoapProject, SoapProjectDto> implements SoapProjectService {

    private static final SoapTypeIdentifier SOAP_TYPE_IDENTIFIER = new SoapTypeIdentifier();

    /**
     * Finds a project by a given name
     * @param name The name of the project that should be retrieved
     * @return Returns a project with the provided name
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     */
    @Override
    public SoapProjectDto findSoapProject(final String name) {
        Preconditions.checkNotNull(name, "Project name cannot be null");
        Preconditions.checkArgument(!name.isEmpty(), "Project name cannot be empty");
        for(SoapProject soapProject : findAllTypes()){
            if(soapProject.getName().equalsIgnoreCase(name)) {
                return toDto(soapProject);
            }
        }
        return null;
    }

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
    @Override
    public SoapPortDto findSoapPort(final Long soapProjectId, final Long soapPortId) {
        final SoapPort soapPort = findSoapPortBySoapProjectIdAndSoapPortId(soapProjectId, soapPortId);
        return soapPort != null ? mapper.map(soapPort, SoapPortDto.class) : null;
    }

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
    @Override
    public SoapOperationDto findSoapOperation(final Long soapProjectId, final Long soapPortId, final Long soapOperationId) {
        final SoapOperation soapOperation = findSoapOperationBySoapProjectIdAndSoapPortIdAndSoapOperationId(soapProjectId, soapPortId, soapOperationId);
        return soapOperation != null ? mapper.map(soapOperation, SoapOperationDto.class) : null;
    }

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
    @Override
    public SoapMockResponseDto findSoapMockResponse(final Long soapProjectId, final Long soapPortId, final Long soapOperationId, final Long soapMockResponseId) {
        final SoapMockResponse soapMockResponse = findSoapMockResponseBySoapProjectIdAndSoapPortIdAndSoapOperationIdAndSoapMockResponseId(soapProjectId, soapPortId, soapOperationId, soapMockResponseId);
        return soapMockResponse != null ? mapper.map(soapMockResponse, SoapMockResponseDto.class) : null;
    }


    /**
     * The method finds a mock responses from an operation that has a specific response status
     * @param soapOperationId The id of the operation that the SOAP mock response has to belong to
     * @param status The status that the SOAP mock responses has to have
     * @return A list of {@link com.fortmocks.core.mock.soap.model.project.dto.SoapMockResponseDto} that match the provided
     * search criteria.
     * @see com.fortmocks.core.mock.soap.model.project.SoapOperation
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto
     */
    @Override
    public List<SoapMockResponseDto> findSoapMockResponses(final Long soapOperationId, final SoapMockResponseStatus status) {
        Preconditions.checkNotNull(status, "The mock response status cannot be null");
        final SoapOperation soapOperation = findSoapOperationBySoapOperationId(soapOperationId);

        if(soapOperation == null){
            throw new IllegalArgumentException("Unable to find a soap operation with id " + soapOperationId);
        }

        final List<SoapMockResponse> soapMockResponses = new ArrayList<SoapMockResponse>();
        for(SoapMockResponse soapMockResponse : soapOperation.getSoapMockResponses()){
            if(soapMockResponse.getSoapMockResponseStatus().equals(status)){
                soapMockResponses.add(soapMockResponse);
            }
        }

        return toDtoList(soapMockResponses, SoapMockResponseDto.class);
    }

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
    @Override
    public SoapOperationDto findSoapOperation(final Long soapProjectId, final String name, final String uri, final SoapOperationMethod soapOperationMethod, final SoapOperationType type) {
        final List<SoapOperation> soapOperations = findSoapOperationsBySoapProjectId(soapProjectId);
        for(SoapOperation soapOperation : soapOperations){
            if(soapOperation.getUri().equals(uri) && soapOperation.getSoapOperationMethod().equals(soapOperationMethod) && soapOperation.getSoapOperationType().equals(type) && soapOperation.getName().equalsIgnoreCase(name)){
                return mapper.map(soapOperation, SoapOperationDto.class);
            }
        }
        return null;
    }


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
    @Override
    public Map<SoapOperationStatus, Integer> getOperationStatusCount(final Long soapProjectId, final Long soapPortId){
        final SoapPort soapPort = findSoapPortBySoapProjectIdAndSoapPortId(soapProjectId, soapPortId);
        return getSoapOperationStatusCount(soapPort.getSoapOperations());
    }


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
    @Override
    public Map<SoapOperationStatus, Integer> getOperationStatusCount(SoapPortDto soapPortDto){
        return getSoapOperationDtoStatusCount(soapPortDto.getSoapOperations());
    }

    /**
     * Count the operation statuses
     * @param soapOperations The list of operations, which status will be counted
     * @return The result of the status count
     */
    private Map<SoapOperationStatus, Integer> getSoapOperationStatusCount(final List<SoapOperation> soapOperations){
        Preconditions.checkNotNull(soapOperations, "The operation list cannot be null");
        final Map<SoapOperationStatus, Integer> statuses = new HashMap<SoapOperationStatus, Integer>();

        for(SoapOperationStatus soapOperationStatus : SoapOperationStatus.values()){
            statuses.put(soapOperationStatus, 0);
        }
        for(SoapOperation soapOperation : soapOperations){
            SoapOperationStatus soapOperationStatus = soapOperation.getSoapOperationStatus();
            statuses.put(soapOperationStatus, statuses.get(soapOperationStatus)+1);
        }
        return statuses;
    }

    /**
     * Count the operation statuses
     * @param soapOperations The list of operations, which status will be counted
     * @return The result of the status count
     */
    private Map<SoapOperationStatus, Integer> getSoapOperationDtoStatusCount(final List<SoapOperationDto> soapOperations){
        Preconditions.checkNotNull(soapOperations, "The operation list cannot be null");
        final Map<SoapOperationStatus, Integer> statuses = new HashMap<SoapOperationStatus, Integer>();

        for(SoapOperationStatus soapOperationStatus : SoapOperationStatus.values()){
            statuses.put(soapOperationStatus, 0);
        }
        for(SoapOperationDto soapOperation : soapOperations){
            SoapOperationStatus soapOperationStatus = soapOperation.getSoapOperationStatus();
            statuses.put(soapOperationStatus, statuses.get(soapOperationStatus)+1);
        }
        return statuses;
    }



    // -------------------------------- Update operation statuses -------------------------------- //

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
    @Override
    public void updateStatus(final Long soapProjectId, final Long soapPortId, final SoapOperationStatus soapOperationStatus) {
        final List<SoapOperation> soapOperations = findSoapOperationsBySoapProjectIdAndSoapPortId(soapProjectId, soapPortId);
        for(SoapOperation soapOperation : soapOperations){
            soapOperation.setSoapOperationStatus(soapOperationStatus);
        }
        save(soapProjectId);
    }

    /**
     * The method provides the functionality to save a list of ports to a SOAP project. The project is identified
     * With the provided soapProjectId
     * @param soapProjectId The identifier for the SOAP project that the ports will be added to
     * @param soapPorts The ports that will be added and saved to the SOAP project
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto
     */
    @Override
    public void saveSoapPorts(final Long soapProjectId, final List<SoapPortDto> soapPorts) {
        final SoapProject soapProject = findOneType(soapProjectId);
        final List<SoapPort> soapPortTypes = toDtoList(soapPorts, SoapPort.class);
        generateId(soapPortTypes);
        soapProject.getSoapPorts().addAll(soapPortTypes);
        save(soapProjectId);
    }

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
    @Override
    public void updateStatus(final Long soapProjectId, final Long soapPortId, final Long soapOperationId, final SoapOperationStatus soapOperationStatus) {
        final SoapOperation soapOperation = findSoapOperationBySoapProjectIdAndSoapPortIdAndSoapOperationId(soapProjectId, soapPortId, soapOperationId);
        soapOperation.setSoapOperationStatus(soapOperationStatus);
        save(soapProjectId);
    }

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
    @Override
    public void deleteSoapPort(final Long soapProjectId, final Long soapPortId) {
        final SoapProject soapProject = findOneType(soapProjectId);
        final SoapPort soapPort = findSoapPortBySoapProjectIdAndSoapPortId(soapProjectId, soapPortId);
        soapProject.getSoapPorts().remove(soapPort);
        save(soapProjectId);
    }

    /**
     * Takes a list of ports and delete them from the database
     * @param soapProjectId The id of the project that the ports belong to
     * @param soapPorts The list of ports that will be deleted
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapPort
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto
     */
    @Override
    public void deleteSoapPorts(final Long soapProjectId, List<SoapPortDto> soapPorts) {
        for(final SoapPortDto soapPort : soapPorts){
            deleteSoapPort(soapProjectId, soapPort.getId());
        }
    }

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
    @Override
    public void updateStatus(final Long soapProjectId, final Long soapPortId, final Long soapOperationId, final Long soapMockResponseId, final SoapMockResponseStatus soapMockResponseStatus) {
        final SoapMockResponse soapMockResponse = findSoapMockResponseBySoapProjectIdAndSoapPortIdAndSoapOperationIdAndSoapMockResponseId(soapProjectId, soapPortId, soapOperationId, soapMockResponseId);
        soapMockResponse.setSoapMockResponseStatus(soapMockResponseStatus);
        save(soapProjectId);
    }

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
    @Override
    public void updateOperation(final Long soapProjectId, final Long soapPortId, final Long soapOperationId, final SoapOperationDto updatedSoapOperation) {
        final SoapOperation soapOperation = findSoapOperationBySoapProjectIdAndSoapPortIdAndSoapOperationId(soapProjectId, soapPortId, soapOperationId);
        soapOperation.setSoapOperationStatus(updatedSoapOperation.getSoapOperationStatus());
        soapOperation.setForwardedEndpoint(updatedSoapOperation.getForwardedEndpoint());
        soapOperation.setSoapResponseStrategy(updatedSoapOperation.getSoapResponseStrategy());
        save(soapProjectId);
    }

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
    @Override
    public void updateForwardedEndpoint(final Long soapProjectId, final Long soapPortId, final List<SoapOperationDto> soapOperations, final String forwardedEndpoint) {
        for(SoapOperationDto soapOperationDto : soapOperations){
            SoapOperation soapOperation = findSoapOperationBySoapProjectIdAndSoapPortIdAndSoapOperationId(soapProjectId, soapPortId, soapOperationDto.getId());
            soapOperation.setForwardedEndpoint(forwardedEndpoint);
        }
        save(soapProjectId);
    }

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
    @Override
    public void saveSoapMockResponse(final Long soapProjectId, final Long soapPortId, final Long soapOperationId, final SoapMockResponseDto soapMockResponseDto) {
        Preconditions.checkNotNull(soapMockResponseDto, "Mock response cannot be null");
        final SoapOperation soapOperation = findSoapOperationBySoapProjectIdAndSoapPortIdAndSoapOperationId(soapProjectId, soapPortId, soapOperationId);
        final SoapMockResponse soapMockResponse = mapper.map(soapMockResponseDto, SoapMockResponse.class);
        final Long soapMockResponseId = getNextSoapMockResponseId();
        soapMockResponse.setId(soapMockResponseId);
        soapOperation.getSoapMockResponses().add(soapMockResponse);
        save(soapProjectId);
    }

    /**
     * The method provides the functionality to save a SOAP mocked response to a SOAP operation. The SOAP operation is
     * identified with operation id
     * @param soapOperationId The id of the operation that the mock SOAP response will be added to
     * @param soapMockResponseDto The mock response that will be added to the operation
     * @see com.fortmocks.core.mock.soap.model.project.SoapOperation
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto
     */
    @Override
    public void saveSoapMockResponse(final Long soapOperationId, final SoapMockResponseDto soapMockResponseDto) {
        final SoapOperation soapOperation = findSoapOperationBySoapOperationId(soapOperationId);
        final Long soapProjectId = findSoapProjectIdForSoapOperation(soapOperationId);
        final SoapMockResponse soapMockResponse = mapper.map(soapMockResponseDto, SoapMockResponse.class);
        soapOperation.getSoapMockResponses().add(soapMockResponse);
        save(soapProjectId);
    }

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
    @Override
    public void deleteSoapMockResponse(final Long soapProjectId, final Long soapPortId, final Long soapOperationId, final Long soapMockResponseId) {
        Preconditions.checkNotNull(soapMockResponseId, "Mock response cannot be null");
        final SoapOperation soapOperation = findSoapOperationBySoapProjectIdAndSoapPortIdAndSoapOperationId(soapProjectId, soapPortId, soapOperationId);
        SoapMockResponse foundSoapMockResponse = null;
        for(SoapMockResponse soapMockResponse : soapOperation.getSoapMockResponses()){
            if(soapMockResponse.getId().equals(soapMockResponseId)){
                foundSoapMockResponse = soapMockResponse;
                break;
            }
        }

        if(foundSoapMockResponse == null){
            // throw exception
        }

        soapOperation.getSoapMockResponses().remove(foundSoapMockResponse);
        save(soapProjectId);
    }

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
    @Override
    public void deleteSoapMockResponses(final Long soapProjectId, final Long soapPortId, final Long soapOperationId, final List<SoapMockResponseDto> mockResponses) {
        Preconditions.checkNotNull(mockResponses, "Mock responses cannot be null");
        final SoapOperation soapOperation = findSoapOperationBySoapProjectIdAndSoapPortIdAndSoapOperationId(soapProjectId, soapPortId, soapOperationId);
        for(SoapMockResponseDto soapMockResponseDto : mockResponses){
            final SoapMockResponse soapMockResponse = new SoapMockResponse();
            soapMockResponse.setId(soapMockResponseDto.getId());
            soapOperation.getSoapMockResponses().remove(soapMockResponse);
        }

        save(soapProjectId);
    }

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
    @Override
    public void updateSoapMockResponse(final Long soapProjectId, final Long soapPortId, final Long soapOperationId, final Long soapMockResponseId, final SoapMockResponseDto soapMockResponseDto) {
        Preconditions.checkNotNull(soapMockResponseDto, "Mock response cannot be null");
        final SoapMockResponse soapMockResponse = findSoapMockResponseBySoapProjectIdAndSoapPortIdAndSoapOperationIdAndSoapMockResponseId(soapProjectId, soapPortId, soapOperationId, soapMockResponseId);
        soapMockResponse.setBody(soapMockResponseDto.getBody());
        save(soapProjectId);
    }


    /**
     * The method provides the functionality to update the forward address for all operations for the provided ports
     * @param soapProjectId The id of the project that the ports belongs to
     * @param soapPorts The list of port that will be assigned the new forward address
     * @param forwardedEndpoint The updated forward endpoint address
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto
     */
    @Override
    public void updateForwardedEndpoint(final Long soapProjectId, final List<SoapPortDto> soapPorts, final String forwardedEndpoint){
        for(SoapPortDto soapPortDto : soapPorts){
            SoapPort soapPort = findSoapPortBySoapProjectIdAndSoapPortId(soapProjectId, soapPortDto.getId());
            for(SoapOperation soapOperation : soapPort.getSoapOperations()){
                soapOperation.setForwardedEndpoint(forwardedEndpoint);
            }
        }
        save(soapProjectId);
    }


    /**
     * The method is responsible for updating the current response sequence index for
     * a specific operation
     * @param soapOperationId The id of the operation that will be updated
     * @param currentResponseSequenceIndex The new current response sequence index
     * @see com.fortmocks.core.mock.soap.model.project.SoapOperation
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto
     */
    @Override
    public void updateCurrentResponseSequenceIndex(final Long soapOperationId, final Integer currentResponseSequenceIndex){
        final SoapOperation soapOperation = findSoapOperationBySoapOperationId(soapOperationId);
        final Long soapProjectId = findSoapProjectIdForSoapOperation(soapOperationId);
        soapOperation.setCurrentResponseSequenceIndex(currentResponseSequenceIndex);
        save(soapProjectId);
    }



    /**
     * Deletes a list of projects
     * @param soapProjects The list of projects that will be deleted
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     */
    @Override
    public void delete(final List<SoapProjectDto> soapProjects) {
        Preconditions.checkNotNull(soapProjects, "The list of products cannot be null");
        for (SoapProjectDto soapProject : soapProjects){
            delete(soapProject.getId());
        }
    }

    /**
     * The save method saves a project to the database
     * @param project Project that will be saved to the database
     * @return The saved project
     */
    @Override
    public SoapProjectDto save(final SoapProjectDto project){
        Preconditions.checkNotNull(project, "Project cannot be null");
        Preconditions.checkArgument(!project.getName().isEmpty(), "Invalid project name. Project name cannot be empty");
        final SoapProjectDto projectInDatebase = findSoapProject(project.getName());
        Preconditions.checkArgument(projectInDatebase == null, "Project name is already taken");
        project.setUpdated(new Date());
        project.setCreated(new Date());

        return super.save(project);
    }

    /**
     * Updates a project with new information
     * @param soapProjectId The id of the project that will be updated
     * @param updatedProject The updated version of the project
     * @return The updated version project
     */
    @Override
    public SoapProjectDto update(final Long soapProjectId, final SoapProjectDto updatedProject){
        Preconditions.checkNotNull(soapProjectId, "Project id be null");
        Preconditions.checkArgument(soapProjectId >= 0, "Project id cannot be negative");
        Preconditions.checkNotNull(updatedProject, "Project cannot be null");
        Preconditions.checkArgument(!updatedProject.getName().isEmpty(), "Invalid project name. Project name cannot be empty");
        final SoapProjectDto projectWithNameDto = findSoapProject(updatedProject.getName());
        Preconditions.checkArgument(projectWithNameDto == null || projectWithNameDto.getId().equals(soapProjectId), "Project name is already taken");
        final SoapProjectDto project = findOne(soapProjectId);
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        return super.save(project);
    }




    /**
     * The method find an port with project id and port id
     * @param soapProjectId The id of the project which the port belongs to
     * @param soapPortId The id of the port that will be retrieved
     * @return Returns an port that matches the search criteria. Returns null if no port matches.
     * @throws java.lang.IllegalArgumentException IllegalArgumentException will be thrown jf no matching SOAP port was found
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapPort
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto
     */
    private SoapPort findSoapPortBySoapProjectIdAndSoapPortId(final Long soapProjectId, final Long soapPortId) {
        Preconditions.checkNotNull(soapProjectId, "Project id cannot be null");
        Preconditions.checkNotNull(soapPortId, "Port id cannot be null");
        final SoapProject soapProject = findOneType(soapProjectId);
        for(SoapPort soapPort : soapProject.getSoapPorts()){
            if(soapPort.getId().equals(soapPortId)){
                return soapPort;
            }
        }
        throw new IllegalArgumentException("Unable to find a soap port with id " + soapPortId);
    }

    /**
     * The method finds a operation that matching the search criteria and returns the result
     * @param soapProjectId The id of the project which the operation belongs to
     * @param soapPortId The id of the port which the operation belongs to
     * @param soapOperationId The id of the operation that will be retrieved
     * @return Returns an operation that matches the search criteria. Returns null if no operation matches.
     * @throws java.lang.IllegalArgumentException IllegalArgumentException will be thrown jf no matching SOAP operation was found
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapPort
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapOperation
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto
     */
    private SoapOperation findSoapOperationBySoapProjectIdAndSoapPortIdAndSoapOperationId(final Long soapProjectId, final Long soapPortId, final Long soapOperationId){
        Preconditions.checkNotNull(soapOperationId, "Operation id cannot be null");
        final SoapPort soapPort = findSoapPortBySoapProjectIdAndSoapPortId(soapProjectId, soapPortId);
        for(SoapOperation soapOperation : soapPort.getSoapOperations()){
            if(soapOperation.getId().equals(soapOperationId)){
                return soapOperation;
            }
        }
        throw new IllegalArgumentException("Unable to find a soap operation with id " + soapOperationId);
    }

    /**
     * Retrieve a list of operations with a specific project id and port id
     * @param soapProjectId The id of the project that the operations belongs to
     * @param soapPortId The id of the port that the operations belongs to
     * @return A list of operations that matches the search criteria.
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto
     * @see com.fortmocks.core.mock.soap.model.project.SoapPort
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto
     */
    private List<SoapOperation> findSoapOperationsBySoapProjectIdAndSoapPortId(final Long soapProjectId, final Long soapPortId) {
        final SoapPort soapPort = findSoapPortBySoapProjectIdAndSoapPortId(soapProjectId, soapPortId);
        return soapPort != null ? soapPort.getSoapOperations() : null;
    }

    /**
     * Finds a operation with the provided operation id
     * @param soapOperationId The id of the operation that should be retrieved
     * @return A operation with the provided id. Null will be returned if no operation has the matching value
     * @throws java.lang.IllegalArgumentException IllegalArgumentException will be thrown jf no matching SOAP operation was found
     * @see com.fortmocks.core.mock.soap.model.project.SoapOperation
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto
     */
    private SoapOperation findSoapOperationBySoapOperationId(final Long soapOperationId) {
        Preconditions.checkNotNull(soapOperationId, "Operation id cannot be null");
        for(SoapProject soapProject : findAllTypes()){
            for(SoapPort soapPort : soapProject.getSoapPorts()){
                for(SoapOperation soapOperation : soapPort.getSoapOperations()){
                    if(soapOperation.getId().equals(soapOperationId)){
                        return soapOperation;
                    }
                }
            }
        }
        throw new IllegalArgumentException("Unable to find a soap operation with id " + soapOperationId);
    }

    /**
     * The method provides the functionality to find a SOAP operation with a specific id
     * @param soapOperationId The identifier for the SOAP operation
     * @return A SOAP operation with a matching identifier
     * @throws java.lang.IllegalArgumentException IllegalArgumentException will be thrown jf no matching SOAP operation was found
     * @see com.fortmocks.core.mock.soap.model.project.SoapOperation
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto
     */
    private Long findSoapProjectIdForSoapOperation(final Long soapOperationId) {
        Preconditions.checkNotNull(soapOperationId, "Operation id cannot be null");
        for(SoapProject soapProject : findAllTypes()){
            for(SoapPort soapPort : soapProject.getSoapPorts()){
                for(SoapOperation soapOperation : soapPort.getSoapOperations()){
                    if(soapOperation.getId().equals(soapOperationId)){
                        return soapProject.getId();
                    }
                }
            }
        }
        throw new IllegalArgumentException("Unable to find an operation with id " + soapOperationId);
    }

    /**
     * Retrieve a list of operations with a specific project id and port id
     * @param soapProjectId The id of the project that the operations belongs to
     * @return A list of operations that matches the search criteria.
     * @see com.fortmocks.core.mock.soap.model.project.SoapProject
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto
     */
    private List<SoapOperation> findSoapOperationsBySoapProjectId(final Long soapProjectId) {
        final SoapProject soapProject = findOneType(soapProjectId);

        if(soapProject == null){
            throw new IllegalArgumentException("Unable to find a project with id " + soapProjectId);
        }

        final List<SoapOperation> soapOperations = new ArrayList<SoapOperation>();
        for(SoapPort soapPort : soapProject.getSoapPorts()){
            soapOperations.addAll(soapPort.getSoapOperations());
        }
        return soapOperations;
    }


    /**
     * The method provides the functionality to retrieve a mocked response with project id, port id,
     * operation id and mock response id
     * @param soapProjectId The id of the project that the mocked response belongs to
     * @param soapPortId The id of the port that the mocked response belongs to
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
    private SoapMockResponse findSoapMockResponseBySoapProjectIdAndSoapPortIdAndSoapOperationIdAndSoapMockResponseId(final Long soapProjectId, final Long soapPortId, final Long soapOperationId, final Long soapMockResponseId) {
        final SoapOperation soapOperation = findSoapOperationBySoapProjectIdAndSoapPortIdAndSoapOperationId(soapProjectId, soapPortId, soapOperationId);
        for(SoapMockResponse soapMockResponse : soapOperation.getSoapMockResponses()){
            if(soapMockResponse.getId().equals(soapMockResponseId)){
                return soapMockResponse;
            }
        }
        throw new IllegalArgumentException("Unable to find a soap mock response with id " + soapMockResponseId);
    }

    /**
     * The method calculates the next SOAP port id
     * @return The new generated SOAP port id
     * @see com.fortmocks.core.mock.soap.model.project.SoapPort
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto
     */
    private Long getNextSoapPortId(){
        Long nextSoapPortId = 0L;
        for(SoapProject soapProject : findAllTypes()){
            for(SoapPort soapPort : soapProject.getSoapPorts()){
                if(soapPort.getId() >= nextSoapPortId){
                    nextSoapPortId = soapPort.getId();
                    nextSoapPortId++;
                }
            }
        }
        return nextSoapPortId;
    }

    /**
     * The method calculates the next SOAP operation id
     * @return The new generated SOAP operation id
     * @see com.fortmocks.core.mock.soap.model.project.SoapOperation
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto
     */
    private Long getNexSoapOperationId(){
        Long nextSoapOperationId = 0L;
        for(SoapProject soapProject : findAllTypes()){
            for(SoapPort soapPort : soapProject.getSoapPorts()){
                for(SoapOperation soapOperation : soapPort.getSoapOperations()){
                    if(soapOperation.getId() >= nextSoapOperationId){
                        nextSoapOperationId = soapPort.getId();
                        nextSoapOperationId++;
                    }
                }
            }
        }
        return nextSoapOperationId;
    }

    /**
     * The method calculates the next SOAP mock response id
     * @return The new generated SOAP mock response id
     * @see com.fortmocks.core.mock.soap.model.project.SoapMockResponse
     * @see com.fortmocks.core.mock.soap.model.project.dto.SoapMockResponseDto
     */
    private Long getNextSoapMockResponseId(){
        Long nextSoapMockResponseId = 0L;
        for(SoapProject soapProject : findAllTypes()){
            for(SoapPort soapPort : soapProject.getSoapPorts()){
                for(SoapOperation soapOperation : soapPort.getSoapOperations()){
                    for(SoapMockResponse soapMockResponse : soapOperation.getSoapMockResponses()){
                        if(soapMockResponse.getId() >= nextSoapMockResponseId){
                            nextSoapMockResponseId = soapPort.getId();
                            nextSoapMockResponseId++;
                        }
                    }
                }
            }
        }
        return nextSoapMockResponseId;
    }

    /**
     * The method provides the functionality to generate new identifiers for a whole list of SOAP ports
     * @param soapPorts The list of SOAP ports that will receive new identifiers
     */
    private void generateId(final List<SoapPort> soapPorts){
        Long nextSoapPortId = getNextSoapPortId();
        Long nextSoapOperationId = getNexSoapOperationId();
        Long nextSoapMockResponseId = getNextSoapMockResponseId();

        for(SoapPort soapPort : soapPorts){
            if(soapPort.getId() == null){
                soapPort.setId(nextSoapPortId++);
            }

            for(SoapOperation soapOperation : soapPort.getSoapOperations()){
                if(soapOperation.getId() == null){
                    soapOperation.setId(nextSoapOperationId++);
                }

                for(SoapMockResponse soapMockResponse : soapOperation.getSoapMockResponses()){
                    if(soapMockResponse.getId() == null){
                        soapMockResponse.setId(nextSoapMockResponseId++);
                    }
                }
            }
        }
    }

    /**
     * Returns the SOAP type identifier.
     * @return The SOAP identifier
     */
    @Override
    public TypeIdentifier getTypeIdentifier() {
        return SOAP_TYPE_IDENTIFIER;
    }

    /**
     * The method is responsible for converting a project dto instance into a project dto subclass.
     * This is used when the {@link com.fortmocks.war.base.model.project.service.ProjectServiceFacadeImpl} needs
     * to manage base project class, but wants to be able to convert it into a specific subclass, for example when
     * creating or updating a project instance.
     * @param projectDto The project dto instance that will be converted into a project dto subclass
     * @return The converted project dto subclass
     * @throws java.lang.NullPointerException Throws NullPointerException in case if the provided project dto instance is null.
     * @see com.fortmocks.war.base.model.project.service.ProjectServiceFacade
     */
    @Override
    public SoapProjectDto convertType(ProjectDto projectDto) {
        Preconditions.checkNotNull(projectDto, "Project cannot be null");
        return new SoapProjectDto(projectDto);
    }
}
