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

package com.castlemock.web.mock.soap.service.project;

import com.castlemock.core.mock.soap.model.project.domain.*;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.web.basis.model.AbstractService;
import com.castlemock.web.mock.soap.repository.project.*;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public abstract class AbstractSoapProjectService extends AbstractService<SoapProject, String, SoapProjectRepository> {

    @Autowired
    protected SoapPortRepository portRepository;
    @Autowired
    protected SoapOperationRepository operationRepository;
    @Autowired
    protected SoapMockResponseRepository mockResponseRepository;
    @Autowired
    protected SoapResourceRepository resourceRepository;

    protected SoapProject deleteProject(final String projectId){
        final List<SoapPort> ports = this.portRepository.findWithProjectId(projectId);
        final List<SoapResource> resources = this.resourceRepository.findWithProjectId(projectId);

        for(SoapPort port : ports){
            this.deletePort(port.getId());
        }
        for(SoapResource resource : resources){
            this.deleteResource(resource.getId());
        }

        return this.repository.delete(projectId);
    }

    protected SoapPort deletePort(final String portId){
        final List<SoapOperation> operations = this.operationRepository.findWithPortId(portId);

        for(SoapOperation operation : operations){
            this.deleteOperation(operation.getId());
        }

        return this.portRepository.delete(portId);
    }

    protected SoapOperation deleteOperation(final String operationId){
        final List<SoapMockResponse> responses = this.mockResponseRepository.findWithOperationId(operationId);

        for(SoapMockResponse response : responses){
            this.deleteMockResponse(response.getId());
        }

        return this.operationRepository.delete(operationId);
    }

    protected SoapMockResponse deleteMockResponse(final String mockReponseId){
        return this.mockResponseRepository.delete(mockReponseId);
    }

    protected SoapResource deleteResource(final String resourceId){
        return this.resourceRepository.delete(resourceId);
    }


    /**
     * The save method saves a project to the database
     * @param project Project that will be saved to the database
     * @return The saved project
     */
    @Override
    public SoapProject save(final SoapProject project){
        Preconditions.checkNotNull(project, "Project cannot be null");
        Preconditions.checkArgument(!project.getName().isEmpty(), "Invalid project name. Project name cannot be empty");
        final SoapProject projectInDatebase = repository.findSoapProjectWithName(project.getName());
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
    public SoapProject update(final String soapProjectId, final SoapProject updatedProject){
        Preconditions.checkNotNull(soapProjectId, "Project id be null");
        Preconditions.checkNotNull(updatedProject, "Project cannot be null");
        Preconditions.checkArgument(!updatedProject.getName().isEmpty(), "Invalid project name. Project name cannot be empty");
        final SoapProject projectWithName = repository.findSoapProjectWithName(updatedProject.getName());
        Preconditions.checkArgument(projectWithName == null || projectWithName.getId().equals(soapProjectId), "Project name is already taken");
        final SoapProject project = find(soapProjectId);
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        return super.save(project);
    }

}
