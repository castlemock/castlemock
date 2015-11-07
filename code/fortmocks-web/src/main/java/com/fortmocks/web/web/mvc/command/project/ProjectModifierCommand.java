/*
 * Copyright 2015 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fortmocks.web.web.mvc.command.project;

import com.fortmocks.core.model.project.Project;
import com.fortmocks.core.model.project.dto.ProjectDto;

/**
 * The ProjectModifierCommand is a command class that is used to identify which
 * projects should be updated with a new status
 * @author Karl Dahlgren
 * @since 1.0
 * @see Project
 * @see ProjectDto
 */
public class ProjectModifierCommand {

    private String[] projects;
    private String projectStatus;

    public String[] getProjects() {
        return projects;
    }

    public void setProjects(String[] projects) {
        this.projects = projects;
    }

    /**
     * Returns the status which wil be the new status for the projects in the projectIds list
     * @return Return the status that will be the new status for all the projects in the projectIds list
     */
    public String getProjectStatus() {
        return projectStatus;
    }

    /**
     * Sets a new value to the status
     * @param projectStatus The new value for the status
     */
    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }
}
