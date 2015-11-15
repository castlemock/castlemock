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

import com.fortmocks.core.basis.model.TypeIdentifier;
import com.fortmocks.core.basis.model.project.service.ProjectServiceAdapter;
import com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.core.mock.soap.model.project.service.message.input.*;
import com.fortmocks.core.mock.soap.model.project.service.message.output.*;
import com.fortmocks.web.mock.soap.model.SoapTypeIdentifier;
import com.fortmocks.web.basis.service.ServiceProcessor;
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

    @Override
    public SoapProjectDto create(SoapProjectDto dto) {
        final CreateSoapProjectOutput output = serviceProcessor.process(new CreateSoapProjectInput(dto));
        return output.getSavedSoapProject();
    }

    @Override
    public void delete(Long id) {
        serviceProcessor.process(new DeleteSoapProjectInput(id));
    }

    @Override
    public SoapProjectDto update(Long id, SoapProjectDto dto) {
        final UpdateSoapProjectOutput output = serviceProcessor.process(new UpdateSoapProjectInput(id, dto));
        return output.getUpdatedSoapProject();
    }

    @Override
    public List<SoapProjectDto> readAll() {
        final ReadAllSoapProjectsOutput output = serviceProcessor.process(new ReadAllSoapProjectsInput());
        return output.getSoapProjects();
    }

    @Override
    public SoapProjectDto read(Long id) {
        final ReadSoapProjectOutput output = serviceProcessor.process(new ReadSoapProjectInput(id));
        return output.getSoapProject();
    }

    @Override
    public TypeIdentifier getTypeIdentifier() {
        return SOAP_TYPE_IDENTIFIER;
    }

    @Override
    public SoapProjectDto convertType(SoapProjectDto parent) {
        return new SoapProjectDto(parent);
    }


    @Override
    public String exportProject(Long id) {
        final ExportSoapProjectOutput output = serviceProcessor.process(new ExportSoapProjectInput(id));
        return output.getExportedProject();
    }

    @Override
    public void importProject(String projectRaw) {
        serviceProcessor.process(new ImportSoapProjectInput(projectRaw));
    }
}
