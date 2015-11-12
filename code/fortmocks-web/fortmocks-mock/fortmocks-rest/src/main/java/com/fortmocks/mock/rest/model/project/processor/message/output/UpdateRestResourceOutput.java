package com.fortmocks.mock.rest.model.project.processor.message.output;

import com.fortmocks.core.model.Output;
import com.fortmocks.core.model.validation.NotNull;
import com.fortmocks.mock.rest.model.project.dto.RestApplicationDto;
import com.fortmocks.mock.rest.model.project.dto.RestResourceDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateRestResourceOutput implements Output {

    @NotNull
    private RestResourceDto updatedRestResource;

    public RestResourceDto getUpdatedRestResource() {
        return updatedRestResource;
    }

    public void setUpdatedRestResource(RestResourceDto updatedRestResource) {
        this.updatedRestResource = updatedRestResource;
    }
}
