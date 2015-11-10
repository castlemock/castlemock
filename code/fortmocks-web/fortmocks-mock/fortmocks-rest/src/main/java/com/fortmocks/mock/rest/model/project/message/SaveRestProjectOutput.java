package com.fortmocks.mock.rest.model.project.message;

import com.fortmocks.core.model.Output;
import com.fortmocks.mock.rest.model.project.dto.RestProjectDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SaveRestProjectOutput implements Output {

    private RestProjectDto savedRestProject;

    public RestProjectDto getSavedRestProject() {
        return savedRestProject;
    }

    public void setSavedRestProject(RestProjectDto savedRestProject) {
        this.savedRestProject = savedRestProject;
    }
}
