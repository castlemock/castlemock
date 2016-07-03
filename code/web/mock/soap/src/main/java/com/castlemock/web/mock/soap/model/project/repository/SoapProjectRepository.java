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

package com.castlemock.web.mock.soap.model.project.repository;

import com.castlemock.core.basis.model.Repository;
import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponse;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.core.mock.soap.model.project.dto.SoapMockResponseDto;
import com.castlemock.core.mock.soap.model.project.dto.SoapOperationDto;
import com.castlemock.core.mock.soap.model.project.dto.SoapPortDto;
import com.castlemock.core.mock.soap.model.project.dto.SoapProjectDto;

/**
 * The soap project file repository provides the functionality to interact with the file system.
 * The repository is responsible for loading and soap project to the file system. Each
 * soap project is stored as a separate file.
 * @author Karl Dahlgren
 * @since 1.0
 * @see SoapProject
 * @see Repository
 */
public interface SoapProjectRepository extends Repository<SoapProject, SoapProjectDto, String> {


    /*
     * FIND OPERATIONS
     */

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
    SoapPortDto findSoapPort(String soapProjectId, String soapPortId);

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
    SoapOperationDto findSoapOperation(String soapProjectId, String soapPortId, String soapOperationId);



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
    SoapMockResponseDto findSoapMockResponse(String soapProjectId, String soapPortId, String soapOperationId, String soapMockResponseId);


    /**
     * Finds a project by a given name
     * @param name The name of the project that should be retrieved
     * @return Returns a project with the provided name
     * @see SoapProject
     */
    SoapProjectDto findSoapProjectWithName(String name);

    /**
     * The method provides the functionality to find a SOAP operation with a specific name
     * @param soapProjectId The id of the {@link SoapProject}
     * @param soapPortId The id of the {@link SoapPort}
     * @param soapOperationName The name of the SOAP operation that should be retrieved
     * @return A SOAP operation that matches the search criteria. If no SOAP operation matches the provided
     * name then null will be returned.
     */
    SoapOperationDto findSoapOperationWithName(String soapProjectId, String soapPortId, String soapOperationName);

    /**
     * The method finds a {@link SoapPortDto} with the provided name
     * @param soapProjectId The id of the {@link SoapProject}
     * @param soapPortName The name of the {@link SoapPort}
     * @return A {@link SoapPort} that matches the provided search criteria.
     */
    SoapPortDto findSoapPortWithName(String soapProjectId, String soapPortName);

    /*
     * SAVE OPERATIONS
     */

    /**
     * The method adds a new {@link SoapPort} to a {@link SoapProject} and then saves
     * the {@link SoapProject} to the file system.
     * @param soapProjectId The id of the {@link SoapProject}
     * @param soapPortDto The dto instance of {@link SoapPort} that will be added to the {@link SoapProject}
     * @return The saved {@link SoapPortDto}
     * @see SoapProject
     * @see SoapProjectDto
     * @see SoapPort
     * @see SoapPortDto
     */
    SoapPortDto saveSoapPort(String soapProjectId, SoapPortDto soapPortDto);

    /**
     * The method adds a new {@link SoapOperation} to a {@link SoapPort} and then saves
     * the {@link SoapProject} to the file system.
     * @param soapProjectId The id of the {@link SoapProject}
     * @param soapPortId The id of the {@link SoapPort}
     * @param soapOperationDto The dto instance of {@link SoapOperation} that will be added to the {@link SoapPort}
     * @return The saved {@link SoapOperationDto}
     * @see SoapProject
     * @see SoapProjectDto
     * @see SoapPort
     * @see SoapPortDto
     * @see SoapOperation
     * @see SoapOperationDto
     */
    SoapOperationDto saveSoapOperation(String soapProjectId, String soapPortId, SoapOperationDto soapOperationDto);


    /**
     * The method adds a new {@link SoapMockResponse} to a {@link SoapOperation} and then saves
     * the {@link SoapProject} to the file system.
     * @param soapProjectId The id of the {@link SoapProject}
     * @param soapPortId The id of the {@link SoapPort}
     * @param soapOperationId The id of the {@link SoapOperation}
     * @param soapMockResponseDto The dto instance of {@link SoapMockResponse} that will be added to the {@link SoapOperation}
     * @return The saved {@link SoapMockResponseDto}
     * @see SoapProject
     * @see SoapProjectDto
     * @see SoapPort
     * @see SoapPortDto
     * @see SoapOperation
     * @see SoapOperationDto
     * @see SoapMockResponse
     * @see SoapMockResponseDto
     */
    SoapMockResponseDto saveSoapMockResponse(String soapProjectId, String soapPortId, String soapOperationId, SoapMockResponseDto soapMockResponseDto);

    /*
     * UPDATE OPERATIONS
     */

    /**
     * The method updates an already existing {@link SoapPort}
     * @param soapProjectId The id of the {@link SoapProject}
     * @param soapPortId The id of the {@link SoapPort} that will be updated
     * @param soapPortDto The updated {@link SoapPortDto}
     * @return The updated version of the {@link SoapPortDto}
     * @see SoapProject
     * @see SoapProjectDto
     * @see SoapPort
     * @see SoapPortDto
     */
    SoapPortDto updateSoapPort(String soapProjectId, String soapPortId, SoapPortDto soapPortDto);

    /**
     * The method updates an already existing {@link SoapOperation}
     * @param soapProjectId The id of the {@link SoapProject}
     * @param soapPortId The id of the {@link SoapPort}
     * @param soapOperationId The id of the {@link SoapOperation} that will be updated
     * @param soapOperationDto The updated {@link SoapOperationDto}
     * @return The updated version of the {@link SoapOperationDto}
     * @see SoapProject
     * @see SoapProjectDto
     * @see SoapPort
     * @see SoapPortDto
     * @see SoapOperation
     * @see SoapOperationDto
     */
    SoapOperationDto updateSoapOperation(String soapProjectId, String soapPortId, String soapOperationId, SoapOperationDto soapOperationDto);


    /**
     * The method updates an already existing {@link SoapOperation}
     * @param soapProjectId The id of the {@link SoapProject}
     * @param soapPortId The id of the {@link SoapPort}
     * @param soapOperationId The id of the {@link SoapOperation}
     * @param soapMockResponseId The id of the {@link SoapMockResponse} that will be updated
     * @param soapMockResponseDto The updated {@link SoapMockResponseDto)
     * @return The updated version of the {@link SoapMockResponseDto}
     * @see SoapProject
     * @see SoapProjectDto
     * @see SoapPort
     * @see SoapPortDto
     * @see SoapOperation
     * @see SoapOperationDto
     * @see SoapMockResponse
     * @see SoapMockResponseDto
     */
    SoapMockResponseDto updateSoapMockResponse(String soapProjectId, String soapPortId, String soapOperationId, String soapMockResponseId, SoapMockResponseDto soapMockResponseDto);

    /*
     * DELETE OPERATIONS
     */

    /**
     * The method deletes a {@link SoapPort}
     * @param soapProjectId The id of the {@link SoapProject}
     * @param soapPortId The id of the {@link SoapPort}
     * @return The deleted {@link SoapPortDto}
     * @see SoapProject
     * @see SoapProjectDto
     * @see SoapPort
     * @see SoapPortDto
     */
    SoapPortDto deleteSoapPort(String soapProjectId, String soapPortId);

    /**
     * The method deletes a {@link SoapOperation}
     * @param soapProjectId The id of the {@link SoapProject}
     * @param soapPortId The id of the {@link SoapPort}
     * @param soapOperationId The id of the {@link SoapOperation}
     * @return The deleted {@link SoapOperationDto}
     * @see SoapProject
     * @see SoapProjectDto
     * @see SoapPort
     * @see SoapPortDto
     * @see SoapOperation
     * @see SoapOperationDto
     */
    SoapOperationDto deleteSoapOperation(String soapProjectId, String soapPortId, String soapOperationId);


    /**
     * The method deletes a {@link SoapMockResponse}
     * @param soapProjectId The id of the {@link SoapProject}
     * @param soapPortId The id of the {@link SoapPort}
     * @param soapOperationId The id of the {@link SoapOperation}
     * @param soapMockResponseId The id of the {@link SoapMockResponse}
     * @return The deleted {@link SoapMockResponseDto}
     * @see SoapProject
     * @see SoapProjectDto
     * @see SoapPort
     * @see SoapPortDto
     * @see SoapOperation
     * @see SoapOperationDto
     * @see SoapMockResponse
     * @see SoapMockResponseDto
     */
    SoapMockResponseDto deleteSoapMockResponse(String soapProjectId, String soapPortId, String soapOperationId, String soapMockResponseId);

}
