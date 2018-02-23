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


package com.castlemock.web.mock.graphql.converter;

import com.castlemock.core.mock.graphql.model.project.dto.*;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
public class GraphQLDefinitionConverterResult {


    private List<GraphQLObjectTypeDto> objects;
    private List<GraphQLEnumTypeDto> enums;
    private List<GraphQLQueryDto> queries;
    private List<GraphQLMutationDto> mutations;
    private List<GraphQLSubscriptionDto> subscriptions;


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
}
