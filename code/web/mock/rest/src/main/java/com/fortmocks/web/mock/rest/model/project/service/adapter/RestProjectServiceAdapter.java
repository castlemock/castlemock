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

package com.fortmocks.web.mock.rest.model.project.service.adapter;

import com.fortmocks.core.basis.model.TypeIdentifier;
import com.fortmocks.core.basis.model.project.dto.ProjectDto;
import com.fortmocks.core.basis.model.project.service.ProjectServiceAdapter;
import com.fortmocks.core.mock.rest.model.project.service.message.input.*;
import com.fortmocks.core.mock.rest.model.project.service.message.output.*;
import com.fortmocks.web.mock.rest.model.RestTypeIdentifier;
import com.fortmocks.core.mock.rest.model.project.dto.RestProjectDto;
import com.fortmocks.core.basis.model.ServiceProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class RestProjectServiceAdapter implements ProjectServiceAdapter<RestProjectDto> {

    @Autowired
    private ServiceProcessor serviceProcessor;
    private RestTypeIdentifier REST_TYPE_IDENTIFIER = new RestTypeIdentifier();

    @Override
    public RestProjectDto create(RestProjectDto dto) {
        final CreateRestProjectOutput output = serviceProcessor.process(new CreateRestProjectInput(dto));
        return output.getSavedRestProject();
    }

    @Override
    public void delete(Long id) {
        serviceProcessor.process(new DeleteRestProjectInput(id));
    }

    @Override
    public RestProjectDto update(Long id, RestProjectDto dto) {
        final UpdateRestProjectOutput output = serviceProcessor.process(new UpdateRestProjectInput(id, dto));
        return output.getUpdatedRestProject();
    }

    @Override
    public List<RestProjectDto> readAll() {
        final ReadAllRestProjectsOutput output = serviceProcessor.process(new ReadAllRestProjectsInput());
        return output.getRestProjects();
    }

    @Override
    public RestProjectDto read(Long id) {
        final ReadRestProjectOutput output = serviceProcessor.process(new ReadRestProjectInput(id));
        return output.getRestProject();
    }

    @Override
    public TypeIdentifier getTypeIdentifier() {
        return REST_TYPE_IDENTIFIER;
    }

    @Override
    public RestProjectDto convertType(ProjectDto parent) {
        return new RestProjectDto(parent);
    }

    @Override
    public String exportProject(Long id) {
        final ExportRestProjectOutput output = serviceProcessor.process(new ExportRestProjectInput(id));
        return output.getExportedProject();
    }

    @Override
    public void importProject(String projectRaw) {
        serviceProcessor.process(new ImportRestProjectInput(projectRaw));
    }
}
