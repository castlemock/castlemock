package com.fortmocks.core.mock.rest.model.event.service.message.input;

import com.fortmocks.core.basis.model.Input;
import com.fortmocks.core.basis.model.validation.NotNull;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadRestEventInput implements Input {

    @NotNull
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
