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

package com.castlemock.service.core.project;

import com.castlemock.model.core.SearchQuery;
import com.castlemock.model.core.SearchResult;
import com.castlemock.model.core.project.OverviewProject;
import com.castlemock.model.core.project.Project;
import com.castlemock.model.core.service.project.ProjectServiceAdapter;
import com.castlemock.model.core.service.project.ProjectServiceFacade;
import com.castlemock.service.core.ServiceFacadeImpl;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The project service component is used to assembly all the project service layers and interact with them
 * in order to retrieve a unified answer independent of the project type. The class is responsible for keeping
 * tracks of all the project services and providing the basic functionality shared among all the
 * project services, such as get, delete, update.
 * @author Karl Dahlgren
 * @since 1.0
 * @see Project
 * @see Project
 */
@Service
public class ProjectServiceFacadeImpl extends ServiceFacadeImpl<Project, String, ProjectServiceAdapter<Project>> implements ProjectServiceFacade {

    /**
     * The initialize method is responsible for for locating all the service instances for a specific module
     * and organizing them depending on the type.
     * @see com.castlemock.model.core.Service
     */
    @Override
    public void initiate(){
        initiate(ProjectServiceAdapter.class);
    }

    /**
     * Searches for resources that matches the provided query. The matching resources will
     * be returned as a collection of {@link SearchResult}
     * @param searchQuery The search query that will be used to identify the resources
     * @return A list of search results
     */
    @Override
    public List<SearchResult> search(final SearchQuery searchQuery) {
        return services.stream()
                .map(adapter -> adapter.search(searchQuery))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<OverviewProject> findAll() {
        return services.stream()
                .map(adapter -> adapter.readAll()
                        .stream()
                        .map(project -> OverviewProject.toBuilder(project)
                                .type(adapter.getType())
                                .build())
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

}
