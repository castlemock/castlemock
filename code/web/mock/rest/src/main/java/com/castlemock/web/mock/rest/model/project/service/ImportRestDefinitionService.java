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
import com.castlemock.core.mock.rest.model.project.dto.RestApplicationDto;
import com.castlemock.core.mock.rest.model.project.dto.RestMethodDto;
import com.castlemock.core.mock.rest.model.project.dto.RestProjectDto;
import com.castlemock.core.mock.rest.model.project.dto.RestResourceDto;
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
        final RestProjectDto restProject = repository.findOne(input.getRestProjectId());
        final RestDefinitionConverter restDefinitionConverter = RestDefinitionConverterFactory.getConverter(input.getDefinitionType());

        List<RestApplicationDto> newRestApplications = new ArrayList<>();

        if(input.getLocation() != null){
            List<RestApplicationDto> result = restDefinitionConverter.convert(input.getLocation(), input.isGenerateResponse());
            newRestApplications.addAll(result);
        }

        // Parse all incoming files and convert them to REST applications
        if(input.getFiles() != null){
            for(File file : input.getFiles()){
                List<RestApplicationDto> result = restDefinitionConverter.convert(file, input.isGenerateResponse());
                newRestApplications.addAll(result);
            }
        }

        final List<RestApplicationDto> existingRestApplications = new ArrayList<>(restProject.getApplications());
        final List<RestApplicationDto> restApplications = new ArrayList<>();

        // Iterate through all new REST application and see if they match an already
        // existing REST application. If so, then it should be updated and replaced
        // with the latest version.
        for(RestApplicationDto newRestApplication : newRestApplications){
            updateRestApplication(newRestApplication, existingRestApplications, restApplications);
        }

        // Iterate through the remaining existing REST applications and add them to the final
        // list of REST applications
        for(RestApplicationDto existingRestApplication : existingRestApplications){
            restApplications.add(existingRestApplication);
        }

        // Set the last version of the REST application
        restProject.setApplications(restApplications);
        save(restProject);
        return createServiceResult(new ImportRestDefinitionOutput());
    }


    /**
     * The method will add a new {@link RestApplicationDto} and update an already existing {@link RestApplicationDto}.
     * @param newRestApplication The new {@link RestApplicationDto} that might be added to the final list of {@link RestApplicationDto} (resultRestApplication).
     * @param existingRestApplications A list of existing {@link RestApplicationDto}
     * @param resultRestApplication A list of the result of {@link RestApplicationDto}. These will be the new {@link RestApplicationDto}.
     * @since 1.10
     */
    private void updateRestApplication(final RestApplicationDto newRestApplication,
                                       final List<RestApplicationDto> existingRestApplications,
                                       final List<RestApplicationDto> resultRestApplication){
        final RestApplicationDto existingRestApplication = findRestApplication(existingRestApplications, newRestApplication.getName());


        if(existingRestApplication == null){
            resultRestApplication.add(newRestApplication);
            return;
        }

        final List<RestResourceDto> existingRestResources = new ArrayList<RestResourceDto>(existingRestApplication.getResources());
        final List<RestResourceDto> resultRestResources = new ArrayList<RestResourceDto>();
        for(RestResourceDto newRestResource : newRestApplication.getResources()) {
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
     * The method will add a new {@link RestResourceDto} and update an already existing {@link RestResourceDto}.
     * @param newRestResource The new {@link RestResourceDto} that might be added to the final list of {@link RestResourceDto} (resultRestApplication).
     * @param existingRestResources A list of existing {@link RestResourceDto}
     * @param resultRestResources A list of the result of {@link RestResourceDto}. These will be the new {@link RestResourceDto}.
     * @since 1.10
     */
    private void updateRestResource(final RestResourceDto newRestResource,
                                    final List<RestResourceDto> existingRestResources,
                                    final List<RestResourceDto> resultRestResources){
        // Check if the new REST resource already exists
        final RestResourceDto existingRestResource = findRestResource(existingRestResources, newRestResource.getName());

        // It doesn't exists. Simply add it to the existing application
        if (existingRestResource == null) {
            resultRestResources.add(newRestResource);
            return;
        }

        // Update resource
        existingRestResource.setUri(newRestResource.getUri());

        final List<RestMethodDto> existingRestMethods = new ArrayList<RestMethodDto>(existingRestResource.getMethods());
        final List<RestMethodDto> resultRestMethods = new ArrayList<RestMethodDto>();
        for(RestMethodDto newRestMethod : newRestResource.getMethods()){
            updateRestMethod(newRestMethod, existingRestMethods, resultRestMethods);
        }
        resultRestResources.add(existingRestResource);
        newRestResource.setMethods(resultRestMethods);
    }

    /**
     * The method will add a new {@link RestMethodDto} and update an already existing {@link RestMethodDto}.
     * @param newRestMethod The new {@link RestMethodDto} that might be added to the final list of {@link RestMethodDto} (resultRestApplication).
     * @param existingRestMethods A list of existing {@link RestMethodDto}
     * @param resultRestMethods A list of the result of {@link RestMethodDto}. These will be the new {@link RestMethodDto}.
     * @since 1.10
     */
    private void updateRestMethod(final RestMethodDto newRestMethod,
                                  final List<RestMethodDto> existingRestMethods,
                                  final List<RestMethodDto> resultRestMethods) {
        final RestMethodDto existingRestMethod = findRestMethod(existingRestMethods, newRestMethod.getName());

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
    public RestApplicationDto findRestApplication(List<RestApplicationDto> restApplications, String name){
        for(RestApplicationDto restApplication : restApplications){
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
    public RestResourceDto findRestResource(List<RestResourceDto> restResources, String name){
        for(RestResourceDto restResource : restResources){
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
    public RestMethodDto findRestMethod(List<RestMethodDto> restMethods, String name){
        for(RestMethodDto restMethod : restMethods){
            if(restMethod.getName().equals(name)){
                return restMethod;
            }
        }
        return null;
    }


}
