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

package com.castlemock.repository.rest.file.project;

import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.SearchValidator;
import com.castlemock.core.basis.model.http.domain.ContentEncoding;
import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponse;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponseStatus;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.file.FileRepository;
import com.castlemock.repository.rest.project.RestMockResponseRepository;
import org.dozer.Mapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
@Profile(Profiles.FILE)
public class RestMockResponseFileRepository extends FileRepository<RestMockResponseFileRepository.RestMockResponseFile, RestMockResponse, String> implements RestMockResponseRepository {

    @Value(value = "${rest.response.file.directory}")
    private String fileDirectory;
    @Value(value = "${rest.response.file.extension}")
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
    protected void checkType(RestMockResponseFile type) {

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
        for(RestMockResponseFile restMockResponse : collection.values()) {
            if(restMockResponse.getParameterQueries() == null){
                final List<RestParameterQueryFile> parameterQueries = new CopyOnWriteArrayList<RestParameterQueryFile>();
                restMockResponse.setParameterQueries(parameterQueries);
                save(restMockResponse);
            }
            if(restMockResponse.getHeaderQueries() == null){
                final List<RestHeaderQueryFile> headerQueries = new CopyOnWriteArrayList<RestHeaderQueryFile>();
                restMockResponse.setHeaderQueries(headerQueries);
                save(restMockResponse);
            }
        }
    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<RestMockResponse> search(SearchQuery query) {
        final List<RestMockResponse> result = new LinkedList<RestMockResponse>();
        for(RestMockResponseFile restMockResponseFile : collection.values()){
            if(SearchValidator.validate(restMockResponseFile.getName(), query.getQuery())){
                RestMockResponse restMockResponse = mapper.map(restMockResponseFile, RestMockResponse.class);
                result.add(restMockResponse);
            }
        }
        return result;
    }

    /**
     * Delete all {@link RestMockResponseFile} that matches the provided
     * <code>methodId</code>.
     *
     * @param methodId The id of the method.
     */
    @Override
    public void deleteWithMethodId(String methodId) {
        Iterator<RestMockResponseFile> iterator = this.collection.values().iterator();
        while (iterator.hasNext()){
            RestMockResponseFile response = iterator.next();
            if(response.getMethodId().equals(methodId)){
                delete(response.getId());
            }
        }
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
        final List<RestMockResponse> applications = new ArrayList<>();
        for(RestMockResponseFile responseFile : this.collection.values()){
            if(responseFile.getMethodId().equals(methodId)){
                RestMockResponse response = this.mapper.map(responseFile, RestMockResponse.class);
                applications.add(response);
            }
        }
        return applications;
    }

    /**
     * Retrieve the {@link RestMethod} id
     * for the {@link RestMockResponse} with the provided id.
     *
     * @param mockResponseId The id of the {@link RestMockResponse}.
     * @return The id of the method.
     * @since 1.20
     */
    @Override
    public String getMethodId(String mockResponseId) {
        final RestMockResponseFile mockResponseFile = this.collection.get(mockResponseId);

        if(mockResponseFile == null){
            throw new IllegalArgumentException("Unable to find a mock response with the following id: " + mockResponseId);
        }
        return mockResponseFile.getMethodId();
    }


    @XmlRootElement(name = "restMockResponse")
    protected static class RestMockResponseFile implements Saveable<String> {

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
        private List<RestParameterQueryFile> parameterQueries = new CopyOnWriteArrayList<RestParameterQueryFile>();
        @Mapping("xpathExpressions")
        private List<RestXPathExpressionFile> xpathExpressions = new CopyOnWriteArrayList<RestXPathExpressionFile>();
        @Mapping("jsonPathExpressions")
        private List<RestJsonPathExpressionFile> jsonPathExpressions = new CopyOnWriteArrayList<RestJsonPathExpressionFile>();
        @Mapping("headerQueries")
        private List<RestHeaderQueryFile> headerQueries = new CopyOnWriteArrayList<RestHeaderQueryFile>();

        @Override
        @XmlElement
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
        public String getMethodId() {
            return methodId;
        }

        public void setMethodId(String methodId) {
            this.methodId = methodId;
        }

        @XmlElement
        public RestMockResponseStatus getStatus() {
            return status;
        }

        public void setStatus(RestMockResponseStatus status) {
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
        public List<HttpHeader> getHttpHeaders() {
            return httpHeaders;
        }

        public void setHttpHeaders(List<HttpHeader> httpHeaders) {
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

        @XmlElementWrapper(name = "parameterQueries")
        @XmlElement(name = "parameterQuery")
        public List<RestParameterQueryFile> getParameterQueries() {
            return parameterQueries;
        }

        public void setParameterQueries(List<RestParameterQueryFile> parameterQueries) {
            this.parameterQueries = parameterQueries;
        }

        @XmlElementWrapper(name = "xpathExpressions")
        @XmlElement(name = "xpathExpression")
        public List<RestXPathExpressionFile> getXpathExpressions() {
            return xpathExpressions;
        }

        public void setXpathExpressions(List<RestXPathExpressionFile> xpathExpressions) {
            this.xpathExpressions = xpathExpressions;
        }

        @XmlElementWrapper(name = "jsonPathExpressions")
        @XmlElement(name = "jsonPathExpression")
        public List<RestJsonPathExpressionFile> getJsonPathExpressions() {
            return jsonPathExpressions;
        }

        public void setJsonPathExpressions(List<RestJsonPathExpressionFile> jsonPathExpressions) {
            this.jsonPathExpressions = jsonPathExpressions;
        }

        @XmlElementWrapper(name = "headerQueries")
        @XmlElement(name = "headerQueries")
        public List<RestHeaderQueryFile> getHeaderQueries() {
            return headerQueries;
        }

        public void setHeaderQueries(List<RestHeaderQueryFile> headerQueries) {
            this.headerQueries = headerQueries;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof RestMockResponseFile))
                return false;

            RestMockResponseFile that = (RestMockResponseFile) o;

            if (id != null ? !id.equals(that.id) : that.id != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            return id != null ? id.hashCode() : 0;
        }
    }

    @XmlRootElement(name = "restParameterQuery")
    protected static class RestParameterQueryFile {

        private String parameter;
        private String query;
        private boolean matchCase;
        private boolean matchAny;
        private boolean matchRegex;

        @XmlElement
        public String getParameter() {
            return parameter;
        }

        public void setParameter(String parameter) {
            this.parameter = parameter;
        }

        @XmlElement
        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        @XmlElement
        public boolean getMatchCase() {
            return matchCase;
        }

        public void setMatchCase(boolean matchCase) {
            this.matchCase = matchCase;
        }

        @XmlElement
        public boolean getMatchAny() {
            return matchAny;
        }

        public void setMatchAny(boolean matchAny) {
            this.matchAny = matchAny;
        }

        @XmlElement
        public boolean getMatchRegex() {
            return matchRegex;
        }

        public void setMatchRegex(boolean matchRegex) {
            this.matchRegex = matchRegex;
        }
    }

    @XmlRootElement(name = "restHeaderQueryFile")
    protected static class RestHeaderQueryFile {

        private String header;
        private String query;
        private boolean matchCase;
        private boolean matchAny;
        private boolean matchRegex;

        @XmlElement
        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        @XmlElement
        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        @XmlElement
        public boolean getMatchCase() {
            return matchCase;
        }

        public void setMatchCase(boolean matchCase) {
            this.matchCase = matchCase;
        }

        @XmlElement
        public boolean getMatchAny() {
            return matchAny;
        }

        public void setMatchAny(boolean matchAny) {
            this.matchAny = matchAny;
        }

        @XmlElement
        public boolean getMatchRegex() {
            return matchRegex;
        }

        public void setMatchRegex(boolean matchRegex) {
            this.matchRegex = matchRegex;
        }
    }

    @XmlRootElement(name = "restXPathExpression")
    protected static class RestXPathExpressionFile {

        private String expression;

        @XmlElement
        public String getExpression() {
            return expression;
        }

        public void setExpression(String expression) {
            this.expression = expression;
        }

    }

    @XmlRootElement(name = "restJsonPathExpression")
    protected static class RestJsonPathExpressionFile {

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
