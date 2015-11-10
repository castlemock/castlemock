package com.fortmocks.mock.rest.model.project.message;

import com.fortmocks.core.model.Output;
import com.fortmocks.mock.rest.model.project.dto.RestProjectDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateRestProjectOutput implements Output {

    private RestProjectDto updatedRestProject;

    public RestProjectDto getUpdatedRestProject() {
        return updatedRestProject;
    }

    public void setUpdatedRestProject(RestProjectDto updatedRestProject) {
        this.updatedRestProject = updatedRestProject;
    }
}
