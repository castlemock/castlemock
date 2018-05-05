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

package com.castlemock.web.mock.graphql.model.event.repository;

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.http.domain.ContentEncoding;
import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.graphql.model.event.domain.GraphQLEvent;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLRequestQuery;
import com.castlemock.web.basis.model.RepositoryImpl;
import com.castlemock.web.basis.model.event.repository.AbstractEventFileRepository;
import com.google.common.base.Preconditions;
import org.dozer.Mapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Iterator;
import java.util.List;

@Repository
public class GraphQLEventRepositoryImpl extends AbstractEventFileRepository<GraphQLEventRepositoryImpl.GraphQLEventFile, GraphQLEvent> implements GraphQLEventRepository {

    @Value(value = "${graphql.event.file.directory}")
    private String graphQLEventFileDirectory;
    @Value(value = "${graphql.event.file.extension}")
    private String graphQLEventFileExtension;

    /**
     * The method returns the directory for the specific file repository. The directory will be used to indicate
     * where files should be saved and loaded from. The method is abstract and every subclass is responsible for
     * overriding the method and provided the directory for their corresponding file type.
     *
     * @return The file directory where the files for the specific file repository could be saved and loaded from.
     */
    @Override
    protected String getFileDirectory() {
        return graphQLEventFileDirectory;
    }

    /**
     * The method returns the postfix for the file that the file repository is responsible for managing.
     * The method is abstract and every subclass is responsible for overriding the method and provided the postfix
     * for their corresponding file type.
     *
     * @return The file extension for the file type that the repository is responsible for managing .
     */
    @Override
    protected String getFileExtension() {
        return graphQLEventFileExtension;
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to the file system is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to the file system, but also loaded from the
     * file system upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param type The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             the file system.
     * @see #save
     */
    @Override
    protected void checkType(GraphQLEventFile type) {
        Preconditions.checkNotNull(type, "Event cannot be null");
        Preconditions.checkNotNull(type.getId(), "Event id cannot be null");
        Preconditions.checkNotNull(type.getEndDate(), "Event end date cannot be null");
        Preconditions.checkNotNull(type.getStartDate(), "Event start date cannot be null");
    }

    /**
     * The service finds the oldest event
     * @return The oldest event
     */
    @Override
    public GraphQLEvent getOldestEvent() {
        GraphQLEventFile oldestEvent = null;
        for(GraphQLEventFile event : collection.values()){
            if(oldestEvent == null){
                oldestEvent = event;
            } else if(event.getStartDate().before(oldestEvent.getStartDate())){
                oldestEvent = event;
            }
        }

        return oldestEvent == null ? null : mapper.map(oldestEvent, GraphQLEvent.class);
    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SearchResult> search(SearchQuery query) {
        throw new UnsupportedOperationException();
    }

    /**
     * The method finds and deletes the oldest event.
     * @return The event that was deleted.
     * @since 1.5
     */
    @Override
    public synchronized GraphQLEvent deleteOldestEvent(){
        GraphQLEvent event = getOldestEvent();
        delete(event.getId());
        return event;
    }

    /**
     * The method clears and deletes all logs.
     * @since 1.7
     */
    @Override
    public void clearAll() {
        Iterator<GraphQLEventFile> iterator = collection.values().iterator();
        while(iterator.hasNext()){
            GraphQLEventFile soapEvent = iterator.next();
            delete(soapEvent.getId());
        }
    }

    @XmlRootElement(name = "graphQLEvent")
    protected static class GraphQLEventFile extends AbstractEventFileRepository.EventFile {

        @Mapping("request")
        private GraphQLRequestFile request;
        @Mapping("response")
        private GraphQLResponseFile response;
        @Mapping("projectId")
        private String projectId;
        @Mapping("applicationId")
        private String applicationId;

        @XmlElement
        public GraphQLRequestFile getRequest() {
            return request;
        }

        public void setRequest(GraphQLRequestFile request) {
            this.request = request;
        }

        @XmlElement
        public GraphQLResponseFile getResponse() {
            return response;
        }

        public void setResponse(GraphQLResponseFile response) {
            this.response = response;
        }

        @XmlElement
        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        @XmlElement
        public String getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(String applicationId) {
            this.applicationId = applicationId;
        }

    }

    @XmlRootElement(name = "graphQLRequest")
    protected static class GraphQLRequestFile {

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
        private List<RepositoryImpl.HttpHeaderFile> httpHeaders;

        @XmlElement
        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        @XmlElement
        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        @XmlElement
        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        @XmlElement
        public HttpMethod getHttpMethod() {
            return httpMethod;
        }

        public void setHttpMethod(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
        }


        @XmlElementWrapper(name = "queries")
        @XmlElement(name = "query")
        public List<GraphQLRequestQuery> getQueries() {
            return queries;
        }

        public void setQueries(List<GraphQLRequestQuery> queries) {
            this.queries = queries;
        }

        @XmlElementWrapper(name = "httpHeaders")
        @XmlElement(name = "httpHeader")
        public List<RepositoryImpl.HttpHeaderFile> getHttpHeaders() {
            return httpHeaders;
        }

        public void setHttpHeaders(List<RepositoryImpl.HttpHeaderFile> httpHeaders) {
            this.httpHeaders = httpHeaders;
        }

    }

    @XmlRootElement(name = "graphQLResponse")
    protected static class GraphQLResponseFile {

        @Mapping("body")
        private String body;
        @Mapping("httpStatusCode")
        private Integer httpStatusCode;
        @Mapping("contentType")
        private String contentType;
        @Mapping("httpHeaders")
        private List<RepositoryImpl.HttpHeaderFile> httpHeaders;
        @Mapping("contentEncodings")
        private List<ContentEncoding> contentEncodings;

        @XmlElement
        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        @XmlElement
        public Integer getHttpStatusCode() {
            return httpStatusCode;
        }

        public void setHttpStatusCode(Integer httpStatusCode) {
            this.httpStatusCode = httpStatusCode;
        }

        @XmlElement
        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        @XmlElementWrapper(name = "httpHeaders")
        @XmlElement(name = "httpHeader")
        public List<RepositoryImpl.HttpHeaderFile> getHttpHeaders() {
            return httpHeaders;
        }

        public void setHttpHeaders(List<RepositoryImpl.HttpHeaderFile> httpHeaders) {
            this.httpHeaders = httpHeaders;
        }

        @XmlElementWrapper(name = "contentEncodings")
        @XmlElement(name = "contentEncoding")
        public List<ContentEncoding> getContentEncodings() {
            return contentEncodings;
        }

        public void setContentEncodings(List<ContentEncoding> contentEncodings) {
            this.contentEncodings = contentEncodings;
        }

    }

}
