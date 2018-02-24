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
import com.castlemock.core.mock.graphql.model.project.domain.*;
import com.castlemock.core.mock.graphql.model.project.dto.*;
import com.castlemock.web.basis.model.RepositoryImpl;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GraphQLProjectRepositoryImpl extends RepositoryImpl<GraphQLProject, GraphQLProjectDto, String> implements GraphQLProjectRepository {

    @Value(value = "${graphql.project.file.directory}")
    private String graphQLProjectFileDirectory;
    @Value(value = "${graphql.project.file.extension}")
    private String graphQLProjectFileExtension;

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
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SearchResult> search(SearchQuery query) {
        return null;
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
