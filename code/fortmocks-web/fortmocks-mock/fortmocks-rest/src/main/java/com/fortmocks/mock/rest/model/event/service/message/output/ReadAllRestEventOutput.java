package com.fortmocks.mock.rest.model.event.service.message.output;

import com.fortmocks.core.model.Output;
import com.fortmocks.mock.rest.model.event.dto.RestEventDto;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadAllRestEventOutput implements Output {

    private List<RestEventDto> restEvents;

    public ReadAllRestEventOutput(List<RestEventDto> restEvents) {
        this.restEvents = restEvents;
    }

    public List<RestEventDto> getRestEvents() {
        return restEvents;
    }

    public void setRestEvents(List<RestEventDto> restEvents) {
        this.restEvents = restEvents;
    }
}
