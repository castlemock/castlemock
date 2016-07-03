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
import com.castlemock.core.mock.rest.model.project.dto.RestResourceDto;
import com.castlemock.core.mock.rest.model.project.service.message.input.IdentifyRestMethodInput;
import com.castlemock.core.mock.rest.model.project.service.message.output.IdentifyRestMethodOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class IdentifyRestMethodService extends AbstractRestProjectService implements Service<IdentifyRestMethodInput, IdentifyRestMethodOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<IdentifyRestMethodOutput> process(final ServiceTask<IdentifyRestMethodInput> serviceTask) {
        final IdentifyRestMethodInput input = serviceTask.getInput();
        final String[] restResourceUriParts = input.getRestResourceUri().split(SLASH);
        final RestResourceDto restResource = findRestResource(input.getRestProjectId(), input.getRestApplicationId(), restResourceUriParts);
        RestMethodDto restMethodDto = null;
        if(restResource != null){
            for(RestMethodDto restMethod : restResource.getMethods()){
                if(input.getHttpMethod().equals(restMethod.getHttpMethod())) {
                    restMethodDto = restMethod;
                    break;
                }
            }
        }

        if(restMethodDto == null){
            throw new IllegalArgumentException("Unable to identify REST method: " + input.getRestResourceUri() + " (" + input.getHttpMethod() + ")");
        }

        return createServiceResult(new IdentifyRestMethodOutput(input.getRestProjectId(), input.getRestApplicationId(), restResource.getId(), restMethodDto.getId(),  restMethodDto));
    }

    /**
     * Find a REST resource with a project id, application id and a set of resource parts
     * @param restProjectId The id of the project that the resource belongs to
     * @param restApplicationId The id of the application that the resource belongs to
     * @param otherRestResourceUriParts The set of resources that will be used to identify the REST resource
     * @return A REST resource that matches the search criteria. Null otherwise
     */
    protected RestResourceDto findRestResource(final String restProjectId, final String restApplicationId, final String[] otherRestResourceUriParts) {
        final RestApplicationDto restApplication = repository.findRestApplication(restProjectId, restApplicationId);

        for(RestResourceDto restResource : restApplication.getResources()){
            final String[] restResourceUriParts = restResource.getUri().split(SLASH);

            if(compareRestResourceUri(restResourceUriParts, otherRestResourceUriParts)){
                return restResource;
            }
        }

        return null;
    }
}
