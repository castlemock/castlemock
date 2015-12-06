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

package com.fortmocks.web.mock.soap.model.project.service;

import com.fortmocks.core.mock.soap.model.project.domain.*;
import com.fortmocks.core.mock.soap.model.project.dto.SoapMockResponseDto;
import com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto;
import com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto;
import com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.web.basis.model.AbstractService;
import com.google.common.base.Preconditions;

import java.util.*;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public abstract class AbstractSoapProjectService extends AbstractService<SoapProject, SoapProjectDto, Long> {

    /**
     * Count the operation statuses
     * @param soapOperations The list of operations, which status will be counted
     * @return The result of the status count
     */
    protected Map<SoapOperationStatus, Integer> getSoapOperationStatusCount(final List<SoapOperation> soapOperations){
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
     * Finds a project by a given name
     * @param name The name of the project that should be retrieved
     * @return Returns a project with the provided name
     * @see SoapProject
     */
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
        final SoapProjectDto project = find(soapProjectId);
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        return super.save(project);
    }




    /**
     * The method find an port with project id and port id
     * @param soapProjectId The id of the project which the port belongs to
     * @param soapPortId The id of the port that will be retrieved
     * @return Returns an port that matches the search criteria. Returns null if no port matches.
     * @throws IllegalArgumentException IllegalArgumentException will be thrown jf no matching SOAP port was found
     * @see SoapProject
     * @see SoapProjectDto
     * @see SoapPort
     * @see SoapPortDto
     */
    protected SoapPort findSoapPortType(final Long soapProjectId, final Long soapPortId) {
        Preconditions.checkNotNull(soapProjectId, "Project id cannot be null");
        Preconditions.checkNotNull(soapPortId, "Port id cannot be null");
        final SoapProject soapProject = findType(soapProjectId);
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
     * @throws IllegalArgumentException IllegalArgumentException will be thrown jf no matching SOAP operation was found
     * @see SoapProject
     * @see SoapProjectDto
     * @see SoapPort
     * @see SoapPortDto
     * @see SoapOperation
     * @see SoapOperationDto
     */
    protected SoapOperation findSoapOperationType(final Long soapProjectId, final Long soapPortId, final Long soapOperationId){
        Preconditions.checkNotNull(soapOperationId, "Operation id cannot be null");
        final SoapPort soapPort = findSoapPortType(soapProjectId, soapPortId);
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
     * @see SoapProject
     * @see SoapProjectDto
     * @see SoapPort
     * @see SoapPortDto
     */
    protected List<SoapOperation> findSoapOperationType(final Long soapProjectId, final Long soapPortId) {
        final SoapPort soapPort = findSoapPortType(soapProjectId, soapPortId);
        return soapPort != null ? soapPort.getSoapOperations() : null;
    }

    /**
     * Finds a operation with the provided operation id
     * @param soapOperationId The id of the operation that should be retrieved
     * @return A operation with the provided id. Null will be returned if no operation has the matching value
     * @throws IllegalArgumentException IllegalArgumentException will be thrown jf no matching SOAP operation was found
     * @see SoapOperation
     * @see SoapOperationDto
     */
    protected SoapOperation findSoapOperationType(final Long soapOperationId) {
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
     * @throws IllegalArgumentException IllegalArgumentException will be thrown jf no matching SOAP operation was found
     * @see SoapOperation
     * @see SoapOperationDto
     */
    protected Long findSoapProjectType(final Long soapOperationId) {
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
     * @see SoapProject
     * @see SoapProjectDto
     */
    protected List<SoapOperation> findSoapOperationTypeWithSoapProjectId(final Long soapProjectId) {
        final SoapProject soapProject = findType(soapProjectId);

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
     * @see SoapProject
     * @see SoapProjectDto
     * @see SoapPort
     * @see SoapPortDto
     * @see SoapOperation
     * @see SoapOperationDto
     * @see SoapMockResponse
     * @see SoapMockResponseDto
     */
    protected SoapMockResponse findSoapMockResponseType(final Long soapProjectId, final Long soapPortId, final Long soapOperationId, final Long soapMockResponseId) {
        final SoapOperation soapOperation = findSoapOperationType(soapProjectId, soapPortId, soapOperationId);
        for(SoapMockResponse soapMockResponse : soapOperation.getSoapMockResponses()){
            if(soapMockResponse.getId().equals(soapMockResponseId)){
                return soapMockResponse;
            }
        }
        throw new IllegalArgumentException("Unable to find a soap mock response with id " + soapMockResponseId);
    }

    /**
     * The method provides the functionality to find a SOAP port with a specific name
     * @param soapProject The SOAP project that will be searched for the SOAP port
     * @param soapPortName The name of the SOAP port that should be retrieved
     * @return A SOAP port that matches the search criteria. If no SOAP ports matches the provided
     * name then null will be returned.
     */
    protected SoapPort findSoapPortWithName(final SoapProject soapProject, final String soapPortName){
        for(SoapPort soapPort : soapProject.getSoapPorts()){
            if(soapPort.getName().equals(soapPortName)){
                return soapPort;
            }
        }
        return null;
    }

    /**
     * The method provides the functionality to find a SOAP operation with a specific name
     * @param soapPort The SOAP port that will be searched for the SOAP operation
     * @param soapOperationName The name of the SOAP operation that should be retrieved
     * @return A SOAP operation that matches the search criteria. If no SOAP operation matches the provided
     * name then null will be returned.
     */
    protected SoapOperation findSoapOperationWithName(final SoapPort soapPort, final String soapOperationName){
        for(SoapOperation soapOperation : soapPort.getSoapOperations()){
            if(soapOperation.getName().equals(soapOperationName)){
                return soapOperation;
            }
        }
        return null;
    }

}
