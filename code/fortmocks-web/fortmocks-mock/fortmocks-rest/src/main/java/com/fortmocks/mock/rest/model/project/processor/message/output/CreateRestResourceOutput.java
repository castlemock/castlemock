package com.fortmocks.mock.rest.model.project.processor.message.output;

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
    private RestResourceDto createdRestResource;

    public CreateRestResourceOutput(RestResourceDto createdRestResource) {
        this.createdRestResource = createdRestResource;
    }

    public RestResourceDto getCreatedRestResource() {
        return createdRestResource;
    }

    public void setCreatedRestResource(RestResourceDto createdRestResource) {
        this.createdRestResource = createdRestResource;
    }
}
