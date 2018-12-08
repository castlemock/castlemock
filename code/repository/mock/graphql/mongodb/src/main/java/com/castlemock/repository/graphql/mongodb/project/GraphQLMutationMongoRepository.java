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

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLMutation;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.graphql.project.GraphQLMutationRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author Mohammad Hewedy
 * @since 1.35
 */
@Repository
@Profile(Profiles.MONGODB)
public class GraphQLMutationMongoRepository extends AbstractGraphQLOperationMongoRepository<GraphQLMutationMongoRepository.GraphQLMutationDocument, GraphQLMutation> implements GraphQLMutationRepository {

    /**
     * The method is responsible for controller that the type that is about the be saved to the mongodb is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to mongodb, but also loaded from the
     * mongodb upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param type The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             mongodb.
     * @see #save
     */
    @Override
    protected void checkType(GraphQLMutationDocument type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<GraphQLMutation> search(SearchQuery query) {
        Query nameQuery = getSearchQuery("name", query);
        List<GraphQLMutationDocument> resources =
                mongoOperations.find(nameQuery, GraphQLMutationDocument.class);
        return toDtoList(resources, GraphQLMutation.class);
    }

    @Override
    public List<GraphQLMutation> findWithApplicationId(final String applicationId) {
        List<GraphQLMutationDocument> resources =
                mongoOperations.find(getApplicationIdQuery(applicationId), GraphQLMutationDocument.class);
        return toDtoList(resources, GraphQLMutation.class);
    }

    @Override
    public void deleteWithApplicationId(final String applicationId) {
        mongoOperations.remove(getApplicationIdQuery(applicationId), GraphQLMutationDocument.class);
    }

    @Document(collection = "graphQLMutation")
    protected static class GraphQLMutationDocument extends AbstractGraphQLOperationMongoRepository.GraphQLOperationDocument {

    }

    private Query getApplicationIdQuery(String applicationId) {
        return query(getApplicationIdCriteria(applicationId));
    }

    private Criteria getApplicationIdCriteria(String applicationId) {
        return where("applicationId").is(applicationId);
    }
}
