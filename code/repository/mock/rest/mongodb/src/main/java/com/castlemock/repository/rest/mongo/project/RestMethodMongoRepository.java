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
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestMethodStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestResource;
import com.castlemock.core.mock.rest.model.project.domain.RestResponseStrategy;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.mongodb.MongoRepository;
import com.castlemock.repository.rest.project.RestMethodRepository;
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
public class RestMethodMongoRepository extends MongoRepository<RestMethodMongoRepository.RestMethodDocument, RestMethod, String> implements RestMethodRepository {

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
    protected void checkType(RestMethodDocument type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<RestMethod> search(SearchQuery query) {
        Query nameQuery = getSearchQuery("name", query);
        List<RestMethodDocument> operations =
                mongoOperations.find(nameQuery, RestMethodDocument.class);
        return toDtoList(operations, RestMethod.class);
    }


    /**
     * Updates the current response sequence index.
     *
     * @param restMethodId The method id.
     * @param index        The new response sequence index.
     * @since 1.17
     */
    @Override
    public void setCurrentResponseSequenceIndex(final String restMethodId,
                                                final Integer index) {
        RestMethodDocument restMethod = mongoOperations.findById(restMethodId, RestMethodDocument.class);

        if (restMethod == null) {
            throw new IllegalArgumentException("Unable to find a method with the following id: " + restMethodId);
        }
        restMethod.setCurrentResponseSequenceIndex(index);
    }

    /**
     * Delete all {@link RestMethod} that matches the provided
     * <code>resourceId</code>.
     *
     * @param resourceId The id of the resource.
     */
    @Override
    public void deleteWithResourceId(String resourceId) {
        mongoOperations.remove(getResourceIdQuery(resourceId), RestMethodDocument.class);
    }

    /**
     * Find all {@link RestMethod} that matches the provided
     * <code>resourceId</code>.
     *
     * @param resourceId The id of the resource.
     * @return A list of {@link RestMethod}.
     * @since 1.20
     */
    @Override
    public List<RestMethod> findWithResourceId(String resourceId) {
        List<RestMethodDocument> responses =
                mongoOperations.find(getResourceIdQuery(resourceId), RestMethodDocument.class);
        return toDtoList(responses, RestMethod.class);
    }

    /**
     * Find all {@link RestMethod} ids that matches the provided
     * <code>resourceId</code>.
     *
     * @param resourceId The id of the resource.
     * @return A list of {@link RestMethod} ids.
     * @since 1.20
     */
    @Override
    public List<String> findIdsWithResourceId(String resourceId) {
        return findWithResourceId(resourceId)
                .stream()
                .map(RestMethod::getId)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve the {@link RestResource} id
     * for the {@link RestMethod} with the provided id.
     *
     * @param methodId The id of the {@link RestMethod}.
     * @return The id of the resource.
     * @since 1.20
     */
    @Override
    public String getResourceId(String methodId) {
        RestMethodDocument methodDocument = mongoOperations.findById(methodId, RestMethodDocument.class);

        if (methodDocument == null) {
            throw new IllegalArgumentException("Unable to find a method with the following id: " + methodId);
        }
        return methodDocument.getResourceId();
    }

    @Document(collection = "restMethod")
    protected static class RestMethodDocument implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("name")
        private String name;
        @Mapping("resourceId")
        private String resourceId;
        @Mapping("defaultBody")
        private String defaultBody;
        @Mapping("httpMethod")
        private HttpMethod httpMethod;
        @Mapping("forwardedEndpoint")
        private String forwardedEndpoint;
        @Mapping("status")
        private RestMethodStatus status;
        @Mapping("responseStrategy")
        private RestResponseStrategy responseStrategy;
        @Mapping("currentResponseSequenceIndex")
        private Integer currentResponseSequenceIndex;
        @Mapping("simulateNetworkDelay")
        private boolean simulateNetworkDelay;
        @Mapping("networkDelay")
        private long networkDelay;
        @Mapping("defaultQueryMockResponseId")
        private String defaultQueryMockResponseId;

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

        public String getResourceId() {
            return resourceId;
        }

        public void setResourceId(String resourceId) {
            this.resourceId = resourceId;
        }

        public String getDefaultBody() {
            return defaultBody;
        }

        public void setDefaultBody(String defaultBody) {
            this.defaultBody = defaultBody;
        }

        public HttpMethod getHttpMethod() {
            return httpMethod;
        }

        public void setHttpMethod(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
        }

        public String getForwardedEndpoint() {
            return forwardedEndpoint;
        }

        public void setForwardedEndpoint(String forwardedEndpoint) {
            this.forwardedEndpoint = forwardedEndpoint;
        }

        public RestMethodStatus getStatus() {
            return status;
        }

        public void setStatus(RestMethodStatus status) {
            this.status = status;
        }

        public RestResponseStrategy getResponseStrategy() {
            return responseStrategy;
        }

        public void setResponseStrategy(RestResponseStrategy responseStrategy) {
            this.responseStrategy = responseStrategy;
        }

        public Integer getCurrentResponseSequenceIndex() {
            return currentResponseSequenceIndex;
        }

        public void setCurrentResponseSequenceIndex(Integer currentResponseSequenceIndex) {
            this.currentResponseSequenceIndex = currentResponseSequenceIndex;
        }

        public boolean getSimulateNetworkDelay() {
            return simulateNetworkDelay;
        }

        public void setSimulateNetworkDelay(boolean simulateNetworkDelay) {
            this.simulateNetworkDelay = simulateNetworkDelay;
        }

        public long getNetworkDelay() {
            return networkDelay;
        }

        public void setNetworkDelay(long networkDelay) {
            this.networkDelay = networkDelay;
        }

        public String getDefaultQueryMockResponseId() {
            return defaultQueryMockResponseId;
        }

        public void setDefaultQueryMockResponseId(String defaultQueryMockResponseId) {
            this.defaultQueryMockResponseId = defaultQueryMockResponseId;
        }
    }

    private Query getResourceIdQuery(String resourceId) {
        return query(getResourceIdCriteria(resourceId));
    }

    private Criteria getResourceIdCriteria(String resourceId) {
        return where("resourceId").is(resourceId);
    }
}
