package com.fortmocks.mock.rest.model.project.message;

import com.fortmocks.core.model.Output;
import com.fortmocks.mock.rest.model.project.dto.RestProjectDto;
import com.fortmocks.mock.rest.model.project.dto.RestResourceDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadRestResourceWithUriOutput implements Output{

    private RestResourceDto restResource;

    public RestResourceDto getRestResource() {
        return restResource;
    }

    public void setRestResource(RestResourceDto restResource) {
        this.restResource = restResource;
    }
}
