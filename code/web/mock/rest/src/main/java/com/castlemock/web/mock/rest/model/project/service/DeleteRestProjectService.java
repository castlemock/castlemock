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
import com.castlemock.core.mock.rest.model.project.dto.RestProjectDto;
import com.castlemock.core.mock.rest.model.project.service.message.input.DeleteRestProjectInput;
import com.castlemock.core.mock.rest.model.project.service.message.output.DeleteRestProjectOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class DeleteRestProjectService extends AbstractRestProjectService implements Service<DeleteRestProjectInput, DeleteRestProjectOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<DeleteRestProjectOutput> process(final ServiceTask<DeleteRestProjectInput> serviceTask) {
        final DeleteRestProjectInput input = serviceTask.getInput();
        final String restProjectId = input.getRestProjectId();
        final RestProjectDto restProject = deleteProject(restProjectId);
        return createServiceResult(new DeleteRestProjectOutput(restProject));
    }
}
