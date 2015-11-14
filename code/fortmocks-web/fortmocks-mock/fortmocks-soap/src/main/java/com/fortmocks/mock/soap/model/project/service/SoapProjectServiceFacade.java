package com.fortmocks.mock.soap.model.project.service;

import com.fortmocks.core.model.ServiceFacade;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.mock.soap.model.project.service.message.input.*;
import com.fortmocks.mock.soap.model.project.service.message.output.CreateSoapProjectOutput;
import com.fortmocks.mock.soap.model.project.service.message.output.ReadAllSoapProjectsOutput;
import com.fortmocks.mock.soap.model.project.service.message.output.ReadSoapProjectOutput;
import com.fortmocks.mock.soap.model.project.service.message.output.UpdateSoapProjectOutput;
import com.fortmocks.web.core.processor.ProcessorMainframe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class SoapProjectServiceFacade implements ServiceFacade<SoapProjectDto, Long> {

    @Autowired
    private ProcessorMainframe processorMainframe;

    @Override
    public SoapProjectDto create(SoapProjectDto dto) {
        final CreateSoapProjectOutput output = processorMainframe.process(new CreateSoapProjectInput(dto));
        return output.getSavedSoapProject();
    }

    @Override
    public void delete(Long id) {
        processorMainframe.process(new DeleteSoapProjectInput(id));
    }

    @Override
    public SoapProjectDto update(Long id, SoapProjectDto dto) {
        final UpdateSoapProjectOutput output = processorMainframe.process(new UpdateSoapProjectInput(id, dto));
        return output.getUpdatedSoapProject();
    }

    @Override
    public List<SoapProjectDto> readAll() {
        final ReadAllSoapProjectsOutput output = processorMainframe.process(new ReadAllSoapProjectsInput());
        return output.getSoapProjects();
    }

    @Override
    public SoapProjectDto read(Long id) {
        final ReadSoapProjectOutput output = processorMainframe.process(new ReadSoapProjectInput(id));
        return output.getSoapProject();
    }

}
