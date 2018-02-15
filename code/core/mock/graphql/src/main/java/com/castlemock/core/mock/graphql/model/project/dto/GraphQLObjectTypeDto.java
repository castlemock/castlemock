package com.castlemock.core.mock.graphql.model.project.dto;

import org.dozer.Mapping;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class GraphQLObjectTypeDto extends GraphQLTypeDto{

    @Mapping("attributes")
    private List<GraphQLAttributeDto> attributes = new CopyOnWriteArrayList<GraphQLAttributeDto>();

    public List<GraphQLAttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<GraphQLAttributeDto> attributes) {
        this.attributes = attributes;
    }
}
