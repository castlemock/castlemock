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


package com.castlemock.web.mock.graphql.converter.schema;

import com.castlemock.core.mock.graphql.model.project.domain.GraphQLObjectType;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLResponseStrategy;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLApplicationDto;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLMutationDto;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLQueryDto;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLSubscriptionDto;
import com.castlemock.web.mock.graphql.converter.AbstractGraphQLDefinitionConverter;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLType;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

public class SchemaGraphQLDefinitionConverter extends AbstractGraphQLDefinitionConverter {
    /**
     * The convert method provides the functionality to convert the provided {@link File} into
     * a list of {@link GraphQLApplicationDto}.
     *
     * @param raw             The file which will be converted to one or more {@link GraphQLApplicationDto}.
     * @param generateResponse Will generate a default response if true. No response will be generated if false.
     * @return A list of {@link GraphQLApplicationDto} based on the provided file.
     */
    @Override
    public List<GraphQLApplicationDto> convertRaw(String raw, boolean generateResponse) {
        GraphQLApplicationDto application = new GraphQLApplicationDto();


        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(raw);

        RuntimeWiring runtimeWiring = newRuntimeWiring().build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        List<GraphQLQueryDto> queries = new ArrayList<>();
        List<GraphQLMutationDto> mutations = new ArrayList<>();
        List<GraphQLSubscriptionDto> subscriptions = new ArrayList<>();
        List<GraphQLObjectType> objectTypes = new ArrayList<>();

        for(GraphQLType graphQLType : graphQLSchema.getAllTypesAsList()){

            if(graphQLType instanceof graphql.schema.GraphQLObjectType){
                graphql.schema.GraphQLObjectType type  =
                        (graphql.schema.GraphQLObjectType) graphQLType;


                GraphQLObjectType graphQLObjectType = new GraphQLObjectType();
                graphQLObjectType.setName(type.getName());
                objectTypes.add(graphQLObjectType);
            }

        }

        if(graphQLSchema.getQueryType() != null){
            List<GraphQLFieldDefinition> fieldQueries = graphQLSchema.getQueryType().getFieldDefinitions();
            for(GraphQLFieldDefinition fieldQuery : fieldQueries){
                GraphQLQueryDto query = new GraphQLQueryDto();
                query.setName(fieldQuery.getName());
                query.setResponseStrategy(GraphQLResponseStrategy.RANDOM);
                queries.add(query);
            }
        }

        if(graphQLSchema.getMutationType() != null){
            List<GraphQLFieldDefinition> fieldMutations = graphQLSchema.getMutationType().getFieldDefinitions();
            for(GraphQLFieldDefinition fieldMutation : fieldMutations){
                GraphQLMutationDto mutation = new GraphQLMutationDto();
                mutation.setName(fieldMutation.getName());
                mutation.setResponseStrategy(GraphQLResponseStrategy.RANDOM);
                mutations.add(mutation);
            }
        }

        if(graphQLSchema.getSubscriptionType() != null){
            List<GraphQLFieldDefinition> fieldSubscriptions = graphQLSchema.getSubscriptionType().getFieldDefinitions();
            for(GraphQLFieldDefinition fieldSubscription : fieldSubscriptions){
                GraphQLSubscriptionDto subscription = new GraphQLSubscriptionDto();
                subscription.setName(fieldSubscription.getName());
                subscription.setResponseStrategy(GraphQLResponseStrategy.RANDOM);
                subscriptions.add(subscription);
            }
        }


        application.setQueries(queries);
        application.setMutations(mutations);
        application.setSubscriptions(subscriptions);

        return Collections.singletonList(application);
    }

    /**
     * The convert method provides the functionality to convert the provided {@link File} into
     * a list of {@link GraphQLApplicationDto}.
     *
     * @param file             The file which will be converted to one or more {@link GraphQLApplicationDto}.
     * @param generateResponse Will generate a default response if true. No response will be generated if false.
     * @return A list of {@link GraphQLApplicationDto} based on the provided file.
     */
    @Override
    public List<GraphQLApplicationDto> convertFile(File file, boolean generateResponse) {
        return null;
    }

    /**
     * The convert method provides the functionality to convert the provided {@link File} into
     * a list of {@link GraphQLApplicationDto}.
     *
     * @param location         The location of the definition file
     * @param generateResponse Will generate a default response if true. No response will be generated if false.
     * @return A list of {@link GraphQLApplicationDto} based on the provided file.
     */
    @Override
    public List<GraphQLApplicationDto> convertRemote(String location, boolean generateResponse) {
        return null;
    }
}
