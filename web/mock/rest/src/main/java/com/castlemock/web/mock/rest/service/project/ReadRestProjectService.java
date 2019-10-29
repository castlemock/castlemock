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
import com.castlemock.core.mock.rest.model.project.domain.RestMethodStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestApplication;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.core.mock.rest.service.project.input.ReadRestProjectInput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestProjectOutput;

import java.util.List;
import java.util.Map;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadRestProjectService extends AbstractRestProjectService implements Service<ReadRestProjectInput, ReadRestProjectOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<ReadRestProjectOutput> process(final ServiceTask<ReadRestProjectInput> serviceTask) {
        final ReadRestProjectInput input = serviceTask.getInput();
        final RestProject restProject = find(input.getRestProjectId());
        final List<RestApplication> applications = this.applicationRepository.findWithProjectId(input.getRestProjectId());
        restProject.setApplications(applications);
        for(final RestApplication restApplication : restProject.getApplications()){
            final Map<RestMethodStatus, Integer> soapOperationStatusCount = getRestMethodStatusCount(restApplication);
            restApplication.setStatusCount(soapOperationStatusCount);
        }
        return createServiceResult(ReadRestProjectOutput.builder()
                .restProject(restProject)
                .build());
    }
}
