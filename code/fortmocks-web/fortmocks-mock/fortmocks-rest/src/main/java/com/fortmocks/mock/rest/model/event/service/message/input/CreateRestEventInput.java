package com.fortmocks.mock.rest.model.event.service.message.input;

import com.fortmocks.core.model.Input;
import com.fortmocks.mock.rest.model.event.dto.RestEventDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateRestEventInput implements Input {

    private RestEventDto restEvent;

    public CreateRestEventInput(RestEventDto restEvent) {
        this.restEvent = restEvent;
    }

    public RestEventDto getRestEvent() {
        return restEvent;
    }

    public void setRestEvent(RestEventDto restEvent) {
        this.restEvent = restEvent;
    }
}
