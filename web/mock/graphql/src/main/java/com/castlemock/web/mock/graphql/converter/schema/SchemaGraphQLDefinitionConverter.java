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

import com.castlemock.core.mock.graphql.model.project.domain.*;
import com.castlemock.web.basis.manager.FileManager;
import com.castlemock.web.mock.graphql.converter.AbstractGraphQLDefinitionConverter;
import com.castlemock.web.mock.graphql.converter.GraphQLDefinitionConverterResult;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SchemaGraphQLDefinitionConverter extends AbstractGraphQLDefinitionConverter {

    private FileManager fileManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(SchemaGraphQLDefinitionConverter.class);

    public SchemaGraphQLDefinitionConverter(FileManager fileManager){
        this.fileManager = fileManager;
    }

    @Override
    public GraphQLDefinitionConverterResult convertRaw(String raw) {
        final SchemaParser schemaParser = new SchemaParser();
        final TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(raw);
        final RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring().build();

        final SchemaGenerator schemaGenerator = new SchemaGenerator();
        final GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        final List<GraphQLQuery> queries = new ArrayList<>();
        final List<GraphQLMutation> mutations = new ArrayList<>();
        final List<GraphQLSubscription> subscriptions = new ArrayList<>();
        final List<GraphQLObjectType> objects = new ArrayList<>();
        final List<GraphQLEnumType> enums = new ArrayList<>();

        for(graphql.schema.GraphQLType graphQLType : graphQLSchema.getAllTypesAsList()){
            GraphQLType type = GraphQLObjectTypeFactory.parse(graphQLType);

            if(type instanceof GraphQLObjectType){
                GraphQLObjectType objectType = (GraphQLObjectType) type;
                objects.add(objectType);
            } else if(type instanceof GraphQLEnumType){
                GraphQLEnumType enumType = (GraphQLEnumType) type;
                enums.add(enumType);
            }
        }

        for(GraphQLObjectType objectType : objects){
            for(GraphQLAttribute attribute : objectType.getAttributes()){
                String typeId = getId(attribute.getTypeName(), attribute.getAttributeType(), objects, enums);
                attribute.setTypeId(typeId);
            }
        }


        if(graphQLSchema.getQueryType() != null){
            List<GraphQLFieldDefinition> fieldQueries = graphQLSchema.getQueryType().getFieldDefinitions();
            for(GraphQLFieldDefinition fieldQuery : fieldQueries){
                GraphQLQuery query = GraphQLObjectTypeFactory.query(fieldQuery);
                mapTypes(query, objects, enums);
                queries.add(query);
            }
        }

        if(graphQLSchema.getMutationType() != null){
            List<GraphQLFieldDefinition> fieldMutations = graphQLSchema.getMutationType().getFieldDefinitions();
            for(GraphQLFieldDefinition fieldMutation : fieldMutations){
                GraphQLMutation mutation = GraphQLObjectTypeFactory.mutation(fieldMutation);
                mapTypes(mutation, objects, enums);
                mutations.add(mutation);
            }
        }

        if(graphQLSchema.getSubscriptionType() != null){
            List<GraphQLFieldDefinition> fieldSubscriptions = graphQLSchema.getSubscriptionType().getFieldDefinitions();
            for(GraphQLFieldDefinition fieldSubscription : fieldSubscriptions){
                GraphQLSubscription subscription = GraphQLObjectTypeFactory.subscription(fieldSubscription);
                mapTypes(subscription, objects, enums);
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

    private void mapTypes(final GraphQLOperation operation,
                          final List<GraphQLObjectType> objects,
                          final List<GraphQLEnumType> enums){
        final GraphQLResult result = operation.getResult();
        final String typeId = getId(result.getTypeName(), result.getAttributeType(), objects, enums);
        result.setTypeId(typeId);

        for(GraphQLArgument argument : operation.getArguments()){
            String argumentTypeId = getId(argument.getTypeName(), argument.getAttributeType(), objects, enums);
            argument.setTypeId(argumentTypeId);
        }
    }


    private String getId(final String typeName,
                          final GraphQLAttributeType attributeType,
                          final List<GraphQLObjectType> objects,
                          final List<GraphQLEnumType> enums){
        if(GraphQLAttributeType.OBJECT_TYPE.equals(attributeType)){
            for(GraphQLObjectType otherObjectType : objects){
                if(typeName.equals(otherObjectType.getName())){
                    return otherObjectType.getId();
                }
            }
        } else if(GraphQLAttributeType.ENUM.equals(attributeType)){
            for(GraphQLEnumType enumType : enums){
                if(typeName.equals(enumType.getName())){
                    return enumType.getId();
                }
            }
        }
        return null;
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
