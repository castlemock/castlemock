package com.castlemock.web.mock.graphql.converter.query;

import java.util.List;

public class GraphQLRequestQuery {

    private String operationName;
    private List<GraphQLRequestField> fields;
    private List<GraphQLRequestArgument> arguments;


    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public List<GraphQLRequestField> getFields() {
        return fields;
    }

    public void setFields(List<GraphQLRequestField> fields) {
        this.fields = fields;
    }

    public List<GraphQLRequestArgument> getArguments() {
        return arguments;
    }

    public void setArguments(List<GraphQLRequestArgument> arguments) {
        this.arguments = arguments;
    }
}
