package com.fortmocks.mock.rest.model.project.message;

import com.fortmocks.core.model.Output;
import com.fortmocks.mock.rest.model.project.dto.RestApplicationDto;
import com.fortmocks.mock.rest.model.project.dto.RestResourceDto;
import com.fortmocks.core.model.validation.NotNull;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateRestResourceOutput implements Output {

    @NotNull
    private RestResourceDto savedRestResource;

    public RestResourceDto getSavedRestResource() {
        return savedRestResource;
    }

    public void setSavedRestResource(RestResourceDto savedRestResource) {
        this.savedRestResource = savedRestResource;
    }
}
