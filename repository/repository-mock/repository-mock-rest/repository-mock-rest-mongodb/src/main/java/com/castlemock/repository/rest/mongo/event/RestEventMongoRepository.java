/*
 * Copyright 2015 Karl Dahlgren
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

package com.castlemock.repository.rest.mongo.event;

import com.castlemock.model.core.model.SearchQuery;
import com.castlemock.model.core.model.SearchResult;
import com.castlemock.model.core.model.http.domain.ContentEncoding;
import com.castlemock.model.core.model.http.domain.HttpMethod;
import com.castlemock.model.mock.rest.domain.RestEvent;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.mongodb.MongoRepository;
import com.castlemock.repository.core.mongodb.event.AbstractEventMongoRepository;
import com.castlemock.repository.rest.event.RestEventRepository;
import com.google.common.base.Preconditions;
import org.dozer.Mapping;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * The class is an implementation of the REST event repository and provides the functionality to interact with mongodb.
 * The repository is responsible for loading and saving rest events from mongodb.
 *
 * @author Mohammad Hewedy
 * @see RestEventMongoRepository
 * @see MongoRepository
 * @since 1.35
 */
@Repository
@Profile(Profiles.MONGODB)
public class RestEventMongoRepository extends AbstractEventMongoRepository<RestEventMongoRepository.RestEventDocument, RestEvent> implements RestEventRepository {

    /**
     * The method is responsible for controller that the type that is about the be saved to mongodb is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to mongodb, but also loaded from
     * mongodb upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param restEvent The instance of the type that will be checked and controlled before it is allowed to be saved on
     *                  mongodb.
     * @see #save
     */
    @Override
    protected void checkType(final RestEventDocument restEvent) {
        Preconditions.checkNotNull(restEvent, "Event cannot be null");
        Preconditions.checkNotNull(restEvent.getEndDate(), "Event end date cannot be null");
        Preconditions.checkNotNull(restEvent.getStartDate(), "Event start date cannot be null");
    }

    /**
     * The service finds the oldest event
     *
     * @return The oldest event
     */
    @Override
    public RestEvent getOldestEvent() {
        RestEventDocument oldestEvent =
                mongoOperations.findOne(getOldestStartDateQuery(), RestEventDocument.class);
        return oldestEvent == null ? null : mapper.map(oldestEvent, RestEvent.class);
    }

    /**
     * Find events by REST method ID
     *
     * @param restMethodId The id of the REST method
     * @return A list of {@link RestEvent} that matches the provided <code>restMethodId</code>
     */
    @Override
    public List<RestEvent> findEventsByMethodId(final String restMethodId) {
        Query operationIdQuery = query(where("methodId").is(restMethodId));
        final List<RestEventDocument> events =
                mongoOperations.find(operationIdQuery, RestEventDocument.class);
        return toDtoList(events, RestEvent.class);
    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<RestEvent> search(SearchQuery query) {
        throw new UnsupportedOperationException();
    }

    /**
     * The method finds and deletes the oldest event.
     *
     * @return The event that was deleted.
     */
    @Override
    public RestEvent deleteOldestEvent() {
        RestEventDocument oldestEvent =
                mongoOperations.findAndRemove(getOldestStartDateQuery(), RestEventDocument.class);
        return oldestEvent == null ? null : mapper.map(oldestEvent, RestEvent.class);
    }

    /**
     * The method clears and deletes all logs.
     */
    @Override
    public void clearAll() {
        //drop vs remove => https://stackoverflow.com/q/12147783
        mongoOperations.dropCollection(RestEventDocument.class);
    }

    @Document(collection = "restEvent")
    protected static class RestEventDocument extends AbstractEventMongoRepository.EventDocument {

        @Mapping("request")
        private RestRequestDocument request;
        @Mapping("response")
        private RestResponseDocument response;
        @Mapping("projectId")
        private String projectId;
        @Mapping("applicationId")
        private String applicationId;
        @Mapping("resourceId")
        private String resourceId;
        @Mapping("methodId")
        private String methodId;

        public RestRequestDocument getRequest() {
            return request;
        }

        public void setRequest(RestRequestDocument request) {
            this.request = request;
        }

        public RestResponseDocument getResponse() {
            return response;
        }

        public void setResponse(RestResponseDocument response) {
            this.response = response;
        }

        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        public String getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(String applicationId) {
            this.applicationId = applicationId;
        }

        public String getResourceId() {
            return resourceId;
        }

        public void setResourceId(String resourceId) {
            this.resourceId = resourceId;
        }

        public String getMethodId() {
            return methodId;
        }

        public void setMethodId(String methodId) {
            this.methodId = methodId;
        }
    }

    @Document(collection = "restRequest")
    protected static class RestRequestDocument {

        @Mapping("body")
        private String body;
        @Mapping("contentType")
        private String contentType;
        @Mapping("uri")
        private String uri;
        @Mapping("httpMethod")
        private HttpMethod httpMethod;
        @Mapping("httpParameters")
        private List<MongoRepository.HttpParameterDocument> httpParameters;
        @Mapping("httpHeaders")
        private List<MongoRepository.HttpHeaderDocument> httpHeaders;

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public HttpMethod getHttpMethod() {
            return httpMethod;
        }

        public void setHttpMethod(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
        }

        public List<MongoRepository.HttpHeaderDocument> getHttpHeaders() {
            return httpHeaders;
        }

        public void setHttpHeaders(List<MongoRepository.HttpHeaderDocument> httpHeaders) {
            this.httpHeaders = httpHeaders;
        }

        public List<MongoRepository.HttpParameterDocument> getHttpParameters() {
            return httpParameters;
        }

        public void setHttpParameters(List<MongoRepository.HttpParameterDocument> httpParameters) {
            this.httpParameters = httpParameters;
        }
    }

    @Document(collection = "restResponse")
    protected static class RestResponseDocument {

        @Mapping("body")
        private String body;
        @Mapping("mockResponseName")
        private String mockResponseName;
        @Mapping("httpStatusCode")
        private Integer httpStatusCode;
        @Mapping("contentType")
        private String contentType;
        @Mapping("httpHeaders")
        private List<MongoRepository.HttpHeaderDocument> httpHeaders;
        @Mapping("contentEncodings")
        private List<ContentEncoding> contentEncodings;

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getMockResponseName() {
            return mockResponseName;
        }

        public void setMockResponseName(String mockResponseName) {
            this.mockResponseName = mockResponseName;
        }

        public Integer getHttpStatusCode() {
            return httpStatusCode;
        }

        public void setHttpStatusCode(Integer httpStatusCode) {
            this.httpStatusCode = httpStatusCode;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public List<MongoRepository.HttpHeaderDocument> getHttpHeaders() {
            return httpHeaders;
        }

        public void setHttpHeaders(List<MongoRepository.HttpHeaderDocument> httpHeaders) {
            this.httpHeaders = httpHeaders;
        }

        public List<ContentEncoding> getContentEncodings() {
            return contentEncodings;
        }

        public void setContentEncodings(List<ContentEncoding> contentEncodings) {
            this.contentEncodings = contentEncodings;
        }
    }

    private Query getOldestStartDateQuery() {
        return new Query().with(Sort.by("startDate")).limit(1);
    }
}
