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
import com.castlemock.service.mock.soap.project.input.ExportSoapProjectInput;
import com.castlemock.service.mock.soap.project.output.ExportSoapProjectOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ExportSoapProjectService extends AbstractSoapProjectService implements Service<ExportSoapProjectInput, ExportSoapProjectOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<ExportSoapProjectOutput> process(final ServiceTask<ExportSoapProjectInput> serviceTask) {
        final ExportSoapProjectInput input = serviceTask.getInput();
        final SoapProject project = repository.findOne(input.getProjectId());
        final List<SoapPort> ports = this.portRepository.findWithProjectId(input.getProjectId());
        final List<SoapResource> resources = this.resourceRepository.findWithProjectId(input.getProjectId());
        final List<SoapOperation> operations = new ArrayList<>();
        final List<SoapMockResponse> mockResponses = new ArrayList<>();

        for(SoapResource resource : resources){
            String content = this.resourceRepository.loadSoapResource(resource.getId());
            resource.setContent(content);
        }

        for(SoapPort port : ports){
            List<SoapOperation> tempOperations = this.operationRepository.findWithPortId(port.getId());
            operations.addAll(tempOperations);

            for(SoapOperation tempOperation : tempOperations){
                List<SoapMockResponse> tempMockResponses = this.mockResponseRepository.findWithOperationId(tempOperation.getId());
                mockResponses.addAll(tempMockResponses);
            }
        }

        final SoapExportContainer exportContainer = new SoapExportContainer();
        exportContainer.setProject(project);
        exportContainer.setPorts(ports);
        exportContainer.setResources(resources);
        exportContainer.setOperations(operations);
        exportContainer.setMockResponses(mockResponses);

        final String serialized = ExportContainerSerializer.serialize(exportContainer);
        return createServiceResult(ExportSoapProjectOutput.builder()
                .project(serialized)
                .build());
    }

}
