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
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLApplication;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLProject;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.mongodb.MongoRepository;
import com.castlemock.repository.graphql.project.GraphQLApplicationRepository;
import org.dozer.Mapping;
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
public class GraphQLApplicationMongoRepository extends MongoRepository<GraphQLApplicationMongoRepository.GraphQLApplicationDocument, GraphQLApplication, String> implements GraphQLApplicationRepository {

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
    protected void checkType(GraphQLApplicationDocument type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<GraphQLApplication> search(SearchQuery query) {
        Query nameQuery = getSearchQuery("name", query);
        List<GraphQLApplicationDocument> operations =
                mongoOperations.find(nameQuery, GraphQLApplicationDocument.class);
        return toDtoList(operations, GraphQLApplication.class);
    }

    @Override
    public List<GraphQLApplication> findWithProjectId(final String projectId) {
        List<GraphQLApplicationDocument> responses =
                mongoOperations.find(getProjectIdQuery(projectId), GraphQLApplicationDocument.class);
        return toDtoList(responses, GraphQLApplication.class);
    }

    @Override
    public void deleteWithProjectId(final String projectId) {
        mongoOperations.remove(getProjectIdQuery(projectId), GraphQLApplicationDocument.class);
    }

    /**
     * Retrieve the {@link GraphQLProject} id
     * for the {@link GraphQLApplication} with the provided id.
     *
     * @param applicationId The id of the {@link GraphQLApplication}.
     * @return The id of the project.
     */
    @Override
    public String getProjectId(String applicationId) {
        GraphQLApplication applicationDocument = mongoOperations.findById(applicationId, GraphQLApplication.class);

        if (applicationDocument == null) {
            throw new IllegalArgumentException("Unable to find an application with the following id: " + applicationId);
        }
        return applicationDocument.getProjectId();
    }


    @Document(collection = "graphQLApplication")
    protected static class GraphQLApplicationDocument implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("name")
        private String name;
        @Mapping("description")
        private String description;
        @Mapping("projectId")
        private String projectId;

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

        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }
    }

    private Query getProjectIdQuery(String projectId) {
        return query(getProjectIdCriteria(projectId));
    }

    private Criteria getProjectIdCriteria(String projectId) {
        return where("projectId").is(projectId);
    }
}
