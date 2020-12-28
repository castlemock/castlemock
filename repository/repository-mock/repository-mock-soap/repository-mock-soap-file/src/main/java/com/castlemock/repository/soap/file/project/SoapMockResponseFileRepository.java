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


package com.castlemock.repository.soap.file.project;

import com.castlemock.model.core.Saveable;
import com.castlemock.model.core.SearchQuery;
import com.castlemock.model.core.SearchResult;
import com.castlemock.model.core.SearchValidator;
import com.castlemock.model.core.http.ContentEncoding;
import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.model.mock.soap.domain.SoapMockResponseStatus;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.file.FileRepository;
import com.castlemock.repository.soap.project.SoapMockResponseRepository;
import com.google.common.base.Strings;
import org.dozer.Mapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
@Profile(Profiles.FILE)
public class SoapMockResponseFileRepository extends FileRepository<SoapMockResponseFileRepository.SoapMockResponseFile, SoapMockResponse, String> implements SoapMockResponseRepository {

    @Value(value = "${soap.response.file.directory}")
    private String fileDirectory;
    @Value(value = "${soap.response.file.extension}")
    private String fileExtension;

    /**
     * The method returns the directory for the specific file repository. The directory will be used to indicate
     * where files should be saved and loaded from. The method is abstract and every subclass is responsible for
     * overriding the method and provided the directory for their corresponding file type.
     *
     * @return The file directory where the files for the specific file repository could be saved and loaded from.
     */
    @Override
    protected String getFileDirectory() {
        return fileDirectory;
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
        return fileExtension;
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
    protected void checkType(SoapMockResponseFile type) {

    }


    /**
     * The post initialize method can be used to run functionality for a specific service. The method is called when
     * the method {@link #initialize} has finished successful.
     *
     * The method is responsible to validate the imported types and make certain that all the collections are
     * initialized.
     * @see #initialize
     * @since 1.4
     */
    @Override
    protected void postInitiate() {
        for(SoapMockResponseFile soapMockResponse : collection.values()) {
            List<HttpHeaderFile> httpHeaders = new CopyOnWriteArrayList<HttpHeaderFile>();
            if (soapMockResponse.getHttpHeaders() != null) {
                httpHeaders.addAll(soapMockResponse.getHttpHeaders());
            }
            soapMockResponse.setHttpHeaders(httpHeaders);

            List<ContentEncoding> contentEncodings = new CopyOnWriteArrayList<ContentEncoding>();
            if (soapMockResponse.getContentEncodings() != null) {
                contentEncodings.addAll(soapMockResponse.getContentEncodings());
            }

            soapMockResponse.setContentEncodings(contentEncodings);

            if(!Strings.isNullOrEmpty(soapMockResponse.getXpathExpression())){
                final SoapXPathExpressionFile xPathExpression = new SoapXPathExpressionFile();
                xPathExpression.setExpression(soapMockResponse.getXpathExpression());
                soapMockResponse.getXpathExpressions().add(xPathExpression);
                soapMockResponse.setXpathExpression(null);
            }

            save(soapMockResponse);
        }
    }


    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SoapMockResponse> search(SearchQuery query) {
        final List<SoapMockResponse> result = new LinkedList<SoapMockResponse>();
        for(SoapMockResponseFile soapMockResponseFile : collection.values()){
            if(SearchValidator.validate(soapMockResponseFile.getName(), query.getQuery())){
                SoapMockResponse mockResponse = mapper.map(soapMockResponseFile, SoapMockResponse.class);
                result.add(mockResponse);
            }
        }
        return result;
    }

    @Override
    public void deleteWithOperationId(String operationId) {
        Iterator<SoapMockResponseFile> iterator = this.collection.values().iterator();
        while (iterator.hasNext()){
            SoapMockResponseFile mockResponse = iterator.next();
            if(mockResponse.getOperationId().equals(operationId)){
                delete(mockResponse.getId());
            }
        }
    }

    @Override
    public List<SoapMockResponse> findWithOperationId(final String operationId) {
        final List<SoapMockResponse> mockResponses = new ArrayList<>();
        for(SoapMockResponseFile mockResponse : this.collection.values()){
            if(operationId.equals(mockResponse.getOperationId())){
                SoapMockResponse operation = this.mapper.map(mockResponse, SoapMockResponse.class);
                mockResponses.add(operation);
            }
        }
        return mockResponses;
    }

    /**
     * Retrieve the {@link SoapOperation} id
     * for the {@link SoapMockResponse} with the provided id.
     *
     * @param mockResponseId The id of the {@link SoapMockResponse}.
     * @return The id of the operation.
     * @since 1.20
     */
    @Override
    public String getOperationId(String mockResponseId) {
        final SoapMockResponseFile mockResponse = this.collection.get(mockResponseId);

        if(mockResponse == null){
            throw new IllegalArgumentException("Unable to find a mock response with the following id: " + mockResponseId);
        }
        return mockResponse.getOperationId();
    }

    @XmlRootElement(name = "soapMockResponse")
    @XmlSeeAlso({HttpHeaderFile.class, SoapXPathExpressionFile.class})
    public static class SoapMockResponseFile implements Saveable<String> {

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
        private List<HttpHeaderFile> httpHeaders = new CopyOnWriteArrayList<HttpHeaderFile>();
        @Mapping("contentEncodings")
        private List<ContentEncoding> contentEncodings = new CopyOnWriteArrayList<ContentEncoding>();
        @Mapping("xpathExpressions")
        private List<SoapXPathExpressionFile> xpathExpressions = new CopyOnWriteArrayList<SoapXPathExpressionFile>();

        @XmlElement
        @Override
        public String getId() {
            return id;
        }

        @Override
        public void setId(String id) {
            this.id = id;
        }

        @XmlElement
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @XmlElement
        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        @XmlElement
        public String getOperationId() {
            return operationId;
        }

        public void setOperationId(String operationId) {
            this.operationId = operationId;
        }

        @XmlElement
        public SoapMockResponseStatus getStatus() {
            return status;
        }

        public void setStatus(SoapMockResponseStatus status) {
            this.status = status;
        }

        @XmlElement
        public Integer getHttpStatusCode() {
            return httpStatusCode;
        }

        public void setHttpStatusCode(Integer httpStatusCode) {
            this.httpStatusCode = httpStatusCode;
        }

        @XmlElement
        public boolean isUsingExpressions() {
            return usingExpressions;
        }

        public void setUsingExpressions(boolean usingExpressions) {
            this.usingExpressions = usingExpressions;
        }

        @XmlElementWrapper(name = "httpHeaders")
        @XmlElement(name = "httpHeader")
        public List<HttpHeaderFile> getHttpHeaders() {
            return httpHeaders;
        }

        public void setHttpHeaders(List<HttpHeaderFile> httpHeaders) {
            this.httpHeaders = httpHeaders;
        }


        @XmlElement
        public String getXpathExpression() {
            return xpathExpression;
        }

        public void setXpathExpression(String xpathExpression) {
            this.xpathExpression = xpathExpression;
        }

        @XmlElementWrapper(name = "contentEncodings")
        @XmlElement(name = "contentEncoding")
        public List<ContentEncoding> getContentEncodings() {
            return contentEncodings;
        }

        public void setContentEncodings(List<ContentEncoding> contentEncodings) {
            this.contentEncodings = contentEncodings;
        }

        @XmlElementWrapper(name = "xpathExpressions")
        @XmlElement(name = "xpathExpression")
        public List<SoapXPathExpressionFile> getXpathExpressions() {
            return xpathExpressions;
        }

        public void setXpathExpressions(List<SoapXPathExpressionFile> xpathExpressions) {
            this.xpathExpressions = xpathExpressions;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof SoapMockResponseFile))
                return false;

            SoapMockResponseFile that = (SoapMockResponseFile) o;

            if (id != null ? !id.equals(that.id) : that.id != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            return id != null ? id.hashCode() : 0;
        }
    }

    @XmlRootElement(name = "soapXPathExpression")
    public static class SoapXPathExpressionFile {

        private String expression;

        @XmlElement
        public String getExpression() {
            return expression;
        }

        public void setExpression(String expression) {
            this.expression = expression;
        }

    }
}
