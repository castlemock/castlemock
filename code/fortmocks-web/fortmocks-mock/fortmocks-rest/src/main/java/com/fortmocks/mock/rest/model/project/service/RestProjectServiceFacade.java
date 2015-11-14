package com.fortmocks.mock.rest.model.project.service;

import com.fortmocks.core.model.ServiceFacade;
import com.fortmocks.mock.rest.model.project.dto.RestProjectDto;
import com.fortmocks.mock.rest.model.project.processor.message.input.*;
import com.fortmocks.mock.rest.model.project.processor.message.output.CreateRestProjectOutput;
import com.fortmocks.mock.rest.model.project.processor.message.output.ReadAllRestProjectsOutput;
import com.fortmocks.mock.rest.model.project.processor.message.output.ReadRestProjectOutput;
import com.fortmocks.mock.rest.model.project.processor.message.output.UpdateRestProjectOutput;
import com.fortmocks.web.core.processor.ProcessorMainframe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class RestProjectServiceFacade implements ServiceFacade<RestProjectDto, Long> {

    @Autowired
    private ProcessorMainframe processorMainframe;

    @Override
    public RestProjectDto create(RestProjectDto dto) {
        final CreateRestProjectOutput output = processorMainframe.process(new CreateRestProjectInput(dto));
        return output.getSavedRestProject();
    }

    @Override
    public void delete(Long id) {
        processorMainframe.process(new DeleteRestProjectInput(id));
    }

    @Override
    public RestProjectDto update(Long id, RestProjectDto dto) {
        final UpdateRestProjectOutput output = processorMainframe.process(new UpdateRestProjectInput(id, dto));
        return output.getUpdatedRestProject();
    }

    @Override
    public List<RestProjectDto> readAll() {
        final ReadAllRestProjectsOutput output = processorMainframe.process(new ReadAllRestProjectsInput());
        return output.getRestProjects();
    }

    @Override
    public RestProjectDto read(Long id) {
        final ReadRestProjectOutput output = processorMainframe.process(new ReadRestProjectInput(id));
        return output.getRestProject();
    }

}
