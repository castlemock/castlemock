package com.fortmocks.mock.rest.model.project.processor.message.output;

import com.fortmocks.core.model.Output;
import com.fortmocks.core.model.validation.NotNull;
import com.fortmocks.mock.rest.model.project.dto.RestApplicationDto;
import com.fortmocks.mock.rest.model.project.dto.RestProjectDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateRestApplicationOutput implements Output {

    @NotNull
    private RestApplicationDto updatedRestApplication;

    public RestApplicationDto getUpdatedRestApplication() {
        return updatedRestApplication;
    }

    public void setUpdatedRestApplication(RestApplicationDto updatedRestApplication) {
        this.updatedRestApplication = updatedRestApplication;
    }
}
