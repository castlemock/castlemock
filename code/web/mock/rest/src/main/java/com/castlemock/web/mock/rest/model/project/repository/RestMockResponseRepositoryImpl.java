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

package com.castlemock.web.mock.rest.model.project.repository;

import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.http.domain.ContentEncoding;
import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponseStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponse;
import com.castlemock.web.basis.model.RepositoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class RestMockResponseRepositoryImpl extends RepositoryImpl<RestMockResponseRepositoryImpl.RestMockResponseFile, RestMockResponse, String> implements RestMockResponseRepository {

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
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SearchResult> search(SearchQuery query) {
        return null;
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


    @XmlRootElement(name = "restMockResponse")
    protected static class RestMockResponseFile implements Saveable<String> {

        private String id;
        private String name;
        private String body;
        private String methodId;
        private RestMockResponseStatus status;
        private Integer httpStatusCode;
        private boolean usingExpressions;
        private List<HttpHeader> httpHeaders = new CopyOnWriteArrayList<HttpHeader>();
        private List<ContentEncoding> contentEncodings = new CopyOnWriteArrayList<ContentEncoding>();

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
}
