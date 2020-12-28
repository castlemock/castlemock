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

package com.castlemock.service.mock.rest.project.adapter;

import com.castlemock.model.core.SearchQuery;
import com.castlemock.model.core.SearchResult;
import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.core.TypeIdentifier;
import com.castlemock.model.core.project.Project;
import com.castlemock.model.core.service.project.ProjectServiceAdapter;
import com.castlemock.model.mock.rest.domain.RestProject;
import com.castlemock.service.mock.rest.project.input.CreateRestProjectInput;
import com.castlemock.service.mock.rest.project.input.DeleteRestProjectInput;
import com.castlemock.service.mock.rest.project.input.ExportRestProjectInput;
import com.castlemock.service.mock.rest.project.input.ImportRestProjectInput;
import com.castlemock.service.mock.rest.project.input.ReadAllRestProjectsInput;
import com.castlemock.service.mock.rest.project.input.ReadRestProjectInput;
import com.castlemock.service.mock.rest.project.input.SearchRestProjectInput;
import com.castlemock.service.mock.rest.project.input.UpdateRestProjectInput;
import com.castlemock.service.mock.rest.project.output.CreateRestProjectOutput;
import com.castlemock.service.mock.rest.project.output.DeleteRestProjectOutput;
import com.castlemock.service.mock.rest.project.output.ExportRestProjectOutput;
import com.castlemock.service.mock.rest.project.output.ImportRestProjectOutput;
import com.castlemock.service.mock.rest.project.output.ReadAllRestProjectsOutput;
import com.castlemock.service.mock.rest.project.output.ReadRestProjectOutput;
import com.castlemock.service.mock.rest.project.output.SearchRestProjectOutput;
import com.castlemock.service.mock.rest.project.output.UpdateRestProjectOutput;
import com.castlemock.service.mock.rest.RestTypeIdentifier;
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
        final CreateRestProjectOutput output = serviceProcessor.process(CreateRestProjectInput.builder()
                .restProject(project)
                .build());
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
        final DeleteRestProjectOutput output = serviceProcessor.process(DeleteRestProjectInput.builder()
                .restProjectId(id)
                .build());
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
        final UpdateRestProjectOutput output = serviceProcessor.process(UpdateRestProjectInput.builder()
                .restProjectId(id)
                .restProject(project)
                .build());
        return output.getUpdatedRestProject();
    }

    /**
     * The method is responsible for retrieving all instances from all the various service types.
     * @return A list containing all the instance independent from type
     */
    @Override
    public List<RestProject> readAll() {
        final ReadAllRestProjectsOutput output = serviceProcessor.process(ReadAllRestProjectsInput.builder().build());
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
        final ReadRestProjectOutput output = serviceProcessor.process(ReadRestProjectInput.builder()
                .restProjectId(id)
                .build());
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
        final ExportRestProjectOutput output = serviceProcessor.process(ExportRestProjectInput.builder()
                .restProjectId(id)
                .build());
        return output.getExportedProject();
    }

    /**
     * The method provides the functionality to import a project as a String
     * @param projectRaw The project as a String
     * @return The imported project
     */
    @Override
    public RestProject importProject(String projectRaw) {
        final ImportRestProjectOutput output =serviceProcessor.process(ImportRestProjectInput.builder()
                .projectRaw(projectRaw)
                .build());
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
        final SearchRestProjectOutput output = serviceProcessor.process(SearchRestProjectInput.builder()
                .searchQuery(searchQuery)
                .build());
        return output.getSearchResults();
    }
}
