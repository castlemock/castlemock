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

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.SearchValidator;
import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponse;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.core.mock.soap.model.project.dto.SoapMockResponseDto;
import com.castlemock.core.mock.soap.model.project.dto.SoapOperationDto;
import com.castlemock.core.mock.soap.model.project.dto.SoapPortDto;
import com.castlemock.core.mock.soap.model.project.dto.SoapProjectDto;
import com.castlemock.web.basis.model.RepositoryImpl;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The class is an implementation of the file repository and provides the functionality to interact with the file system.
 * The repository is responsible for loading and saving soap project from the file system. Each soap project is stored as
 * a separate file. The class also contains the directory and the filename extension for the soap project.
 * @author Karl Dahlgren
 * @since 1.0
 * @see SoapProjectRepository
 * @see RepositoryImpl
 * @see SoapProject
 */
@Repository
public class SoapProjectRepositoryImpl extends RepositoryImpl<SoapProject, SoapProjectDto, String> implements SoapProjectRepository {

    private static final String SLASH = "/";
    private static final String SOAP = "soap";
    private static final String PROJECT = "project";
    private static final String PORT = "port";
    private static final String OPERATION = "operation";
    private static final String RESPONSE = "response";
    private static final String COMMA = ", ";
    private static final String SOAP_TYPE = "SOAP";

    @Autowired
    private MessageSource messageSource;
    @Value(value = "${soap.project.file.directory}")
    private String soapProjectFileDirectory;
    @Value(value = "${soap.project.file.extension}")
    private String soapProjectFileExtension;

    /**
     * The method returns the directory for the specific file repository. The directory will be used to indicate
     * where files should be saved and loaded from.
     * @return The file directory where the files for the specific file repository could be saved and loaded from.
     */
    @Override
    protected String getFileDirectory() {
        return soapProjectFileDirectory;
    }

    /**
     * The method returns the postfix for the file that the file repository is responsible for managing.
     * @return The file extension for the file type that the repository is responsible for managing .
     */
    @Override
    protected String getFileExtension() {
        return soapProjectFileExtension;
    }

    /**
     * The post initialize method can be used to run functionality for a specific service. The method is called when
     * the method {@link #initialize} has finished successful.
     *
     * The method is responsible to validate the imported types and make certain that all the collections are
     * initialized.
     * @see #initialize
     * @see SoapProject
     * @since 1.4
     */
    @Override
    protected void postInitiate() {
        for(SoapProject soapProject : collection.values()){
            List<SoapPort> soapPorts = new CopyOnWriteArrayList<SoapPort>();
            if(soapProject.getPorts() != null){
                soapPorts.addAll(soapProject.getPorts());
            }
            soapProject.setPorts(soapPorts);

            for(SoapPort soapPort : soapProject.getPorts()){
                List<SoapOperation> soapOperations = new CopyOnWriteArrayList<SoapOperation>();
                if(soapPort.getOperations() != null){
                    soapOperations.addAll(soapPort.getOperations());
                }
                soapPort.setOperations(soapOperations);

                for(SoapOperation soapOperation : soapPort.getOperations()){
                    List<SoapMockResponse> soapMockResponses = new CopyOnWriteArrayList<SoapMockResponse>();
                    if(soapOperation.getMockResponses() != null){
                        soapMockResponses.addAll(soapOperation.getMockResponses());
                    }
                    soapOperation.setMockResponses(soapMockResponses);

                    for(SoapMockResponse soapMockResponse : soapOperation.getMockResponses()){
                        List<HttpHeader> httpHeaders = new CopyOnWriteArrayList<HttpHeader>();
                        if(soapMockResponse.getHttpHeaders() != null){
                            httpHeaders.addAll(soapMockResponse.getHttpHeaders());
                        }
                        soapMockResponse.setHttpHeaders(httpHeaders);
                    }
                }

            }
        }
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to the file system is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to the file system, but also loaded from the
     * file system upon application startup. The method will throw an exception in case of the type not being acceptable.
     * @param soapProject The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             the file system.
     * @see #save
     * @see SoapProject
     */
    @Override
    protected void checkType(SoapProject soapProject) {

    }

    /**
     * The save method provides the functionality to save an instance to the file system.
     * @param soapProject The type that will be saved to the file system.
     * @return The type that was saved to the file system. The main reason for it is being returned is because
     *         there could be modifications of the object during the save process. For example, if the type does not
     *         have an identifier, then the method will generate a new identifier for the type.
     */
    @Override
    public SoapProjectDto save(final SoapProject soapProject) {
        for(SoapPort soapPort : soapProject.getPorts()){
            if(soapPort.getId() == null){
                String soapPortId = generateId();
                soapPort.setId(soapPortId);
            }
            for(SoapOperation soapOperation : soapPort.getOperations()){
                if(soapOperation.getId() == null){
                    String soapOperationId = generateId();
                    soapOperation.setId(soapOperationId);
                }
                for(SoapMockResponse soapMockResponse : soapOperation.getMockResponses()){
                    if(soapMockResponse.getId() == null){
                        String soapMockResponseId = generateId();
                        soapMockResponse.setId(soapMockResponseId);
                    }
                }
            }
        }
        return super.save(soapProject);
    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SearchResult> search(final SearchQuery query) {
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        for(SoapProject soapProject : collection.values()){
            List<SearchResult> soapProjectSearchResult = searchSoapProject(soapProject, query);
            searchResults.addAll(soapProjectSearchResult);
        }
        return searchResults;
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
    @Override
    public SoapPortDto findSoapPort(final String soapProjectId, final String soapPortId) {
        Preconditions.checkNotNull(soapProjectId, "Project id cannot be null");
        Preconditions.checkNotNull(soapPortId, "Port id cannot be null");
        final SoapPort soapPort = findSoapPortType(soapProjectId, soapPortId);
        return mapper.map(soapPort, SoapPortDto.class);
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
    @Override
    public SoapOperationDto findSoapOperation(final String soapProjectId, final String soapPortId, final String soapOperationId){
        Preconditions.checkNotNull(soapOperationId, "Operation id cannot be null");
        final SoapOperation soapOperation = findSoapOperationType(soapProjectId, soapPortId, soapOperationId);
        return mapper.map(soapOperation, SoapOperationDto.class);
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
    @Override
    public SoapMockResponseDto findSoapMockResponse(final String soapProjectId, final String soapPortId, final String soapOperationId, final String soapMockResponseId) {
        Preconditions.checkNotNull(soapOperationId, "Mock response id cannot be null");
        final SoapMockResponse soapMockResponse = findSoapMockResponseType(soapProjectId, soapPortId, soapOperationId, soapMockResponseId);
        return mapper.map(soapMockResponse, SoapMockResponseDto.class);
    }

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
    @Override
    public SoapPortDto saveSoapPort(final String soapProjectId, final SoapPortDto soapPortDto) {
        SoapProject soapProject = collection.get(soapProjectId);
        SoapPort soapPort = mapper.map(soapPortDto, SoapPort.class);
        soapProject.getPorts().add(soapPort);
        save(soapProjectId);
        return mapper.map(soapPort, SoapPortDto.class);
    }

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
    @Override
    public SoapOperationDto saveSoapOperation(final String soapProjectId, final String soapPortId, final SoapOperationDto soapOperationDto) {
        SoapPort soapPort = findSoapPortType(soapProjectId, soapPortId);
        SoapOperation soapOperation = mapper.map(soapOperationDto, SoapOperation.class);
        soapPort.getOperations().add(soapOperation);
        save(soapProjectId);
        return mapper.map(soapOperation, SoapOperationDto.class);
    }

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
    @Override
    public SoapMockResponseDto saveSoapMockResponse(final String soapProjectId, final String soapPortId, final String soapOperationId, final SoapMockResponseDto soapMockResponseDto) {
        SoapOperation soapOperation = findSoapOperationType(soapProjectId, soapPortId, soapOperationId);
        SoapMockResponse soapMockResponse = mapper.map(soapMockResponseDto, SoapMockResponse.class);
        soapOperation.getMockResponses().add(soapMockResponse);
        save(soapProjectId);
        return mapper.map(soapMockResponse, SoapMockResponseDto.class);
    }

    /**
     * The method updates an already existing {@link SoapPort}
     * @param soapProjectId The id of the {@link SoapProject}
     * @parma soapPortId The id of the {@link SoapPort} that will be updated
     * @param soapPortDto The updated {@link SoapPortDto}
     * @return The updated version of the {@link SoapPortDto}
     * @see SoapProject
     * @see SoapProjectDto
     * @see SoapPort
     * @see SoapPortDto
     */
    @Override
    public SoapPortDto updateSoapPort(final String soapProjectId, final String soapPortId, final SoapPortDto soapPortDto) {
        final SoapPort soapPort = findSoapPortType(soapProjectId, soapPortId);
        soapPort.setUri(soapPortDto.getUri());
        save(soapProjectId);
        return soapPortDto;
    }

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
    @Override
    public SoapOperationDto updateSoapOperation(final String soapProjectId, final String soapPortId,
                                                final String soapOperationId, final SoapOperationDto soapOperationDto) {
        SoapOperation soapOperation = findSoapOperationType(soapProjectId, soapPortId, soapOperationId);
        soapOperation.setStatus(soapOperationDto.getStatus());
        soapOperation.setForwardedEndpoint(soapOperationDto.getForwardedEndpoint());
        soapOperation.setResponseStrategy(soapOperationDto.getResponseStrategy());
        save(soapProjectId);
        return soapOperationDto;
    }

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
    @Override
    public SoapMockResponseDto updateSoapMockResponse(final String soapProjectId, final String soapPortId,
                                                      final String soapOperationId, final String soapMockResponseId,
                                                      final SoapMockResponseDto soapMockResponseDto) {
        SoapMockResponse soapMockResponse = findSoapMockResponseType(soapProjectId, soapPortId, soapOperationId, soapMockResponseId);
        Preconditions.checkNotNull(soapMockResponse, "Unable to find SOAP mock response with id " + soapMockResponse.getId());
        final List<HttpHeader> headers = toDtoList(soapMockResponseDto.getHttpHeaders(), HttpHeader.class);
        soapMockResponse.setName(soapMockResponseDto.getName());
        soapMockResponse.setBody(soapMockResponseDto.getBody());
        soapMockResponse.setHttpStatusCode(soapMockResponseDto.getHttpStatusCode());
        soapMockResponse.setStatus(soapMockResponseDto.getStatus());
        soapMockResponse.setHttpHeaders(headers);
        soapMockResponse.setUsingExpressions(soapMockResponseDto.isUsingExpressions());
        save(soapProjectId);
        return soapMockResponseDto;
    }

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
    @Override
    public SoapPortDto deleteSoapPort(final String soapProjectId, final String soapPortId) {
        SoapProject soapProject = collection.get(soapProjectId);
        Iterator<SoapPort> soapPortIterator = soapProject.getPorts().iterator();
        SoapPort deletedSoapPort = null;
        while(soapPortIterator.hasNext()){
            SoapPort tempSoapPort = soapPortIterator.next();
            if(soapPortId.equals(tempSoapPort.getId())){
                deletedSoapPort = tempSoapPort;
                break;
            }
        }

        if(deletedSoapPort != null){
            soapProject.getPorts().remove(deletedSoapPort);
            save(soapProjectId);
        }
        return deletedSoapPort != null ? mapper.map(deletedSoapPort, SoapPortDto.class) : null;
    }

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
    @Override
    public SoapOperationDto deleteSoapOperation(final String soapProjectId, final String soapPortId, final String soapOperationId) {
        SoapPort soapPort = findSoapPortType(soapProjectId, soapPortId);
        Iterator<SoapOperation> soapOperationIterator = soapPort.getOperations().iterator();
        SoapOperation deletedSoapOperation = null;
        while(soapOperationIterator.hasNext()){
            SoapOperation tempSoapOperation = soapOperationIterator.next();
            if(soapOperationId.equals(tempSoapOperation.getId())){
                deletedSoapOperation = tempSoapOperation;
                break;
            }
        }

        if(deletedSoapOperation != null){
            soapPort.getOperations().remove(deletedSoapOperation);
            save(soapProjectId);
        }
        return deletedSoapOperation != null ? mapper.map(deletedSoapOperation, SoapOperationDto.class) : null;
    }

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
    @Override
    public SoapMockResponseDto deleteSoapMockResponse(final String soapProjectId, final String soapPortId, final String soapOperationId, final String soapMockResponseId) {
        SoapOperation soapOperation = findSoapOperationType(soapProjectId, soapPortId, soapOperationId);
        Iterator<SoapMockResponse> soapMockResponseIterator = soapOperation.getMockResponses().iterator();
        SoapMockResponse deleteSoapMockResponse = null;
        while(soapMockResponseIterator.hasNext()){
            SoapMockResponse tempSoapMockResponse = soapMockResponseIterator.next();
            if(soapMockResponseId.equals(tempSoapMockResponse.getId())){
                deleteSoapMockResponse = tempSoapMockResponse;
                break;
            }
        }

        if(deleteSoapMockResponse != null){
            soapOperation.getMockResponses().remove(deleteSoapMockResponse);
            save(soapProjectId);
        }
        return deleteSoapMockResponse != null ? mapper.map(deleteSoapMockResponse, SoapMockResponseDto.class) : null;
    }

    /**
     * The method provides the functionality to find a SOAP operation with a specific name
     * @param soapProjectId The id of the {@link SoapProject}
     * @param soapPortId The id of the {@link SoapPort}
     * @param soapOperationName The name of the SOAP operation that should be retrieved
     * @return A SOAP operation that matches the search criteria. If no SOAP operation matches the provided
     * name then null will be returned.
     */
    @Override
    public SoapOperationDto findSoapOperationWithName(final String soapProjectId, final String soapPortId, final String soapOperationName){
        final SoapPort soapPort = findSoapPortType(soapProjectId, soapPortId);
        for(SoapOperation soapOperation : soapPort.getOperations()){
            if(soapOperation.getName().equals(soapOperationName)){
                return mapper.map(soapOperation, SoapOperationDto.class);
            }
        }
        return null;
    }

    /**
     * The method finds a {@link SoapPortDto} with the provided name
     * @param soapProjectId The id of the {@link SoapProject}
     * @param soapPortName The name of the {@link SoapPort}
     * @return A {@link SoapPort} that matches the provided search criteria.
     */
    @Override
    public SoapPortDto findSoapPortWithName(final String soapProjectId, final String soapPortName) {
        final SoapProject soapProject = collection.get(soapProjectId);
        for(SoapPort soapPort : soapProject.getPorts()){
            if(soapPort.getName().equals(soapPortName)){
                return mapper.map(soapPort, SoapPortDto.class);
            }
        }
        return null;
    }

    /**
     * Finds a project by a given name
     * @param name The name of the project that should be retrieved
     * @return Returns a project with the provided name
     * @see SoapProject
     */
    @Override
    public SoapProjectDto findSoapProjectWithName(final String name) {
        Preconditions.checkNotNull(name, "Project name cannot be null");
        Preconditions.checkArgument(!name.isEmpty(), "Project name cannot be empty");
        for(SoapProject soapProject : collection.values()){
            if(soapProject.getName().equalsIgnoreCase(name)) {
                return mapper.map(soapProject, SoapProjectDto.class);
            }
        }
        return null;
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
    private SoapPort findSoapPortType(final String soapProjectId, final String soapPortId) {
        Preconditions.checkNotNull(soapProjectId, "Project id cannot be null");
        Preconditions.checkNotNull(soapPortId, "Port id cannot be null");
        final SoapProject soapProject = collection.get(soapProjectId);

        if(soapProject == null){
            throw new IllegalArgumentException("Unable to find a SOAP project with id " + soapProjectId);
        }

        for(SoapPort soapPort : soapProject.getPorts()){
            if(soapPort.getId().equals(soapPortId)){
                return soapPort;
            }
        }
        throw new IllegalArgumentException("Unable to find a SOAP port with id " + soapPortId);
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
    private SoapOperation findSoapOperationType(final String soapProjectId, final String soapPortId, final String soapOperationId){
        Preconditions.checkNotNull(soapOperationId, "Operation id cannot be null");
        final SoapPort soapPort = findSoapPortType(soapProjectId, soapPortId);
        for(SoapOperation soapOperation : soapPort.getOperations()){
            if(soapOperation.getId().equals(soapOperationId)){
                return soapOperation;
            }
        }
        throw new IllegalArgumentException("Unable to find a SOAP operation with id " + soapOperationId);
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
    private SoapMockResponse findSoapMockResponseType(final String soapProjectId, final String soapPortId, final String soapOperationId, final String soapMockResponseId) {
        final SoapOperation soapOperation = findSoapOperationType(soapProjectId, soapPortId, soapOperationId);
        for(SoapMockResponse soapMockResponse : soapOperation.getMockResponses()){
            if(soapMockResponse.getId().equals(soapMockResponseId)){
                return soapMockResponse;
            }
        }
        throw new IllegalArgumentException("Unable to find a SOAP mock response with id " + soapMockResponseId);
    }



    /**
     * Search through a SOAP project and all its resources
     * @param soapProject The SOAP project which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchSoapProject(final SoapProject soapProject, final SearchQuery query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        if(SearchValidator.validate(soapProject.getName(), query.getQuery())){
            final String projectType = messageSource.getMessage("general.type.project", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(soapProject.getName());
            searchResult.setLink(SOAP + SLASH + PROJECT + SLASH + soapProject.getId());
            searchResult.setDescription(SOAP_TYPE + COMMA + projectType);
            searchResults.add(searchResult);
        }


        for(SoapPort soapPort : soapProject.getPorts()){
            List<SearchResult> soapOperationSearchResult = searchSoapPort(soapProject, soapPort, query);
            searchResults.addAll(soapOperationSearchResult);
        }
        return searchResults;
    }

    /**
     * Search through a SOAP port and all its resources
     * @param soapProject The SOAP project which will be searched
     * @param soapPort The SOAP port which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchSoapPort(final SoapProject soapProject, final SoapPort soapPort, final SearchQuery query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        if(SearchValidator.validate(soapPort.getName(), query.getQuery())){
            final String portType = messageSource.getMessage("soap.type.port", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(soapPort.getName());
            searchResult.setLink(SOAP + SLASH + PROJECT + SLASH + soapProject.getId() + SLASH + PORT + SLASH + soapPort.getId());
            searchResult.setDescription(SOAP_TYPE + COMMA + portType);
            searchResults.add(searchResult);
        }

        for(SoapOperation soapOperation : soapPort.getOperations()){
            List<SearchResult> soapOperationSearchResult = searchSoapOperation(soapProject, soapPort, soapOperation, query);
            searchResults.addAll(soapOperationSearchResult);
        }
        return searchResults;
    }

    /**
     * Search through a SOAP operation and all its resources
     * @param soapProject The SOAP project which will be searched
     * @param soapPort The SOAP port which will be searched
     * @param soapOperation The SOAP operation which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchSoapOperation(final SoapProject soapProject, final SoapPort soapPort, final SoapOperation soapOperation, final SearchQuery query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        if(SearchValidator.validate(soapOperation.getName(), query.getQuery())){
            final String operationType = messageSource.getMessage("soap.type.operation", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(soapOperation.getName());
            searchResult.setLink(SOAP + SLASH + PROJECT + SLASH + soapProject.getId() + SLASH + PORT + SLASH + soapPort.getId() + SLASH + OPERATION + SLASH + soapOperation.getId());
            searchResult.setDescription(SOAP_TYPE + COMMA + operationType);
            searchResults.add(searchResult);
        }

        for(SoapMockResponse soapMockResponse : soapOperation.getMockResponses()){
            SearchResult soapMockResponseSearchResult = searchSoapMockResponse(soapProject, soapPort, soapOperation, soapMockResponse, query);
            if(soapMockResponseSearchResult != null){
                searchResults.add(soapMockResponseSearchResult);
            }
        }
        return searchResults;
    }

    /**
     * Search through a SOAP response
     * @param soapProject The SOAP project which will be searched
     * @param soapPort The SOAP port which will be searched
     * @param soapOperation The SOAP operation which will be searched
     * @param soapMockResponse The SOAP response that will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private SearchResult searchSoapMockResponse(final SoapProject soapProject, final SoapPort soapPort, final SoapOperation soapOperation, final SoapMockResponse soapMockResponse, final SearchQuery query){
        SearchResult searchResult = null;

        if(SearchValidator.validate(soapMockResponse.getName(), query.getQuery())){
            final String mockResponseType = messageSource.getMessage("soap.type.mockresponse", null , LocaleContextHolder.getLocale());
            searchResult = new SearchResult();
            searchResult.setTitle(soapMockResponse.getName());
            searchResult.setLink(SOAP + SLASH + PROJECT + SLASH + soapProject.getId() + SLASH + PORT + SLASH + soapPort.getId() + SLASH + OPERATION + SLASH + soapOperation.getId() + SLASH + RESPONSE + SLASH + soapMockResponse.getId());
            searchResult.setDescription(SOAP_TYPE + COMMA + mockResponseType);
        }

        return searchResult;
    }

}
