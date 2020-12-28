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

import com.castlemock.model.core.model.Saveable;
import com.castlemock.model.core.model.SearchQuery;
import com.castlemock.model.core.model.SearchResult;
import com.castlemock.model.core.model.SearchValidator;
import com.castlemock.model.mock.rest.domain.RestApplication;
import com.castlemock.model.mock.rest.domain.RestProject;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.file.FileRepository;
import com.castlemock.repository.rest.project.RestApplicationRepository;
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
public class RestApplicationFileRepository extends FileRepository<RestApplicationFileRepository.RestApplicationFile, RestApplication, String> implements RestApplicationRepository {

    @Value(value = "${rest.application.file.directory}")
    private String fileDirectory;
    @Value(value = "${rest.application.file.extension}")
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
    protected void checkType(RestApplicationFile type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<RestApplication> search(SearchQuery query) {
        final List<RestApplication> result = new LinkedList<RestApplication>();
        for(RestApplicationFile restApplicationFile : collection.values()){
            if(SearchValidator.validate(restApplicationFile.getName(), query.getQuery())){
                RestApplication restApplication = mapper.map(restApplicationFile, RestApplication.class);
                result.add(restApplication);
            }
        }
        return result;
    }

    /**
     * Delete all {@link RestApplication} that matches the provided
     * <code>projectId</code>.
     *
     * @param projectId The id of the project.
     */
    @Override
    public void deleteWithProjectId(String projectId) {
        Iterator<RestApplicationFile> iterator = this.collection.values().iterator();
        while (iterator.hasNext()){
            RestApplicationFile application = iterator.next();
            if(application.getProjectId().equals(projectId)){
                delete(application.getId());
            }
        }
    }

    /**
     * Find all {@link RestApplication} that matches the provided
     * <code>projectId</code>.
     *
     * @param projectId The id of the project.
     * @return A list of {@link RestApplication}.
     */
    @Override
    public List<RestApplication> findWithProjectId(String projectId) {
        final List<RestApplication> applications = new ArrayList<>();
        for(RestApplicationFile applicationFile : this.collection.values()){
            if(applicationFile.getProjectId().equals(projectId)){
                RestApplication application = this.mapper.map(applicationFile, RestApplication.class);
                applications.add(application);
            }
        }
        return applications;
    }

    /**
     * Retrieve the {@link RestProject} id
     * for the {@link RestApplication} with the provided id.
     *
     * @param applicationId The id of the {@link RestApplication}.
     * @return The id of the project.
     * @since 1.20
     */
    @Override
    public String getProjectId(String applicationId) {
        final RestApplicationFile applicationFile = this.collection.get(applicationId);

        if(applicationFile == null){
            throw new IllegalArgumentException("Unable to find an application with the following id: " + applicationId);
        }
        return applicationFile.getProjectId();
    }

    @XmlRootElement(name = "restApplication")
    protected static class RestApplicationFile implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("name")
        private String name;
        @Mapping("projectId")
        private String projectId;

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
        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

    }

}
