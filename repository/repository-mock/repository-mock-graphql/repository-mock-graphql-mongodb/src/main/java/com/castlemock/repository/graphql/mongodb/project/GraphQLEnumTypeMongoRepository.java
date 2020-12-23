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
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLEnumType;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.graphql.project.GraphQLEnumTypeRepository;
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
public class GraphQLEnumTypeMongoRepository extends AbstractGraphQLTypeMongoRepository<GraphQLEnumTypeMongoRepository.GraphQLEnumTypeDocument, GraphQLEnumType> implements GraphQLEnumTypeRepository {

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
    protected void checkType(GraphQLEnumTypeDocument type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<GraphQLEnumType> search(SearchQuery query) {
        Query nameQuery = getSearchQuery("name", query);
        List<GraphQLEnumTypeDocument> resources =
                mongoOperations.find(nameQuery, GraphQLEnumTypeDocument.class);
        return toDtoList(resources, GraphQLEnumType.class);
    }

    @Override
    public List<GraphQLEnumType> findWithApplicationId(final String applicationId) {
        List<GraphQLEnumTypeDocument> resources =
                mongoOperations.find(getApplicationIdQuery(applicationId), GraphQLEnumTypeDocument.class);
        return toDtoList(resources, GraphQLEnumType.class);
    }

    @Override
    public void deleteWithApplicationId(final String applicationId) {
        mongoOperations.remove(getApplicationIdQuery(applicationId), GraphQLEnumTypeDocument.class);
    }

    @Document(collection = "graphQLEnumType")
    protected static class GraphQLEnumTypeDocument extends AbstractGraphQLTypeMongoRepository.GraphQLTypeDocument {

        @Mapping("definitions")
        private List<GraphQLEnumValueDefinitionDocument> definitions
                = new CopyOnWriteArrayList<GraphQLEnumValueDefinitionDocument>();

        public List<GraphQLEnumValueDefinitionDocument> getDefinitions() {
            return definitions;
        }

        public void setDefinitions(List<GraphQLEnumValueDefinitionDocument> definitions) {
            this.definitions = definitions;
        }
    }

    @Document(collection = "graphQLEnumValueDefinition")
    protected static class GraphQLEnumValueDefinitionDocument implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("name")
        private String name;

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
    }

    private Query getApplicationIdQuery(String applicationId) {
        return query(getApplicationIdCriteria(applicationId));
    }

    private Criteria getApplicationIdCriteria(String applicationId) {
        return where("applicationId").is(applicationId);
    }
}
