package com.fortmocks.mock.rest.model.project.processor.message.input;

import com.fortmocks.core.model.Input;
import com.fortmocks.core.model.validation.NotNull;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class DeleteRestMockResponseInput implements Input{

    @NotNull
    private Long restProjectId;
    @NotNull
    private Long restApplicationId;
    @NotNull
    private Long restResourceId;
    @NotNull
    private Long restMethodId;
    @NotNull
    private Long restMockResponseId;

    public DeleteRestMockResponseInput(Long restProjectId, Long restApplicationId, Long restResourceId, Long restMethodId, Long restMockResponseId) {
        this.restProjectId = restProjectId;
        this.restApplicationId = restApplicationId;
        this.restResourceId = restResourceId;
        this.restMethodId = restMethodId;
        this.restMockResponseId = restMockResponseId;
    }

    public Long getRestProjectId() {
        return restProjectId;
    }

    public void setRestProjectId(Long restProjectId) {
        this.restProjectId = restProjectId;
    }

    public Long getRestApplicationId() {
        return restApplicationId;
    }

    public void setRestApplicationId(Long restApplicationId) {
        this.restApplicationId = restApplicationId;
    }

    public Long getRestResourceId() {
        return restResourceId;
    }

    public void setRestResourceId(Long restResourceId) {
        this.restResourceId = restResourceId;
    }

    public Long getRestMethodId() {
        return restMethodId;
    }

    public void setRestMethodId(Long restMethodId) {
        this.restMethodId = restMethodId;
    }

    public Long getRestMockResponseId() {
        return restMockResponseId;
    }

    public void setRestMockResponseId(Long restMockResponseId) {
        this.restMockResponseId = restMockResponseId;
    }
}
