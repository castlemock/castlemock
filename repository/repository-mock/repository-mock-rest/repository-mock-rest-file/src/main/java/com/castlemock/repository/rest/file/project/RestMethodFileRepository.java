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

import com.castlemock.model.core.Saveable;
import com.castlemock.model.core.SearchQuery;
import com.castlemock.model.core.SearchResult;
import com.castlemock.model.core.SearchValidator;
import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.model.mock.rest.domain.RestMethodStatus;
import com.castlemock.model.mock.rest.domain.RestResource;
import com.castlemock.model.mock.rest.domain.RestResponseStrategy;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.file.FileRepository;
import com.castlemock.repository.rest.project.RestMethodRepository;
import org.dozer.Mapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Repository
@Profile(Profiles.FILE)
public class RestMethodFileRepository extends FileRepository<RestMethodFileRepository.RestMethodFile, RestMethod, String> implements RestMethodRepository {

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
     * The post initialize method can be used to run functionality for a specific service. The method is called when
     * the method {@link #initialize} has finished successful.
     *
     * The method is responsible to validate the imported types and make certain that all the collections are
     * initialized.
     * @see #initialize
     * @since 1.35
     */
    @Override
    protected void postInitiate() {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<RestMethod> search(SearchQuery query) {
        final List<RestMethod> result = new LinkedList<RestMethod>();
        for(RestMethodFile restMethodFile : collection.values()){
            if(SearchValidator.validate(restMethodFile.getName(), query.getQuery())){
                RestMethod restMethod = mapper.map(restMethodFile, RestMethod.class);
                result.add(restMethod);
            }
        }
        return result;
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
     * @since 1.20
     */
    @Override
    public List<RestMethod> findWithResourceId(String resourceId) {
        final List<RestMethod> methods = new ArrayList<>();
        for(RestMethodFile methodFile : this.collection.values()){
            if(methodFile.getResourceId().equals(resourceId)){
                RestMethod method = this.mapper.map(methodFile, RestMethod.class);
                methods.add(method);
            }
        }
        return methods;
    }

    /**
     * Find all {@link RestMethod} ids that matches the provided
     * <code>resourceId</code>.
     *
     * @param resourceId The id of the resource.
     * @return A list of {@link RestMethod} ids.
     * @since 1.20
     */
    @Override
    public List<String> findIdsWithResourceId(String resourceId) {
        final List<String> ids = new ArrayList<>();
        for(RestMethodFile methodFile : this.collection.values()){
            if(methodFile.getResourceId().equals(resourceId)){
                ids.add(methodFile.getId());
            }
        }
        return ids;
    }

    /**
     * Retrieve the {@link RestResource} id
     * for the {@link RestMethod} with the provided id.
     *
     * @param methodId The id of the {@link RestMethod}.
     * @return The id of the resource.
     * @since 1.20
     */
    @Override
    public String getResourceId(String methodId) {
        final RestMethodFile methodFile = this.collection.get(methodId);

        if(methodFile == null){
            throw new IllegalArgumentException("Unable to find a method with the following id: " + methodId);
        }
        return methodFile.getResourceId();
    }

    @XmlRootElement(name = "restMethod")
    protected static class RestMethodFile implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("name")
        private String name;
        @Mapping("resourceId")
        private String resourceId;
        @Mapping("defaultBody")
        private String defaultBody;
        @Mapping("httpMethod")
        private HttpMethod httpMethod;
        @Mapping("forwardedEndpoint")
        private String forwardedEndpoint;
        @Mapping("status")
        private RestMethodStatus status;
        @Mapping("responseStrategy")
        private RestResponseStrategy responseStrategy;
        @Mapping("currentResponseSequenceIndex")
        private Integer currentResponseSequenceIndex;
        @Mapping("simulateNetworkDelay")
        private boolean simulateNetworkDelay;
        @Mapping("networkDelay")
        private long networkDelay;
        @Mapping("defaultMockResponseId")
        private String defaultMockResponseId;
        @Mapping("automaticForward")
        private boolean automaticForward;

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

        @XmlElement
        public String getDefaultMockResponseId() {
            return defaultMockResponseId;
        }

        public void setDefaultMockResponseId(String defaultMockResponseId) {
            this.defaultMockResponseId = defaultMockResponseId;
        }

        @XmlElement
        protected boolean getAutomaticForward() {
            return automaticForward;
        }

        protected void setAutomaticForward(boolean automaticForward) {
            this.automaticForward = automaticForward;
        }
    }
}
