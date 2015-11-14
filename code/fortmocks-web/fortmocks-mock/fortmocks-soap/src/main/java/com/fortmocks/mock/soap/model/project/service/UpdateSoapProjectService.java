package com.fortmocks.mock.soap.model.project.service;

import com.fortmocks.core.model.Service;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.mock.soap.model.project.service.message.input.UpdateSoapProjectInput;
import com.fortmocks.mock.soap.model.project.service.message.output.UpdateSoapProjectOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class UpdateSoapProjectService extends AbstractSoapProjectProcessor implements Service<UpdateSoapProjectInput, UpdateSoapProjectOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the service
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<UpdateSoapProjectOutput> process(final Task<UpdateSoapProjectInput> task) {
        final UpdateSoapProjectInput input = task.getInput();
        final Long soapProjectId = input.getSoapProjectId();
        final SoapProjectDto soapProject = input.getSoapProject();
        final SoapProjectDto updatedSoapProject = update(soapProjectId, soapProject);
        final UpdateSoapProjectOutput output = new UpdateSoapProjectOutput();
        output.setUpdatedSoapProject(updatedSoapProject);
        return createResult(output);
    }
}
