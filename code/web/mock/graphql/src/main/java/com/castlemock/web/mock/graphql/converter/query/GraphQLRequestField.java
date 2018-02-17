package com.castlemock.web.mock.graphql.converter.query;

import java.util.List;

public class GraphQLRequestField {

    private String name;

    private List<GraphQLRequestField> fields;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GraphQLRequestField> getFields() {
        return fields;
    }

    public void setFields(List<GraphQLRequestField> fields) {
        this.fields = fields;
    }
}
