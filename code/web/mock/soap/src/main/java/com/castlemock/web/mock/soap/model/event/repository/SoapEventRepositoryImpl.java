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

package com.castlemock.web.mock.soap.model.event.repository;

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.http.domain.ContentEncoding;
import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.soap.model.event.domain.SoapEvent;
import com.castlemock.core.mock.soap.model.project.domain.SoapVersion;
import com.castlemock.web.basis.model.RepositoryImpl;
import com.castlemock.web.basis.model.event.repository.AbstractEventFileRepository;
import com.google.common.base.Preconditions;
import org.dozer.Mapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The class is an implementation of the file repository and provides the functionality to interact with the file system.
 * The repository is responsible for loading and saving soap events from the file system. Each soap event is stored as
 * a separate file. The class also contains the directory and the filename extension for the soap event.
 * @author Karl Dahlgren
 * @since 1.0
 * @see SoapEventRepositoryImpl
 * @see RepositoryImpl
 */
@Repository
public class SoapEventRepositoryImpl extends AbstractEventFileRepository<SoapEventRepositoryImpl.SoapEventFile, SoapEvent> implements SoapEventRepository {

    @Value(value = "${soap.event.file.directory}")
    private String soapEventFileDirectory;
    @Value(value = "${legacy.soap.event.v1.directory}")
    private String soapEventLegacyV1FileDirectory;
    @Value(value = "${soap.event.file.extension}")
    private String soapEventFileExtension;

    /**
     * The method returns the directory for the specific file repository. The directory will be used to indicate
     * where files should be saved and loaded from.
     * @return The file directory where the files for the specific file repository could be saved and loaded from.
     */
    @Override
    protected String getFileDirectory() {
        return soapEventFileDirectory;
    }

    /**
     * The method returns the postfix for the file that the file repository is responsible for managing.
     * @return The file extension for the file type that the repository is responsible for managing .
     */
    @Override
    protected String getFileExtension() {
        return soapEventFileExtension;
    }

    /**
     * The initialize method is responsible for initiating the file repository. This procedure involves loading
     * the types (TYPE) from the file system and store them in the collection.
     * @see #loadFiles()
     * @see #postInitiate()
     */
    @Override
    public void initialize(){

        // Move the old event files to the new directory
        fileRepositorySupport.moveAllFiles(soapEventLegacyV1FileDirectory,
                soapEventFileDirectory, soapEventFileExtension);

        super.initialize();
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to the file system is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to the file system, but also loaded from the
     * file system upon application startup. The method will throw an exception in case of the type not being acceptable.
     * @param soapEvent The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             the file system.
     * @see #save
     */
    @Override
    protected void checkType(final SoapEventFile soapEvent) {
        Preconditions.checkNotNull(soapEvent, "Event cannot be null");
        Preconditions.checkNotNull(soapEvent.getId(), "Event id cannot be null");
        Preconditions.checkNotNull(soapEvent.getEndDate(), "Event end date cannot be null");
        Preconditions.checkNotNull(soapEvent.getStartDate(), "Event start date cannot be null");
    }

    /**
     * The events for a specific operation id
     * @param operationId The id of the operation that the event belongs to
     * @return Returns a list of events
     */
    @Override
    public List<SoapEvent> findEventsByOperationId(String operationId) {
        final List<SoapEventFile> events = new ArrayList<SoapEventFile>();
        for(SoapEventFile event : collection.values()){
            if(event.getOperationId().equals(operationId)){
                events.add(event);
            }
        }
        return toDtoList(events, SoapEvent.class);
    }

    /**
     * The service finds the oldest event
     * @return The oldest event
     */
    @Override
    public SoapEvent getOldestEvent() {
        EventFile oldestEvent = null;
        for(EventFile event : collection.values()){
            if(oldestEvent == null){
                oldestEvent = event;
            } else if(event.getStartDate().before(oldestEvent.getStartDate())){
                oldestEvent = event;
            }
        }

        return oldestEvent == null ? null : mapper.map(oldestEvent, SoapEvent.class);
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
    public synchronized SoapEvent deleteOldestEvent(){
        SoapEvent event = getOldestEvent();
        delete(event.getId());
        return event;
    }

    /**
     * The method clears and deletes all logs.
     * @since 1.7
     */
    @Override
    public void clearAll() {
        Iterator<SoapEventFile> iterator = collection.values().iterator();
        while(iterator.hasNext()){
            SoapEventFile soapEvent = iterator.next();
            delete(soapEvent.getId());
        }
    }

    @XmlRootElement(name = "soapEvent")
    protected static class SoapEventFile extends AbstractEventFileRepository.EventFile {

        @Mapping("request")
        private SoapRequestFile request;
        @Mapping("response")
        private SoapResponseFile response;
        @Mapping("projectId")
        private String projectId;
        @Mapping("portId")
        private String portId;
        @Mapping("operationId")
        private String operationId;


        @XmlElement
        public SoapRequestFile getRequest() {
            return request;
        }

        public void setRequest(SoapRequestFile request) {
            this.request = request;
        }

        @XmlElement
        public SoapResponseFile getResponse() {
            return response;
        }

        public void setResponse(SoapResponseFile response) {
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
        public String getPortId() {
            return portId;
        }

        public void setPortId(String portId) {
            this.portId = portId;
        }

        @XmlElement
        public String getOperationId() {
            return operationId;
        }

        public void setOperationId(String operationId) {
            this.operationId = operationId;
        }


    }

    @XmlRootElement(name = "soapRequest")
    protected static class SoapRequestFile {

        @Mapping("body")
        private String body;
        @Mapping("contentType")
        private String contentType;
        @Mapping("uri")
        private String uri;
        @Mapping("httpMethod")
        private HttpMethod httpMethod;
        @Mapping("operationName")
        private String operationName;
        @Mapping("operationIdentifier")
        private String operationIdentifier;
        @Mapping("soapVersion")
        private SoapVersion soapVersion;
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

        @XmlElement
        public String getOperationName() {
            return operationName;
        }

        public void setOperationName(String operationName) {
            this.operationName = operationName;
        }

        @XmlElement
        public String getOperationIdentifier() {
            return operationIdentifier;
        }

        public void setOperationIdentifier(String operationIdentifier) {
            this.operationIdentifier = operationIdentifier;
        }

        @XmlElement
        public SoapVersion getSoapVersion() {
            return soapVersion;
        }

        public void setSoapVersion(SoapVersion soapVersion) {
            this.soapVersion = soapVersion;
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


    @XmlRootElement(name = "soapResponse")
    protected static class SoapResponseFile {

        @Mapping("body")
        private String body;
        @Mapping("mockResponseName")
        private String mockResponseName;
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
