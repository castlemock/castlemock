package com.fortmocks.mock.rest.model.event.service.message.input;

import com.fortmocks.core.model.Input;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadRestEventWithMethodIdInput implements Input {

    private Long restMethodId;

    public ReadRestEventWithMethodIdInput(Long restMethodId) {
        this.restMethodId = restMethodId;
    }

    public Long getRestMethodId() {
        return restMethodId;
    }

    public void setRestMethodId(Long restMethodId) {
        this.restMethodId = restMethodId;
    }
}
