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

package com.fortmocks.web.mock.rest.model.project;

import com.fortmocks.core.mock.rest.model.project.domain.RestApplication;
import com.fortmocks.core.mock.rest.model.project.domain.RestProject;
import com.fortmocks.core.mock.rest.model.project.dto.RestApplicationDto;
import com.fortmocks.core.mock.rest.model.project.dto.RestProjectDto;

import java.util.ArrayList;
import java.util.Date;


/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class RestProjectDtoGenerator {

    public static RestProjectDto generateRestProjectDto(){
        final RestProjectDto projectDto = new RestProjectDto();
        projectDto.setId("REST PROJECT");
        projectDto.setName("Project name");
        projectDto.setDescription("Project description");
        projectDto.setCreated(new Date());
        projectDto.setUpdated(new Date());
        projectDto.setApplications(new ArrayList<RestApplicationDto>());
        return projectDto;
    }

    public static RestProject generateRestProject(){
        final RestProject projectDto = new RestProject();
        projectDto.setId("REST PROJECT");
        projectDto.setName("Project name");
        projectDto.setDescription("Project description");
        projectDto.setCreated(new Date());
        projectDto.setUpdated(new Date());
        projectDto.setApplications(new ArrayList<RestApplication>());
        return projectDto;
    }
}
