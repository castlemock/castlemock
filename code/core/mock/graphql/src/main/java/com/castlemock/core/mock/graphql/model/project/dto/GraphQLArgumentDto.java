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


public class GraphQLArgumentDto {

    @Mapping("id")
    private String id;

    @Mapping("name")
    private String name;

    @Mapping("objectType")
    private String objectType;

    @Mapping("defaultValue")
    private Object defaultValue;

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

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
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
