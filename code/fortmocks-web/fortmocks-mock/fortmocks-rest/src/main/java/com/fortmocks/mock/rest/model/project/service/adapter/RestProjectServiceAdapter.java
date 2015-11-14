package com.fortmocks.mock.rest.model.project.service.adapter;

import com.fortmocks.core.model.TypeIdentifier;
import com.fortmocks.core.model.project.dto.ProjectDto;
import com.fortmocks.core.model.project.service.ProjectServiceAdapter;
import com.fortmocks.mock.rest.model.RestTypeIdentifier;
import com.fortmocks.mock.rest.model.project.dto.RestProjectDto;
import com.fortmocks.mock.rest.model.project.service.message.input.*;
import com.fortmocks.mock.rest.model.project.service.message.output.CreateRestProjectOutput;
import com.fortmocks.mock.rest.model.project.service.message.output.ReadAllRestProjectsOutput;
import com.fortmocks.mock.rest.model.project.service.message.output.ReadRestProjectOutput;
import com.fortmocks.mock.rest.model.project.service.message.output.UpdateRestProjectOutput;
import com.fortmocks.web.core.service.ServiceProcessor;
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
    public RestProjectDto convertType(RestProjectDto parent) {
        return new RestProjectDto(parent);
    }

    @Override
    public String exportProject(Long id) {
        return null;
    }

    @Override
    public void importProject(String projectRaw) {

    }
}
