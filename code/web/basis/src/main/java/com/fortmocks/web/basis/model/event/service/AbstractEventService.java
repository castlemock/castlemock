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

package com.fortmocks.web.basis.model.event.service;

import com.fortmocks.core.basis.model.event.domain.Event;
import com.fortmocks.core.basis.model.event.dto.EventDto;
import com.fortmocks.web.basis.model.AbstractService;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public abstract class AbstractEventService<T extends Event, D extends EventDto> extends AbstractService<T,D,String> {

    /**
     * The service finds the
     * @return
     */
    protected EventDto getOldestEvent(){
        Event oldestEvent = null;
        for(Event event : findAllTypes()){
            if(oldestEvent == null){
                oldestEvent = event;
            } else if(event.getStartDate().before(oldestEvent.getStartDate())){
               oldestEvent = event;
            }
        }

        return oldestEvent == null ? null : mapper.map(oldestEvent, EventDto.class);
    }

}
