package com.fortmocks.mock.rest.model.project.message;

import com.fortmocks.core.model.Output;
import com.fortmocks.mock.rest.model.project.dto.RestProjectDto;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class FindAllRestProjectsOutput implements Output {

    private List<RestProjectDto> restProjects;

    public List<RestProjectDto> getRestProjects() {
        return restProjects;
    }

    public void setRestProjects(List<RestProjectDto> restProjects) {
        this.restProjects = restProjects;
    }
}
