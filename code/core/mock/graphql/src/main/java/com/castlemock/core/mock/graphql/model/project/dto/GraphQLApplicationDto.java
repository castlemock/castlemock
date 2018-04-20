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

import org.dozer.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
public class GraphQLApplicationDto {

    @Mapping("id")
    private String id;

    @Mapping("name")
    private String name;

    @Mapping("description")
    private String description;

    @Mapping("projectId")
    private String projectId;

    private List<GraphQLObjectTypeDto> objects = new ArrayList<>();
    private List<GraphQLEnumTypeDto> enums = new ArrayList<>();
    private List<GraphQLQueryDto> queries = new ArrayList<>();
    private List<GraphQLMutationDto> mutations = new ArrayList<>();
    private List<GraphQLSubscriptionDto> subscriptions = new ArrayList<>();
    private String invokeAddress;

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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public List<GraphQLObjectTypeDto> getObjects() {
        return objects;
    }

    public void setObjects(List<GraphQLObjectTypeDto> objects) {
        this.objects = objects;
    }

    public List<GraphQLEnumTypeDto> getEnums() {
        return enums;
    }

    public void setEnums(List<GraphQLEnumTypeDto> enums) {
        this.enums = enums;
    }

    public List<GraphQLQueryDto> getQueries() {
        return queries;
    }

    public void setQueries(List<GraphQLQueryDto> queries) {
        this.queries = queries;
    }

    public List<GraphQLMutationDto> getMutations() {
        return mutations;
    }

    public void setMutations(List<GraphQLMutationDto> mutations) {
        this.mutations = mutations;
    }

    public List<GraphQLSubscriptionDto> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<GraphQLSubscriptionDto> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public String getInvokeAddress() {
        return invokeAddress;
    }

    public void setInvokeAddress(String invokeAddress) {
        this.invokeAddress = invokeAddress;
    }
}
