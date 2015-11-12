package com.fortmocks.mock.rest.model.project.message;

import com.fortmocks.core.model.Output;
import com.fortmocks.mock.rest.model.project.dto.RestProjectDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadRestProjectOutput implements Output{

    private RestProjectDto restProject;

    public RestProjectDto getRestProject() {
        return restProject;
    }

    public void setRestProject(RestProjectDto restProject) {
        this.restProject = restProject;
    }
}
