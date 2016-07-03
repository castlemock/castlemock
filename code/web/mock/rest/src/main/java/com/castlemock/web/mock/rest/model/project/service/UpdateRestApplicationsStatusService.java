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
import com.castlemock.core.mock.rest.model.project.service.message.input.UpdateRestApplicationsStatusInput;
import com.castlemock.core.mock.rest.model.project.service.message.output.UpdateRestApplicationsStatusOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class UpdateRestApplicationsStatusService extends AbstractRestProjectService implements Service<UpdateRestApplicationsStatusInput, UpdateRestApplicationsStatusOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<UpdateRestApplicationsStatusOutput> process(final ServiceTask<UpdateRestApplicationsStatusInput> serviceTask) {
        final UpdateRestApplicationsStatusInput input = serviceTask.getInput();
        final RestApplicationDto restApplication = repository.findRestApplication(input.getRestProjectId(), input.getRestApplicationId());
        for(RestResourceDto restResource : restApplication.getResources()){
            for(RestMethodDto restMethod : restResource.getMethods()){
                restMethod.setStatus(input.getRestMethodStatus());
                repository.updateRestMethod(input.getRestProjectId(), restApplication.getId(), restResource.getId(), restMethod.getId(), restMethod);
            }
        }
        return createServiceResult(new UpdateRestApplicationsStatusOutput());
    }
}
