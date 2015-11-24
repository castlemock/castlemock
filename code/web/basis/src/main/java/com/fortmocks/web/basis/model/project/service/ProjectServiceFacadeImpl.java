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

package com.fortmocks.web.basis.model.project.service;

import com.fortmocks.core.basis.model.SearchQuery;
import com.fortmocks.core.basis.model.SearchResult;
import com.fortmocks.core.basis.model.TypeIdentifiable;
import com.fortmocks.core.basis.model.TypeIdentifier;
import com.fortmocks.core.basis.model.project.domain.Project;
import com.fortmocks.core.basis.model.project.dto.ProjectDto;
import com.fortmocks.core.basis.model.project.service.ProjectServiceAdapter;
import com.fortmocks.core.basis.model.project.service.ProjectServiceFacade;
import com.fortmocks.web.basis.model.ServiceFacadeImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * The project service component is used to assembly all the project service layers and interact with them
 * in order to retrieve a unified answer independent of the project type. The class is responsible for keeping
 * tracks of all the project services and providing the basic functionality shared among all the
 * project services, such as get, delete, update.
 * @author Karl Dahlgren
 * @since 1.0
 * @see Project
 * @see ProjectDto
 */
@Service
public class ProjectServiceFacadeImpl extends ServiceFacadeImpl<ProjectDto, Long, ProjectServiceAdapter<ProjectDto>> implements ProjectServiceFacade {

    /**
     * The initiate method is responsible for for locating all the service instances for a specific module
     * and organizing them depending on the type.
     * @see com.fortmocks.core.basis.model.Service
     * @see TypeIdentifier
     * @see TypeIdentifiable
     */
    @Override
    public void initiate(){
        initiate(ProjectServiceAdapter.class);
    }

    /**
     * The method provides the functionality to export a project and convert it to a String
     * @param typeUrl The url for the specific type that the instance belongs to
     * @param id The id of the project that will be converted and exported
     * @return The project with the provided id as a String
     */
    @Override
    public String exportProject(final String typeUrl, final Long id){
        final ProjectServiceAdapter<ProjectDto> service = findByTypeUrl(typeUrl);
        return service.exportProject(id);
    }

    /**
     * The method provides the functionality to import a project as a String
     * @param type The type value for the specific type that the instance belongs to
     * @param rawProject The imported project file
     */
    @Override
    public void importProject(String type, String rawProject) {
        final ProjectServiceAdapter<ProjectDto> service = findByType(type);
        service.importProject(rawProject);
    }

    /**
     * Searches for resources that matches the provided query. The matching resources will
     * be returned as a collection of {@link SearchResult}
     * @param searchQuery The search query that will be used to identify the resources
     * @return A list of search results
     */
    @Override
    public List<SearchResult> search(SearchQuery searchQuery) {
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        for(ProjectServiceAdapter projectServiceAdapter : services.values()){
            List<SearchResult> projectServiceSearchResult = projectServiceAdapter.search(searchQuery);
            searchResults.addAll(projectServiceSearchResult);
        }
        return searchResults;
    }

}
