package com.fortmocks.mock.rest.model.event.service.message.input;

import com.fortmocks.core.basis.model.Input;
import com.fortmocks.core.basis.model.validation.NotNull;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadRestEventWithMethodIdInput implements Input {

    @NotNull
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
