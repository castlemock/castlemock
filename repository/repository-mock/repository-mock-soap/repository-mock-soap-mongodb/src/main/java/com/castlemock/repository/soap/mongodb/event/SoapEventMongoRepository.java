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

package com.castlemock.repository.soap.mongodb.event;

import com.castlemock.model.core.model.SearchQuery;
import com.castlemock.model.core.model.SearchResult;
import com.castlemock.model.core.model.http.domain.ContentEncoding;
import com.castlemock.model.core.model.http.domain.HttpMethod;
import com.castlemock.model.mock.soap.domain.SoapEvent;
import com.castlemock.model.mock.soap.domain.SoapVersion;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.mongodb.MongoRepository;
import com.castlemock.repository.core.mongodb.event.AbstractEventMongoRepository;
import com.castlemock.repository.soap.event.SoapEventRepository;
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
 * The class is an implementation of the mongo repository and provides the functionality to interact with mongodb.
 * The repository is responsible for loading and saving soap events from mongodb.
 *
 * @author Mohammad Hewedy
 * @see SoapEventMongoRepository
 * @see MongoRepository
 * @since 1.35
 */
@Repository
@Profile(Profiles.MONGODB)
public class SoapEventMongoRepository extends AbstractEventMongoRepository<SoapEventMongoRepository.SoapEventDocument, SoapEvent> implements SoapEventRepository {


    /**
     * The method is responsible for controller that the type that is about the be saved to mongodb is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to mongodb, but also loaded from
     * mongodb upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param soapEvent The instance of the type that will be checked and controlled before it is allowed to be saved on
     *                  mongodb.
     * @see #save
     */
    @Override
    protected void checkType(final SoapEventDocument soapEvent) {
        Preconditions.checkNotNull(soapEvent, "Event cannot be null");
        Preconditions.checkNotNull(soapEvent.getEndDate(), "Event end date cannot be null");
        Preconditions.checkNotNull(soapEvent.getStartDate(), "Event start date cannot be null");
    }

    /**
     * The events for a specific operation id
     *
     * @param operationId The id of the operation that the event belongs to
     * @return Returns a list of events
     */
    @Override
    public List<SoapEvent> findEventsByOperationId(String operationId) {
        Query operationIdQuery = query(where("operationId").is(operationId));
        final List<SoapEventDocument> events =
                mongoOperations.find(operationIdQuery, SoapEventDocument.class);
        return toDtoList(events, SoapEvent.class);
    }

    /**
     * The service finds the oldest event
     *
     * @return The oldest event
     */
    @Override
    public SoapEvent getOldestEvent() {
        SoapEventDocument oldestEvent =
                mongoOperations.findOne(getOldestStartDateQuery(), SoapEventDocument.class);
        return oldestEvent == null ? null : mapper.map(oldestEvent, SoapEvent.class);
    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SoapEvent> search(SearchQuery query) {
        throw new UnsupportedOperationException();
    }

    /**
     * The method finds and deletes the oldest event.
     *
     * @return The event that was deleted.
     */
    @Override
    public SoapEvent deleteOldestEvent() {
        SoapEventDocument oldestEvent =
                mongoOperations.findAndRemove(getOldestStartDateQuery(), SoapEventDocument.class);
        return oldestEvent == null ? null : mapper.map(oldestEvent, SoapEvent.class);
    }

    /**
     * The method clears and deletes all logs.
     */
    @Override
    public void clearAll() {
        //drop vs remove => https://stackoverflow.com/q/12147783
        mongoOperations.dropCollection(SoapEventDocument.class);
    }

    @Document(collection = "soapEvent")
    protected static class SoapEventDocument extends AbstractEventMongoRepository.EventDocument {

        @Mapping("request")
        private SoapRequestDocument request;
        @Mapping("response")
        private SoapResponseDocument response;
        @Mapping("projectId")
        private String projectId;
        @Mapping("portId")
        private String portId;
        @Mapping("operationId")
        private String operationId;

        public SoapRequestDocument getRequest() {
            return request;
        }

        public void setRequest(SoapRequestDocument request) {
            this.request = request;
        }

        public SoapResponseDocument getResponse() {
            return response;
        }

        public void setResponse(SoapResponseDocument response) {
            this.response = response;
        }

        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        public String getPortId() {
            return portId;
        }

        public void setPortId(String portId) {
            this.portId = portId;
        }

        public String getOperationId() {
            return operationId;
        }

        public void setOperationId(String operationId) {
            this.operationId = operationId;
        }
    }

    @Document(collection = "soapRequest")
    protected static class SoapRequestDocument {

        @Mapping("body")
        private String body;
        @Mapping("envelope")
        private String envelope;
        @Mapping("contentType")
        private String contentType;
        @Mapping("uri")
        private String uri;
        @Mapping("httpMethod")
        private HttpMethod httpMethod;
        @Mapping("operationName")
        private String operationName;
        @Mapping("operationIdentifier")
        private SoapOperationIdentifierDocument operationIdentifier;
        @Mapping("soapVersion")
        private SoapVersion soapVersion;
        @Mapping("httpHeaders")
        private List<HttpHeaderDocument> httpHeaders;

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getEnvelope() {
            return envelope;
        }

        public void setEnvelope(String envelope) {
            this.envelope = envelope;
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

        public String getOperationName() {
            return operationName;
        }

        public void setOperationName(String operationName) {
            this.operationName = operationName;
        }

        public SoapOperationIdentifierDocument getOperationIdentifier() {
            return operationIdentifier;
        }

        public void setOperationIdentifier(SoapOperationIdentifierDocument operationIdentifier) {
            this.operationIdentifier = operationIdentifier;
        }

        public SoapVersion getSoapVersion() {
            return soapVersion;
        }

        public void setSoapVersion(SoapVersion soapVersion) {
            this.soapVersion = soapVersion;
        }

        public List<HttpHeaderDocument> getHttpHeaders() {
            return httpHeaders;
        }

        public void setHttpHeaders(List<HttpHeaderDocument> httpHeaders) {
            this.httpHeaders = httpHeaders;
        }
    }

    @Document(collection = "soapResponse")
    protected static class SoapResponseDocument {

        @Mapping("body")
        private String body;
        @Mapping("mockResponseName")
        private String mockResponseName;
        @Mapping("httpStatusCode")
        private Integer httpStatusCode;
        @Mapping("contentType")
        private String contentType;
        @Mapping("httpHeaders")
        private List<HttpHeaderDocument> httpHeaders;
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

        public List<HttpHeaderDocument> getHttpHeaders() {
            return httpHeaders;
        }

        public void setHttpHeaders(List<HttpHeaderDocument> httpHeaders) {
            this.httpHeaders = httpHeaders;
        }

        public List<ContentEncoding> getContentEncodings() {
            return contentEncodings;
        }

        public void setContentEncodings(List<ContentEncoding> contentEncodings) {
            this.contentEncodings = contentEncodings;
        }
    }

    @Document(collection = "soapOperationIdentifier")
    protected static class SoapOperationIdentifierDocument {

        @Mapping("name")
        private String name;
        @Mapping("namespace")
        private String namespace;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNamespace() {
            return namespace;
        }

        public void setNamespace(String namespace) {
            this.namespace = namespace;
        }
    }

    private Query getOldestStartDateQuery() {
        return new Query().with(Sort.by("startDate")).limit(1);
    }
}
