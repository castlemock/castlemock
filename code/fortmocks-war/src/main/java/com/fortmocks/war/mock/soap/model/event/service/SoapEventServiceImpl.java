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

package com.fortmocks.war.mock.soap.model.event.service;

import com.fortmocks.core.base.model.TypeIdentifier;
import com.fortmocks.core.base.model.event.dto.EventDto;
import com.fortmocks.core.base.model.event.service.EventServiceFacade;
import com.fortmocks.core.mock.soap.model.event.SoapEvent;
import com.fortmocks.core.mock.soap.model.event.dto.SoapEventDto;
import com.fortmocks.core.mock.soap.model.event.service.SoapEventService;
import com.fortmocks.war.base.model.event.service.EventServiceImpl;
import com.fortmocks.war.mock.soap.model.SoapTypeIdentifier;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The SOAP event service is responsible for all the functionality related to the SOAP events.
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class SoapEventServiceImpl extends EventServiceImpl<SoapEvent, SoapEventDto> implements SoapEventService {

    private static final SoapTypeIdentifier SOAP_TYPE_IDENTIFIER = new SoapTypeIdentifier();

    /**
     * The events for a specific operation id
     * @param operationId The id of the operation that the event belongs to
     * @return Returns a list of events
     */
    @Override
    public List<SoapEventDto> findEventsByOperationId(final Long operationId){
        final List<SoapEvent> events = new ArrayList<SoapEvent>();
        for(SoapEvent event : findAllTypes()){
            if(event.getSoapOperationId().equals(operationId)){
                events.add(event);
            }
        }
        return toDtoList(events);
    }

    /**
     * Returns the SOAP type identifier.
     * @return The SOAP identifier
     */
    @Override
    public TypeIdentifier getTypeIdentifier() {
        return SOAP_TYPE_IDENTIFIER;
    }

    /**
     * The method is responsible for converting an event dto instance into a event dto subclass.
     * This is used when the {@link com.fortmocks.war.base.model.project.service.ProjectServiceFacadeImpl} needs
     * to manage base event class, but wants to be able to convert it into a specific subclass, for example when
     * creating or updating a event instance.
     * @param eventDto The event dto instance that will be converted into a event dto subclass
     * @return The converted event dto subclass
     * @throws java.lang.NullPointerException Throws NullPointerException in case if the provided event dto instance is null.
     * @see EventServiceFacade
     */
    @Override
    public SoapEventDto convertType(EventDto eventDto) {
        Preconditions.checkNotNull("Event DTO cannot be null", eventDto);
        return new SoapEventDto(eventDto);
    }
}

