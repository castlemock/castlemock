package com.fortmocks.mock.rest.model.project.processor.message.input;

import com.fortmocks.core.model.Input;
import com.fortmocks.mock.rest.model.project.dto.RestApplicationDto;
import com.fortmocks.core.model.validation.NotNull;
import com.fortmocks.mock.rest.model.project.dto.RestResourceDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateRestResourceInput implements Input {

    @NotNull
    private Long restProjectId;
    @NotNull
    private Long restApplicationId;
    @NotNull
    private RestResourceDto restResource;

    public CreateRestResourceInput(Long restProjectId, Long restApplicationId, RestResourceDto restResource) {
        this.restProjectId = restProjectId;
        this.restApplicationId = restApplicationId;
        this.restResource = restResource;
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

    public RestResourceDto getRestResource() {
        return restResource;
    }

    public void setRestResource(RestResourceDto restResource) {
        this.restResource = restResource;
    }
}
