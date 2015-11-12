package com.fortmocks.mock.rest.model.project.message;

import com.fortmocks.core.model.Input;
import com.fortmocks.mock.rest.model.project.dto.RestProjectDto;
import com.fortmocks.core.model.validation.NotNull;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateRestProjectInput implements Input {

    @NotNull
    private RestProjectDto restProject;

    public RestProjectDto getRestProject() {
        return restProject;
    }

    public void setRestProject(RestProjectDto restProject) {
        this.restProject = restProject;
    }
}
