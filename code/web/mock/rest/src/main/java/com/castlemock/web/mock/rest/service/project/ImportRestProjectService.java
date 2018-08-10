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

package com.castlemock.web.mock.rest.service.project;

import com.castlemock.core.basis.model.Service;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.basis.utility.serializer.ExportContainerSerializer;
import com.castlemock.core.mock.rest.model.RestExportContainer;
import com.castlemock.core.mock.rest.model.project.domain.*;
import com.castlemock.core.mock.rest.service.project.input.ImportRestProjectInput;
import com.castlemock.core.mock.rest.service.project.output.ImportRestProjectOutput;
import com.castlemock.web.mock.rest.legacy.repository.project.v1.RestProjectV1LegacyRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ImportRestProjectService extends AbstractRestProjectService implements Service<ImportRestProjectInput, ImportRestProjectOutput> {

    @Autowired
    private RestProjectV1LegacyRepository legacyRepository;

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<ImportRestProjectOutput> process(final ServiceTask<ImportRestProjectInput> serviceTask) {
        final ImportRestProjectInput input = serviceTask.getInput();
        // Try to import the project as a legacy project first.
        RestProject project = this.legacyRepository.importOne(input.getProjectRaw());

        if(project == null){
            // Unable to load the project as a legacy project.
            RestExportContainer exportContainer = ExportContainerSerializer.deserialize(input.getProjectRaw(), RestExportContainer.class);

            project = exportContainer.getProject();

            if(this.repository.exists(project.getId())){
                throw new IllegalArgumentException("A project with the following key already exists: " + project.getId());
            }

            this.repository.save(project);

            for(RestApplication application : exportContainer.getApplications()){
                if(this.applicationRepository.exists(application.getId())){
                    throw new IllegalArgumentException("An application with the following key already exists: " + application.getId());
                }

                this.applicationRepository.save(application);
            }

            for(RestResource resource : exportContainer.getResources()){
                if(this.resourceRepository.exists(resource.getId())){
                    throw new IllegalArgumentException("A resource with the following key already exists: " + resource.getId());
                }

                this.resourceRepository.save(resource);
            }

            for(RestMethod method : exportContainer.getMethods()){
                if(this.methodRepository.exists(method.getId())){
                    throw new IllegalArgumentException("A method with the following key already exists: " + method.getId());
                }

                method.setCurrentResponseSequenceIndex(0);
                this.methodRepository.save(method);
            }

            for(RestMockResponse mockResponse : exportContainer.getMockResponses()){
                if(this.mockResponseRepository.exists(mockResponse.getId())){
                    throw new IllegalArgumentException("A mocked response with the following key already exists: " + mockResponse.getId());
                }

                this.mockResponseRepository.save(mockResponse);
            }
        }
        return createServiceResult(ImportRestProjectOutput.builder()
                .project(project)
                .build());
    }
}
