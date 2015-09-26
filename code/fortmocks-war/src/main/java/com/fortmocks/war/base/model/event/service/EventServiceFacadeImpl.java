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

package com.fortmocks.war.base.model.event.service;

import com.fortmocks.core.base.model.event.Event;
import com.fortmocks.core.base.model.event.dto.EventDto;
import com.fortmocks.war.base.model.ServiceFacadeImpl;
import org.springframework.stereotype.Service;

/**
 * The Event service component is used to assembly all the events service layers and interact with them
 * in order to retrieve a unified answer independent of the event type.
 * @author Karl Dahlgren
 * @since 1.0
 * @see com.fortmocks.core.base.model.event.Event
 * @see com.fortmocks.core.base.model.event.dto.EventDto
 */
@Service
public class EventServiceFacadeImpl extends ServiceFacadeImpl<Event, EventDto, Long, EventServiceImpl<Event, EventDto>> implements EventServiceFacade {

    /**
     * The initiate method is responsible for for locating all the service instances for a specific module
     * and organizing them depending on the type.
     * @see com.fortmocks.core.base.model.Service
     * @see com.fortmocks.core.base.model.TypeIdentifier
     * @see com.fortmocks.core.base.model.TypeIdentifiable
     */
    @Override
    public void initiate(){
        initiate(EventServiceImpl.class);
    }
}
