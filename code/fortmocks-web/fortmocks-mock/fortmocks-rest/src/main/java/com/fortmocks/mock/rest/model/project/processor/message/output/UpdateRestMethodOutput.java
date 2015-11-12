package com.fortmocks.mock.rest.model.project.processor.message.output;

import com.fortmocks.core.model.Output;
import com.fortmocks.core.model.validation.NotNull;
import com.fortmocks.mock.rest.model.project.dto.RestMethodDto;
import com.fortmocks.mock.rest.model.project.dto.RestProjectDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateRestMethodOutput implements Output {

    @NotNull
    private RestMethodDto restMethod;

    public RestMethodDto getRestMethod() {
        return restMethod;
    }

    public void setRestMethod(RestMethodDto restMethod) {
        this.restMethod = restMethod;
    }
}
