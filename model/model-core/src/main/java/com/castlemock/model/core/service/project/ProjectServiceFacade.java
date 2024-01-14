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

package com.castlemock.model.core.service.project;

import com.castlemock.model.core.ServiceFacade;
import com.castlemock.model.core.project.OverviewProject;
import com.castlemock.model.core.project.Project;

import java.util.List;


/**
 * The project service facade is used to assembly all the project service layers and interact with them
 * in order to retrieve a unified answer independent of the project type. The class is responsible for keeping
 * tracks of all the project services and providing the basic functionality shared among all the
 * project services, such as get, delete, update.
 * @author Karl Dahlgren
 * @since 1.0
 * @see Project
 */
public interface ProjectServiceFacade extends ServiceFacade<Project, String> {

    List<OverviewProject> findAll();
}
