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
import com.castlemock.core.mock.graphql.model.project.dto.*;
import com.castlemock.web.basis.model.RepositoryImpl;
import com.google.common.base.Preconditions;
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
            List<SearchResult> restProjectSearchResult = searchProject(project, query);
            searchResults.addAll(restProjectSearchResult);
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

        for(GraphQLQuery graphQLQuery : project.getQueries()){
            List<SearchResult> results = searchQuery(project, graphQLQuery, query);
            searchResults.addAll(results);
        }

        for(GraphQLMutation graphQLMutation : project.getMutations()){
            List<SearchResult> results = searchMutation(project, graphQLMutation, query);
            searchResults.addAll(results);
        }

        for(GraphQLSubscription graphQLSubscription : project.getSubscriptions()){
            List<SearchResult> results = searchSubscription(project, graphQLSubscription, query);
            searchResults.addAll(results);
        }

        for(GraphQLObjectType graphQLObjectType : project.getObjects()){
            List<SearchResult> results = searchObject(project, graphQLObjectType, query);
            searchResults.addAll(results);
        }

        for(GraphQLEnumType graphQLEnumType : project.getEnums()){
            List<SearchResult> results = searchEnum(project, graphQLEnumType, query);
            searchResults.addAll(results);
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
        for(GraphQLQuery query : project.getQueries()){
            if(query.getId() == null){
                String queryId = generateId();
                query.setId(queryId);
            }
        }
        for(GraphQLMutation mutation : project.getMutations()){
            if(mutation.getId() == null){
                String mutationId = generateId();
                mutation.setId(mutationId);
            }
        }
        for(GraphQLSubscription subscription : project.getSubscriptions()){
            if(subscription.getId() == null){
                String subscriptionId = generateId();
                subscription.setId(subscriptionId);
            }
        }
        for(GraphQLObjectType object : project.getObjects()){
            if(object.getId() == null){
                String objectId = generateId();
                object.setId(objectId);
            }
        }
        for(GraphQLEnumType enumType : project.getEnums()){
            if(enumType.getId() == null){
                String enumTypeId = generateId();
                enumType.setId(enumTypeId);
            }
        }
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

    /**
     * Finds a {@link GraphQLQueryDto} with the provided ids.
     *
     * @param graphQLProjectId The id of the {@link GraphQLProject}
     * @param graphQLQueryId   The id of the {@link GraphQLQuery}
     * @return A {@link GraphQLQueryDto} that matches the search criteria.
     * @see GraphQLProject
     * @see GraphQLProjectDto
     * @see GraphQLQuery
     * @see GraphQLQueryDto
     */
    @Override
    public GraphQLQueryDto findGraphQLQuery(String graphQLProjectId, String graphQLQueryId) {
        final GraphQLQuery graphQLQuery = this.findGraphQLQueryType(graphQLProjectId, graphQLQueryId);
        return mapper.map(graphQLQuery, GraphQLQueryDto.class);
    }

    /**
     * Finds a {@link GraphQLMutationDto} with the provided ids.
     *
     * @param graphQLProjectId The id of the {@link GraphQLProject}
     * @param graphQLQueryId   The id of the {@link GraphQLMutation}
     * @return A {@link GraphQLMutationDto} that matches the search criteria.
     * @see GraphQLProject
     * @see GraphQLProjectDto
     * @see GraphQLMutation
     * @see GraphQLMutationDto
     */
    @Override
    public GraphQLMutationDto findGraphQLMutation(String graphQLProjectId, String graphQLQueryId) {
        final GraphQLMutation mutation = this.findGraphQLMutationType(graphQLProjectId, graphQLQueryId);
        return mapper.map(mutation, GraphQLMutationDto.class);
    }

    /**
     * Finds a {@link GraphQLSubscriptionDto} with the provided ids.
     *
     * @param graphQLProjectId The id of the {@link GraphQLProject}
     * @param graphQLQueryId   The id of the {@link GraphQLSubscription}
     * @return A {@link GraphQLSubscriptionDto} that matches the search criteria.
     * @see GraphQLProject
     * @see GraphQLProjectDto
     * @see GraphQLSubscription
     * @see GraphQLSubscriptionDto
     */
    @Override
    public GraphQLSubscriptionDto findGraphQLSubscription(String graphQLProjectId, String graphQLQueryId) {
        final GraphQLSubscription subscription = this.findGraphQLSubscriptionType(graphQLProjectId, graphQLQueryId);
        return mapper.map(subscription, GraphQLSubscriptionDto.class);
    }

    /**
     * Finds a {@link GraphQLObjectTypeDto} with the provided ids.
     *
     * @param graphQLProjectId The id of the {@link GraphQLProject}
     * @param graphQLQueryId   The id of the {@link GraphQLObjectType}
     * @return A {@link GraphQLObjectTypeDto} that matches the search criteria.
     * @see GraphQLProject
     * @see GraphQLProjectDto
     * @see GraphQLObjectType
     * @see GraphQLObjectTypeDto
     */
    @Override
    public GraphQLObjectTypeDto findGraphQLObjectType(String graphQLProjectId, String graphQLQueryId) {
        final GraphQLObjectType objectType = this.findGraphQLObjectTypeType(graphQLProjectId, graphQLQueryId);
        return mapper.map(objectType, GraphQLObjectTypeDto.class);
    }

    /**
     * Finds a {@link GraphQLEnumTypeDto} with the provided ids.
     *
     * @param graphQLProjectId The id of the {@link GraphQLProject}
     * @param graphQLQueryId   The id of the {@link GraphQLEnumType}
     * @return A {@link GraphQLEnumTypeDto} that matches the search criteria.
     * @see GraphQLProject
     * @see GraphQLProjectDto
     * @see GraphQLEnumType
     * @see GraphQLEnumTypeDto
     */
    @Override
    public GraphQLEnumTypeDto findGraphQLEnumType(String graphQLProjectId, String graphQLQueryId) {
        final GraphQLEnumType graphQLEnumType = this.findGraphQLEnumTypeType(graphQLProjectId, graphQLQueryId);
        return mapper.map(graphQLEnumType, GraphQLEnumTypeDto.class);
    }



    /**
     * Finds a {@link GraphQLQuery} with the provided ids.
     * @param graphQLProjectId The id of the {@link GraphQLProject}
     * @param graphQLQueryId The id of the {@link GraphQLQuery}
     * @return A {@link GraphQLQuery} that matches the search criteria.
     * @see GraphQLProject
     * @see GraphQLQuery
     */
    private GraphQLQuery findGraphQLQueryType(final String graphQLProjectId, final String graphQLQueryId) {
        Preconditions.checkNotNull(graphQLProjectId, "Project id cannot be null");
        Preconditions.checkNotNull(graphQLQueryId, "Query id cannot be null");
        final GraphQLProject graphQLProject = collection.get(graphQLProjectId);

        if(graphQLProject == null){
            throw new IllegalArgumentException("Unable to find a GraphQL project with id " + graphQLProjectId);
        }

        for(GraphQLQuery graphQLQuery : graphQLProject.getQueries()){
            if(graphQLQuery.getId().equals(graphQLQueryId)){
                return graphQLQuery;
            }
        }
        throw new IllegalArgumentException("Unable to find a GraphQL query with id " + graphQLQueryId);
    }

    /**
     * Finds a {@link GraphQLMutation} with the provided ids.
     * @param graphQLProjectId The id of the {@link GraphQLProject}
     * @param graphQLMutationId The id of the {@link GraphQLMutation}
     * @return A {@link GraphQLMutation} that matches the search criteria.
     * @see GraphQLProject
     * @see GraphQLMutation
     */
    private GraphQLMutation findGraphQLMutationType(final String graphQLProjectId, final String graphQLMutationId) {
        Preconditions.checkNotNull(graphQLProjectId, "Project id cannot be null");
        Preconditions.checkNotNull(graphQLMutationId, "Mutation id cannot be null");
        final GraphQLProject graphQLProject = collection.get(graphQLProjectId);

        if(graphQLProject == null){
            throw new IllegalArgumentException("Unable to find a GraphQL project with id " + graphQLProjectId);
        }

        for(GraphQLMutation graphQLMutation : graphQLProject.getMutations()){
            if(graphQLMutation.getId().equals(graphQLMutationId)){
                return graphQLMutation;
            }
        }
        throw new IllegalArgumentException("Unable to find a GraphQL mutation with id " + graphQLMutationId);
    }

    /**
     * Finds a {@link GraphQLSubscription} with the provided ids.
     * @param graphQLProjectId The id of the {@link GraphQLProject}
     * @param graphQLSubscriptionId The id of the {@link GraphQLSubscription}
     * @return A {@link GraphQLSubscription} that matches the search criteria.
     * @see GraphQLProject
     * @see GraphQLSubscription
     */
    private GraphQLSubscription findGraphQLSubscriptionType(final String graphQLProjectId, final String graphQLSubscriptionId) {
        Preconditions.checkNotNull(graphQLProjectId, "Project id cannot be null");
        Preconditions.checkNotNull(graphQLSubscriptionId, "Subscription id cannot be null");
        final GraphQLProject graphQLProject = collection.get(graphQLProjectId);

        if(graphQLProject == null){
            throw new IllegalArgumentException("Unable to find a GraphQL project with id " + graphQLProjectId);
        }

        for(GraphQLSubscription graphQLSubscription : graphQLProject.getSubscriptions()){
            if(graphQLSubscription.getId().equals(graphQLSubscriptionId)){
                return graphQLSubscription;
            }
        }
        throw new IllegalArgumentException("Unable to find a GraphQL subscription with id " + graphQLSubscriptionId);
    }

    /**
     * Finds a {@link GraphQLObjectType} with the provided ids.
     * @param graphQLProjectId The id of the {@link GraphQLProject}
     * @param graphQLObjectTypeId The id of the {@link GraphQLObjectType}
     * @return A {@link GraphQLObjectType} that matches the search criteria.
     * @see GraphQLProject
     * @see GraphQLObjectType
     */
    private GraphQLObjectType findGraphQLObjectTypeType(final String graphQLProjectId, final String graphQLObjectTypeId) {
        Preconditions.checkNotNull(graphQLProjectId, "Project id cannot be null");
        Preconditions.checkNotNull(graphQLObjectTypeId, "ObjectType id cannot be null");
        final GraphQLProject graphQLProject = collection.get(graphQLProjectId);

        if(graphQLProject == null){
            throw new IllegalArgumentException("Unable to find a GraphQL project with id " + graphQLProjectId);
        }

        for(GraphQLObjectType graphQLObjectType : graphQLProject.getObjects()){
            if(graphQLObjectType.getId().equals(graphQLObjectTypeId)){
                return graphQLObjectType;
            }
        }
        throw new IllegalArgumentException("Unable to find a GraphQL object type with id " + graphQLObjectTypeId);
    }

    /**
     * Finds a {@link GraphQLEnumType} with the provided ids.
     * @param graphQLProjectId The id of the {@link GraphQLProject}
     * @param graphQLEnumTypeId The id of the {@link GraphQLEnumType}
     * @return A {@link GraphQLEnumType} that matches the search criteria.
     * @see GraphQLProject
     * @see GraphQLEnumType
     */
    private GraphQLEnumType findGraphQLEnumTypeType(final String graphQLProjectId, final String graphQLEnumTypeId) {
        Preconditions.checkNotNull(graphQLProjectId, "Project id cannot be null");
        Preconditions.checkNotNull(graphQLEnumTypeId, "EnumType id cannot be null");
        final GraphQLProject graphQLProject = collection.get(graphQLProjectId);

        if(graphQLProject == null){
            throw new IllegalArgumentException("Unable to find a GraphQL project with id " + graphQLProjectId);
        }

        for(GraphQLEnumType graphQLEnumType : graphQLProject.getEnums()){
            if(graphQLEnumType.getId().equals(graphQLEnumTypeId)){
                return graphQLEnumType;
            }
        }
        throw new IllegalArgumentException("Unable to find a GraphQL enum type with id " + graphQLEnumTypeId);
    }
    
}
