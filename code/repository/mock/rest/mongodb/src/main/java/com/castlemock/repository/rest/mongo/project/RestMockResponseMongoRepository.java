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
import com.castlemock.core.basis.model.http.domain.ContentEncoding;
import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponse;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponseStatus;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.mongodb.MongoRepository;
import com.castlemock.repository.rest.project.RestMockResponseRepository;
import org.dozer.Mapping;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collections;
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
public class RestMockResponseMongoRepository extends MongoRepository<RestMockResponseMongoRepository.RestMockResponseDocument, RestMockResponse, String> implements RestMockResponseRepository {

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
    protected void checkType(RestMockResponseDocument type) {

    }

    /**
     * The post initialize method can be used to run functionality for a specific service. The method is called when
     * the method {@link #initialize} has finished successful.
     * <p>
     * The method is responsible to validate the imported types and make certain that all the collections are
     * initialized.
     *
     * @see #initialize
     */
    @Override
    protected void postInitiate() {
        final String parameterQueries = "parameterQueries";
        mongoOperations.updateMulti(query(where(parameterQueries).is(null)),
                BasicUpdate.update(parameterQueries, Collections.emptyList()),
                RestMockResponseDocument.class);
    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<RestMockResponse> search(SearchQuery query) {
        Query nameQuery = getSearchQuery("name", query);
        List<RestMockResponseDocument> responses =
                mongoOperations.find(nameQuery, RestMockResponseDocument.class);
        return toDtoList(responses, RestMockResponse.class);
    }

    /**
     * Delete all {@link RestMockResponseDocument} that matches the provided
     * <code>methodId</code>.
     *
     * @param methodId The id of the method.
     */
    @Override
    public void deleteWithMethodId(String methodId) {
        mongoOperations.remove(getMethodIdQuery(methodId), RestMockResponseDocument.class);
    }

    /**
     * Find all {@link RestMockResponse} that matches the provided
     * <code>methodId</code>.
     *
     * @param methodId The id of the method.
     * @return A list of {@link RestMockResponse}.
     */
    @Override
    public List<RestMockResponse> findWithMethodId(String methodId) {
        List<RestMockResponseDocument> responses =
                mongoOperations.find(getMethodIdQuery(methodId), RestMockResponseDocument.class);
        return toDtoList(responses, RestMockResponse.class);
    }

    /**
     * Retrieve the {@link RestMethod} id
     * for the {@link RestMockResponse} with the provided id.
     *
     * @param mockResponseId The id of the {@link RestMockResponse}.
     * @return The id of the method.
     */
    @Override
    public String getMethodId(String mockResponseId) {
        RestMockResponseDocument mockResponse =
                mongoOperations.findById(mockResponseId, RestMockResponseDocument.class);
        if (mockResponse == null) {
            throw new IllegalArgumentException("Unable to find a mock response with the following id: " + mockResponseId);
        }
        return mockResponse.getMethodId();
    }


    @Document(collection = "restMockResponse")
    protected static class RestMockResponseDocument implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("name")
        private String name;
        @Mapping("body")
        private String body;
        @Mapping("methodId")
        private String methodId;
        @Mapping("status")
        private RestMockResponseStatus status;
        @Mapping("httpStatusCode")
        private Integer httpStatusCode;
        @Mapping("usingExpressions")
        private boolean usingExpressions;
        @Mapping("httpHeaders")
        private List<HttpHeader> httpHeaders = new CopyOnWriteArrayList<HttpHeader>();
        @Mapping("contentEncodings")
        private List<ContentEncoding> contentEncodings = new CopyOnWriteArrayList<ContentEncoding>();
        @Mapping("parameterQueries")
        private List<RestParameterQueryDocument> parameterQueries = new CopyOnWriteArrayList<RestParameterQueryDocument>();

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

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getMethodId() {
            return methodId;
        }

        public void setMethodId(String methodId) {
            this.methodId = methodId;
        }

        public RestMockResponseStatus getStatus() {
            return status;
        }

        public void setStatus(RestMockResponseStatus status) {
            this.status = status;
        }

        public Integer getHttpStatusCode() {
            return httpStatusCode;
        }

        public void setHttpStatusCode(Integer httpStatusCode) {
            this.httpStatusCode = httpStatusCode;
        }

        public boolean isUsingExpressions() {
            return usingExpressions;
        }

        public void setUsingExpressions(boolean usingExpressions) {
            this.usingExpressions = usingExpressions;
        }

        public List<HttpHeader> getHttpHeaders() {
            return httpHeaders;
        }

        public void setHttpHeaders(List<HttpHeader> httpHeaders) {
            this.httpHeaders = httpHeaders;
        }

        public List<ContentEncoding> getContentEncodings() {
            return contentEncodings;
        }

        public void setContentEncodings(List<ContentEncoding> contentEncodings) {
            this.contentEncodings = contentEncodings;
        }

        public List<RestParameterQueryDocument> getParameterQueries() {
            return parameterQueries;
        }

        public void setParameterQueries(List<RestParameterQueryDocument> parameterQueries) {
            this.parameterQueries = parameterQueries;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof RestMockResponseDocument))
                return false;

            RestMockResponseDocument that = (RestMockResponseDocument) o;

            if (id != null ? !id.equals(that.id) : that.id != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            return id != null ? id.hashCode() : 0;
        }
    }

    @Document(collection = "restParameterQuery")
    protected static class RestParameterQueryDocument {

        private String parameter;
        private String query;
        private boolean matchCase;
        private boolean matchAny;
        private boolean matchRegex;

        public String getParameter() {
            return parameter;
        }

        public void setParameter(String parameter) {
            this.parameter = parameter;
        }

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public boolean getMatchCase() {
            return matchCase;
        }

        public void setMatchCase(boolean matchCase) {
            this.matchCase = matchCase;
        }

        public boolean getMatchAny() {
            return matchAny;
        }

        public void setMatchAny(boolean matchAny) {
            this.matchAny = matchAny;
        }

        public boolean getMatchRegex() {
            return matchRegex;
        }

        public void setMatchRegex(boolean matchRegex) {
            this.matchRegex = matchRegex;
        }
    }

    private Query getMethodIdQuery(String methodId) {
        return query(getMethodIdCriteria(methodId));
    }

    private Criteria getMethodIdCriteria(String methodId) {
        return where("methodId").is(methodId);
    }
}
