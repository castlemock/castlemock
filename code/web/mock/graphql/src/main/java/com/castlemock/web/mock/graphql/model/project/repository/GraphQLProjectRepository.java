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

import com.castlemock.core.basis.model.Repository;
import com.castlemock.core.mock.graphql.model.project.domain.*;
import com.castlemock.core.mock.graphql.model.project.dto.*;

public interface GraphQLProjectRepository extends Repository<GraphQLProject, GraphQLProjectDto, String> {


    /**
     * Finds a project by a given name
     * @param name The name of the project that should be retrieved
     * @return Returns a project with the provided name
     * @see GraphQLProjectDto
     */
    GraphQLProjectDto findGraphQLProjectWithName(String name);

    /**
     * Finds a {@link GraphQLApplicationDto} with the provided id.
     * @param graphQLProjectId The id of the {@link GraphQLProject}
     * @param graphQLApplicationId The id of the {@link GraphQLApplication}
     * @return A {@link GraphQLApplicationDto} that matches the search criteria.
     * @see GraphQLProject
     * @see GraphQLProjectDto
     * @see GraphQLApplication
     * @see GraphQLApplicationDto
     */
    GraphQLApplicationDto findGraphQLApplication(String graphQLProjectId,
                                     String graphQLApplicationId);

    /**
     * Finds a {@link GraphQLQueryDto} with the provided ids.
     * @param graphQLProjectId The id of the {@link GraphQLProject}
     * @param graphQLApplicationId The id of the {@link GraphQLApplication}
     * @param graphQLQueryId The id of the {@link GraphQLQuery}
     * @return A {@link GraphQLQueryDto} that matches the search criteria.
     * @see GraphQLProject
     * @see GraphQLProjectDto
     * @see GraphQLQuery
     * @see GraphQLQueryDto
     */
    GraphQLQueryDto findGraphQLQuery(String graphQLProjectId,
                                     String graphQLApplicationId,
                                     String graphQLQueryId);


    /**
     * Finds a {@link GraphQLMutationDto} with the provided ids.
     * @param graphQLProjectId The id of the {@link GraphQLProject}
     * @param graphQLApplicationId The id of the {@link GraphQLApplication}
     * @param graphQLQueryId The id of the {@link GraphQLMutation}
     * @return A {@link GraphQLMutationDto} that matches the search criteria.
     * @see GraphQLProject
     * @see GraphQLProjectDto
     * @see GraphQLMutation
     * @see GraphQLMutationDto
     */
    GraphQLMutationDto findGraphQLMutation(String graphQLProjectId,
                                           String graphQLApplicationId,
                                           String graphQLQueryId);

    /**
     * Finds a {@link GraphQLSubscriptionDto} with the provided ids.
     * @param graphQLProjectId The id of the {@link GraphQLProject}
     * @param graphQLApplicationId The id of the {@link GraphQLApplication}
     * @param graphQLQueryId The id of the {@link GraphQLSubscription}
     * @return A {@link GraphQLSubscriptionDto} that matches the search criteria.
     * @see GraphQLProject
     * @see GraphQLProjectDto
     * @see GraphQLSubscription
     * @see GraphQLSubscriptionDto
     */
    GraphQLSubscriptionDto findGraphQLSubscription(String graphQLProjectId,
                                                   String graphQLApplicationId,
                                                   String graphQLQueryId);

    /**
     * Finds a {@link GraphQLObjectTypeDto} with the provided ids.
     * @param graphQLProjectId The id of the {@link GraphQLProject}
     * @param graphQLApplicationId The id of the {@link GraphQLApplication}
     * @param graphQLQueryId The id of the {@link GraphQLObjectType}
     * @return A {@link GraphQLObjectTypeDto} that matches the search criteria.
     * @see GraphQLProject
     * @see GraphQLProjectDto
     * @see GraphQLObjectType
     * @see GraphQLObjectTypeDto
     */
    GraphQLObjectTypeDto findGraphQLObjectType(String graphQLProjectId,
                                               String graphQLApplicationId,
                                               String graphQLQueryId);

    /**
     * Finds a {@link GraphQLEnumTypeDto} with the provided ids.
     * @param graphQLProjectId The id of the {@link GraphQLProject}
     * @param graphQLApplicationId The id of the {@link GraphQLApplication}
     * @param graphQLQueryId The id of the {@link GraphQLEnumType}
     * @return A {@link GraphQLEnumTypeDto} that matches the search criteria.
     * @see GraphQLProject
     * @see GraphQLProjectDto
     * @see GraphQLEnumType
     * @see GraphQLEnumTypeDto
     */
    GraphQLEnumTypeDto findGraphQLEnumType(String graphQLProjectId,
                                           String graphQLApplicationId,
                                           String graphQLQueryId);


    /*
     * UPDATE OPERATIONS
     */


    /**
     * The method updates an already existing {@link GraphQLApplicationDto}
     * @param graphQLProjectId The id of the {@link GraphQLProject}
     * @param application The updated {@link GraphQLApplicationDto)
     * @return The updated version of the {@link GraphQLApplicationDto}
     * @see GraphQLProject
     * @see GraphQLProjectDto
     * @see GraphQLApplication
     * @see GraphQLApplicationDto
     */
    GraphQLApplicationDto updateGraphQLApplication(String graphQLProjectId,
                                                   String graphQLApplicationId,
                                                   GraphQLApplicationDto application);


    /**
     * The method updates an already existing {@link GraphQLApplicationDto}
     * @param graphQLProjectId The id of the {@link GraphQLProject}
     * @param graphQLApplicationId  The id of the {@link GraphQLApplication}
     * @param graphQLObjectTypeId The id of the {@link GraphQLObjectType}
     * @param objectTypeDto The updated {@link GraphQLObjectTypeDto)
     * @return The updated version of the {@link GraphQLObjectTypeDto}
     * @see GraphQLProject
     * @see GraphQLProjectDto
     * @see GraphQLApplication
     * @see GraphQLApplicationDto
     * @see GraphQLObjectType
     * @see GraphQLObjectTypeDto
     */
    GraphQLObjectTypeDto updateGraphQLObjectType(String graphQLProjectId,
                                                   String graphQLApplicationId,
                                                   String graphQLObjectTypeId,
                                                   GraphQLObjectTypeDto objectTypeDto);

    /*
     * SAVE OPERATIONS
     */

    /**
     * The method adds a new {@link GraphQLApplication} to a {@link GraphQLProject} and then saves
     * the {@link GraphQLProject} to the file system.
     * @param graphQLProjectId The id of the {@link GraphQLProject}
     * @param graphQLApplicationDto The dto instance of {@link GraphQLApplication} that will be added to the {@link GraphQLProject}
     * @return The saved {@link GraphQLApplicationDto}
     * @see GraphQLProject
     * @see GraphQLProjectDto
     * @see GraphQLApplication
     * @see GraphQLApplicationDto
     */
    GraphQLApplicationDto saveGraphQLApplication(String graphQLProjectId, GraphQLApplicationDto graphQLApplicationDto);


    /**
     * The method adds a new {@link GraphQLQuery} to a {@link GraphQLApplication} and then saves
     * the {@link GraphQLProject} to the file system.
     * @param graphQLProjectId The id of the {@link GraphQLProject}
     * @param graphQLApplicationId The id of the {@link GraphQLApplication}
     * @param graphQLQueryDto The dto instance of {@link GraphQLQueryDto} that will be added to the {@link GraphQLApplication}
     * @return The saved {@link GraphQLQueryDto}
     * @see GraphQLProject
     * @see GraphQLProjectDto
     * @see GraphQLApplication
     * @see GraphQLApplicationDto
     * @see GraphQLQuery
     * @see GraphQLQueryDto
     */
    GraphQLQueryDto saveGraphQLQuery(String graphQLProjectId, String graphQLApplicationId, GraphQLQueryDto graphQLQueryDto);

    /**
     * The method adds a new {@link GraphQLSubscription} to a {@link GraphQLApplication} and then saves
     * the {@link GraphQLProject} to the file system.
     * @param graphQLProjectId The id of the {@link GraphQLProject}
     * @param graphQLApplicationId The id of the {@link GraphQLApplication}
     * @param graphQLSubscriptionDto The dto instance of {@link GraphQLSubscriptionDto} that will be added to the {@link GraphQLApplication}
     * @return The saved {@link GraphQLSubscriptionDto}
     * @see GraphQLProject
     * @see GraphQLProjectDto
     * @see GraphQLApplication
     * @see GraphQLApplicationDto
     * @see GraphQLSubscription
     * @see GraphQLSubscriptionDto
     */
    GraphQLSubscriptionDto saveGraphQLSubscription(String graphQLProjectId, String graphQLApplicationId, GraphQLSubscriptionDto graphQLSubscriptionDto);

    /**
     * The method adds a new {@link GraphQLEnumType} to a {@link GraphQLApplication} and then saves
     * the {@link GraphQLProject} to the file system.
     * @param graphQLProjectId The id of the {@link GraphQLProject}
     * @param graphQLApplicationId The id of the {@link GraphQLApplication}
     * @param graphQLEnumTypeDto The dto instance of {@link GraphQLEnumTypeDto} that will be added to the {@link GraphQLApplication}
     * @return The saved {@link GraphQLEnumTypeDto}
     * @see GraphQLProject
     * @see GraphQLProjectDto
     * @see GraphQLApplication
     * @see GraphQLApplicationDto
     * @see GraphQLEnumType
     * @see GraphQLEnumTypeDto
     */
    GraphQLEnumTypeDto saveGraphQLEnumType(String graphQLProjectId, String graphQLApplicationId, GraphQLEnumTypeDto graphQLEnumTypeDto);

    /**
     * The method adds a new {@link GraphQLObjectType} to a {@link GraphQLApplication} and then saves
     * the {@link GraphQLProject} to the file system.
     * @param graphQLProjectId The id of the {@link GraphQLProject}
     * @param graphQLApplicationId The id of the {@link GraphQLApplication}
     * @param graphQLObjectTypeDto The dto instance of {@link GraphQLObjectTypeDto} that will be added to the {@link GraphQLApplication}
     * @return The saved {@link GraphQLObjectTypeDto}
     * @see GraphQLProject
     * @see GraphQLProjectDto
     * @see GraphQLApplication
     * @see GraphQLApplicationDto
     * @see GraphQLObjectType
     * @see GraphQLObjectTypeDto
     */
    GraphQLObjectTypeDto saveGraphQLObjectType(String graphQLProjectId, String graphQLApplicationId, GraphQLObjectTypeDto graphQLObjectTypeDto);

    /**
     * The method adds a new {@link GraphQLMutation} to a {@link GraphQLApplication} and then saves
     * the {@link GraphQLProject} to the file system.
     * @param graphQLProjectId The id of the {@link GraphQLProject}
     * @param graphQLApplicationId The id of the {@link GraphQLApplication}
     * @param graphQLMutationDto The dto instance of {@link GraphQLMutationDto} that will be added to the {@link GraphQLApplication}
     * @return The saved {@link GraphQLMutationDto}
     * @see GraphQLProject
     * @see GraphQLProjectDto
     * @see GraphQLApplication
     * @see GraphQLApplicationDto
     * @see GraphQLMutation
     * @see GraphQLMutationDto
     */
    GraphQLMutationDto saveGraphQLMutation(String graphQLProjectId, String graphQLApplicationId, GraphQLMutationDto graphQLMutationDto);



    /*
     * DELETE OPERATIONS
     */

    /**
     * The method deletes a {@link GraphQLApplication}
     * @param graphQLProjectId The id of the {@link GraphQLProject}
     * @param graphQLApplicationId The id of the {@link GraphQLApplication} that will be deleted
     * @return The deleted {@link GraphQLApplicationDto}
     * @see GraphQLProject
     * @see GraphQLProjectDto
     * @see GraphQLApplication
     * @see GraphQLApplicationDto
     */
    GraphQLApplicationDto deleteGraphQLApplication(String graphQLProjectId, String graphQLApplicationId);
}
