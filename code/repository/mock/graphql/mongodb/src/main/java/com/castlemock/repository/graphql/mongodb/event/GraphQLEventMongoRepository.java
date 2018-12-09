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

package com.castlemock.repository.graphql.mongodb.event;

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.http.domain.ContentEncoding;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.graphql.model.event.domain.GraphQLEvent;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLRequestQuery;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.mongodb.MongoRepository;
import com.castlemock.repository.core.mongodb.event.AbstractEventMongoRepository;
import com.castlemock.repository.graphql.event.GraphQLEventRepository;
import com.google.common.base.Preconditions;
import org.dozer.Mapping;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author Mohammad Hewedy
 * @since 1.35
 */
@Repository
@Profile(Profiles.MONGODB)
public class GraphQLEventMongoRepository extends AbstractEventMongoRepository<GraphQLEventMongoRepository.GraphQLEventDocument, GraphQLEvent> implements GraphQLEventRepository {

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
    protected void checkType(GraphQLEventDocument type) {
        Preconditions.checkNotNull(type, "Event cannot be null");
        Preconditions.checkNotNull(type.getEndDate(), "Event end date cannot be null");
        Preconditions.checkNotNull(type.getStartDate(), "Event start date cannot be null");
    }

    /**
     * The service finds the oldest event
     *
     * @return The oldest event
     */
    @Override
    public GraphQLEvent getOldestEvent() {
        GraphQLEventDocument oldestEvent =
                mongoOperations.findOne(getOldestStartDateQuery(), GraphQLEventDocument.class);
        return oldestEvent == null ? null : mapper.map(oldestEvent, GraphQLEvent.class);
    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link GraphQLEvent} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<GraphQLEvent> search(SearchQuery query) {
        throw new UnsupportedOperationException();
    }

    /**
     * The method finds and deletes the oldest event.
     *
     * @return The event that was deleted.
     */
    @Override
    public synchronized GraphQLEvent deleteOldestEvent() {
        GraphQLEventDocument oldestEvent =
                mongoOperations.findAndRemove(getOldestStartDateQuery(), GraphQLEventDocument.class);
        return oldestEvent == null ? null : mapper.map(oldestEvent, GraphQLEvent.class);
    }

    /**
     * The method clears and deletes all logs.
     *
     * @since 1.7
     */
    @Override
    public void clearAll() {
        //drop vs remove => https://stackoverflow.com/q/12147783
        mongoOperations.dropCollection(GraphQLEventDocument.class);
    }

    @Document(collection = "graphQLEvent")
    protected static class GraphQLEventDocument extends AbstractEventMongoRepository.EventDocument {

        @Mapping("request")
        private GraphQLRequestDocument request;
        @Mapping("response")
        private GraphQLResponseDocument response;
        @Mapping("projectId")
        private String projectId;
        @Mapping("applicationId")
        private String applicationId;

        public GraphQLRequestDocument getRequest() {
            return request;
        }

        public void setRequest(GraphQLRequestDocument request) {
            this.request = request;
        }

        public GraphQLResponseDocument getResponse() {
            return response;
        }

        public void setResponse(GraphQLResponseDocument response) {
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

    }

    @Document(collection = "graphQLRequest")
    protected static class GraphQLRequestDocument {

        @Mapping("body")
        private String body;
        @Mapping("contentType")
        private String contentType;
        @Mapping("uri")
        private String uri;
        @Mapping("httpMethod")
        private HttpMethod httpMethod;
        @Mapping("queries")
        private List<GraphQLRequestQuery> queries;
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

        public List<GraphQLRequestQuery> getQueries() {
            return queries;
        }

        public void setQueries(List<GraphQLRequestQuery> queries) {
            this.queries = queries;
        }

        public List<MongoRepository.HttpHeaderDocument> getHttpHeaders() {
            return httpHeaders;
        }

        public void setHttpHeaders(List<MongoRepository.HttpHeaderDocument> httpHeaders) {
            this.httpHeaders = httpHeaders;
        }

    }

    @Document(collection = "graphQLResponse")
    protected static class GraphQLResponseDocument {

        @Mapping("body")
        private String body;
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
