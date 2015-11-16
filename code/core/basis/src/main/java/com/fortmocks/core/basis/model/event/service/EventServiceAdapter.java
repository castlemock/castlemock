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

package com.fortmocks.core.basis.model.event.service;

import com.fortmocks.core.basis.model.Saveable;
import com.fortmocks.core.basis.model.event.domain.Event;
import com.fortmocks.core.basis.model.event.dto.EventDto;
import com.fortmocks.core.basis.model.ServiceAdapter;

/**
 * The event service is responsible for providing the basic functionality for all the
 * event services.
 * @author Karl Dahlgren
 * @since 1.0
 * @param <D> The dto event type
 * @see Event
 * @see EventDto
 */
public interface EventServiceAdapter<D extends EventDto> extends ServiceAdapter<EventDto, D, Long> {

}