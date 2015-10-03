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

import com.fortmocks.core.base.model.TypeConverter;
import com.fortmocks.core.base.model.TypeIdentifiable;
import com.fortmocks.core.base.model.TypeIdentifier;
import com.fortmocks.core.base.model.event.Event;
import com.fortmocks.core.base.model.event.dto.EventDto;
import com.fortmocks.core.base.model.event.service.EventService;
import com.fortmocks.war.base.model.ServiceImpl;

/**
 * The event service implementation is an abstract class that all the event service inherit. The
 * class contains functionality that is shared among all the event services.
 * @author Karl Dahlgren
 * @since 1.0
 * @param <T> The event type
 * @param <D> The dto event type
 * @see com.fortmocks.core.base.model.event.Event
 * @see com.fortmocks.core.base.model.event.dto.EventDto
 */
public abstract class EventServiceImpl<T extends Event, D extends EventDto> extends ServiceImpl<T, D, Long> implements EventService<T, D>, TypeIdentifiable, TypeConverter<EventDto, D> {
    @Override
    public void setTypeIdentifier(TypeIdentifier typeIdentifier) {
        throw new UnsupportedOperationException();
    }
}
