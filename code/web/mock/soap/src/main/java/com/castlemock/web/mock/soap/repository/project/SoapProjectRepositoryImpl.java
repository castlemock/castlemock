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

package com.castlemock.web.mock.soap.repository.project;

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.SearchValidator;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.web.basis.model.RepositoryImpl;
import com.castlemock.web.basis.model.project.repository.AbstractProjectFileRepository;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

/**
 * The class is an implementation of the file repository and provides the functionality to interact with the file system.
 * The repository is responsible for loading and saving soap project from the file system. Each soap project is stored as
 * a separate file. The class also contains the directory and the filename extension for the soap project.
 * @author Karl Dahlgren
 * @since 1.0
 * @see SoapProjectRepository
 * @see RepositoryImpl
 */
@Repository
public class SoapProjectRepositoryImpl extends AbstractProjectFileRepository<SoapProjectRepositoryImpl.SoapProjectFile, SoapProject> implements SoapProjectRepository {

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
     */
    @Override
    public SoapProject findSoapProjectWithName(final String name) {
        Preconditions.checkNotNull(name, "Project name cannot be null");
        Preconditions.checkArgument(!name.isEmpty(), "Project name cannot be empty");
        for(SoapProjectFile soapProject : collection.values()){
            if(soapProject.getName().equalsIgnoreCase(name)) {
                return mapper.map(soapProject, SoapProject.class);
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
     */
    @Override
    protected void checkType(SoapProjectFile soapProject) {

    }

    /**
     * Delete an instance that match the provided id
     * @param soapProjectId The instance that matches the provided id will be deleted in the database
     */
    @Override
    public SoapProject delete(final String soapProjectId) {
        Preconditions.checkNotNull(soapProjectId, "Project id cannot be null");
        final SoapProjectFile soapProject = collection.get(soapProjectId);

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
    public List<SoapProject> search(final SearchQuery query) {
        final List<SoapProject> soapProjects = new LinkedList<SoapProject>();
        for(SoapProjectFile soapProjectFile : collection.values()){
            if(SearchValidator.validate(soapProjectFile.getName(), query.getQuery())){
                SoapProject soapProject = mapper.map(soapProjectFile, SoapProject.class);
                soapProjects.add(soapProject);
            }
        }
        return soapProjects;
    }

    @XmlRootElement(name = "soapProject")
    protected static class SoapProjectFile extends AbstractProjectFileRepository.ProjectFile {

    }

}
