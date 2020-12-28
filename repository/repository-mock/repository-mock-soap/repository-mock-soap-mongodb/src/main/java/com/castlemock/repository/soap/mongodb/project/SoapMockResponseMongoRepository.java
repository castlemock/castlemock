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


package com.castlemock.repository.soap.mongodb.project;

import com.castlemock.model.core.model.Saveable;
import com.castlemock.model.core.model.SearchQuery;
import com.castlemock.model.core.model.SearchResult;
import com.castlemock.model.core.model.http.domain.ContentEncoding;
import com.castlemock.model.core.model.http.domain.HttpHeader;
import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.model.mock.soap.domain.SoapMockResponseStatus;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.mongodb.MongoRepository;
import com.castlemock.repository.soap.project.SoapMockResponseRepository;
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
public class SoapMockResponseMongoRepository extends MongoRepository<SoapMockResponseMongoRepository.SoapMockResponseDocument, SoapMockResponse, String> implements SoapMockResponseRepository {

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
    protected void checkType(SoapMockResponseDocument type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SoapMockResponse> search(SearchQuery query) {
        Query nameQuery = getSearchQuery("name", query);
        List<SoapMockResponseDocument> responses =
                mongoOperations.find(nameQuery, SoapMockResponseDocument.class);
        return toDtoList(responses, SoapMockResponse.class);
    }

    @Override
    public void deleteWithOperationId(String operationId) {
        mongoOperations.remove(getOperationIdQuery(operationId), SoapMockResponseDocument.class);
    }

    @Override
    public List<SoapMockResponse> findWithOperationId(String operationId) {
        List<SoapMockResponseDocument> responses =
                mongoOperations.find(getOperationIdQuery(operationId), SoapMockResponseDocument.class);
        return toDtoList(responses, SoapMockResponse.class);
    }

    /**
     * Retrieve the {@link SoapOperation} id
     * for the {@link SoapMockResponse} with the provided id.
     *
     * @param mockResponseId The id of the {@link SoapMockResponse}.
     * @return The id of the operation.
     */
    @Override
    public String getOperationId(String mockResponseId) {
        SoapMockResponseDocument mockResponse =
                mongoOperations.findById(mockResponseId, SoapMockResponseDocument.class);
        if (mockResponse == null) {
            throw new IllegalArgumentException("Unable to find a mock response with the following id: " + mockResponseId);
        }
        return mockResponse.getOperationId();
    }

    @Document(collection = "soapMockResponse")
    protected static class SoapMockResponseDocument implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("name")
        private String name;
        @Mapping("body")
        private String body;
        @Mapping("operationId")
        private String operationId;
        @Mapping("status")
        private SoapMockResponseStatus status;
        @Mapping("httpStatusCode")
        private Integer httpStatusCode;
        @Mapping("usingExpressions")
        private boolean usingExpressions;
        @Mapping("xpathExpression")
        @Deprecated
        private String xpathExpression;
        @Mapping("httpHeaders")
        private List<HttpHeader> httpHeaders = new CopyOnWriteArrayList<HttpHeader>();
        @Mapping("contentEncodings")
        private List<ContentEncoding> contentEncodings = new CopyOnWriteArrayList<ContentEncoding>();
        @Mapping("xpathExpressions")
        private List<SoapXPathExpressionDocument> xpathExpressions = new CopyOnWriteArrayList<SoapXPathExpressionDocument>();

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

        public String getOperationId() {
            return operationId;
        }

        public void setOperationId(String operationId) {
            this.operationId = operationId;
        }

        public SoapMockResponseStatus getStatus() {
            return status;
        }

        public void setStatus(SoapMockResponseStatus status) {
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

        public String getXpathExpression() {
            return xpathExpression;
        }

        public void setXpathExpression(String xpathExpression) {
            this.xpathExpression = xpathExpression;
        }

        public List<ContentEncoding> getContentEncodings() {
            return contentEncodings;
        }

        public void setContentEncodings(List<ContentEncoding> contentEncodings) {
            this.contentEncodings = contentEncodings;
        }

        public List<SoapXPathExpressionDocument> getXpathExpressions() {
            return xpathExpressions;
        }

        public void setXpathExpressions(List<SoapXPathExpressionDocument> xpathExpressions) {
            this.xpathExpressions = xpathExpressions;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof SoapMockResponseDocument))
                return false;

            SoapMockResponseDocument that = (SoapMockResponseDocument) o;

            if (id != null ? !id.equals(that.id) : that.id != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            return id != null ? id.hashCode() : 0;
        }
    }

    @Document(collection = "soapXPathExpression")
    protected static class SoapXPathExpressionDocument {

        private String expression;

        public String getExpression() {
            return expression;
        }

        public void setExpression(String expression) {
            this.expression = expression;
        }
    }

    private Query getOperationIdQuery(String operationId) {
        return query(getOperationIdCriteria(operationId));
    }

    private Criteria getOperationIdCriteria(String operationId) {
        return where("operationId").is(operationId);
    }
}
