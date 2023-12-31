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

package com.castlemock.model.core.service.event;

import com.castlemock.model.core.ServiceAdapter;
import com.castlemock.model.core.event.Event;

/**
 * The event service adapter provides the functionality to translate incoming
 * requests and transform them into correct service input messages
 * @author Karl Dahlgren
 * @since 1.0
 * @param <D> The dto event type
 * @see Event
 * @see Event
 */
public interface EventServiceAdapter<D extends Event> extends ServiceAdapter<Event, D, String> {


    /**
     * The method will clear and remove all previous events.
     * @since 1.7
     */
    void clearAll();

}
