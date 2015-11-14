package com.fortmocks.mock.soap.model.project.service;

import com.fortmocks.core.model.Service;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.mock.soap.model.project.service.message.input.ReadSoapProjectInput;
import com.fortmocks.mock.soap.model.project.service.message.output.ReadSoapProjectOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadSoapProjectService extends AbstractSoapProjectProcessor implements Service<ReadSoapProjectInput, ReadSoapProjectOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the service
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<ReadSoapProjectOutput> process(final Task<ReadSoapProjectInput> task) {
        final ReadSoapProjectInput input = task.getInput();
        final SoapProjectDto soapProject = find(input.getSoapProjectId());
        final ReadSoapProjectOutput output = new ReadSoapProjectOutput();
        output.setSoapProject(soapProject);
        return createResult(output);
    }
}
