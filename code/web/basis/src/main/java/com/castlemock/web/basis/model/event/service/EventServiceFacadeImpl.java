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

package com.castlemock.web.basis.model.event.service;

import com.castlemock.core.basis.model.TypeIdentifiable;
import com.castlemock.core.basis.model.TypeIdentifier;
import com.castlemock.core.basis.model.event.domain.Event;
import com.castlemock.core.basis.model.event.dto.EventDto;
import com.castlemock.core.basis.model.event.dto.EventDtoStartDateComparator;
import com.castlemock.core.basis.model.event.service.EventServiceAdapter;
import com.castlemock.core.basis.model.event.service.EventServiceFacade;
import com.castlemock.web.basis.model.ServiceFacadeImpl;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * The Event service component is used to assembly all the events service layers and interact with them
 * in order to retrieve a unified answer independent of the event type.
 * @author Karl Dahlgren
 * @since 1.0
 * @see Event
 * @see EventDto
 */
@Service
public class EventServiceFacadeImpl extends ServiceFacadeImpl<EventDto, String, EventServiceAdapter<EventDto>> implements EventServiceFacade {

    /**
     * The initialize method is responsible for for locating all the service instances for a specific module
     * and organizing them depending on the type.
     * @see com.castlemock.core.basis.model.Service
     * @see TypeIdentifier
     * @see TypeIdentifiable
     */
    @Override
    public void initiate(){
        initiate(EventServiceAdapter.class);
    }

    /**
     * The method is responsible for retrieving all instances of events and its subclasses. The events
     * will be sorted based on their start date. The newest will be the first object in the list and the
     * oldest will be in the end of the list.
     * @return A list containing all the event instances
     * @since 1.3
     */
    @Override
    public List<EventDto> findAll(){
        final List<EventDto> events = super.findAll();
        Collections.sort(events, new EventDtoStartDateComparator());
        return events;
    }

    public void clearAll(){
        for(Map.Entry<String, EventServiceAdapter<EventDto>> entry : services.entrySet()){
            EventServiceAdapter<EventDto> eventDtoEventServiceAdapter = entry.getValue();
            eventDtoEventServiceAdapter.clearAll();
        }
    }
}
