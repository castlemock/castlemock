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

package com.castlemock.web.mock.graphql.model.project.repository;

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.SearchValidator;
import com.castlemock.core.mock.graphql.model.project.domain.*;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLProjectDto;
import com.castlemock.web.basis.model.RepositoryImpl;
import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class GraphQLProjectRepositoryImpl extends RepositoryImpl<GraphQLProject, GraphQLProjectDto, String> implements GraphQLProjectRepository {

    @Autowired
    private MessageSource messageSource;
    @Value(value = "${graphql.project.file.directory}")
    private String graphQLProjectFileDirectory;
    @Value(value = "${graphql.project.file.extension}")
    private String graphQLProjectFileExtension;

    private static final String SLASH = "/";
    private static final String GRAPHQL = "graphql";
    private static final String PROJECT = "project";
    private static final String COMMA = ", ";
    private static final String GRAPHQL_TYPE = "GraphQL";
    private static final String QUERY = "query";
    private static final String MUTATION = "mutation";
    private static final String SUBSCRIPTION = "subscription";
    private static final String OBJECT_TYPE = "object";
    private static final String ENUM_TYPE = "enum";


    private static final Logger LOGGER = Logger.getLogger(GraphQLProjectRepositoryImpl.class);

    /**
     * The method returns the directory for the specific file repository. The directory will be used to indicate
     * where files should be saved and loaded from. The method is abstract and every subclass is responsible for
     * overriding the method and provided the directory for their corresponding file type.
     *
     * @return The file directory where the files for the specific file repository could be saved and loaded from.
     */
    @Override
    protected String getFileDirectory() {
        return graphQLProjectFileDirectory;
    }

    /**
     * The method returns the postfix for the file that the file repository is responsible for managing.
     * The method is abstract and every subclass is responsible for overriding the method and provided the postfix
     * for their corresponding file type.
     *
     * @return The file extension for the file type that the repository is responsible for managing .
     */
    @Override
    protected String getFileExtension() {
        return graphQLProjectFileExtension;
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to the file system is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to the file system, but also loaded from the
     * file system upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param type The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             the file system.
     * @see #save
     */
    @Override
    protected void checkType(GraphQLProject type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SearchResult> search(SearchQuery query) {
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        for(GraphQLProject project : collection.values()){
            List<SearchResult> graphQLProjectSearchResult = searchProject(project, query);
            searchResults.addAll(graphQLProjectSearchResult);
        }
        return searchResults;
    }

    /**
     * Search through a GraphQL project and all its resources
     * @param project The GraphQL project which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchProject(final GraphQLProject project, final SearchQuery query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        if(SearchValidator.validate(project.getName(), query.getQuery())){
            final String projectType = messageSource.getMessage("general.type.project", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(project.getName());
            searchResult.setLink(GRAPHQL + SLASH + PROJECT + SLASH + project.getId());
            searchResult.setDescription(GRAPHQL_TYPE + COMMA + projectType);
            searchResults.add(searchResult);
        }
        return searchResults;
    }


    /**
     * Search through a GraphQL query and all its resources
     * @param graphQLProject The GraphQL project which will be searched
     * @param graphQLQuery The GraphQL application which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchQuery(final GraphQLProject graphQLProject, final GraphQLQuery graphQLQuery, final SearchQuery query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        if(SearchValidator.validate(graphQLQuery.getName(), query.getQuery())){
            final String queryType = messageSource.getMessage("graphql.type.query", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(graphQLQuery.getName());
            searchResult.setLink(GRAPHQL + SLASH + PROJECT + SLASH + graphQLProject.getId() + SLASH + QUERY + SLASH + graphQLQuery.getId());
            searchResult.setDescription(GRAPHQL_TYPE + COMMA + queryType);
            searchResults.add(searchResult);
        }
        return searchResults;
    }

    /**
     * Search through a GraphQL mutation and all its resources
     * @param graphQLProject The GraphQL project which will be searched
     * @param graphQLMutation The GraphQL mutation which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchMutation(final GraphQLProject graphQLProject, final GraphQLMutation graphQLMutation, final SearchQuery query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        if(SearchValidator.validate(graphQLMutation.getName(), query.getQuery())){
            final String type = messageSource.getMessage("graphql.type.mutation", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(graphQLMutation.getName());
            searchResult.setLink(GRAPHQL + SLASH + PROJECT + SLASH + graphQLProject.getId() + SLASH + MUTATION + SLASH + graphQLMutation.getId());
            searchResult.setDescription(GRAPHQL_TYPE + COMMA + type);
            searchResults.add(searchResult);
        }
        return searchResults;
    }

    /**
     * Search through a GraphQL subscription and all its resources
     * @param graphQLProject The GraphQL project which will be searched
     * @param graphQLSubscription The GraphQL subscription which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchSubscription(final GraphQLProject graphQLProject, final GraphQLSubscription graphQLSubscription, final SearchQuery query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        if(SearchValidator.validate(graphQLSubscription.getName(), query.getQuery())){
            final String queryType = messageSource.getMessage("graphql.type.subscription", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(graphQLSubscription.getName());
            searchResult.setLink(GRAPHQL + SLASH + PROJECT + SLASH + graphQLProject.getId() + SLASH + SUBSCRIPTION + SLASH + graphQLSubscription.getId());
            searchResult.setDescription(GRAPHQL_TYPE + COMMA + queryType);
            searchResults.add(searchResult);
        }
        return searchResults;
    }


    /**
     * Search through a GraphQL subscription and all its resources
     * @param graphQLProject The GraphQL project which will be searched
     * @param graphQLObjectType The GraphQL subscription which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchObject(final GraphQLProject graphQLProject, final GraphQLObjectType graphQLObjectType, final SearchQuery query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        if(SearchValidator.validate(graphQLObjectType.getName(), query.getQuery())){
            final String queryType = messageSource.getMessage("graphql.type.object", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(graphQLObjectType.getName());
            searchResult.setLink(GRAPHQL + SLASH + PROJECT + SLASH + graphQLProject.getId() + SLASH + OBJECT_TYPE + SLASH + graphQLObjectType.getId());
            searchResult.setDescription(GRAPHQL_TYPE + COMMA + queryType);
            searchResults.add(searchResult);
        }
        return searchResults;
    }

    /**
     * Search through a GraphQL enum and all its resources
     * @param graphQLProject The GraphQL project which will be searched
     * @param graphQLEnumType The GraphQL enum which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchEnum(final GraphQLProject graphQLProject, final GraphQLEnumType graphQLEnumType, final SearchQuery query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        if(SearchValidator.validate(graphQLEnumType.getName(), query.getQuery())){
            final String queryType = messageSource.getMessage("graphql.type.enum", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(graphQLEnumType.getName());
            searchResult.setLink(GRAPHQL + SLASH + PROJECT + SLASH + graphQLProject.getId() + SLASH + ENUM_TYPE + SLASH + graphQLEnumType.getId());
            searchResult.setDescription(GRAPHQL_TYPE + COMMA + queryType);
            searchResults.add(searchResult);
        }
        return searchResults;
    }





    /**
     * The save method provides the functionality to save an instance to the file system.
     * @param project The type that will be saved to the file system.
     * @return The type that was saved to the file system. The main reason for it is being returned is because
     *         there could be modifications of the object during the save process. For example, if the type does not
     *         have an identifier, then the method will generate a new identifier for the type.
     */
    @Override
    public GraphQLProjectDto save(final GraphQLProject project) {
        return super.save(project);
    }


    /**
     * Finds a project by a given name
     *
     * @param name The name of the project that should be retrieved
     * @return Returns a project with the provided name
     * @see GraphQLProjectDto
     */
    @Override
    public GraphQLProjectDto findGraphQLProjectWithName(String name) {
        Preconditions.checkNotNull(name, "Project name cannot be null");
        Preconditions.checkArgument(!name.isEmpty(), "Project name cannot be empty");
        for(GraphQLProject graphQLProject : collection.values()){
            if(graphQLProject.getName().equalsIgnoreCase(name)) {
                return mapper.map(graphQLProject, GraphQLProjectDto.class);
            }
        }
        return null;
    }

    
}
