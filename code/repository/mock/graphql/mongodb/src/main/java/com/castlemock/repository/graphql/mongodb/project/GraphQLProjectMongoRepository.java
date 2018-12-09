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

package com.castlemock.repository.graphql.mongodb.project;

import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLAttributeType;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLProject;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLRequestArgument;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLRequestField;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.mongodb.project.AbstractProjectMongoRepository;
import com.castlemock.repository.graphql.project.GraphQLProjectRepository;
import com.google.common.base.Preconditions;
import org.dozer.Mapping;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author Mohammad Hewedy
 * @since 1.35
 */
@Repository
@Profile(Profiles.MONGODB)
public class GraphQLProjectMongoRepository extends AbstractProjectMongoRepository<GraphQLProjectMongoRepository.GraphQLProjectDocument, GraphQLProject> implements GraphQLProjectRepository {

    /**
     * The method is responsible for controller that the type that is about the be saved to mongodb is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to mongodb, but also loaded from
     * mongodb upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param type The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             mongodb.
     * @see #save
     */
    @Override
    protected void checkType(GraphQLProjectDocument type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<GraphQLProject> search(SearchQuery query) {
        Query nameQuery = getSearchQuery("name", query);
        List<GraphQLProjectDocument> operations =
                mongoOperations.find(nameQuery, GraphQLProjectDocument.class);
        return toDtoList(operations, GraphQLProject.class);
    }


    /**
     * Finds a project by a given name
     *
     * @param name The name of the project that should be retrieved
     * @return Returns a project with the provided name
     * @see GraphQLProject
     */
    @Override
    public GraphQLProject findGraphQLProjectWithName(String name) {
        Preconditions.checkNotNull(name, "Project name cannot be null");
        Preconditions.checkArgument(!name.isEmpty(), "Project name cannot be empty");

        Query exactNameIgnoreCaseQuery = query(where("name").regex("^" + name + "$", "i"));
        GraphQLProjectDocument project = mongoOperations.findOne(exactNameIgnoreCaseQuery, GraphQLProjectDocument.class);
        return project == null ? null : mapper.map(project, GraphQLProject.class);
    }

    @Document(collection = "graphQLProject")
    protected static class GraphQLProjectDocument extends AbstractProjectMongoRepository.ProjectDocument {

    }

    @Document(collection = "graphQLRequestQuery")
    protected static class GraphQLRequestQueryDocument implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("operationName")
        private String operationName;
        @Mapping("fields")
        private List<GraphQLRequestField> fields = new CopyOnWriteArrayList<GraphQLRequestField>();
        @Mapping("arguments")
        private List<GraphQLRequestArgument> arguments = new CopyOnWriteArrayList<GraphQLRequestArgument>();

        @Override
        public String getId() {
            return id;
        }

        @Override
        public void setId(String id) {
            this.id = id;
        }

        public String getOperationName() {
            return operationName;
        }

        public void setOperationName(String operationName) {
            this.operationName = operationName;
        }

        public List<GraphQLRequestField> getFields() {
            return fields;
        }

        public void setFields(List<GraphQLRequestField> fields) {
            this.fields = fields;
        }

        public List<GraphQLRequestArgument> getArguments() {
            return arguments;
        }

        public void setArguments(List<GraphQLRequestArgument> arguments) {
            this.arguments = arguments;
        }
    }

    @Document(collection = "graphQLAttribute")
    protected static class GraphQLAttributeDocument implements Saveable<String> {

        private String id;
        private String name;
        private String description;
        private String typeId;
        private String typeName;
        private Boolean nullable;
        private Boolean listable;
        private String objectTypeId;
        private String value;
        private GraphQLAttributeType attributeType;
        private List<GraphQLArgumentDocument> arguments = new CopyOnWriteArrayList<GraphQLArgumentDocument>();

        public String getId() {
            return id;
        }

        @Override
        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
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

        public List<GraphQLArgumentDocument> getArguments() {
            return arguments;
        }

        public void setArguments(List<GraphQLArgumentDocument> arguments) {
            this.arguments = arguments;
        }

        public String getObjectTypeId() {
            return objectTypeId;
        }

        public void setObjectTypeId(String objectTypeId) {
            this.objectTypeId = objectTypeId;
        }
    }

    @Document(collection = "graphQLRequestArgument")
    protected static class GraphQLRequestArgumentDocument {

        @Mapping("name")
        private String name;
        @Mapping("arguments")
        private List<GraphQLRequestArgumentDocument> arguments = new CopyOnWriteArrayList<GraphQLRequestArgumentDocument>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<GraphQLRequestArgumentDocument> getArguments() {
            return arguments;
        }

        public void setArguments(List<GraphQLRequestArgumentDocument> arguments) {
            this.arguments = arguments;
        }
    }

    @Document(collection = "graphQLRequestField")
    public class GraphQLRequestFieldDocument {

        @Mapping("name")
        private String name;
        @Mapping("fields")
        private List<GraphQLRequestFieldDocument> fields = new CopyOnWriteArrayList<GraphQLRequestFieldDocument>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<GraphQLRequestFieldDocument> getFields() {
            return fields;
        }

        public void setFields(List<GraphQLRequestFieldDocument> fields) {
            this.fields = fields;
        }
    }


    @Document(collection = "graphQLArgument")
    protected static class GraphQLArgumentDocument implements Saveable<String> {

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
        @Mapping("defaultValue")
        private Object defaultValue;
        @Mapping("nullable")
        private Boolean nullable;
        @Mapping("listable")
        private Boolean listable;
        @Mapping("attributeType")
        private GraphQLAttributeType attributeType;

        @Override
        public String getId() {
            return id;
        }

        @Override
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

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
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
}
