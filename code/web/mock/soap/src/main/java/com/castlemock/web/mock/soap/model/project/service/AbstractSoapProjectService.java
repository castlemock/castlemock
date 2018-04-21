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

package com.castlemock.web.mock.soap.model.project.service;

import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.core.mock.soap.model.project.dto.*;
import com.castlemock.web.basis.model.AbstractService;
import com.castlemock.web.mock.soap.model.project.repository.*;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public abstract class AbstractSoapProjectService extends AbstractService<SoapProject, SoapProjectDto, String, SoapProjectRepository> {

    @Autowired
    protected SoapPortRepository portRepository;
    @Autowired
    protected SoapOperationRepository operationRepository;
    @Autowired
    protected SoapMockResponseRepository mockResponseRepository;
    @Autowired
    protected SoapResourceRepository resourceRepository;

    protected SoapProjectDto deleteProject(final String projectId){
        final List<SoapPortDto> ports = this.portRepository.findWithProjectId(projectId);
        final List<SoapResourceDto> resources = this.resourceRepository.findWithProjectId(projectId);

        for(SoapPortDto port : ports){
            this.deletePort(port.getId());
        }
        for(SoapResourceDto resource : resources){
            this.deleteResource(resource.getId());
        }

        return this.repository.delete(projectId);
    }

    protected SoapPortDto deletePort(final String portId){
        final List<SoapOperationDto> operations = this.operationRepository.findWithPortId(portId);

        for(SoapOperationDto operation : operations){
            this.deleteOperation(operation.getId());
        }

        return this.portRepository.delete(portId);
    }

    protected SoapOperationDto deleteOperation(final String operationId){
        final List<SoapMockResponseDto> responses = this.mockResponseRepository.findWithOperationId(operationId);

        for(SoapMockResponseDto response : responses){
            this.deleteMockResponse(response.getId());
        }

        return this.operationRepository.delete(operationId);
    }

    protected SoapMockResponseDto deleteMockResponse(final String mockReponseId){
        return this.mockResponseRepository.delete(mockReponseId);
    }

    protected SoapResourceDto deleteResource(final String resourceId){
        return this.resourceRepository.delete(resourceId);
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
        final SoapProjectDto projectInDatebase = repository.findSoapProjectWithName(project.getName());
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
    public SoapProjectDto update(final String soapProjectId, final SoapProjectDto updatedProject){
        Preconditions.checkNotNull(soapProjectId, "Project id be null");
        Preconditions.checkNotNull(updatedProject, "Project cannot be null");
        Preconditions.checkArgument(!updatedProject.getName().isEmpty(), "Invalid project name. Project name cannot be empty");
        final SoapProjectDto projectWithNameDto = repository.findSoapProjectWithName(updatedProject.getName());
        Preconditions.checkArgument(projectWithNameDto == null || projectWithNameDto.getId().equals(soapProjectId), "Project name is already taken");
        final SoapProjectDto project = find(soapProjectId);
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        return super.save(project);
    }

}
