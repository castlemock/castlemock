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

package com.castlemock.repository.rest.mongo.project;

import com.castlemock.model.core.model.Saveable;
import com.castlemock.model.core.model.SearchQuery;
import com.castlemock.model.core.model.SearchResult;
import com.castlemock.model.mock.rest.domain.RestApplication;
import com.castlemock.model.mock.rest.domain.RestProject;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.mongodb.MongoRepository;
import com.castlemock.repository.rest.project.RestApplicationRepository;
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
public class RestApplicationMongoRepository extends MongoRepository<RestApplicationMongoRepository.RestApplicationDocument, RestApplication, String> implements RestApplicationRepository {

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
    protected void checkType(RestApplicationDocument type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<RestApplication> search(SearchQuery query) {
        Query nameQuery = getSearchQuery("name", query);
        List<RestApplicationDocument> operations =
                mongoOperations.find(nameQuery, RestApplicationDocument.class);
        return toDtoList(operations, RestApplication.class);
    }

    /**
     * Delete all {@link RestApplication} that matches the provided
     * <code>projectId</code>.
     *
     * @param projectId The id of the project.
     */
    @Override
    public void deleteWithProjectId(String projectId) {
        mongoOperations.remove(getProjectIdQuery(projectId), RestApplicationDocument.class);
    }

    /**
     * Find all {@link RestApplication} that matches the provided
     * <code>projectId</code>.
     *
     * @param projectId The id of the project.
     * @return A list of {@link RestApplication}.
     */
    @Override
    public List<RestApplication> findWithProjectId(String projectId) {
        List<RestApplicationDocument> responses =
                mongoOperations.find(getProjectIdQuery(projectId), RestApplicationDocument.class);
        return toDtoList(responses, RestApplication.class);
    }

    /**
     * Retrieve the {@link RestProject} id
     * for the {@link RestApplication} with the provided id.
     *
     * @param applicationId The id of the {@link RestApplication}.
     * @return The id of the project.
     */
    @Override
    public String getProjectId(String applicationId) {
        RestApplicationDocument applicationDocument = mongoOperations.findById(applicationId, RestApplicationDocument.class);

        if (applicationDocument == null) {
            throw new IllegalArgumentException("Unable to find an application with the following id: " + applicationId);
        }
        return applicationDocument.getProjectId();
    }

    @Document(collection = "restApplication")
    protected static class RestApplicationDocument implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("name")
        private String name;
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
