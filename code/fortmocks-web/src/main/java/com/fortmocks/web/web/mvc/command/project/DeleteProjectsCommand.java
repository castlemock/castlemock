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
 * The DeleteProjectsCommand is a command class and is used to identify which projects
 * will be deleted from the server.
 * @author Karl Dahlgren
 * @since 1.0
 * @see Project
 * @see ProjectDto
 */
public class DeleteProjectsCommand {

    private String[] projectIds;
    private String[] typeUrls;

    public String[] getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(String[] projectIds) {
        this.projectIds = projectIds;
    }

    public String[] getTypeUrls() {
        return typeUrls;
    }

    public void setTypeUrls(String[] typeUrls) {
        this.typeUrls = typeUrls;
    }
}

