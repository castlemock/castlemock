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

package com.castlemock.web.mock.rest.model.event.service;

import com.castlemock.core.basis.model.Service;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.basis.model.event.dto.EventDtoStartDateComparator;
import com.castlemock.core.mock.rest.model.event.dto.RestEventDto;
import com.castlemock.core.mock.rest.model.event.service.message.input.ReadRestEventWithMethodIdInput;
import com.castlemock.core.mock.rest.model.event.service.message.output.ReadRestEventWithMethodIdOutput;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;

import java.util.Collections;
import java.util.List;

/**
 * The service provides the functionality to retrieve a list of events that belongs to a specific REST method.
 * @author Karl Dahlgren
 * @since 1.0
 * @see RestMethod
 */
@org.springframework.stereotype.Service
public class ReadRestEventWithMethodIdService extends AbstractRestEventService implements Service<ReadRestEventWithMethodIdInput, ReadRestEventWithMethodIdOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<ReadRestEventWithMethodIdOutput> process(ServiceTask<ReadRestEventWithMethodIdInput> serviceTask) {
        final ReadRestEventWithMethodIdInput input = serviceTask.getInput();
        final List<RestEventDto> events = repository.findEventsByMethodId(input.getRestMethodId());
        Collections.sort(events, new EventDtoStartDateComparator());
        return createServiceResult(new ReadRestEventWithMethodIdOutput(events));
    }
}
