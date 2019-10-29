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

import com.castlemock.core.mock.graphql.model.project.domain.*;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
public class GraphQLDefinitionConverterResult {


    private List<GraphQLObjectType> objects;
    private List<GraphQLEnumType> enums;
    private List<GraphQLQuery> queries;
    private List<GraphQLMutation> mutations;
    private List<GraphQLSubscription> subscriptions;


    public List<GraphQLObjectType> getObjects() {
        return objects;
    }

    public void setObjects(List<GraphQLObjectType> objects) {
        this.objects = objects;
    }

    public List<GraphQLEnumType> getEnums() {
        return enums;
    }

    public void setEnums(List<GraphQLEnumType> enums) {
        this.enums = enums;
    }

    public List<GraphQLQuery> getQueries() {
        return queries;
    }

    public void setQueries(List<GraphQLQuery> queries) {
        this.queries = queries;
    }

    public List<GraphQLMutation> getMutations() {
        return mutations;
    }

    public void setMutations(List<GraphQLMutation> mutations) {
        this.mutations = mutations;
    }

    public List<GraphQLSubscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<GraphQLSubscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
