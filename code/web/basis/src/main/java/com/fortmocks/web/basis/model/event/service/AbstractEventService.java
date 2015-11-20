package com.fortmocks.web.basis.model.event.service;

import com.fortmocks.core.basis.model.event.domain.Event;
import com.fortmocks.core.basis.model.event.dto.EventDto;
import com.fortmocks.web.basis.model.AbstractService;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public abstract class AbstractEventService<T extends Event, D extends EventDto> extends AbstractService<T,D,Long> {

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
