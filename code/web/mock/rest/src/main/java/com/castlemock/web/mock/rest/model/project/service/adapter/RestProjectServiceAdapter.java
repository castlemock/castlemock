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

package com.castlemock.web.mock.rest.model.project.service.adapter;

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.basis.model.TypeIdentifier;
import com.castlemock.core.basis.model.project.domain.Project;
import com.castlemock.core.basis.model.project.service.ProjectServiceAdapter;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.core.mock.rest.model.project.service.message.input.*;
import com.castlemock.core.mock.rest.model.project.service.message.output.*;
import com.castlemock.web.mock.rest.model.RestTypeIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The REST project service adapter is responsible for providing the basic functionality for all the
 * project services.
 * @author Karl Dahlgren
 * @since 1.0
 * @see RestProject
 */
@Service
public class RestProjectServiceAdapter implements ProjectServiceAdapter<RestProject> {

    @Autowired
    private ServiceProcessor serviceProcessor;
    private RestTypeIdentifier REST_TYPE_IDENTIFIER = new RestTypeIdentifier();

    /**
     * The method provides the functionality to create and store a  instance to a specific service.
     * The service is identified with the provided type value.
     * @param project The instance that will be created
     * @return The saved instance
     */
    @Override
    public RestProject create(RestProject project) {
        final CreateRestProjectOutput output = serviceProcessor.process(new CreateRestProjectInput(project));
        return output.getSavedRestProject();
    }

    /**
     * The method provides the functionality to delete a specific instance. The type is
     * identified with the provided typeUrl value. When the type has been identified, the instance
     * itself has to be identified. This is done with the provided id. The instance with the matching type
     * and id will be deleted.
     * @param id The id of the instance that will be deleted
     */
    @Override
    public RestProject delete(String id) {
        final DeleteRestProjectOutput output = serviceProcessor.process(new DeleteRestProjectInput(id));
        return output.getProject();
    }

    /**
     * The method is used to update an already existing instance. The instance type is
     * identified with the provided typeUrl value. When the instance type has been identified, the instance
     * itself has to be identified. This is done with the provided id. The instance with the matching id will be
     * replaced with the provided  instance. Please note that not all values will be updated. It depends on the instance
     * type.
     * @param id The id of the instance that will be updated
     * @param project The instance with the new updated values
     * @return The updated instance
     */
    @Override
    public RestProject update(String id, RestProject project) {
        final UpdateRestProjectOutput output = serviceProcessor.process(new UpdateRestProjectInput(id, project));
        return output.getUpdatedRestProject();
    }

    /**
     * The method is responsible for retrieving all instances from all the various service types.
     * @return A list containing all the instance independent from type
     */
    @Override
    public List<RestProject> readAll() {
        final ReadAllRestProjectsOutput output = serviceProcessor.process(new ReadAllRestProjectsInput());
        return output.getRestProjects();
    }

    /**
     * The method is used to retrieve a instance with a specific type. The type is
     * identified with the provided typeUrl value. When the instance type has been identified, the instance
     * itself has to be identified.
     * @param id The id of the instance that will be retrieved
     * @return A instance that matches the instance type and the provided id. If no instance matches the provided
     *         values, null will be returned.
     */
    @Override
    public RestProject read(String id) {
        final ReadRestProjectOutput output = serviceProcessor.process(new ReadRestProjectInput(id));
        return output.getRestProject();
    }

    /**
     * The TypeIdentifier that is used to identify a specific class
     * @return Returns the type identifier
     */
    @Override
    public TypeIdentifier getTypeIdentifier() {
        return REST_TYPE_IDENTIFIER;
    }

    /**
     * The method provides the functionality to convert an instance (parent) to an instance of a subclass to the
     * provided parent.
     * @param parent The parent that will be converted into a subtype of the provided parent
     * @return A new instance of the parent, but as a subtype of the parent
     */
    @Override
    public RestProject convertType(Project parent) {
        return new RestProject(parent);
    }

    /**
     * The method provides the functionality to export a project and convert it to a String
     * @param id The id of the project that will be converted and exported
     * @return The project with the provided id as a String
     */
    @Override
    public String exportProject(String id) {
        final ExportRestProjectOutput output = serviceProcessor.process(new ExportRestProjectInput(id));
        return output.getExportedProject();
    }

    /**
     * The method provides the functionality to import a project as a String
     * @param projectRaw The project as a String
     * @return The imported project
     */
    @Override
    public RestProject importProject(String projectRaw) {
        final ImportRestProjectOutput output =serviceProcessor.process(new ImportRestProjectInput(projectRaw));
        return output.getProject();
    }

    /**
     * Searches for resources that matches the provided query. The matching resources will
     * be returned as a collection of {@link SearchResult}
     * @param searchQuery The search query that will be used to identify the resources
     * @return A list of search results
     */
    @Override
    public List<SearchResult> search(SearchQuery searchQuery) {
        final SearchRestProjectOutput output = serviceProcessor.process(new SearchRestProjectInput(searchQuery));
        return output.getSearchResults();
    }
}
