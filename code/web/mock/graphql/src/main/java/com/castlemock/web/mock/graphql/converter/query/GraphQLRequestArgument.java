package com.castlemock.web.mock.graphql.converter.query;

import java.util.List;

public class GraphQLRequestArgument {

    private String name;
    private List<GraphQLRequestArgument> arguments;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GraphQLRequestArgument> getArguments() {
        return arguments;
    }

    public void setArguments(List<GraphQLRequestArgument> arguments) {
        this.arguments = arguments;
    }
}
