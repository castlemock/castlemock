package com.fortmocks.mock.rest.model.project.processor.message.input;

import com.fortmocks.core.model.Input;
import com.fortmocks.core.model.validation.NotNull;
import com.fortmocks.mock.rest.model.project.dto.RestResourceDto;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class DeleteRestResourcesInput implements Input{

    @NotNull
    private Long restProjectId;
    @NotNull
    private Long restApplicationId;
    @NotNull
    private List<RestResourceDto> restResources;

    public DeleteRestResourcesInput(Long restProjectId, Long restApplicationId, List<RestResourceDto> restResources) {
        this.restProjectId = restProjectId;
        this.restApplicationId = restApplicationId;
        this.restResources = restResources;
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

    public List<RestResourceDto> getRestResources() {
        return restResources;
    }

    public void setRestResources(List<RestResourceDto> restResources) {
        this.restResources = restResources;
    }
}
