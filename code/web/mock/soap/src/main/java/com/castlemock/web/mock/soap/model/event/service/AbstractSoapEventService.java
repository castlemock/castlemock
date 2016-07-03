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

package com.castlemock.web.mock.soap.model.event.service;


import com.castlemock.core.mock.soap.model.event.domain.SoapEvent;
import com.castlemock.core.mock.soap.model.event.dto.SoapEventDto;
import com.castlemock.web.basis.model.event.service.AbstractEventService;
import com.castlemock.web.mock.soap.model.event.repository.SoapEventRepository;

import java.util.List;

/**
 * The SOAP event service is responsible for all the functionality related to the SOAP events.
 * @author Karl Dahlgren
 * @since 1.0
 */
public class AbstractSoapEventService extends AbstractEventService<SoapEvent, SoapEventDto, SoapEventRepository> {

    /**
     * The events for a specific operation id
     * @param operationId The id of the operation that the event belongs to
     * @return Returns a list of events
     */
    protected List<SoapEventDto> findEventsByOperationId(final String operationId){
        return repository.findEventsByOperationId(operationId);
    }

}

