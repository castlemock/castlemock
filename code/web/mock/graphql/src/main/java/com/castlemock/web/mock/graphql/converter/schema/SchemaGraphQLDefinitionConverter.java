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

import com.castlemock.core.mock.graphql.model.project.domain.GraphQLAttributeType;
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

public class SchemaGraphQLDefinitionConverter extends AbstractGraphQLDefinitionConverter {

    private FileManager fileManager;
    private static final Logger LOGGER = Logger.getLogger(SchemaGraphQLDefinitionConverter.class);

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

        final List<GraphQLQueryDto> queries = new ArrayList<>();
        final List<GraphQLMutationDto> mutations = new ArrayList<>();
        final List<GraphQLSubscriptionDto> subscriptions = new ArrayList<>();
        final List<GraphQLObjectTypeDto> objects = new ArrayList<>();
        final List<GraphQLEnumTypeDto> enums = new ArrayList<>();

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

        for(GraphQLObjectTypeDto objectType : objects){
            for(GraphQLAttributeDto attribute : objectType.getAttributes()){
                String typeId = getId(attribute.getTypeName(), attribute.getAttributeType(), objects, enums);
                attribute.setTypeId(typeId);
            }
        }


        if(graphQLSchema.getQueryType() != null){
            List<GraphQLFieldDefinition> fieldQueries = graphQLSchema.getQueryType().getFieldDefinitions();
            for(GraphQLFieldDefinition fieldQuery : fieldQueries){
                GraphQLQueryDto query = GraphQLObjectTypeFactory.query(fieldQuery);
                mapTypes(query, objects, enums);
                queries.add(query);
            }
        }

        if(graphQLSchema.getMutationType() != null){
            List<GraphQLFieldDefinition> fieldMutations = graphQLSchema.getMutationType().getFieldDefinitions();
            for(GraphQLFieldDefinition fieldMutation : fieldMutations){
                GraphQLMutationDto mutation = GraphQLObjectTypeFactory.mutation(fieldMutation);
                mapTypes(mutation, objects, enums);
                mutations.add(mutation);
            }
        }

        if(graphQLSchema.getSubscriptionType() != null){
            List<GraphQLFieldDefinition> fieldSubscriptions = graphQLSchema.getSubscriptionType().getFieldDefinitions();
            for(GraphQLFieldDefinition fieldSubscription : fieldSubscriptions){
                GraphQLSubscriptionDto subscription = GraphQLObjectTypeFactory.subscription(fieldSubscription);
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

    private void mapTypes(final GraphQLOperationDto operation,
                          final List<GraphQLObjectTypeDto> objects,
                          final List<GraphQLEnumTypeDto> enums){
        final GraphQLResultDto result = operation.getResult();
        final String typeId = getId(result.getTypeName(), result.getAttributeType(), objects, enums);
        result.setTypeId(typeId);

        for(GraphQLArgumentDto argument : operation.getArguments()){
            String argumentTypeId = getId(argument.getTypeName(), argument.getAttributeType(), objects, enums);
            argument.setTypeId(argumentTypeId);
        }
    }


    private String getId(final String typeName,
                          final GraphQLAttributeType attributeType,
                          final List<GraphQLObjectTypeDto> objects,
                          final List<GraphQLEnumTypeDto> enums){
        if(GraphQLAttributeType.OBJECT_TYPE.equals(attributeType)){
            for(GraphQLObjectTypeDto otherObjectType : objects){
                if(typeName.equals(otherObjectType.getName())){
                    return otherObjectType.getId();
                }
            }
        } else if(GraphQLAttributeType.ENUM.equals(attributeType)){
            for(GraphQLEnumTypeDto enumType : enums){
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
