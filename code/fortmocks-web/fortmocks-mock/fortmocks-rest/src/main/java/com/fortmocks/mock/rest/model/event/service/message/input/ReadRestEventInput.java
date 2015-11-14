package com.fortmocks.mock.rest.model.event.service.message.input;

import com.fortmocks.core.model.Input;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadRestEventInput implements Input {

    private Long restEventId;

    public ReadRestEventInput(Long restEventId) {
        this.restEventId = restEventId;
    }

    public Long getRestEventId() {
        return restEventId;
    }

    public void setRestEventId(Long restEventId) {
        this.restEventId = restEventId;
    }
}
