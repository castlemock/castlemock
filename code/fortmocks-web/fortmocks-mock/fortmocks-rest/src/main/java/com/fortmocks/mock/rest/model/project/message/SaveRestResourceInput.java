package com.fortmocks.mock.rest.model.project.message;

import com.fortmocks.core.model.Input;
import com.fortmocks.mock.rest.model.project.dto.RestApplicationDto;
import com.sun.istack.internal.NotNull;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SaveRestResourceInput implements Input {

    @NotNull
    private Long restProjectId;
    @NotNull
    private Long restApplicationId;
    @NotNull
    private RestApplicationDto restApplication;

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

    public RestApplicationDto getRestApplication() {
        return restApplication;
    }

    public void setRestApplication(RestApplicationDto restApplication) {
        this.restApplication = restApplication;
    }
}
