package com.castlemock.core.mock.graphql.model.project.dto;

import org.dozer.Mapping;


public class GraphQLTypeDto {

    @Mapping("id")
    private String id;

    @Mapping("name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
