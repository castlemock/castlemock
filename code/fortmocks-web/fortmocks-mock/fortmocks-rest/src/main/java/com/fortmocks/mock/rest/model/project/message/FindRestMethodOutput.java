package com.fortmocks.mock.rest.model.project.message;

import com.fortmocks.core.model.Output;
import com.fortmocks.mock.rest.model.project.dto.RestProjectDto;
import com.sun.istack.internal.NotNull;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class FindRestMethodOutput implements Output{

    @NotNull
    private RestProjectDto restProject;

    public RestProjectDto getRestProject() {
        return restProject;
    }

    public void setRestProject(RestProjectDto restProject) {
        this.restProject = restProject;
    }
}
