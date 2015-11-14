package com.fortmocks.core.mock.soap.model.project.service.adapter;

import com.fortmocks.core.basis.model.TypeIdentifier;
import com.fortmocks.core.basis.model.project.service.ProjectServiceAdapter;
import com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.core.mock.soap.model.project.service.message.input.*;
import com.fortmocks.core.mock.soap.model.project.service.message.output.CreateSoapProjectOutput;
import com.fortmocks.core.mock.soap.model.project.service.message.output.ReadAllSoapProjectsOutput;
import com.fortmocks.core.mock.soap.model.project.service.message.output.ReadSoapProjectOutput;
import com.fortmocks.core.mock.soap.model.project.service.message.output.UpdateSoapProjectOutput;
import com.fortmocks.core.mock.soap.model.SoapTypeIdentifier;
import com.fortmocks.web.core.service.ServiceProcessor;
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
        return null;
    }

    @Override
    public void importProject(String projectRaw) {

    }
}
