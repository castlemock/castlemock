package com.castlemock.core.mock.graphql.model.project.dto;

import com.castlemock.core.mock.graphql.model.project.domain.GraphQLAttributeType;
import org.dozer.Mapping;


public class GraphQLAttributeDto {

    @Mapping("id")
    private String id;

    @Mapping("name")
    private String name;

    @Mapping("objectType")
    private String objectType;

    @Mapping("nullable")
    private Boolean nullable;

    @Mapping("listable")
    private Boolean listable;

    @Mapping("attributeType")
    private GraphQLAttributeType attributeType;

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

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public Boolean getNullable() {
        return nullable;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public Boolean getListable() {
        return listable;
    }

    public void setListable(Boolean listable) {
        this.listable = listable;
    }

    public GraphQLAttributeType getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(GraphQLAttributeType attributeType) {
        this.attributeType = attributeType;
    }
}
