package com.castlemock.web.mock.graphql.converter.schema;

import com.castlemock.core.mock.graphql.model.project.domain.GraphQLAttributeType;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLAttributeDto;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLObjectTypeDto;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLTypeDto;
import graphql.schema.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GraphQLObjectTypeFactory {

    private static final Set<String> REFUSED_OBJECT_TYPES = new HashSet<String>(Arrays.asList(
            "Query", "ID", "String", "Int", "__Schema",
            "__Type", "__TypeKind", "__Field", "__InputValue",
            "Boolean", "__EnumValue", "__Directive", "__DirectiveLocation"));


    public static GraphQLTypeDto parse(graphql.schema.GraphQLType graphQLType){
        if(graphQLType instanceof graphql.schema.GraphQLObjectType) {
            graphql.schema.GraphQLObjectType type =
                    (graphql.schema.GraphQLObjectType) graphQLType;
            final String name = graphQLType.getName();

            if(REFUSED_OBJECT_TYPES.contains(name)){
                return null;
            }

            final GraphQLObjectTypeDto graphQLObjectType = new GraphQLObjectTypeDto();
            graphQLObjectType.setName(name);

            for(GraphQLFieldDefinition definition : type.getFieldDefinitions()){
                GraphQLAttributeDto attribute = getAttribute(definition);
                graphQLObjectType.getAttributes().add(attribute);
            }

            return graphQLObjectType;
        } else if(graphQLType instanceof graphql.schema.GraphQLEnumType) {

        }



        return null;
    }


    private static GraphQLAttributeDto getAttribute(final GraphQLFieldDefinition definition){
        final GraphQLOutputType type = definition.getType();
        final boolean nullable = isNullable(type);
        final boolean listable = isListable(type);

        final GraphQLAttributeDto attribute = new GraphQLAttributeDto();
        final GraphQLAttributeType attributeType = getAttributeType(type);
        attribute.setName(definition.getName());
        attribute.setNullable(nullable);
        attribute.setListable(listable);
        attribute.setAttributeType(attributeType);
        attribute.setObjectType(type.getName());

        return attribute;
    }

    private static final String TYPE_ID = "ID";
    private static final String TYPE_STRING = "String";
    private static final String TYPE_INT = "Int";
    private static final String TYPE_FLOAT = "Float";
    private static final String TYPE_BOOLEAN = "Boolean";

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


}
