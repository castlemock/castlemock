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

package com.castlemock.service.mock.soap.project.adapter;

import com.castlemock.model.core.SearchQuery;
import com.castlemock.model.core.SearchResult;
import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.core.TypeIdentifier;
import com.castlemock.model.core.project.Project;
import com.castlemock.model.core.service.project.ProjectServiceAdapter;
import com.castlemock.model.mock.soap.domain.SoapProject;
import com.castlemock.service.mock.soap.SoapTypeIdentifier;
import com.castlemock.service.mock.soap.project.input.CreateSoapProjectInput;
import com.castlemock.service.mock.soap.project.input.DeleteSoapProjectInput;
import com.castlemock.service.mock.soap.project.input.ExportSoapProjectInput;
import com.castlemock.service.mock.soap.project.input.ImportSoapProjectInput;
import com.castlemock.service.mock.soap.project.input.ReadAllSoapProjectsInput;
import com.castlemock.service.mock.soap.project.input.ReadSoapProjectInput;
import com.castlemock.service.mock.soap.project.input.SearchSoapProjectInput;
import com.castlemock.service.mock.soap.project.input.UpdateSoapProjectInput;
import com.castlemock.service.mock.soap.project.output.CreateSoapProjectOutput;
import com.castlemock.service.mock.soap.project.output.DeleteSoapProjectOutput;
import com.castlemock.service.mock.soap.project.output.ExportSoapProjectOutput;
import com.castlemock.service.mock.soap.project.output.ImportSoapProjectOutput;
import com.castlemock.service.mock.soap.project.output.ReadAllSoapProjectsOutput;
import com.castlemock.service.mock.soap.project.output.ReadSoapProjectOutput;
import com.castlemock.service.mock.soap.project.output.SearchSoapProjectOutput;
import com.castlemock.service.mock.soap.project.output.UpdateSoapProjectOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The SOAP project service adapter is responsible for providing the basic functionality for all the
 * project services.
 * @author Karl Dahlgren
 * @since 1.0
 * @see SoapProject
 */
@Service
public class SoapProjectServiceAdapter implements ProjectServiceAdapter<SoapProject> {

    @Autowired
    private ServiceProcessor serviceProcessor;
    private static final SoapTypeIdentifier SOAP_TYPE_IDENTIFIER = new SoapTypeIdentifier();

    /**
     * The method provides the functionality to create and store a  instance to a specific service.
     * The service is identified with the provided type value.
     * @param project The instance that will be created
     * @return The saved instance
     */
    @Override
    public SoapProject create(final SoapProject project) {
        final CreateSoapProjectOutput output = serviceProcessor.process(CreateSoapProjectInput.builder()
                .project(project)
                .build());
        return output.getProject();
    }

    /**
     * The method provides the functionality to delete a specific instance. The type is
     * identified with the provided typeUrl value. When the type has been identified, the instance
     * itself has to be identified. This is done with the provided id. The instance with the matching type
     * and id will be deleted.
     * @param id The id of the instance that will be deleted
     */
    @Override
    public SoapProject delete(final String id) {
        final DeleteSoapProjectOutput output = serviceProcessor.process(DeleteSoapProjectInput.builder()
                .projectId(id)
                .build());
        return output.getProject();
    }

    /**
     * The method is used to update an already existing instance. The instance type is
     * identified with the provided typeUrl value. When the instance type has been identified, the instance
     * itself has to be identified. This is done with the provided id. The instance with the matching id will be
     * replaced with the provided  instance. Please note that not all values will be updated. It depends on the instance
     * type.
     * @param id The id of the instance that will be updated
     * @param project The instance with the new updated values
     * @return The updated instance
     */
    @Override
    public SoapProject update(final String id,
                              final SoapProject project) {
        final UpdateSoapProjectOutput output = serviceProcessor.process(UpdateSoapProjectInput.builder()
                .projectId(id)
                .project(project)
                .build());
        return output.getProject();
    }

    /**
     * The method is responsible for retrieving all instances from all the various service types.
     * @return A list containing all the instance independent from type
     */
    @Override
    public List<SoapProject> readAll() {
        final ReadAllSoapProjectsOutput output = serviceProcessor.process(ReadAllSoapProjectsInput.builder().build());
        return output.getProjects();
    }

    /**
     * The method is used to retrieve a instance with a specific type. The type is
     * identified with the provided typeUrl value. When the instance type has been identified, the instance
     * itself has to be identified.
     * @param id The id of the instance that will be retrieved
     * @return A instance that matches the instance type and the provided id. If no instance matches the provided
     *         values, null will be returned.
     */
    @Override
    public SoapProject read(final String id) {
        final ReadSoapProjectOutput output = serviceProcessor.process(ReadSoapProjectInput.builder()
                .projectId(id)
                .build());
        return output.getProject();
    }

    /**
     * The TypeIdentifier that is used to identify a specific class
     * @return Returns the type identifier
     */
    @Override
    public TypeIdentifier getTypeIdentifier() {
        return SOAP_TYPE_IDENTIFIER;
    }

    /**
     * The method provides the functionality to convert an instance (parent) to an instance of a subclass to the
     * provided parent.
     * @param parent The parent that will be converted into a subtype of the provided parent
     * @return A new instance of the parent, but as a subtype of the parent
     */
    @Override
    public SoapProject convertType(Project parent) {
        return new SoapProject(parent);
    }


    /**
     * The method provides the functionality to export a project and convert it to a String
     * @param id The id of the project that will be converted and exported
     * @return The project with the provided id as a String
     */
    @Override
    public String exportProject(final String id) {
        final ExportSoapProjectOutput output = serviceProcessor.process(ExportSoapProjectInput.builder()
                .projectId(id)
                .build());
        return output.getProject();
    }

    /**
     * The method provides the functionality to import a project as a String
     * @param projectRaw The project as a String
     * @return The imported project
     */
    @Override
    public SoapProject importProject(final String projectRaw) {
        final ImportSoapProjectOutput output = serviceProcessor.process(ImportSoapProjectInput.builder()
                .projectRaw(projectRaw)
                .build());
        return output.getProject();
    }

    /**
     * Searches for resources that matches the provided query. The matching resources will
     * be returned as a collection of {@link SearchResult}
     * @param searchQuery The search query that will be used to identify the resources
     * @return A list of search results
     */
    @Override
    public List<SearchResult> search(final SearchQuery searchQuery) {
        final SearchSoapProjectOutput output = serviceProcessor.process(SearchSoapProjectInput.builder()
                .searchQuery(searchQuery)
                .build());
        return output.getSearchResults();
    }
}
