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


package com.castlemock.core.mock.graphql.model.project.dto;

import com.castlemock.core.mock.graphql.model.project.domain.GraphQLAttributeType;
import org.dozer.Mapping;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class GraphQLAttributeDto {

    @Mapping("id")
    private String id;

    @Mapping("name")
    private String name;

    @Mapping("description")
    private String description;

    @Mapping("typeName")
    private String typeName;

    @Mapping("typeId")
    private String typeId;

    @Mapping("nullable")
    private Boolean nullable;

    @Mapping("listable")
    private Boolean listable;

    @Mapping("attributeType")
    private GraphQLAttributeType attributeType;

    @Mapping("arguments")
    private List<GraphQLArgumentDto> arguments = new CopyOnWriteArrayList<GraphQLArgumentDto>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public List<GraphQLArgumentDto> getArguments() {
        return arguments;
    }

    public void setArguments(List<GraphQLArgumentDto> arguments) {
        this.arguments = arguments;
    }
}
