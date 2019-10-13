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

import com.castlemock.core.basis.model.Service;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.basis.utility.serializer.ExportContainerSerializer;
import com.castlemock.core.mock.soap.model.SoapExportContainer;
import com.castlemock.core.mock.soap.model.project.domain.*;
import com.castlemock.core.mock.soap.service.project.input.ImportSoapProjectInput;
import com.castlemock.core.mock.soap.service.project.output.ImportSoapProjectOutput;
import com.castlemock.repository.soap.file.project.legacy.SoapProjectV1LegacyRepository;
import com.google.common.base.Strings;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ImportSoapProjectService extends AbstractSoapProjectService implements Service<ImportSoapProjectInput, ImportSoapProjectOutput> {

    @Autowired
    private SoapProjectV1LegacyRepository legacyRepository;

    private static final Logger LOGGER = Logger.getLogger(ImportSoapProjectService.class);


    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<ImportSoapProjectOutput> process(final ServiceTask<ImportSoapProjectInput> serviceTask) {
        final ImportSoapProjectInput input = serviceTask.getInput();

        // Try to import the project as a legacy project first.
        SoapProject project = this.legacyRepository.importOne(input.getProjectRaw());

        if(project == null){
            // Unable to load the project as a legacy project.
            SoapExportContainer exportContainer = ExportContainerSerializer.deserialize(input.getProjectRaw(), SoapExportContainer.class);

            project = exportContainer.getProject();

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

                this.resourceRepository.saveSoapResource(resource, resource.getContent());
            }

            for(SoapOperation operation : exportContainer.getOperations()){
                if(this.operationRepository.exists(operation.getId())){
                    throw new IllegalArgumentException("An operation with the following key already exists: " + operation.getId());
                }

                if(operation.getOperationIdentifier() == null){
                    SoapOperationIdentifier operationIdentifier =
                            new SoapOperationIdentifier();
                    operationIdentifier.setName(operation.getIdentifier());
                    operation.setOperationIdentifier(operationIdentifier);
                    operation.setIdentifier(null);
                }
                if(operation.getIdentifyStrategy() == null){
                    operation.setIdentifyStrategy(SoapOperationIdentifyStrategy.ELEMENT_NAMESPACE);
                }
                if(operation.getCurrentResponseSequenceIndex() == null){
                    operation.setCurrentResponseSequenceIndex(0);
                }
                if(!Strings.isNullOrEmpty(operation.getDefaultXPathMockResponseId())){
                    operation.setDefaultMockResponseId(operation.getDefaultXPathMockResponseId());
                    operation.setDefaultXPathMockResponseId(null);
                }
                
                operation.setCurrentResponseSequenceIndex(0);
                this.operationRepository.save(operation);
            }

            for(SoapMockResponse mockResponse : exportContainer.getMockResponses()){
                if(this.mockResponseRepository.exists(mockResponse.getId())){
                    throw new IllegalArgumentException("A mocked response with the following key already exists: " + mockResponse.getId());
                }

                if(!Strings.isNullOrEmpty(mockResponse.getXpathExpression())){
                    final SoapXPathExpression xPathExpression = new SoapXPathExpression();
                    xPathExpression.setExpression(mockResponse.getXpathExpression());
                    mockResponse.getXpathExpressions().add(xPathExpression);
                    mockResponse.setXpathExpression(null);
                }

                this.mockResponseRepository.save(mockResponse);
            }
        }

        return createServiceResult(ImportSoapProjectOutput.builder()
                .project(project)
                .build());
    }
}
