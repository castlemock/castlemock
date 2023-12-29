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

package com.castlemock.service.core.event;

import com.castlemock.model.core.event.Event;
import com.castlemock.model.core.event.OverviewEvent;
import com.castlemock.model.core.service.event.EventServiceAdapter;
import com.castlemock.model.core.service.event.EventServiceFacade;
import com.castlemock.service.core.ServiceFacadeImpl;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Event service component is used to assembly all the events service layers and interact with them
 * in order to retrieve a unified answer independent of the event type.
 * @author Karl Dahlgren
 * @since 1.0
 * @see Event
 * @see Event
 */
@Service
public class EventServiceFacadeImpl extends ServiceFacadeImpl<Event, String, EventServiceAdapter<Event>> implements EventServiceFacade {

    /**
     * The initialize method is responsible for for locating all the service instances for a specific module
     * and organizing them depending on the type.
     * @see com.castlemock.model.core.Service
     */
    @Override
    public void initiate(){
        super.initiate(EventServiceAdapter.class);
    }

    @Override
    public void clearAll(){
        this.services.forEach(EventServiceAdapter::clearAll);
    }

    @Override
    public List<OverviewEvent> findAll() {
        return services.stream()
                .map(adapter -> adapter.readAll()
                        .stream()
                        .map(event -> OverviewEvent.toBuilder(event)
                                .type(adapter.getType())
                                .build())
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
