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
import com.castlemock.core.mock.rest.model.project.dto.RestProjectDto;
import com.castlemock.web.basis.model.RepositoryImpl;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Repository;

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
 * @see RestProject
 */
@Repository
public class RestProjectRepositoryImpl extends RepositoryImpl<RestProject, RestProjectDto, String> implements RestProjectRepository {

    @Autowired
    private MessageSource messageSource;

    private static final String SLASH = "/";
    private static final String REST = "rest";
    private static final String PROJECT = "project";
    private static final String APPLICATION = "application";
    private static final String RESOURCE = "resource";
    private static final String METHOD = "method";
    private static final String RESPONSE = "response";
    private static final String COMMA = ", ";
    private static final String REST_TYPE = "REST";

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
     * @see RestProject
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
     * @see RestProject
     */
    @Override
    protected void checkType(RestProject restProject) {

    }

    /**
     * The save method provides the functionality to save an instance to the file system.
     * @param dto The type that will be saved to the file system.
     * @return The type that was saved to the file system. The main reason for it is being returned is because
     *         there could be modifications of the object during the save process. For example, if the type does not
     *         have an identifier, then the method will generate a new identifier for the type.
     */
    @Override
    public RestProjectDto save(final RestProject dto) {
        return super.save(dto);
    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SearchResult> search(SearchQuery query) {
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        for(RestProject restProject : collection.values()){
            List<SearchResult> restProjectSearchResult = searchRestProject(restProject, query);
            searchResults.addAll(restProjectSearchResult);
        }
        return searchResults;
    }

    /*
     * FIND OPERATIONS
     */

    /**
     * Finds a {@link RestProjectDto} with a provided REST project name.
     * @param restProjectName The name of the REST project that will be retrieved.
     * @return A {@link RestProjectDto} that matches the provided name.
     * @see RestProject
     * @see RestProjectDto
     */
    @Override
    public RestProjectDto findRestProjectWithName(final String restProjectName) {
        Preconditions.checkNotNull(restProjectName, "Project name cannot be null");
        Preconditions.checkArgument(!restProjectName.isEmpty(), "Project name cannot be empty");
        for(RestProject restProject : collection.values()){
            if(restProject.getName().equalsIgnoreCase(restProjectName)) {
                return mapper.map(restProject, RestProjectDto.class);
            }
        }
        return null;
    }


    /**
     * Search through a REST project and all its resources
     * @param restProject The REST project which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchRestProject(final RestProject restProject, final SearchQuery query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        if(SearchValidator.validate(restProject.getName(), query.getQuery())){
            final String projectType = messageSource.getMessage("general.type.project", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(restProject.getName());
            searchResult.setLink(REST + SLASH + PROJECT + SLASH + restProject.getId());
            searchResult.setDescription(REST_TYPE + COMMA + projectType);
            searchResults.add(searchResult);
        }

        return searchResults;
    }

}
