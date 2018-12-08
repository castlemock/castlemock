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

import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.mock.rest.model.project.domain.RestApplication;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.core.mock.rest.model.project.domain.RestResource;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.mongodb.MongoRepository;
import com.castlemock.repository.rest.project.RestResourceRepository;
import org.dozer.Mapping;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author Mohammad Hewedy
 * @since 1.35
 */
@Repository
@Profile(Profiles.MONGODB)
public class RestResourceMongoRepository extends MongoRepository<RestResourceMongoRepository.RestResourceDocument, RestResource, String> implements RestResourceRepository {

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
    protected void checkType(RestResourceDocument type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<RestResource> search(SearchQuery query) {
        Query nameQuery = getSearchQuery("name", query);
        List<RestResourceDocument> resources =
                mongoOperations.find(nameQuery, RestResourceDocument.class);
        return toDtoList(resources, RestResource.class);
    }

    /**
     * Delete all {@link RestResource} that matches the provided
     * <code>applicationId</code>.
     *
     * @param applicationId The id of the applicationId.
     */
    @Override
    public void deleteWithApplicationId(String applicationId) {
        mongoOperations.remove(getApplicationIdQuery(applicationId), RestResourceDocument.class);
    }

    /**
     * Find all {@link RestResource} that matches the provided
     * <code>applicationId</code>.
     *
     * @param applicationId The id of the applicationId.
     * @return A list of {@link RestResource}.
     */
    @Override
    public List<RestResource> findWithApplicationId(String applicationId) {
        List<RestResourceDocument> resources =
                mongoOperations.find(getApplicationIdQuery(applicationId), RestResourceDocument.class);
        return toDtoList(resources, RestResource.class);
    }

    /**
     * Find all {@link RestResource} ids that matches the provided
     * <code>applicationId</code>.
     *
     * @param applicationId The id of the applicationId.
     * @return A list of {@link RestResource} ids.
     * @since 1.20
     */
    @Override
    public List<String> findIdsWithApplicationId(String applicationId) {
        return findWithApplicationId(applicationId)
                .stream()
                .map(RestResource::getId)
                .collect(Collectors.toList());
    }

    /**
     * Finds a {@link RestResource} with a URI
     *
     * @param applicationId The id of the {@link RestApplication}
     * @param resourceUri   The URI of a {@link RestResource}
     * @return A {@link RestResource} that matches the search criteria.
     * @see RestProject
     * @see RestApplication
     * @see RestResource
     */
    @Override
    public RestResource findRestResourceByUri(String applicationId, String resourceUri) {
        Query applicationIdAndUriIgnoreCaseQuery =
                query(where("applicationId").is(applicationId)
                        .and("uri").regex("^" + resourceUri + "$", "i"));
        RestResourceDocument resourceDocument =
                mongoOperations.findOne(applicationIdAndUriIgnoreCaseQuery, RestResourceDocument.class);

        return resourceDocument == null ? null : this.mapper.map(resourceDocument, RestResource.class);
    }

    /**
     * Retrieve the {@link RestApplication} id
     * for the {@link RestResource} with the provided id.
     *
     * @param resourceId The id of the {@link RestResource}.
     * @return The id of the application.
     * @since 1.20
     */
    @Override
    public String getApplicationId(String resourceId) {
        final RestResourceDocument resource =
                mongoOperations.findById(resourceId, RestResourceDocument.class);

        if (resource == null) {
            throw new IllegalArgumentException("Unable to find a resource with the following id: " + resourceId);
        }
        return resource.getApplicationId();
    }

    @Document(collection = "restResource")
    protected static class RestResourceDocument implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("name")
        private String name;
        @Mapping("uri")
        private String uri;
        @Mapping("applicationId")
        private String applicationId;

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

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(String applicationId) {
            this.applicationId = applicationId;
        }
    }

    private Query getApplicationIdQuery(String applicationId) {
        return query(getApplicationIdCriteria(applicationId));
    }

    private Criteria getApplicationIdCriteria(String applicationId) {
        return where("applicationId").is(applicationId);
    }
}
