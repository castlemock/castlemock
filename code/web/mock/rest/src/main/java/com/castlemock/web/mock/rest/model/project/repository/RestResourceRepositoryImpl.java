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
import com.castlemock.core.mock.rest.model.project.domain.RestApplication;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.core.mock.rest.model.project.domain.RestResource;
import com.castlemock.web.basis.model.RepositoryImpl;
import org.dozer.Mapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class RestResourceRepositoryImpl extends RepositoryImpl<RestResourceRepositoryImpl.RestResourceFile, RestResource, String> implements RestResourceRepository {

    @Value(value = "${rest.resource.file.directory}")
    private String fileDirectory;
    @Value(value = "${rest.resource.file.extension}")
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
    protected void checkType(RestResourceFile type) {

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
     * Delete all {@link RestResource} that matches the provided
     * <code>applicationId</code>.
     *
     * @param applicationId The id of the applicationId.
     */
    @Override
    public void deleteWithApplicationId(String applicationId) {
        Iterator<RestResourceFile> iterator = this.collection.values().iterator();
        while (iterator.hasNext()){
            RestResourceFile resource = iterator.next();
            if(resource.getApplicationId().equals(applicationId)){
                delete(resource.getId());
            }
        }
    }

    /**
     * Find all {@link RestResource} that matches the provided
     * <code>applicationId</code>.
     *
     * @param applicationId The id of the applicationId.
     * @return A list of {@link RestResource}.
     */
    @Override
    public List<RestResource> findWithApplicationId(String applicationId) {
        final List<RestResource> applications = new ArrayList<>();
        for(RestResourceFile resourceFile : this.collection.values()){
            if(resourceFile.getApplicationId().equals(applicationId)){
                RestResource resource = this.mapper.map(resourceFile, RestResource.class);
                applications.add(resource);
            }
        }
        return applications;
    }

    /**
     * Finds a {@link RestResource} with a URI
     *
     * @param applicationId The id of the {@link RestApplication}
     * @param resourceUri   The URI of a {@link RestResource}
     * @return A {@link RestResource} that matches the search criteria.
     * @see RestProject
     * @see RestApplication
     * @see RestResource
     */
    @Override
    public RestResource findRestResourceByUri(String applicationId, String resourceUri) {
        for(RestResourceFile resourceFile : this.collection.values()){
            if(resourceFile.getApplicationId().equals(applicationId) &&
                    resourceUri.equalsIgnoreCase(resourceFile.getUri())){
                RestResource resource = this.mapper.map(resourceFile, RestResource.class);
                return resource;

            }
        }
        return null;
    }

    @XmlRootElement(name = "restResource")
    protected static class RestResourceFile implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("name")
        private String name;
        @Mapping("uri")
        private String uri;
        @Mapping("applicationId")
        private String applicationId;

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
        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        @XmlElement
        public String getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(String applicationId) {
            this.applicationId = applicationId;
        }
    }

}
