package com.fortmocks.mock.rest.model.project.processor.message.output;

import com.fortmocks.core.model.Output;
import com.fortmocks.core.model.validation.NotNull;
import com.fortmocks.mock.rest.model.project.dto.RestMethodDto;
import com.fortmocks.mock.rest.model.project.dto.RestProjectDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateRestMethodOutput implements Output {

    @NotNull
    private RestMethodDto createdRestMethod;

    public CreateRestMethodOutput(RestMethodDto createdRestMethod) {
        this.createdRestMethod = createdRestMethod;
    }

    public RestMethodDto getCreatedRestMethod() {
        return createdRestMethod;
    }

    public void setCreatedRestMethod(RestMethodDto createdRestMethod) {
        this.createdRestMethod = createdRestMethod;
    }
}
