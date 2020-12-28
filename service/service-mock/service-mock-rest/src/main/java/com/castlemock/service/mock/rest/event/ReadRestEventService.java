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

package com.castlemock.service.mock.rest.event;

import com.castlemock.model.core.model.Service;
import com.castlemock.model.core.model.ServiceResult;
import com.castlemock.model.core.model.ServiceTask;
import com.castlemock.model.mock.rest.domain.RestEvent;
import com.castlemock.service.mock.rest.event.input.ReadRestEventInput;
import com.castlemock.service.mock.rest.event.output.ReadRestEventOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadRestEventService extends AbstractRestEventService implements Service<ReadRestEventInput, ReadRestEventOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<ReadRestEventOutput> process(ServiceTask<ReadRestEventInput> serviceTask) {
        final ReadRestEventInput input = serviceTask.getInput();
        final RestEvent restEvent = find(input.getRestEventId());
        return createServiceResult(ReadRestEventOutput.builder()
                .restEvent(restEvent)
                .build());
    }
}
