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

package com.fortmocks.web.mock.soap.model.project.service.adapter;

import com.fortmocks.core.basis.model.SearchQuery;
import com.fortmocks.core.basis.model.SearchResult;
import com.fortmocks.core.basis.model.TypeIdentifier;
import com.fortmocks.core.basis.model.project.dto.ProjectDto;
import com.fortmocks.core.basis.model.project.service.ProjectServiceAdapter;
import com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.core.mock.soap.model.project.service.message.input.*;
import com.fortmocks.core.mock.soap.model.project.service.message.output.*;
import com.fortmocks.web.mock.soap.model.SoapTypeIdentifier;
import com.fortmocks.core.basis.model.ServiceProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class SoapProjectServiceAdapter implements ProjectServiceAdapter<SoapProjectDto> {

    @Autowired
    private ServiceProcessor serviceProcessor;
    private static final SoapTypeIdentifier SOAP_TYPE_IDENTIFIER = new SoapTypeIdentifier();

    /**
     * The method provides the functionality to create and store a DTO instance to a specific service.
     * The service is identified with the provided type value.
     * @param dto The instance that will be created
     * @return The saved instance
     */
    @Override
    public SoapProjectDto create(SoapProjectDto dto) {
        final CreateSoapProjectOutput output = serviceProcessor.process(new CreateSoapProjectInput(dto));
        return output.getSavedSoapProject();
    }

    /**
     * The method provides the functionality to delete a specific instance. The type is
     * identified with the provided typeUrl value. When the type has been identified, the instance
     * itself has to be identified. This is done with the provided id. The instance with the matching type
     * and id will be deleted.
     * @param id The id of the instance that will be deleted
     */
    @Override
    public void delete(Long id) {
        serviceProcessor.process(new DeleteSoapProjectInput(id));
    }

    /**
     * The method is used to update an already existing instance. The instance type is
     * identified with the provided typeUrl value. When the instance type has been identified, the instance
     * itself has to be identified. This is done with the provided id. The instance with the matching id will be
     * replaced with the provided dto instance. Please note that not all values will be updated. It depends on the instance
     * type.
     * @param id The id of the instance that will be updated
     * @param dto The instance with the new updated values
     * @return The updated instance
     */
    @Override
    public SoapProjectDto update(Long id, SoapProjectDto dto) {
        final UpdateSoapProjectOutput output = serviceProcessor.process(new UpdateSoapProjectInput(id, dto));
        return output.getUpdatedSoapProject();
    }

    /**
     * The method is responsible for retrieving all instances from all the various service types.
     * @return A list containing all the instance independent from type
     */
    @Override
    public List<SoapProjectDto> readAll() {
        final ReadAllSoapProjectsOutput output = serviceProcessor.process(new ReadAllSoapProjectsInput());
        return output.getSoapProjects();
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
    public SoapProjectDto read(Long id) {
        final ReadSoapProjectOutput output = serviceProcessor.process(new ReadSoapProjectInput(id));
        return output.getSoapProject();
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
    public SoapProjectDto convertType(ProjectDto parent) {
        return new SoapProjectDto(parent);
    }


    /**
     * The method provides the functionality to export a project and convert it to a String
     * @param id The id of the project that will be converted and exported
     * @return The project with the provided id as a String
     */
    @Override
    public String exportProject(Long id) {
        final ExportSoapProjectOutput output = serviceProcessor.process(new ExportSoapProjectInput(id));
        return output.getExportedProject();
    }

    /**
     * The method provides the functionality to import a project as a String
     * @param projectRaw The project as a String
     */
    @Override
    public void importProject(String projectRaw) {
        serviceProcessor.process(new ImportSoapProjectInput(projectRaw));
    }

    /**
     * Searches for resources that matches the provided query. The matching resources will
     * be returned as a collection of {@link SearchResult}
     * @param searchQuery The search query that will be used to identify the resources
     * @return A list of search results
     */
    @Override
    public List<SearchResult> search(SearchQuery searchQuery) {
        final SearchSoapProjectOutput output = serviceProcessor.process(new SearchSoapProjectInput(searchQuery));
        return output.getSearchResults();
    }
}
