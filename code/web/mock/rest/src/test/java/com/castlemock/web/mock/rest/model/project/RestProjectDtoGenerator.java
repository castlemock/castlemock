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

package com.castlemock.web.mock.rest.model.project;

import com.castlemock.core.mock.rest.model.project.domain.*;
import com.castlemock.core.mock.rest.model.project.dto.*;

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

    public static RestProject generateFullRestProject(){
        final RestProject projectDto = new RestProject();
        projectDto.setId("REST PROJECT");
        projectDto.setName("Project name");
        projectDto.setDescription("Project description");
        projectDto.setCreated(new Date());
        projectDto.setUpdated(new Date());
        projectDto.setApplications(new ArrayList<RestApplication>());

        for(int applicationIndex = 0; applicationIndex < 3; applicationIndex++){
            final RestApplication restApplication = RestApplicationDtoGenerator.generateRestApplication();
            restApplication.setResources(new ArrayList<RestResource>());
            projectDto.getApplications().add(restApplication);

            for(int resourceIndex = 0; resourceIndex < 3; resourceIndex++){
                final RestResource restResource = RestResourceDtoGenerator.generateRestResource();
                restResource.setMethods(new ArrayList<RestMethod>());
                restApplication.getResources().add(restResource);

                for(int methodIndex = 0; methodIndex < 3; methodIndex++){
                    final RestMethod restMethod = RestMethodDtoGenerator.generateRestMethod();
                    restMethod.setMockResponses(new ArrayList<RestMockResponse>());
                    restResource.getMethods().add(restMethod);

                    for(int responseIndex = 0; responseIndex < 3; responseIndex++){
                        final RestMockResponse restMockResponse = RestMockResponseDtoGenerator.generateRestMockResponse();
                        restMethod.getMockResponses().add(restMockResponse);
                    }
                }

            }
        }

        return projectDto;
    }

    public static RestProjectDto generateFullRestProjectDto(){
        final RestProjectDto projectDto = new RestProjectDto();
        projectDto.setId("REST PROJECT");
        projectDto.setName("Project name");
        projectDto.setDescription("Project description");
        projectDto.setCreated(new Date());
        projectDto.setUpdated(new Date());
        projectDto.setApplications(new ArrayList<RestApplicationDto>());

        for(int applicationIndex = 0; applicationIndex < 3; applicationIndex++){
            final RestApplicationDto restApplication = RestApplicationDtoGenerator.generateRestApplicationDto();
            restApplication.setResources(new ArrayList<RestResourceDto>());
            projectDto.getApplications().add(restApplication);

            for(int resourceIndex = 0; resourceIndex < 3; resourceIndex++){
                final RestResourceDto restResource = RestResourceDtoGenerator.generateRestResourceDto();
                restResource.setMethods(new ArrayList<RestMethodDto>());
                restApplication.getResources().add(restResource);

                for(int methodIndex = 0; methodIndex < 3; methodIndex++){
                    final RestMethodDto restMethod = RestMethodDtoGenerator.generateRestMethodDto();
                    restMethod.setMockResponses(new ArrayList<RestMockResponseDto>());
                    restResource.getMethods().add(restMethod);

                    for(int responseIndex = 0; responseIndex < 3; responseIndex++){
                        final RestMockResponseDto restMockResponse = RestMockResponseDtoGenerator.generateRestMockResponseDto();
                        restMethod.getMockResponses().add(restMockResponse);
                    }
                }

            }
        }

        return projectDto;
    }

}
