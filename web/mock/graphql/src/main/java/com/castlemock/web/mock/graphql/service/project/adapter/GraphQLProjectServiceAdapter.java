/*
 * Copyright 2018 Karl Dahlgren
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

package com.castlemock.web.mock.graphql.service.project.adapter;

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.basis.model.TypeIdentifier;
import com.castlemock.core.basis.model.project.domain.Project;
import com.castlemock.core.basis.service.project.ProjectServiceAdapter;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLProject;
import com.castlemock.core.mock.graphql.service.project.input.*;
import com.castlemock.core.mock.graphql.service.project.output.*;
import com.castlemock.web.mock.graphql.model.GraphQLTypeIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The GRAPHQL project service adapter is responsible for providing the basic functionality for all the
 * project services.
 * @author Karl Dahlgren
 * @since 1.19
 * @see GraphQLProject
 */
@Service
public class GraphQLProjectServiceAdapter implements ProjectServiceAdapter<GraphQLProject> {

    @Autowired
    private ServiceProcessor serviceProcessor;
    private GraphQLTypeIdentifier GRAPHQL_TYPE_IDENTIFIER = new GraphQLTypeIdentifier();

    /**
     * The method provides the functionality to create and store a  instance to a specific service.
     * The service is identified with the provided type value.
     * @param project The  instance that will be created
     * @return The saved instance
     */
    @Override
    public GraphQLProject create(GraphQLProject project) {
        final CreateGraphQLProjectOutput output = serviceProcessor.process(new CreateGraphQLProjectInput(project));
        return output.getSavedGraphQLProject();
    }

    /**
     * The method provides the functionality to delete a specific instance. The type is
     * identified with the provided typeUrl value. When the type has been identified, the instance
     * itself has to be identified. This is done with the provided id. The instance with the matching type
     * and id will be deleted.
     * @param id The id of the instance that will be deleted
     */
    @Override
    public GraphQLProject delete(String id) {
        final DeleteGraphQLProjectOutput output = serviceProcessor.process(new DeleteGraphQLProjectInput(id));
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
    public GraphQLProject update(String id, GraphQLProject project) {
        final UpdateGraphQLProjectOutput output = serviceProcessor.process(new UpdateGraphQLProjectInput(id, project));
        return output.getUpdatedGraphQLProject();
    }

    /**
     * The method is responsible for retrieving all instances from all the various service types.
     * @return A list containing all the instance independent from type
     */
    @Override
    public List<GraphQLProject> readAll() {
        final ReadAllGraphQLProjectsOutput output = serviceProcessor.process(new ReadAllGraphQLProjectsInput());
        return output.getGraphQLProjects();
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
    public GraphQLProject read(String id) {
        final ReadGraphQLProjectOutput output = serviceProcessor.process(new ReadGraphQLProjectInput(id));
        return output.getGraphQLProject();
    }

    /**
     * The TypeIdentifier that is used to identify a specific class
     * @return Returns the type identifier
     */
    @Override
    public TypeIdentifier getTypeIdentifier() {
        return GRAPHQL_TYPE_IDENTIFIER;
    }

    /**
     * The method provides the functionality to convert an instance (parent) to an instance of a subclass to the
     * provided parent.
     * @param parent The parent that will be converted into a subtype of the provided parent
     * @return A new instance of the parent, but as a subtype of the parent
     */
    @Override
    public GraphQLProject convertType(Project parent) {
        return new GraphQLProject(parent);
    }

    /**
     * The method provides the functionality to export a project and convert it to a String
     * @param id The id of the project that will be converted and exported
     * @return The project with the provided id as a String
     */
    @Override
    public String exportProject(String id) {
        final ExportGraphQLProjectOutput output = serviceProcessor.process(new ExportGraphQLProjectInput(id));
        return output.getExportedProject();
    }

    /**
     * The method provides the functionality to import a project as a String
     * @param projectRaw The project as a String
     */
    @Override
    public GraphQLProject importProject(String projectRaw) {
        final ImportGraphQLProjectOutput output = serviceProcessor.process(new ImportGraphQLProjectInput(projectRaw));
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
        final SearchGraphQLProjectOutput output = serviceProcessor.process(new SearchGraphQLProjectInput(searchQuery));
        return output.getSearchResults();
    }
}
