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
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestMethodStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestResponseStrategy;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;
import com.castlemock.web.basis.model.RepositoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class RestMethodRepositoryImpl extends RepositoryImpl<RestMethodRepositoryImpl.RestMethodFile, RestMethod, String> implements RestMethodRepository {

    @Value(value = "${rest.method.file.directory}")
    private String fileDirectory;
    @Value(value = "${rest.method.file.extension}")
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
    protected void checkType(RestMethodFile type) {

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
     * Updates the current response sequence index.
     *
     * @param restMethodId      The method id.
     * @param index             The new response sequence index.
     * @since 1.17
     */
    @Override
    public void setCurrentResponseSequenceIndex(final String restMethodId,
                                                final Integer index) {
        RestMethodFile restMethod = this.collection.get(restMethodId);
        restMethod.setCurrentResponseSequenceIndex(index);
    }

    /**
     * Delete all {@link RestMethod} that matches the provided
     * <code>resourceId</code>.
     *
     * @param resourceId The id of the resource.
     */
    @Override
    public void deleteWithResourceId(String resourceId) {
        Iterator<RestMethodFile> iterator = this.collection.values().iterator();
        while (iterator.hasNext()){
            RestMethodFile method = iterator.next();
            if(method.getResourceId().equals(resourceId)){
                delete(method.getId());
            }
        }
    }

    /**
     * Find all {@link RestMethod} that matches the provided
     * <code>resourceId</code>.
     *
     * @param resourceId The id of the resource.
     * @return A list of {@link RestMethod}.
     */
    @Override
    public List<RestMethod> findWithResourceId(String resourceId) {
        final List<RestMethod> applications = new ArrayList<>();
        for(RestMethodFile methodFile : this.collection.values()){
            if(methodFile.getResourceId().equals(resourceId)){
                RestMethod method = this.mapper.map(methodFile, RestMethod.class);
                applications.add(method);
            }
        }
        return applications;
    }

    @XmlRootElement(name = "restMethod")
    protected static class RestMethodFile implements Saveable<String> {

        private String id;
        private String name;
        private String resourceId;
        private String defaultBody;
        private HttpMethod httpMethod;
        private String forwardedEndpoint;
        private RestMethodStatus status;
        private RestResponseStrategy responseStrategy;
        private Integer currentResponseSequenceIndex;
        private boolean simulateNetworkDelay;
        private long networkDelay;

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
        public String getResourceId() {
            return resourceId;
        }

        public void setResourceId(String resourceId) {
            this.resourceId = resourceId;
        }

        @XmlElement
        public String getDefaultBody() {
            return defaultBody;
        }

        public void setDefaultBody(String defaultBody) {
            this.defaultBody = defaultBody;
        }

        @XmlElement
        public HttpMethod getHttpMethod() {
            return httpMethod;
        }

        public void setHttpMethod(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
        }

        @XmlElement
        public String getForwardedEndpoint() {
            return forwardedEndpoint;
        }

        public void setForwardedEndpoint(String forwardedEndpoint) {
            this.forwardedEndpoint = forwardedEndpoint;
        }

        @XmlElement
        public RestMethodStatus getStatus() {
            return status;
        }

        public void setStatus(RestMethodStatus status) {
            this.status = status;
        }

        @XmlElement
        public RestResponseStrategy getResponseStrategy() {
            return responseStrategy;
        }

        public void setResponseStrategy(RestResponseStrategy responseStrategy) {
            this.responseStrategy = responseStrategy;
        }

        @XmlElement
        public Integer getCurrentResponseSequenceIndex() {
            return currentResponseSequenceIndex;
        }

        public void setCurrentResponseSequenceIndex(Integer currentResponseSequenceIndex) {
            this.currentResponseSequenceIndex = currentResponseSequenceIndex;
        }

        @XmlElement
        public boolean getSimulateNetworkDelay() {
            return simulateNetworkDelay;
        }

        public void setSimulateNetworkDelay(boolean simulateNetworkDelay) {
            this.simulateNetworkDelay = simulateNetworkDelay;
        }

        @XmlElement
        public long getNetworkDelay() {
            return networkDelay;
        }

        public void setNetworkDelay(long networkDelay) {
            this.networkDelay = networkDelay;
        }
    }
}
