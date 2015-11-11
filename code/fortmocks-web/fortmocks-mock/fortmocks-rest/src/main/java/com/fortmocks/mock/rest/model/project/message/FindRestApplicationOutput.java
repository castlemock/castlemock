package com.fortmocks.mock.rest.model.project.message;

import com.fortmocks.core.model.Output;
import com.fortmocks.mock.rest.model.project.dto.RestApplicationDto;
import com.fortmocks.mock.rest.model.project.dto.RestProjectDto;
import com.sun.istack.internal.NotNull;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class FindRestApplicationOutput implements Output{

    @NotNull
    private RestApplicationDto restApplication;

    public RestApplicationDto getRestApplication() {
        return restApplication;
    }

    public void setRestApplication(RestApplicationDto restApplication) {
        this.restApplication = restApplication;
    }
}
