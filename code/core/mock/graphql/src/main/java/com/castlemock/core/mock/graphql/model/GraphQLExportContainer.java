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

package com.castlemock.core.mock.graphql.model;

import com.castlemock.core.basis.model.ExportContainer;
import com.castlemock.core.mock.graphql.model.project.domain.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.20
 */
@XmlRootElement
public class GraphQLExportContainer extends ExportContainer {

    private GraphQLProject project;
    private List<GraphQLApplication> applications;
    private List<GraphQLObjectType> objectTypes;
    private List<GraphQLEnumType> enumTypes;
    private List<GraphQLQuery> queries;
    private List<GraphQLMutation> mutations;
    private List<GraphQLSubscription> subscriptions;

    @XmlElement
    public GraphQLProject getProject() {
        return project;
    }

    public void setProject(GraphQLProject project) {
        this.project = project;
    }

    @XmlElementWrapper(name = "applications")
    @XmlElement(name = "application")
    public List<GraphQLApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<GraphQLApplication> applications) {
        this.applications = applications;
    }

    @XmlElementWrapper(name = "objectTypes")
    @XmlElement(name = "objectType")
    public List<GraphQLObjectType> getObjectTypes() {
        return objectTypes;
    }

    public void setObjectTypes(List<GraphQLObjectType> objectTypes) {
        this.objectTypes = objectTypes;
    }

    @XmlElementWrapper(name = "enumTypes")
    @XmlElement(name = "enumType")
    public List<GraphQLEnumType> getEnumTypes() {
        return enumTypes;
    }

    public void setEnumTypes(List<GraphQLEnumType> enumTypes) {
        this.enumTypes = enumTypes;
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
