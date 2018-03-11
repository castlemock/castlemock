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

package com.castlemock.core.mock.graphql.model.project.domain;

import com.castlemock.core.basis.model.Saveable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@XmlRootElement
public class GraphQLApplication implements Saveable<String> {

    private String id;
    private String name;
    private String description;
    private List<GraphQLObjectType> objects = new CopyOnWriteArrayList<GraphQLObjectType>();
    private List<GraphQLEnumType> enums = new CopyOnWriteArrayList<GraphQLEnumType>();
    private List<GraphQLQuery> queries = new CopyOnWriteArrayList<GraphQLQuery>();
    private List<GraphQLMutation> mutations = new CopyOnWriteArrayList<GraphQLMutation>();
    private List<GraphQLSubscription> subscriptions = new CopyOnWriteArrayList<GraphQLSubscription>();


    @Override
    @XmlElement
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElementWrapper(name = "objects")
    @XmlElement(name = "object")
    public List<GraphQLObjectType> getObjects() {
        return objects;
    }

    public void setObjects(List<GraphQLObjectType> objects) {
        this.objects = objects;
    }

    @XmlElementWrapper(name = "enums")
    @XmlElement(name = "enum")
    public List<GraphQLEnumType> getEnums() {
        return enums;
    }

    public void setEnums(List<GraphQLEnumType> enums) {
        this.enums = enums;
    }


    @XmlElementWrapper(name = "queries")
    @XmlElement(name = "query")
    public List<GraphQLQuery> getQueries() {
        return queries;
    }

    public void setQueries(List<GraphQLQuery> queries) {
        this.queries = queries;
    }

    @XmlElementWrapper(name = "mutations")
    @XmlElement(name = "mutation")
    public List<GraphQLMutation> getMutations() {
        return mutations;
    }

    public void setMutations(List<GraphQLMutation> mutations) {
        this.mutations = mutations;
    }

    @XmlElementWrapper(name = "subscriptions")
    @XmlElement(name = "subscription")
    public List<GraphQLSubscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<GraphQLSubscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

}
