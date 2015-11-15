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

package com.fortmocks.web.mock.rest.model.project.service;

import com.fortmocks.core.basis.model.Service;
import com.fortmocks.core.basis.model.ServiceResult;
import com.fortmocks.core.basis.model.ServiceTask;
import com.fortmocks.core.mock.rest.model.project.domain.RestApplication;
import com.fortmocks.core.mock.rest.model.project.dto.RestMethodDto;
import com.fortmocks.core.mock.rest.model.project.dto.RestResourceDto;
import com.fortmocks.core.mock.rest.model.project.service.message.input.CreateRestApplicationsInput;
import com.fortmocks.core.mock.rest.model.project.service.message.output.CreateRestApplicationsOutput;
import com.fortmocks.core.mock.rest.model.project.domain.RestProject;
import com.fortmocks.core.mock.rest.model.project.dto.RestApplicationDto;

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
        final RestProject restProject = findType(input.getRestProjectId());

        for(RestApplicationDto restApplicationDto : input.getRestApplications()){
            final Long restApplicationId = getNextRestApplicationId();
            restApplicationDto.setId(restApplicationId);

            for(RestResourceDto restResourceDto : restApplicationDto.getRestResources()){
                final Long restResourceId = getNextRestResourceId();
                restResourceDto.setId(restResourceId);

                for(RestMethodDto restMethodDto : restResourceDto.getRestMethods()){
                    final Long restMethodId = getNextRestMethodId();
                    restMethodDto.setId(restMethodId);
                }
            }
            RestApplication restApplication = mapper.map(restApplicationDto, RestApplication.class);
            restProject.getRestApplications().add(restApplication);
        }

        return createServiceResult(new CreateRestApplicationsOutput());
    }
}
