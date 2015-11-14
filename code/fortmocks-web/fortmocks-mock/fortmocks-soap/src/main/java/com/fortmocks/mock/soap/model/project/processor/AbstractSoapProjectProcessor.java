package com.fortmocks.mock.soap.model.project.processor;

import com.fortmocks.mock.soap.model.project.domain.SoapMockResponse;
import com.fortmocks.mock.soap.model.project.domain.SoapOperation;
import com.fortmocks.mock.soap.model.project.domain.SoapPort;
import com.fortmocks.mock.soap.model.project.domain.SoapProject;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.web.core.model.AbstractProcessor;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public abstract class AbstractSoapProjectProcessor extends AbstractProcessor<SoapProject, SoapProjectDto, Long> {



    /**
     * The method find an port with project id and port id
     * @param soapProjectId The id of the project which the port belongs to
     * @param soapPortId The id of the port that will be retrieved
     * @return Returns an port that matches the search criteria. Returns null if no port matches.
     * @throws IllegalArgumentException IllegalArgumentException will be thrown jf no matching SOAP port was found
     * @see SoapProject
     * @see com.fortmocks.mock.soap.model.project.dto.SoapProjectDto
     * @see SoapPort
     * @see com.fortmocks.mock.soap.model.project.dto.SoapPortDto
     */
    private SoapPort findSoapPortType(final Long soapProjectId, final Long soapPortId) {
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
     * @see com.fortmocks.mock.soap.model.project.dto.SoapProjectDto
     * @see SoapPort
     * @see com.fortmocks.mock.soap.model.project.dto.SoapPortDto
     * @see SoapOperation
     * @see com.fortmocks.mock.soap.model.project.dto.SoapOperationDto
     */
    private SoapOperation findSoapOperationType(final Long soapProjectId, final Long soapPortId, final Long soapOperationId){
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
     * @see com.fortmocks.mock.soap.model.project.dto.SoapProjectDto
     * @see SoapPort
     * @see com.fortmocks.mock.soap.model.project.dto.SoapPortDto
     */
    private List<SoapOperation> findSoapOperationType(final Long soapProjectId, final Long soapPortId) {
        final SoapPort soapPort = findSoapPortType(soapProjectId, soapPortId);
        return soapPort != null ? soapPort.getSoapOperations() : null;
    }

    /**
     * Finds a operation with the provided operation id
     * @param soapOperationId The id of the operation that should be retrieved
     * @return A operation with the provided id. Null will be returned if no operation has the matching value
     * @throws IllegalArgumentException IllegalArgumentException will be thrown jf no matching SOAP operation was found
     * @see SoapOperation
     * @see com.fortmocks.mock.soap.model.project.dto.SoapOperationDto
     */
    private SoapOperation findSoapOperationType(final Long soapOperationId) {
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
     * @see com.fortmocks.mock.soap.model.project.dto.SoapOperationDto
     */
    private Long findSoapProjectType(final Long soapOperationId) {
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
     * @see com.fortmocks.mock.soap.model.project.dto.SoapProjectDto
     */
    private List<SoapOperation> findSoapOperationTypeWithSoapProjectId(final Long soapProjectId) {
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
     * @see com.fortmocks.mock.soap.model.project.dto.SoapProjectDto
     * @see SoapPort
     * @see com.fortmocks.mock.soap.model.project.dto.SoapPortDto
     * @see SoapOperation
     * @see com.fortmocks.mock.soap.model.project.dto.SoapOperationDto
     * @see SoapMockResponse
     * @see com.fortmocks.mock.soap.model.project.dto.SoapMockResponseDto
     */
    private SoapMockResponse findSoapMockResponseType(final Long soapProjectId, final Long soapPortId, final Long soapOperationId, final Long soapMockResponseId) {
        final SoapOperation soapOperation = findSoapOperationType(soapProjectId, soapPortId, soapOperationId);
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
     * @see SoapPort
     * @see com.fortmocks.mock.soap.model.project.dto.SoapPortDto
     */
    private Long getNextSoapPortId(){
        Long nextSoapPortId = 0L;
        for(SoapProject soapProject : findAllTypes()){
            for(SoapPort soapPort : soapProject.getSoapPorts()){
                if(soapPort.getId() >= nextSoapPortId){
                    nextSoapPortId = soapPort.getId() + 1;
                }
            }
        }
        return nextSoapPortId;
    }

    /**
     * The method calculates the next SOAP operation id
     * @return The new generated SOAP operation id
     * @see SoapOperation
     * @see com.fortmocks.mock.soap.model.project.dto.SoapOperationDto
     */
    private Long getNexSoapOperationId(){
        Long nextSoapOperationId = 0L;
        for(SoapProject soapProject : findAllTypes()){
            for(SoapPort soapPort : soapProject.getSoapPorts()){
                for(SoapOperation soapOperation : soapPort.getSoapOperations()){
                    if(soapOperation.getId() >= nextSoapOperationId){
                        nextSoapOperationId = soapOperation.getId() + 1;
                    }
                }
            }
        }
        return nextSoapOperationId;
    }

    /**
     * The method calculates the next SOAP mock response id
     * @return The new generated SOAP mock response id
     * @see SoapMockResponse
     * @see com.fortmocks.mock.soap.model.project.dto.SoapMockResponseDto
     */
    private Long getNextSoapMockResponseId(){
        Long nextSoapMockResponseId = 0L;
        for(SoapProject soapProject : findAllTypes()){
            for(SoapPort soapPort : soapProject.getSoapPorts()){
                for(SoapOperation soapOperation : soapPort.getSoapOperations()){
                    for(SoapMockResponse soapMockResponse : soapOperation.getSoapMockResponses()){
                        if(soapMockResponse.getId() >= nextSoapMockResponseId){
                            nextSoapMockResponseId = soapMockResponse.getId() + 1;
                        }
                    }
                }
            }
        }
        return nextSoapMockResponseId;
    }

    /**
     * The method provides the functionality to generate new identifiers for a of SOAP port
     * @param soapPort The SOAP port that will receive new identifiers
     */
    private void generateId(final SoapPort soapPort){
        Long nextSoapPortId = getNextSoapPortId();
        Long nextSoapOperationId = getNexSoapOperationId();
        Long nextSoapMockResponseId = getNextSoapMockResponseId();


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

    /**
     * The method provides the functionality to generate new identifiers for a SOAP operation
     * @param soapOperation The SOAP port that will receive new identifiers
     */
    private void generateId(final SoapOperation soapOperation){
        Long nextSoapOperationId = getNexSoapOperationId();
        Long nextSoapMockResponseId = getNextSoapMockResponseId();

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
