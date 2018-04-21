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

package com.castlemock.web.mock.soap.model.project.repository;

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.SearchValidator;
import com.castlemock.core.basis.model.http.domain.ContentEncoding;
import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.castlemock.core.mock.soap.model.project.domain.*;
import com.castlemock.core.mock.soap.model.project.dto.*;
import com.castlemock.web.basis.model.RepositoryImpl;
import com.castlemock.web.basis.support.FileRepositorySupport;
import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The class is an implementation of the file repository and provides the functionality to interact with the file system.
 * The repository is responsible for loading and saving soap project from the file system. Each soap project is stored as
 * a separate file. The class also contains the directory and the filename extension for the soap project.
 * @author Karl Dahlgren
 * @since 1.0
 * @see SoapProjectRepository
 * @see RepositoryImpl
 * @see SoapProject
 */
@Repository
public class SoapProjectRepositoryImpl extends RepositoryImpl<SoapProject, SoapProjectDto, String> implements SoapProjectRepository {

    private static final String SLASH = "/";
    private static final String SOAP = "soap";
    private static final String PROJECT = "project";
    private static final String PORT = "port";
    private static final String OPERATION = "operation";
    private static final String RESPONSE = "response";
    private static final String COMMA = ", ";
    private static final String SOAP_TYPE = "SOAP";
    private static final String WSDL_DIRECTORY = "wsdl";
    private static final String SCHEMA_DIRECTORY = "wsdl";

    private static final Logger LOGGER = Logger.getLogger(SoapProjectRepositoryImpl.class);

    @Autowired
    private FileRepositorySupport fileRepositorySupport;

    @Autowired
    private MessageSource messageSource;
    @Value(value = "${soap.project.file.directory}")
    private String soapProjectFileDirectory;
    @Value(value = "${soap.project.file.extension}")
    private String soapProjectFileExtension;
    @Value(value = "${soap.resource.file.directory}")
    private String soapResourceFileDirectory;
    @Value(value = "${soap.resource.file.extension}")
    private String soapResourceFileExtension;

    /**
     * The method returns the directory for the specific file repository. The directory will be used to indicate
     * where files should be saved and loaded from.
     * @return The file directory where the files for the specific file repository could be saved and loaded from.
     */
    @Override
    protected String getFileDirectory() {
        return soapProjectFileDirectory;
    }

    /**
     * The method returns the postfix for the file that the file repository is responsible for managing.
     * @return The file extension for the file type that the repository is responsible for managing .
     */
    @Override
    protected String getFileExtension() {
        return soapProjectFileExtension;
    }

    /**
     * Finds a project by a given name
     * @param name The name of the project that should be retrieved
     * @return Returns a project with the provided name
     * @see SoapProject
     */
    @Override
    public SoapProjectDto findSoapProjectWithName(final String name) {
        Preconditions.checkNotNull(name, "Project name cannot be null");
        Preconditions.checkArgument(!name.isEmpty(), "Project name cannot be empty");
        for(SoapProject soapProject : collection.values()){
            if(soapProject.getName().equalsIgnoreCase(name)) {
                return mapper.map(soapProject, SoapProjectDto.class);
            }
        }
        return null;
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to the file system is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to the file system, but also loaded from the
     * file system upon application startup. The method will throw an exception in case of the type not being acceptable.
     * @param soapProject The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             the file system.
     * @see #save
     * @see SoapProject
     */
    @Override
    protected void checkType(SoapProject soapProject) {

    }

    /**
     * Delete an instance that match the provided id
     * @param soapProjectId The instance that matches the provided id will be deleted in the database
     */
    @Override
    public SoapProjectDto delete(final String soapProjectId) {
        Preconditions.checkNotNull(soapProjectId, "Project id cannot be null");
        final SoapProject soapProject = collection.get(soapProjectId);

        if(soapProject == null){
            throw new IllegalArgumentException("Unable to find a SOAP project with id " + soapProjectId);
        }

        return super.delete(soapProjectId);
    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SearchResult> search(final SearchQuery query) {
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        for(SoapProject soapProject : collection.values()){
            List<SearchResult> soapProjectSearchResult = searchSoapProject(soapProject, query);
            searchResults.addAll(soapProjectSearchResult);
        }
        return searchResults;
    }


    /**
     * Search through a SOAP project and all its resources
     * @param soapProject The SOAP project which will be searched
     * @param query The provided search query
     * @return A list of search results that matches the provided query
     */
    private List<SearchResult> searchSoapProject(final SoapProject soapProject, final SearchQuery query){
        final List<SearchResult> searchResults = new LinkedList<SearchResult>();
        if(SearchValidator.validate(soapProject.getName(), query.getQuery())){
            final String projectType = messageSource.getMessage("general.type.project", null , LocaleContextHolder.getLocale());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(soapProject.getName());
            searchResult.setLink(SOAP + SLASH + PROJECT + SLASH + soapProject.getId());
            searchResult.setDescription(SOAP_TYPE + COMMA + projectType);
            searchResults.add(searchResult);
        }

        return searchResults;
    }


}
