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

package com.castlemock.service.mock.rest.project;

import com.castlemock.model.core.Service;
import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.core.utility.serializer.ExportContainerSerializer;
import com.castlemock.model.mock.rest.RestExportContainer;
import com.castlemock.model.mock.rest.domain.RestApplication;
import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.model.mock.rest.domain.RestProject;
import com.castlemock.model.mock.rest.domain.RestResource;
import com.castlemock.service.mock.rest.project.input.ExportRestProjectInput;
import com.castlemock.service.mock.rest.project.output.ExportRestProjectOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ExportRestProjectService extends AbstractRestProjectService implements Service<ExportRestProjectInput, ExportRestProjectOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<ExportRestProjectOutput> process(final ServiceTask<ExportRestProjectInput> serviceTask) {
        final ExportRestProjectInput input = serviceTask.getInput();
        final RestProject project = repository.findOne(input.getRestProjectId());
        final List<RestApplication> applications = this.applicationRepository.findWithProjectId(input.getRestProjectId());
        final List<RestResource> resources = new ArrayList<>();
        final List<RestMethod> methods = new ArrayList<>();
        final List<RestMockResponse> mockResponses = new ArrayList<>();


        for(RestApplication application : applications){
            List<RestResource> tempResources = this.resourceRepository.findWithApplicationId(application.getId());
            resources.addAll(tempResources);

            for(RestResource tempResource : tempResources){
                List<RestMethod> tempMethods = this.methodRepository.findWithResourceId(tempResource.getId());
                methods.addAll(tempMethods);

                for(RestMethod tempMethod : tempMethods){
                    List<RestMockResponse> tempMockResponses = this.mockResponseRepository.findWithMethodId(tempMethod.getId());
                    mockResponses.addAll(tempMockResponses);
                }
            }
        }

        final RestExportContainer exportContainer = new RestExportContainer();
        exportContainer.setProject(project);
        exportContainer.setApplications(applications);
        exportContainer.setResources(resources);
        exportContainer.setMethods(methods);
        exportContainer.setMockResponses(mockResponses);

        final String serialized = ExportContainerSerializer.serialize(exportContainer);
        return createServiceResult(ExportRestProjectOutput.builder()
                .exportedProject(serialized)
                .build());
    }
}
