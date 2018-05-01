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

package com.castlemock.web.mock.rest.model.project.service;

import com.castlemock.core.basis.model.Service;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.rest.model.project.domain.RestApplication;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponse;
import com.castlemock.core.mock.rest.model.project.domain.RestResource;
import com.castlemock.core.mock.rest.model.project.service.message.input.ImportRestDefinitionInput;
import com.castlemock.core.mock.rest.model.project.service.message.output.ImportRestDefinitionOutput;
import com.castlemock.web.mock.rest.converter.RestDefinitionConverter;
import com.castlemock.web.mock.rest.converter.RestDefinitionConverterFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ImportRestDefinitionService extends AbstractRestProjectService implements Service<ImportRestDefinitionInput, ImportRestDefinitionOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<ImportRestDefinitionOutput> process(final ServiceTask<ImportRestDefinitionInput> serviceTask) {
        final ImportRestDefinitionInput input = serviceTask.getInput();
        final String projectId = input.getRestProjectId();

        final RestDefinitionConverter restDefinitionConverter = RestDefinitionConverterFactory.getConverter(input.getDefinitionType());

        List<RestApplication> newRestApplications = new ArrayList<>();

        if(input.getLocation() != null){
            List<RestApplication> result = restDefinitionConverter.convert(input.getLocation(), input.isGenerateResponse());
            newRestApplications.addAll(result);
        }

        // Parse all incoming files and convert them to REST applications
        if(input.getFiles() != null){
            for(File file : input.getFiles()){
                List<RestApplication> result = restDefinitionConverter.convert(file, input.isGenerateResponse());
                newRestApplications.addAll(result);
            }
        }

        final List<RestApplication> existingRestApplications =
                this.applicationRepository.findWithProjectId(input.getRestProjectId());
        final List<RestApplication> restApplications = new ArrayList<>();

        // Iterate through all new REST application and see if they match an already
        // existing REST application. If so, then it should be updated and replaced
        // with the latest version.
        for(RestApplication newRestApplication : newRestApplications){
            updateRestApplication(newRestApplication, existingRestApplications, restApplications);
        }

        // Iterate through the remaining existing REST applications and add them to the final
        // list of REST applications
        for(RestApplication existingRestApplication : existingRestApplications){
            restApplications.add(existingRestApplication);
        }

        for(RestApplication application : restApplications){
            application.setProjectId(projectId);
            RestApplication savedApplication = this.applicationRepository.save(application);

            for(RestResource restResource : application.getResources()){
                restResource.setApplicationId(savedApplication.getId());
                RestResource savedResource = this.resourceRepository.save(restResource);

                for(RestMethod method : restResource.getMethods()){
                    method.setResourceId(savedResource.getId());
                    RestMethod savedMethod = this.methodRepository.save(method);

                    for(RestMockResponse mockResponse : method.getMockResponses()){
                        mockResponse.setMethodId(savedMethod.getId());
                        this.mockResponseRepository.save(mockResponse);
                    }
                }
            }
        }

        // Set the last version of the REST application
        return createServiceResult(new ImportRestDefinitionOutput());
    }


    /**
     * The method will add a new {@link RestApplication} and update an already existing {@link RestApplication}.
     * @param newRestApplication The new {@link RestApplication} that might be added to the final list of {@link RestApplication} (resultRestApplication).
     * @param existingRestApplications A list of existing {@link RestApplication}
     * @param resultRestApplication A list of the result of {@link RestApplication}. These will be the new {@link RestApplication}.
     * @since 1.10
     */
    private void updateRestApplication(final RestApplication newRestApplication,
                                       final List<RestApplication> existingRestApplications,
                                       final List<RestApplication> resultRestApplication){
        final RestApplication existingRestApplication = findRestApplication(existingRestApplications, newRestApplication.getName());


        if(existingRestApplication == null){
            resultRestApplication.add(newRestApplication);
            return;
        }

        final List<RestResource> existingRestResources =
                this.resourceRepository.findWithApplicationId(existingRestApplication.getId());
        final List<RestResource> resultRestResources = new ArrayList<RestResource>();
        for(RestResource newRestResource : newRestApplication.getResources()) {
            updateRestResource(newRestResource, existingRestResources, resultRestResources);
        }
        resultRestApplication.add(existingRestApplication);
        newRestApplication.setResources(resultRestResources);

        // Remove the existing REST application from the list of existing REST application
        // This is done so that we can add the REST applications which have not been
        // either updated or re-added from the import.
        existingRestApplications.remove(existingRestApplication);
    }

    /**
     * The method will add a new {@link RestResource} and update an already existing {@link RestResource}.
     * @param newRestResource The new {@link RestResource} that might be added to the final list of {@link RestResource} (resultRestApplication).
     * @param existingRestResources A list of existing {@link RestResource}
     * @param resultRestResources A list of the result of {@link RestResource}. These will be the new {@link RestResource}.
     * @since 1.10
     */
    private void updateRestResource(final RestResource newRestResource,
                                    final List<RestResource> existingRestResources,
                                    final List<RestResource> resultRestResources){
        // Check if the new REST resource already exists
        final RestResource existingRestResource = findRestResource(existingRestResources, newRestResource.getName());

        // It doesn't exists. Simply add it to the existing application
        if (existingRestResource == null) {
            resultRestResources.add(newRestResource);
            return;
        }

        // Update resource
        existingRestResource.setUri(newRestResource.getUri());

        final List<RestMethod> existingRestMethods = this.methodRepository.findWithResourceId(existingRestResource.getId());
        final List<RestMethod> resultRestMethods = new ArrayList<RestMethod>();
        for(RestMethod newRestMethod : newRestResource.getMethods()){
            updateRestMethod(newRestMethod, existingRestMethods, resultRestMethods);
        }
        resultRestResources.add(existingRestResource);
        newRestResource.setMethods(resultRestMethods);
    }

    /**
     * The method will add a new {@link RestMethod} and update an already existing {@link RestMethod}.
     * @param newRestMethod The new {@link RestMethod} that might be added to the final list of {@link RestMethod} (resultRestApplication).
     * @param existingRestMethods A list of existing {@link RestMethod}
     * @param resultRestMethods A list of the result of {@link RestMethod}. These will be the new {@link RestMethod}.
     * @since 1.10
     */
    private void updateRestMethod(final RestMethod newRestMethod,
                                  final List<RestMethod> existingRestMethods,
                                  final List<RestMethod> resultRestMethods) {
        final RestMethod existingRestMethod = findRestMethod(existingRestMethods, newRestMethod.getName());

        // The new REST method does not exists. Add it to the resource
        if (existingRestMethod == null) {
            resultRestMethods.add(newRestMethod);
            return;
        }

        // THe REST method already exists. Update it.
        existingRestMethod.setHttpMethod(newRestMethod.getHttpMethod());
        resultRestMethods.add(existingRestMethod);
    }




    /**
     * Find a REST application with a specific name for a rest project
     * @param name The name of the REST application
     * @return A REST application that matches the search criteria. Null otherwise.
     */
    public RestApplication findRestApplication(List<RestApplication> restApplications, String name){
        for(RestApplication restApplication : restApplications){
            if(restApplication.getName().equals(name)){
                return restApplication;
            }
        }
        return null;
    }

    /**
     * Find a REST resource with a specific name for a REST application
     * @param name The name of the REST resource
     * @return A REST resource that matches the search criteria. Null otherwise.
     */
    public RestResource findRestResource(List<RestResource> restResources, String name){
        for(RestResource restResource : restResources){
            if(restResource.getName().equals(name)){
                return restResource;
            }
        }
        return null;
    }

    /**
     * Find a REST method with a specific name for a REST resource
     * @param name The name of the REST method
     * @return A REST method that matches the search criteria. Null otherwise.
     */
    public RestMethod findRestMethod(List<RestMethod> restMethods, String name){
        for(RestMethod restMethod : restMethods){
            if(restMethod.getName().equals(name)){
                return restMethod;
            }
        }
        return null;
    }


}
