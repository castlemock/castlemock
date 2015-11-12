package com.fortmocks.mock.rest.model.project.processor.message.output;

import com.fortmocks.core.model.Output;
import com.fortmocks.mock.rest.model.project.dto.RestMethodDto;
import com.fortmocks.mock.rest.model.project.dto.RestProjectDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadRestMethodWithMethodTypeOutput implements Output{

    private RestMethodDto restMethod;

    public ReadRestMethodWithMethodTypeOutput(RestMethodDto restMethod) {
        this.restMethod = restMethod;
    }

    public RestMethodDto getRestMethod() {
        return restMethod;
    }

    public void setRestMethod(RestMethodDto restMethod) {
        this.restMethod = restMethod;
    }
}
