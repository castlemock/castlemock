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
import com.castlemock.core.mock.soap.model.project.domain.*;
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
     *
     * @param soapProjectId
     * @param soapPortDto
     * @return
     * @see SoapProject
     * @see SoapProjectDto
     * @see SoapPort
     * @see SoapPortDto
     */
    SoapPortDto saveSoapPort(String soapProjectId, SoapPortDto soapPortDto);

    /**
     *
     * @param soapProjectId
     * @param soapPortId
     * @param soapOperationDto
     * @return
     * @see SoapProject
     * @see SoapProjectDto
     * @see SoapPort
     * @see SoapPortDto
     * @see SoapOperation
     * @see SoapOperationDto
     */
    SoapOperationDto saveSoapOperation(String soapProjectId, String soapPortId, SoapOperationDto soapOperationDto);


    /**
     *
     * @param soapProjectId
     * @param soapPortId
     * @param soapOperationId
     * @param soapMockResponseDto
     * @return
     */
    SoapMockResponseDto saveSoapMockResponse(String soapProjectId, String soapPortId, String soapOperationId, SoapMockResponseDto soapMockResponseDto);

    /*
     * UPDATE OPERATIONS
     */

    /**
     *
     * @param soapProjectId
     * @parma soapPortId
     * @param soapPortDto
     * @return
     * @see SoapProject
     * @see SoapProjectDto
     * @see SoapPort
     * @see SoapPortDto
     */
    SoapPortDto updateSoapPort(String soapProjectId, String soapPortId, SoapPortDto soapPortDto);

    /**
     *
     * @param soapProjectId
     * @param soapPortId
     * @param soapOperationId
     * @param soapOperationDto
     * @return
     * @see SoapProject
     * @see SoapProjectDto
     * @see SoapPort
     * @see SoapPortDto
     * @see SoapOperation
     * @see SoapOperationDto
     */
    SoapOperationDto updateSoapOperation(String soapProjectId, String soapPortId, String soapOperationId, SoapOperationDto soapOperationDto);


    /**
     *
     * @param soapProjectId
     * @param soapPortId
     * @param soapOperationId
     * @param soapMockResponseId
     * @param soapMockResponseDto
     * @return
     */
    SoapMockResponseDto updateSoapMockResponse(String soapProjectId, String soapPortId, String soapOperationId, String soapMockResponseId, SoapMockResponseDto soapMockResponseDto);

    /*
     * DELETE OPERATIONS
     */

    /**
     *
     * @param soapProjectId
     * @param soapPortId
     * @return
     * @see SoapProject
     * @see SoapProjectDto
     * @see SoapPort
     * @see SoapPortDto
     */
    SoapPortDto deleteSoapPort(String soapProjectId, String soapPortId);

    /**
     *
     * @param soapProjectId
     * @param soapPortId
     * @param soapOperationId
     * @return
     * @see SoapProject
     * @see SoapProjectDto
     * @see SoapPort
     * @see SoapPortDto
     * @see SoapOperation
     * @see SoapOperationDto
     */
    SoapOperationDto deleteSoapOperation(String soapProjectId, String soapPortId, String soapOperationId);


    /**
     *
     * @param soapProjectId
     * @param soapPortId
     * @param soapOperationId
     * @param soapMockResponseId
     * @return
     * @see SoapProject
     * @see SoapProjectDto
     * @see SoapPort
     * @see SoapPortDto
     * @see SoapOperation
     * @see SoapOperationDto
     */
    SoapMockResponseDto deleteSoapMockResponse(String soapProjectId, String soapPortId, String soapOperationId, String soapMockResponseId);

}
