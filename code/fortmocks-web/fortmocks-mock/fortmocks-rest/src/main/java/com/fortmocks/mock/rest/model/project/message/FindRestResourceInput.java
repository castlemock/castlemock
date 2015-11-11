package com.fortmocks.mock.rest.model.project.message;

import com.fortmocks.core.model.Input;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class FindRestResourceInput implements Input {

    private Long restProjectId;
    private Long restApplicationId;
    private Long restResourceId;

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
}
