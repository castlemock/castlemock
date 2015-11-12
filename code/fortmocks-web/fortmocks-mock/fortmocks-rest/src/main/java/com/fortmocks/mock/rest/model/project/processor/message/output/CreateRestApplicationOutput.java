package com.fortmocks.mock.rest.model.project.processor.message.output;

import com.fortmocks.core.model.Output;
import com.fortmocks.mock.rest.model.project.dto.RestApplicationDto;
import com.fortmocks.mock.rest.model.project.dto.RestProjectDto;
import com.fortmocks.core.model.validation.NotNull;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateRestApplicationOutput implements Output {

    @NotNull
    private RestApplicationDto savedRestApplication;

    public CreateRestApplicationOutput(RestApplicationDto savedRestApplication) {
        this.savedRestApplication = savedRestApplication;
    }

    public RestApplicationDto getSavedRestApplication() {
        return savedRestApplication;
    }

    public void setSavedRestApplication(RestApplicationDto savedRestApplication) {
        this.savedRestApplication = savedRestApplication;
    }
}
