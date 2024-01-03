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

package com.castlemock.repository.rest.file.event;

import com.castlemock.model.core.SearchQuery;
import com.castlemock.model.core.SearchResult;
import com.castlemock.model.core.http.ContentEncoding;
import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.mock.rest.domain.RestEvent;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.file.FileRepository;
import com.castlemock.repository.core.file.event.AbstractEventFileRepository;
import com.castlemock.repository.rest.event.RestEventRepository;
import com.google.common.base.Preconditions;
import org.dozer.Mapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * The class is an implementation of the REST event repository and provides the functionality to interact with the file system.
 * The repository is responsible for loading and saving rest events from the file system. Each rest event is stored as
 * a separate file. The class also contains the directory and the filename extension for the rest event.
 * @author Karl Dahlgren
 * @since 1.0
 * @see RestEventFileRepository
 * @see FileRepository
 */
@Repository
@Profile(Profiles.FILE)
public class RestEventFileRepository extends AbstractEventFileRepository<RestEventFileRepository.RestEventFile, RestEvent> implements RestEventRepository {

    @Value(value = "${rest.event.file.directory}")
    private String restEventFileDirectory;
    @Value(value = "${rest.event.file.extension}")
    private String restEventFileExtension;

    /**
     * The method returns the directory for the specific file repository. The directory will be used to indicate
     * where files should be saved and loaded from.
     * @return The file directory where the files for the specific file repository could be saved and loaded from.
     */
    @Override
    protected String getFileDirectory() {
        return restEventFileDirectory;
    }

    /**
     * The method returns the postfix for the file that the file repository is responsible for managing.
     * @return The file extension for the file type that the repository is responsible for managing .
     */
    @Override
    protected String getFileExtension() {
        return restEventFileExtension;
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to the file system is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to the file system, but also loaded from the
     * file system upon application startup. The method will throw an exception in case of the type not being acceptable.
     * @param restEvent The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             the file system.
     * @see #save
     */
    @Override
    protected void checkType(final RestEventFile restEvent) {
        Preconditions.checkNotNull(restEvent, "Event cannot be null");
        Preconditions.checkNotNull(restEvent.getId(), "Event id cannot be null");
        Preconditions.checkNotNull(restEvent.getEndDate(), "Event end date cannot be null");
        Preconditions.checkNotNull(restEvent.getStartDate(), "Event start date cannot be null");
    }

    /**
     * The service finds the oldest event
     * @return The oldest event
     */
    @Override
    public RestEvent getOldestEvent() {
        RestEventFile oldestEvent = null;
        for(RestEventFile event : collection.values()){
            if(oldestEvent == null){
                oldestEvent = event;
            } else if(event.getStartDate().before(oldestEvent.getStartDate())){
                oldestEvent = event;
            }
        }

        return oldestEvent == null ? null : mapper.map(oldestEvent, RestEvent.class);
    }

    /**
     * Find events by REST method ID
     * @param restMethodId The id of the REST method
     * @return A list of {@link RestEvent} that matches the provided <code>restMethodId</code>
     */
    @Override
    public List<RestEvent> findEventsByMethodId(final String restMethodId) {
        return this.collection.values()
                .stream()
                .filter(event -> event.getMethodId().equals(restMethodId))
                .map(event ->  this.mapper.map(event, RestEvent.class))
                .toList();
    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<RestEvent> search(final SearchQuery query) {
        throw new UnsupportedOperationException();
    }

    /**
     * The method finds and deletes the oldest event.
     * @return The event that was deleted.
     * @since 1.5
     */
    @Override
    public synchronized RestEvent deleteOldestEvent(){
        final RestEvent event = getOldestEvent();
        this.delete(event.getId());
        return event;
    }

    /**
     * The method clears and deletes all logs.
     * @since 1.7
     */
    @Override
    public void clearAll() {
        this.collection.values()
                .stream()
                .map(RestEventFile::getId)
                .toList()
                .forEach(this::delete);
    }

    @XmlRootElement(name = "restEvent")
    protected static class RestEventFile extends AbstractEventFileRepository.EventFile {

        @Mapping("request")
        private RestRequestFile request;
        @Mapping("response")
        private RestResponseFile response;
        @Mapping("projectId")
        private String projectId;
        @Mapping("applicationId")
        private String applicationId;
        @Mapping("resourceId")
        private String resourceId;
        @Mapping("methodId")
        private String methodId;

        public RestRequestFile getRequest() {
            return request;
        }

        public void setRequest(RestRequestFile request) {
            this.request = request;
        }

        public RestResponseFile getResponse() {
            return response;
        }

        public void setResponse(RestResponseFile response) {
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

    @XmlRootElement(name = "restRequest")
    protected static class RestRequestFile {

        @Mapping("body")
        private String body;
        @Mapping("contentType")
        private String contentType;
        @Mapping("uri")
        private String uri;
        @Mapping("httpMethod")
        private HttpMethod httpMethod;
        @Mapping("httpParameters")
        private List<FileRepository.HttpParameterFile> httpParameters;
        @Mapping("httpHeaders")
        private List<HttpHeaderFile> httpHeaders;

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

        @XmlElementWrapper(name = "httpHeaders")
        @XmlElement(name = "httpHeader")
        public List<HttpHeaderFile> getHttpHeaders() {
            return httpHeaders;
        }

        public void setHttpHeaders(List<HttpHeaderFile> httpHeaders) {
            this.httpHeaders = httpHeaders;
        }

        @XmlElementWrapper(name = "httpParameters")
        @XmlElement(name = "httpParameter")
        public List<FileRepository.HttpParameterFile> getHttpParameters() {
            return httpParameters;
        }

        public void setHttpParameters(List<FileRepository.HttpParameterFile> httpParameters) {
            this.httpParameters = httpParameters;
        }
    }

    @XmlRootElement(name = "restResponse")
    protected static class RestResponseFile {

        @Mapping("body")
        private String body;
        @Mapping("mockResponseName")
        private String mockResponseName;
        @Mapping("httpStatusCode")
        private Integer httpStatusCode;
        @Mapping("contentType")
        private String contentType;
        @Mapping("httpHeaders")
        private List<HttpHeaderFile> httpHeaders;
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
        public String getMockResponseName() {
            return mockResponseName;
        }

        public void setMockResponseName(String mockResponseName) {
            this.mockResponseName = mockResponseName;
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
        public List<HttpHeaderFile> getHttpHeaders() {
            return httpHeaders;
        }

        public void setHttpHeaders(List<HttpHeaderFile> httpHeaders) {
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
