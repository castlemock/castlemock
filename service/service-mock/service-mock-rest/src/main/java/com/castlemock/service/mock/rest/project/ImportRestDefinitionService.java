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
import com.castlemock.model.mock.rest.domain.RestApplication;
import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.model.mock.rest.domain.RestResource;
import com.castlemock.service.core.manager.FileManager;
import com.castlemock.service.mock.rest.project.converter.RestDefinitionConverter;
import com.castlemock.service.mock.rest.project.converter.RestDefinitionConverterFactory;
import com.castlemock.service.mock.rest.project.input.ImportRestDefinitionInput;
import com.castlemock.service.mock.rest.project.output.ImportRestDefinitionOutput;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ImportRestDefinitionService extends AbstractRestProjectService implements Service<ImportRestDefinitionInput, ImportRestDefinitionOutput> {

    @Autowired
    private FileManager fileManager;

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
        final String projectId = input.getProjectId();

        final RestDefinitionConverter restDefinitionConverter =
                RestDefinitionConverterFactory.getConverter(input.getDefinitionType(), fileManager);

        List<RestApplication> newRestApplications = new ArrayList<>();

        if(input.getLocation().isPresent()){
            List<RestApplication> result = restDefinitionConverter.convert(input.getLocation().get(),
                    projectId, input.getGenerateResponse().orElse(false));
            newRestApplications.addAll(result);
        }

        // Parse all incoming files and convert them to REST applications
        if(input.getFiles() != null){
            for(File file : input.getFiles()){
                List<RestApplication> result = restDefinitionConverter.convert(file,
                        projectId, input.getGenerateResponse().orElse(false));
                newRestApplications.addAll(result);
            }
        }

        final List<RestApplication> existingRestApplications =
                this.applicationRepository.findWithProjectId(input.getProjectId());
        final List<RestApplication> restApplications = new ArrayList<>();

        // Iterate through all new REST application and see if they match an already
        // existing REST application. If so, then it should be updated and replaced
        // with the latest version.
        for(RestApplication newRestApplication : newRestApplications){
            updateRestApplication(newRestApplication, existingRestApplications, restApplications);
        }

        // Iterate through the remaining existing REST applications and add them to the final
        // list of REST applications
        restApplications.addAll(existingRestApplications);

        for(RestApplication application : restApplications){
            final RestApplication savedApplication = this.applicationRepository.save(application.toBuilder()
                    .projectId(projectId)
                    .build());

            for(RestResource restResource : application.getResources()){
                RestResource savedResource = this.resourceRepository.save(restResource.toBuilder()
                        .applicationId(savedApplication.getId())
                        .build());

                for(RestMethod method : restResource.getMethods()){
                    final RestMethod savedMethod = this.methodRepository.save(method.toBuilder()
                            .resourceId(savedResource.getId())
                            .build());

                    for(RestMockResponse mockResponse : method.getMockResponses()){
                        this.mockResponseRepository.save(mockResponse.toBuilder()
                                .methodId(savedMethod.getId())
                                .build());
                    }
                }
            }
        }

        // Set the last version of the REST application
        return createServiceResult(ImportRestDefinitionOutput.builder().build());
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
        final RestApplication existingRestApplication = findRestApplication(existingRestApplications, newRestApplication.getName())
                .orElse(null);


        if(existingRestApplication == null){
            resultRestApplication.add(newRestApplication);
            return;
        }

        final List<RestResource> existingRestResources =
                this.resourceRepository.findWithApplicationId(existingRestApplication.getId());
        final List<RestResource> resultRestResources = new ArrayList<>();
        for(RestResource newRestResource : newRestApplication.getResources()) {
            updateRestResource(newRestResource, existingRestResources, resultRestResources);
        }
        resultRestApplication.add(existingRestApplication);

        //TODO: FIX
        //newRestApplication.setResources(resultRestResources);

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
        final RestResource existingRestResource = findRestResource(existingRestResources, newRestResource.getName())
                .orElse(null);

        // It doesn't exists. Simply add it to the existing application
        if (existingRestResource == null) {
            resultRestResources.add(newRestResource);
            return;
        }

        // Update resource
        //TODO: FIX
        //existingRestResource.setUri(newRestResource.getUri());

        final List<RestMethod> existingRestMethods = this.methodRepository.findWithResourceId(existingRestResource.getId());
        final List<RestMethod> resultRestMethods = new ArrayList<>();
        for(RestMethod newRestMethod : newRestResource.getMethods()){
            updateRestMethod(newRestMethod, existingRestMethods, resultRestMethods);
        }
        resultRestResources.add(existingRestResource);

        //TODO: FIX

        //newRestResource.setMethods(resultRestMethods);
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
        final RestMethod existingRestMethod = findRestMethod(existingRestMethods, newRestMethod.getName())
                .orElse(null);

        // The new REST method does not exists. Add it to the resource
        if (existingRestMethod == null) {
            resultRestMethods.add(newRestMethod);
            return;
        }

        // THe REST method already exists. Update it.
        //TODO: FIX
        //existingRestMethod.setHttpMethod(newRestMethod.getHttpMethod());
        resultRestMethods.add(existingRestMethod);
    }




    /**
     * Find a REST application with a specific name for a rest project
     * @param name The name of the REST application
     * @return A REST application that matches the search criteria. Null otherwise.
     */
    public Optional<RestApplication> findRestApplication(List<RestApplication> restApplications, String name){
        for(RestApplication restApplication : restApplications){
            if(restApplication.getName().equals(name)){
                return Optional.of(restApplication);
            }
        }
        return Optional.empty();
    }

    /**
     * Find a REST resource with a specific name for a REST application
     * @param name The name of the REST resource
     * @return A REST resource that matches the search criteria. Null otherwise.
     */
    public Optional<RestResource> findRestResource(List<RestResource> restResources, String name){
        for(RestResource restResource : restResources){
            if(restResource.getName().equals(name)){
                return Optional.of(restResource);
            }
        }
        return Optional.empty();
    }

    /**
     * Find a REST method with a specific name for a REST resource
     * @param name The name of the REST method
     * @return A REST method that matches the search criteria. Null otherwise.
     */
    public Optional<RestMethod> findRestMethod(List<RestMethod> restMethods, String name){
        for(RestMethod restMethod : restMethods){
            if(restMethod.getName().equals(name)){
                return Optional.of(restMethod);
            }
        }
        return Optional.empty();
    }


}
