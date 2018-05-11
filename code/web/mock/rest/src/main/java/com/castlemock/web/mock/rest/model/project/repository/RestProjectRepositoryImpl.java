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

package com.castlemock.web.mock.rest.model.project.repository;

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.SearchValidator;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.web.basis.model.RepositoryImpl;
import com.castlemock.web.basis.model.project.repository.AbstractProjectFileRepository;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

/**
 * The class is an implementation of the file repository and provides the functionality to interact with the file system.
 * The repository is responsible for loading and saving REST project from the file system. Each REST project is stored as
 * a separate file. The class also contains the directory and the filename extension for the REST project.
 * @author Karl Dahlgren
 * @since 1.0
 * @see RestProjectRepository
 * @see RepositoryImpl
 */
@Repository
public class RestProjectRepositoryImpl extends AbstractProjectFileRepository<RestProjectRepositoryImpl.RestProjectFile, RestProject> implements RestProjectRepository {

    @Value(value = "${rest.project.file.directory}")
    private String restProjectFileDirectory;
    @Value(value = "${rest.project.file.extension}")
    private String restProjectFileExtension;

    /**
     * The method returns the directory for the specific file repository. The directory will be used to indicate
     * where files should be saved and loaded from.
     * @return The file directory where the files for the specific file repository could be saved and loaded from.
     */
    @Override
    protected String getFileDirectory() {
        return restProjectFileDirectory;
    }

    /**
     * The method returns the postfix for the file that the file repository is responsible for managing.
     * @return The file extension for the file type that the repository is responsible for managing .
     */
    @Override
    protected String getFileExtension() {
        return restProjectFileExtension;
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

    }


    /**
     * The method is responsible for controller that the type that is about the be saved to the file system is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to the file system, but also loaded from the
     * file system upon application startup. The method will throw an exception in case of the type not being acceptable.
     * @param restProject The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             the file system.
     * @see #save
     */
    @Override
    protected void checkType(RestProjectFile restProject) {

    }

    /**
     * The save method provides the functionality to save an instance to the file system.
     * @param project The type that will be saved to the file system.
     * @return The type that was saved to the file system. The main reason for it is being returned is because
     *         there could be modifications of the object during the save process. For example, if the type does not
     *         have an identifier, then the method will generate a new identifier for the type.
     */
    @Override
    public RestProject save(final RestProjectFile project) {
        return super.save(project);
    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<RestProject> search(SearchQuery query) {
        final List<RestProject> result = new LinkedList<RestProject>();
        for(RestProjectFile restProjectFile : collection.values()){
            if(SearchValidator.validate(restProjectFile.getName(), query.getQuery())){
                RestProject restProject = mapper.map(restProjectFile, RestProject.class);
                result.add(restProject);
            }
        }
        return result;
    }

    /**
     * Finds a {@link RestProject} with a provided REST project name.
     * @param restProjectName The name of the REST project that will be retrieved.
     * @return A {@link RestProject} that matches the provided name.
     * @see RestProject
     */
    @Override
    public RestProject findRestProjectWithName(final String restProjectName) {
        Preconditions.checkNotNull(restProjectName, "Project name cannot be null");
        Preconditions.checkArgument(!restProjectName.isEmpty(), "Project name cannot be empty");
        for(RestProjectFile restProject : collection.values()){
            if(restProject.getName().equalsIgnoreCase(restProjectName)) {
                return mapper.map(restProject, RestProject.class);
            }
        }
        return null;
    }

    @XmlRootElement(name = "restProject")
    protected static class RestProjectFile extends AbstractProjectFileRepository.ProjectFile {

    }


}
