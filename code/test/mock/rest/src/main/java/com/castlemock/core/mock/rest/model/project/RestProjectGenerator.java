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

package com.castlemock.core.mock.rest.model.project;

import com.castlemock.core.mock.rest.model.project.domain.*;

import java.util.ArrayList;
import java.util.Date;


/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class RestProjectGenerator {

    public static RestProject generateRestProject(){
        final RestProject project = new RestProject();
        project.setId("REST PROJECT");
        project.setName("Project name");
        project.setDescription("Project description");
        project.setCreated(new Date());
        project.setUpdated(new Date());
        project.setApplications(new ArrayList<RestApplication>());
        return project;
    }

    public static RestProject generateFullRestProject(){
        final RestProject project = new RestProject();
        project.setId("REST PROJECT");
        project.setName("Project name");
        project.setDescription("Project description");
        project.setCreated(new Date());
        project.setUpdated(new Date());
        project.setApplications(new ArrayList<RestApplication>());

        for(int applicationIndex = 0; applicationIndex < 3; applicationIndex++){
            final RestApplication restApplication = RestApplicationGenerator.generateRestApplication();
            restApplication.setResources(new ArrayList<RestResource>());
            project.getApplications().add(restApplication);

            for(int resourceIndex = 0; resourceIndex < 3; resourceIndex++){
                final RestResource restResource = RestResourceGenerator.generateRestResource();
                restResource.setMethods(new ArrayList<RestMethod>());
                restApplication.getResources().add(restResource);

                for(int methodIndex = 0; methodIndex < 3; methodIndex++){
                    final RestMethod restMethod = RestMethodGenerator.generateRestMethod();
                    restMethod.setMockResponses(new ArrayList<RestMockResponse>());
                    restResource.getMethods().add(restMethod);

                    for(int responseIndex = 0; responseIndex < 3; responseIndex++){
                        final RestMockResponse restMockResponse = RestMockResponseGenerator.generateRestMockResponse();
                        restMethod.getMockResponses().add(restMockResponse);
                    }
                }

            }
        }

        return project;
    }


}
