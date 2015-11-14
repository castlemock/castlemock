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

package com.fortmocks.core.mock.rest.model.event.service;

import com.fortmocks.core.mock.rest.model.event.dto.RestEventDto;
import com.fortmocks.core.mock.rest.model.event.domain.RestEvent;
import com.fortmocks.web.basis.model.AbstractProcessor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The REST event service is responsible for all the functionality related to the REST events.
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class AbstractRestEventProcessor extends AbstractProcessor<RestEvent, RestEventDto, Long>{


    public List<RestEventDto> findEventsByMethodId(Long restMethodId) {
        final List<RestEvent> events = new ArrayList<RestEvent>();
        for(RestEvent event : findAllTypes()){
            if(event.getRestMethodId().equals(restMethodId)){
                events.add(event);
            }
        }
        return toDtoList(events);
    }
}

