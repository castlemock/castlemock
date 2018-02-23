package com.castlemock.web.mock.graphql.converter.query;

import graphql.language.*;
import graphql.parser.Parser;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.util.ArrayList;
import java.util.List;

public class QueryGraphQLConverter {


    public List<GraphQLRequestQuery> parseQuery(final String body){
        Parser parser = new Parser();
        Document document;
        try {
            document = parser.parseDocument(body);
            document.getChildren();

            for(Definition definition : document.getDefinitions()){

                if(definition instanceof OperationDefinition){
                    OperationDefinition operationDefinition = (OperationDefinition) definition;
                    OperationDefinition.Operation operation = operationDefinition.getOperation();

                    if(operation == OperationDefinition.Operation.QUERY){
                        return getQuery(operationDefinition);
                    }

                }

            }

        } catch (ParseCancellationException e) {
            System.out.println(e);
        }
        return null;
    }


    private List<GraphQLRequestQuery> getQuery(final OperationDefinition operationDefinition){
        final List<GraphQLRequestQuery> queries = new ArrayList<>();
        for(Selection selection : operationDefinition.getSelectionSet().getSelections()){
            if(selection instanceof Field){
                Field field = (Field) selection;

                final GraphQLRequestQuery query = new GraphQLRequestQuery();
                final List<GraphQLRequestField> fields = new ArrayList<>();
                final List<GraphQLRequestArgument> arguments = new ArrayList<>();
                query.setOperationName(field.getName());
                query.setFields(fields);
                query.setArguments(arguments);

                for(Selection subSelection : field.getSelectionSet().getSelections()){
                    if(subSelection instanceof Field){
                        Field subField = (Field) subSelection;
                        GraphQLRequestField subRequestField = getField(subField);
                        fields.add(subRequestField);
                    }
                }

                for(Argument argument : field.getArguments()){
                    GraphQLRequestArgument requestArgument = getArgument(argument);
                    arguments.add(requestArgument);
                }

                queries.add(query);

            }
        }

        return queries;
    }

    private GraphQLRequestField getField(Field field){
        final GraphQLRequestField requestField = new GraphQLRequestField();
        final List<GraphQLRequestField> fields = new ArrayList<>();
        final List<GraphQLRequestArgument> arguments = new ArrayList<>();
        requestField.setName(field.getName());
        requestField.setFields(fields);

        if(field.getSelectionSet() != null && field.getSelectionSet().getSelections() != null){
            for(Selection selection : field.getSelectionSet().getSelections()){
                if(selection instanceof Field){
                    Field subField = (Field) selection;
                    GraphQLRequestField subRequestField = getField(subField);
                    fields.add(subRequestField);
                }
            }
        }

        for(Argument argument : field.getArguments()){
            GraphQLRequestArgument requestArgument = getArgument(argument);
            arguments.add(requestArgument);
        }


        return requestField;
    }

    private GraphQLRequestArgument getArgument(final Argument argument){
        final GraphQLRequestArgument requestArgument = new GraphQLRequestArgument();
        requestArgument.setName(argument.getName());
        return requestArgument;

    }

}
