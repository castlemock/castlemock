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
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLResponseStrategy;
import com.castlemock.core.mock.graphql.model.project.dto.*;
import graphql.language.*;
import graphql.schema.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GraphQLObjectTypeFactory {

    private static final String TYPE_ID = "ID";
    private static final String TYPE_STRING = "String";
    private static final String TYPE_INT = "Int";
    private static final String TYPE_FLOAT = "Float";
    private static final String TYPE_BOOLEAN = "Boolean";
    private static final Set<String> REFUSED_OBJECT_TYPES = new HashSet<String>(Arrays.asList(
            "Query", "ID", "String", "Int", "__Schema",
            "__Type", "__TypeKind", "__Field", "__InputValue",
            "Boolean", "__EnumValue", "__Directive", "__DirectiveLocation"));


    public static GraphQLMutationDto mutation(GraphQLFieldDefinition definition){
        final String id = generateId();
        final GraphQLMutationDto mutation = new GraphQLMutationDto();
        mutation.setId(id);
        mutation.setName(definition.getName());
        mutation.setDescription(definition.getDescription());
        mutation.setResponseStrategy(GraphQLResponseStrategy.RANDOM);

        for(GraphQLArgument argument : definition.getArguments()){
            GraphQLArgumentDto graphQLArgument = getArgument(argument);
            mutation.getArguments().add(graphQLArgument);
        }

        GraphQLResultDto result = getResult(definition.getType());
        mutation.setResult(result);

        return mutation;
    }

    public static GraphQLQueryDto query(GraphQLFieldDefinition definition){
        final String id = generateId();
        final GraphQLQueryDto query = new GraphQLQueryDto();
        query.setId(id);
        query.setName(definition.getName());
        query.setDescription(definition.getDescription());
        query.setResponseStrategy(GraphQLResponseStrategy.RANDOM);

        for(GraphQLArgument argument : definition.getArguments()){
            GraphQLArgumentDto graphQLArgument = getArgument(argument);
            query.getArguments().add(graphQLArgument);
        }

        GraphQLResultDto result = getResult(definition.getType());
        query.setResult(result);

        return query;
    }

    public static GraphQLSubscriptionDto subscription(GraphQLFieldDefinition definition){
        final String id = generateId();
        final GraphQLSubscriptionDto subscription = new GraphQLSubscriptionDto();
        subscription.setId(id);
        subscription.setName(definition.getName());
        subscription.setDescription(definition.getDescription());
        subscription.setResponseStrategy(GraphQLResponseStrategy.RANDOM);

        for(GraphQLArgument argument : definition.getArguments()){
            GraphQLArgumentDto graphQLArgument = getArgument(argument);
            subscription.getArguments().add(graphQLArgument);
        }

        GraphQLResultDto result = getResult(definition.getType());
        subscription.setResult(result);

        return subscription;
    }

    public static GraphQLTypeDto parse(graphql.schema.GraphQLType graphQLType){

        if(graphQLType instanceof graphql.schema.GraphQLObjectType) {
            graphql.schema.GraphQLObjectType type =
                    (graphql.schema.GraphQLObjectType) graphQLType;
            return getObjectType(type);
        } else if(graphQLType instanceof graphql.schema.GraphQLEnumType) {
            graphql.schema.GraphQLEnumType type =
                    (graphql.schema.GraphQLEnumType) graphQLType;
            return getEnum(type);
        }





        return null;
    }


    private static GraphQLObjectTypeDto getObjectType(final graphql.schema.GraphQLObjectType type){
        final String name = type.getName();
        if(REFUSED_OBJECT_TYPES.contains(name)){
            return null;
        }


        final String id = generateId();
        final GraphQLObjectTypeDto graphQLObjectType = new GraphQLObjectTypeDto();
        graphQLObjectType.setName(name);
        graphQLObjectType.setId(id);
        graphQLObjectType.setDescription(type.getDescription());

        for(GraphQLFieldDefinition definition : type.getFieldDefinitions()){
            GraphQLAttributeDto attribute = getAttribute(definition);
            graphQLObjectType.getAttributes().add(attribute);
        }

        return graphQLObjectType;
    }

    private static GraphQLEnumTypeDto getEnum(final graphql.schema.GraphQLEnumType type){
        final String name = type.getName();
        if(REFUSED_OBJECT_TYPES.contains(name)){
            return null;
        }

        final String id = generateId();
        final GraphQLEnumTypeDto graphQLEnumType = new GraphQLEnumTypeDto();
        graphQLEnumType.setId(id);
        graphQLEnumType.setName(name);
        graphQLEnumType.setDescription(type.getDescription());

        for(EnumValueDefinition definition : type.getDefinition().getEnumValueDefinitions()){
            GraphQLEnumValueDefinitionDto enumValueDefinition = new GraphQLEnumValueDefinitionDto();
            enumValueDefinition.setName(definition.getName());
            graphQLEnumType.getDefinitions().add(enumValueDefinition);
        }

        return graphQLEnumType;
    }

    private static GraphQLResultDto getResult(final GraphQLOutputType outputType){
        final GraphQLType type = getType(outputType);
        final boolean nullable = isNullable(outputType);
        final boolean listable = isListable(outputType);
        final GraphQLAttributeType attributeType = getAttributeType(type);

        final String id = generateId();
        final GraphQLResultDto result = new GraphQLResultDto();
        result.setId(id);
        result.setTypeName(type.getName());
        result.setListable(listable);
        result.setNullable(nullable);
        result.setAttributeType(attributeType);

        return result;
    }

    private static GraphQLAttributeDto getAttribute(final GraphQLFieldDefinition definition){
        final GraphQLType type = definition.getType();
        final boolean nullable = isNullable(type);
        final boolean listable = isListable(type);
        final GraphQLType coreType = getType(definition.getType());

        final String id = generateId();
        final GraphQLAttributeDto attribute = new GraphQLAttributeDto();
        final GraphQLAttributeType attributeType = getAttributeType(coreType);
        attribute.setName(definition.getName());
        attribute.setId(id);
        attribute.setDescription(definition.getDescription());
        attribute.setNullable(nullable);
        attribute.setListable(listable);
        attribute.setAttributeType(attributeType);

        for(GraphQLArgument argument : definition.getArguments()){
            GraphQLArgumentDto graphQLArgument = getArgument(argument);
            attribute.getArguments().add(graphQLArgument);
        }

        if(GraphQLAttributeType.OBJECT_TYPE.equals(attributeType)){
            attribute.setTypeName(coreType.getName());
        } else if(GraphQLAttributeType.ENUM.equals(attributeType)){
            attribute.setTypeName(coreType.getName());
        }

        return attribute;
    }

    private static GraphQLArgumentDto getArgument(final GraphQLArgument argument){
        final GraphQLArgumentDto graphQLArgument = new GraphQLArgumentDto();
        final GraphQLType type = argument.getType();
        final boolean nullable = isNullable(type);
        final boolean listable = isListable(type);
        final GraphQLType coreType = getType(argument.getType());
        final GraphQLAttributeType attributeType = getAttributeType(coreType);
        final String id = generateId();

        graphQLArgument.setName(argument.getName());
        graphQLArgument.setId(id);
        graphQLArgument.setNullable(nullable);
        graphQLArgument.setDescription(argument.getDescription());
        graphQLArgument.setListable(listable);
        graphQLArgument.setAttributeType(attributeType);
        graphQLArgument.setDefaultValue(argument.getDefaultValue());

        if(GraphQLAttributeType.OBJECT_TYPE.equals(attributeType)){
            graphQLArgument.setTypeName(coreType.getName());
        } else if(GraphQLAttributeType.ENUM.equals(attributeType)){
            graphQLArgument.setTypeName(coreType.getName());
        }

        return graphQLArgument;
    }

    private static GraphQLAttributeType getAttributeType(GraphQLType type){
        type = getType(type);

        final String name = type.getName();
        if(type instanceof GraphQLScalarType){
            if(TYPE_ID.equalsIgnoreCase(name)){
                return GraphQLAttributeType.ID;
            } else if(TYPE_STRING.equalsIgnoreCase(name)){
                return GraphQLAttributeType.STRING;
            } else if(TYPE_INT.equalsIgnoreCase(name)){
                return GraphQLAttributeType.INT;
            } else if(TYPE_FLOAT.equalsIgnoreCase(name)){
                return GraphQLAttributeType.FLOAT;
            } else if(TYPE_BOOLEAN.equalsIgnoreCase(name)){
                return GraphQLAttributeType.BOOLEAN;
            }
        } else if(type instanceof graphql.schema.GraphQLObjectType){
            return GraphQLAttributeType.OBJECT_TYPE;
        } else if(type instanceof graphql.schema.GraphQLEnumType){
            return GraphQLAttributeType.ENUM;
        }

        throw new IllegalArgumentException("Unable to parse the following attribute: " + name);
    }


    private static boolean isNullable(final GraphQLType type){
        if(type instanceof GraphQLList){
            GraphQLType wrappedType = ((GraphQLList) type).getWrappedType();
            return isNullable(wrappedType);
        }

        return !(type instanceof GraphQLNonNull);
    }

    private static boolean isListable(final GraphQLType type){
        if(type instanceof GraphQLNonNull){
            GraphQLType wrappedType = ((GraphQLNonNull) type).getWrappedType();
            return isListable(wrappedType);
        }

        return (type instanceof GraphQLList);
    }

    private static GraphQLType getType(final GraphQLType type){
        if(type instanceof GraphQLNonNull){
            GraphQLType wrappedType = ((GraphQLNonNull) type).getWrappedType();
            return getType(wrappedType);
        }
        if(type instanceof GraphQLList){
            GraphQLType wrappedType = ((GraphQLList) type).getWrappedType();
            return getType(wrappedType);
        }

        return type;
    }

    /**
     * The method is responsible for generating new ID for the entity. The
     * ID will be six character and contain both characters and numbers.
     * @return A generated ID
     */
    protected static String generateId(){
        return RandomStringUtils.random(6, true, true);
    }

}
