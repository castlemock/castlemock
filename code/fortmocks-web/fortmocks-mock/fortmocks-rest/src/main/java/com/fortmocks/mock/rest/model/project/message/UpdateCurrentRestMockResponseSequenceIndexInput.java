package com.fortmocks.mock.rest.model.project.message;

import com.fortmocks.core.model.Input;
import com.fortmocks.core.model.validation.NotNull;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateCurrentRestMockResponseSequenceIndexInput implements Input {

    @NotNull
    private Long restMethodId;
    @NotNull
    private Integer currentRestMockResponseSequenceIndex;

    public Long getRestMethodId() {
        return restMethodId;
    }

    public void setRestMethodId(Long restMethodId) {
        this.restMethodId = restMethodId;
    }

    public Integer getCurrentRestMockResponseSequenceIndex() {
        return currentRestMockResponseSequenceIndex;
    }

    public void setCurrentRestMockResponseSequenceIndex(Integer currentRestMockResponseSequenceIndex) {
        this.currentRestMockResponseSequenceIndex = currentRestMockResponseSequenceIndex;
    }
}
