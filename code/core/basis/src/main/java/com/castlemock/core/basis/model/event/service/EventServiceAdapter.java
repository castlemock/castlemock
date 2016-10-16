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

package com.castlemock.core.basis.model.event.service;

import com.castlemock.core.basis.model.ServiceAdapter;
import com.castlemock.core.basis.model.event.domain.Event;
import com.castlemock.core.basis.model.event.dto.EventDto;

/**
 * The event service adapter provides the functionality to translate incoming
 * requests and transform them into correct service input messages
 * @author Karl Dahlgren
 * @since 1.0
 * @param <D> The dto event type
 * @see Event
 * @see EventDto
 */
public interface EventServiceAdapter<D extends EventDto> extends ServiceAdapter<EventDto, D, String> {

    /**
     * The method provides the functionality to generate resource link for an incoming
     * event. The resource link will be based on event attributes.
     * @param event The incoming event which will be used to generate the resource link
     * @return The resource link generated based on the incoming event
     */
    String generateResourceLink(D event);


    /**
     * The method will clear and remove all previous events.
     * @since 1.7
     */
    void clearAll();

}
