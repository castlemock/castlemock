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
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLArgument;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLAttribute;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLAttributeType;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.mongodb.MongoRepository;
import com.castlemock.repository.graphql.project.GraphQLAttributeRepository;
import org.dozer.Mapping;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
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
public class GraphQLAttributeMongoRepository extends MongoRepository<GraphQLAttributeMongoRepository.GraphQLAttributeDocument, GraphQLAttribute, String> implements GraphQLAttributeRepository {

    /**
     * The method is responsible for controller that the type that is about the be saved to mongodb is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved mongodb, but also loaded from the
     * mongodb upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param type The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             the file system.
     * @see #save
     */
    @Override
    protected void checkType(GraphQLAttributeDocument type) {

    }

    /**
     * Updates an instance that matches the provided id.
     *
     * @param id   The id of the instance that will be updated.
     * @param type The updated version that will replace the old one.
     * @return A copy of the replaced value.
     * @since 1.20
     */
    @Override
    public GraphQLAttribute update(final String id, final GraphQLAttribute type) {
        final GraphQLAttribute existing = this.findOne(id);
        existing.setValue(type.getValue());
        this.save(existing);
        return existing;
    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<GraphQLAttribute> search(SearchQuery query) {
        Query nameQuery = getSearchQuery("name", query);
        List<GraphQLAttributeDocument> operations =
                mongoOperations.find(nameQuery, GraphQLAttributeDocument.class);
        return toDtoList(operations, GraphQLAttribute.class);
    }

    @Override
    public List<GraphQLAttribute> findWithObjectTypeId(final String objectId) {
        List<GraphQLAttributeDocument> responses =
                mongoOperations.find(getObjectTypeIdQuery(objectId), GraphQLAttributeDocument.class);
        return toDtoList(responses, GraphQLAttribute.class);
    }

    @Override
    public void deleteWithObjectTypeId(final String objectId) {
        mongoOperations.remove(getObjectTypeIdQuery(objectId), GraphQLAttribute.class);
    }

    @Document(collection = "graphQLAttribute")
    protected static class GraphQLAttributeDocument implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("name")
        private String name;
        @Mapping("description")
        private String description;
        @Mapping("typeId")
        private String typeId;
        @Mapping("typeName")
        private String typeName;
        @Mapping("nullable")
        private Boolean nullable;
        @Mapping("listable")
        private Boolean listable;
        @Mapping("objectTypeId")
        private String objectTypeId;
        @Mapping("value")
        private String value;
        @Mapping("attributeType")
        private GraphQLAttributeType attributeType;
        @Mapping("arguments")
        private List<GraphQLArgument> arguments = new CopyOnWriteArrayList<GraphQLArgument>();

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

        public List<GraphQLArgument> getArguments() {
            return arguments;
        }

        public void setArguments(List<GraphQLArgument> arguments) {
            this.arguments = arguments;
        }

        public String getObjectTypeId() {
            return objectTypeId;
        }

        public void setObjectTypeId(String objectTypeId) {
            this.objectTypeId = objectTypeId;
        }
    }

    private Query getObjectTypeIdQuery(String objectTypeId) {
        return query(getObjectTypeIdCriteria(objectTypeId));
    }

    private Criteria getObjectTypeIdCriteria(String objectTypeId) {
        return where("objectTypeId").is(objectTypeId);
    }
}
