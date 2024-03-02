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
import com.castlemock.model.mock.rest.domain.RestProject;
import com.castlemock.service.mock.rest.project.input.UpdateRestProjectInput;
import com.castlemock.service.mock.rest.project.output.UpdateRestProjectOutput;

import java.util.Date;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class UpdateRestProjectService extends AbstractRestProjectService implements Service<UpdateRestProjectInput, UpdateRestProjectOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<UpdateRestProjectOutput> process(final ServiceTask<UpdateRestProjectInput> serviceTask) {
        final UpdateRestProjectInput input = serviceTask.getInput();
        final String restProjectId = input.getProjectId();
        final Optional<RestProject> updatedRestProject = find(restProjectId)
                .map(project -> project.toBuilder()
                        .name(input.getName())
                        .description(input.getDescription().orElse(null))
                        .updated(new Date())
                        .build())
                .flatMap(project -> update(restProjectId, project));
        return createServiceResult(UpdateRestProjectOutput.builder()
                .project(updatedRestProject.orElse(null))
                .build());
    }
}
