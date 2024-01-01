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

package com.castlemock.service.mock.soap.project;

import com.castlemock.model.core.Service;
import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.core.utility.serializer.ExportContainerSerializer;
import com.castlemock.model.mock.soap.SoapExportContainer;
import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapPort;
import com.castlemock.model.mock.soap.domain.SoapProject;
import com.castlemock.model.mock.soap.domain.SoapResource;
import com.castlemock.service.mock.soap.project.input.ImportSoapProjectInput;
import com.castlemock.service.mock.soap.project.output.ImportSoapProjectOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ImportSoapProjectService extends AbstractSoapProjectService implements Service<ImportSoapProjectInput, ImportSoapProjectOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    @SuppressWarnings("deprecation")
    public ServiceResult<ImportSoapProjectOutput> process(final ServiceTask<ImportSoapProjectInput> serviceTask) {
        final ImportSoapProjectInput input = serviceTask.getInput();

        final SoapExportContainer exportContainer = ExportContainerSerializer.deserialize(input.getProjectRaw(), SoapExportContainer.class);
        final SoapProject project = exportContainer.getProject();

        if(this.repository.exists(project.getId())){
            throw new IllegalArgumentException("A project with the following key already exists: " + project.getId());
        }

        this.repository.save(project);

        for(SoapPort port : exportContainer.getPorts()){
            if(this.portRepository.exists(port.getId())){
                throw new IllegalArgumentException("A port with the following key already exists: " + port.getId());
            }

            this.portRepository.save(port);
        }

        for(SoapResource resource : exportContainer.getResources()){
            if(this.resourceRepository.exists(resource.getId())){
                throw new IllegalArgumentException("A resource with the following key already exists: " + resource.getId());
            }

            this.resourceRepository.saveSoapResource(resource, resource.getContent().orElse(null));
        }

        for(SoapOperation operation : exportContainer.getOperations()){
            if(this.operationRepository.exists(operation.getId())){
                throw new IllegalArgumentException("An operation with the following key already exists: " + operation.getId());
            }

            this.operationRepository.save(operation.toBuilder()
                    .currentResponseSequenceIndex(0)
                    .build());
        }

        for(SoapMockResponse mockResponse : exportContainer.getMockResponses()){
            if(this.mockResponseRepository.exists(mockResponse.getId())){
                throw new IllegalArgumentException("A mocked response with the following key already exists: " + mockResponse.getId());
            }

            this.mockResponseRepository.save(mockResponse);
        }

        return createServiceResult(ImportSoapProjectOutput.builder()
                .project(project)
                .build());
    }
}
