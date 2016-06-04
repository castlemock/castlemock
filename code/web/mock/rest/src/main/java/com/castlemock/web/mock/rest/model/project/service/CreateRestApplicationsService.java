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
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.core.mock.rest.model.project.domain.RestResource;
import com.castlemock.core.mock.rest.model.project.dto.RestApplicationDto;
import com.castlemock.core.mock.rest.model.project.dto.RestMethodDto;
import com.castlemock.core.mock.rest.model.project.dto.RestProjectDto;
import com.castlemock.core.mock.rest.model.project.dto.RestResourceDto;
import com.castlemock.core.mock.rest.model.project.service.message.input.CreateRestApplicationsInput;
import com.castlemock.core.mock.rest.model.project.service.message.output.CreateRestApplicationsOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class CreateRestApplicationsService extends AbstractRestProjectService implements Service<CreateRestApplicationsInput, CreateRestApplicationsOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<CreateRestApplicationsOutput> process(final ServiceTask<CreateRestApplicationsInput> serviceTask) {
        final CreateRestApplicationsInput input = serviceTask.getInput();
        final RestProjectDto restProject = repository.findOne(input.getRestProjectId());

        List<RestApplicationDto> restApplications = new ArrayList<RestApplicationDto>();
        for(RestApplicationDto newRestApplication : input.getRestApplications()){
            RestApplicationDto existingRestApplication = findRestApplication(restProject, newRestApplication.getName());

            if(existingRestApplication == null){
                restApplications.add(newRestApplication);
                continue;
            }

            List<RestResourceDto> restResources = new ArrayList<RestResourceDto>();
            for(RestResourceDto newRestResource : newRestApplication.getResources()){
                RestResourceDto existingRestResource = findRestResource(existingRestApplication, newRestResource.getName());

                if (existingRestResource == null) {
                    restResources.add(newRestResource);
                    continue;
                }

                existingRestResource.setUri(newRestResource.getUri());

                List<RestMethodDto> restMethods = new ArrayList<RestMethodDto>();
                for(RestMethodDto newRestMethod : newRestResource.getMethods()){
                    RestMethodDto existingRestMethod = findRestMethod(existingRestResource, newRestMethod.getName());

                    if (existingRestMethod == null) {
                        restMethods.add(newRestMethod);
                        continue;
                    }

                    existingRestMethod.setHttpMethod(newRestMethod.getHttpMethod());
                    restMethods.add(newRestMethod);
                }
                existingRestResource.setMethods(restMethods);
                restResources.add(existingRestResource);
            }
            existingRestApplication.setResources(restResources);
            restApplications.add(newRestApplication);
        }
        restProject.setApplications(restApplications);

        save(restProject);
        return createServiceResult(new CreateRestApplicationsOutput());
    }

    /**
     * Find a REST application with a specific name for a rest project
     * @param restProject The REST project that the application belongs to
     * @param name The name of the REST application
     * @return A REST application that matches the search criteria. Null otherwise.
     */
    public RestApplicationDto findRestApplication(RestProjectDto restProject, String name){
        for(RestApplicationDto restApplication : restProject.getApplications()){
            if(restApplication.getName().equals(name)){
                return restApplication;
            }
        }
        return null;
    }

    /**
     * Find a REST resource with a specific name for a REST application
     * @param restApplication The REST application that the resource belongs to
     * @param name The name of the REST resource
     * @return A REST resource that matches the search criteria. Null otherwise.
     */
    public RestResourceDto findRestResource(RestApplicationDto restApplication, String name){
        for(RestResourceDto restResource : restApplication.getResources()){
            if(restResource.getName().equals(name)){
                return restResource;
            }
        }
        return null;
    }

    /**
     * Find a REST method with a specific name for a REST resource
     * @param restResource The REST resource that the method belongs to
     * @param name The name of the REST method
     * @return A REST method that matches the search criteria. Null otherwise.
     */
    public RestMethodDto findRestMethod(RestResourceDto restResource, String name){
        for(RestMethodDto restMethod : restResource.getMethods()){
            if(restMethod.getName().equals(name)){
                return restMethod;
            }
        }
        return null;
    }
}
