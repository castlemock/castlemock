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

import com.castlemock.core.mock.graphql.model.project.dto.*;
import com.castlemock.web.basis.manager.FileManager;
import com.castlemock.web.mock.graphql.converter.AbstractGraphQLDefinitionConverter;
import com.castlemock.web.mock.graphql.converter.GraphQLDefinitionConverterResult;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLType;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

public class SchemaGraphQLDefinitionConverter extends AbstractGraphQLDefinitionConverter {

    private FileManager fileManager;
    private static final Logger LOGGER = Logger.getLogger(SchemaGraphQLDefinitionConverter.class);

    public SchemaGraphQLDefinitionConverter(FileManager fileManager){
        this.fileManager = fileManager;
    }

    @Override
    public GraphQLDefinitionConverterResult convertRaw(String raw) {
        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(raw);

        RuntimeWiring runtimeWiring = newRuntimeWiring().build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        List<GraphQLQueryDto> queries = new ArrayList<>();
        List<GraphQLMutationDto> mutations = new ArrayList<>();
        List<GraphQLSubscriptionDto> subscriptions = new ArrayList<>();
        List<GraphQLObjectTypeDto> objects = new ArrayList<>();
        List<GraphQLEnumTypeDto> enums = new ArrayList<>();

        for(GraphQLType graphQLType : graphQLSchema.getAllTypesAsList()){
            GraphQLTypeDto type = GraphQLObjectTypeFactory.parse(graphQLType);

            if(type instanceof GraphQLObjectTypeDto){
                GraphQLObjectTypeDto objectType = (GraphQLObjectTypeDto) type;
                objects.add(objectType);
            } else if(type instanceof GraphQLEnumTypeDto){
                GraphQLEnumTypeDto enumType = (GraphQLEnumTypeDto) type;
                enums.add(enumType);
            }
        }

        if(graphQLSchema.getQueryType() != null){
            List<GraphQLFieldDefinition> fieldQueries = graphQLSchema.getQueryType().getFieldDefinitions();
            for(GraphQLFieldDefinition fieldQuery : fieldQueries){
                GraphQLQueryDto query = GraphQLObjectTypeFactory.query(fieldQuery);
                queries.add(query);
            }
        }

        if(graphQLSchema.getMutationType() != null){
            List<GraphQLFieldDefinition> fieldMutations = graphQLSchema.getMutationType().getFieldDefinitions();
            for(GraphQLFieldDefinition fieldMutation : fieldMutations){
                GraphQLMutationDto mutation = GraphQLObjectTypeFactory.mutation(fieldMutation);
                mutations.add(mutation);
            }
        }

        if(graphQLSchema.getSubscriptionType() != null){
            List<GraphQLFieldDefinition> fieldSubscriptions = graphQLSchema.getSubscriptionType().getFieldDefinitions();
            for(GraphQLFieldDefinition fieldSubscription : fieldSubscriptions){
                GraphQLSubscriptionDto subscription = GraphQLObjectTypeFactory.subscription(fieldSubscription);
                subscriptions.add(subscription);
            }
        }

        GraphQLDefinitionConverterResult result = new GraphQLDefinitionConverterResult();
        result.setObjects(objects);
        result.setEnums(enums);
        result.setQueries(queries);
        result.setMutations(mutations);
        result.setSubscriptions(subscriptions);

        return result;
    }

    @Override
    public GraphQLDefinitionConverterResult convertFile(File file) {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            stream.read(data);
            String schema = new String(data, "UTF-8");
            return convertRaw(schema);
        } catch (Exception e){
            throw new IllegalStateException(e);
        } finally {
            if(stream != null){
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public GraphQLDefinitionConverterResult convertRemote(String location) {
        List<File> files = null;
        try {
            files = fileManager.uploadFiles(location);
            GraphQLDefinitionConverterResult result = convertFile(files.get(0));
            return result;
        } catch (IOException e) {
            LOGGER.error("Unable to download file file: " + location, e);
            throw new IllegalStateException(e);
        } finally {
            if(files != null){
                for(File uploadedFile : files){
                    boolean deletionResult = fileManager.deleteFile(uploadedFile);
                    if(deletionResult){
                        LOGGER.debug("Deleted the following WADL file: " + uploadedFile.getName());
                    } else {
                        LOGGER.warn("Unable to delete the following WADL file: " + uploadedFile.getName());
                    }

                }
            }
        }
    }

}
