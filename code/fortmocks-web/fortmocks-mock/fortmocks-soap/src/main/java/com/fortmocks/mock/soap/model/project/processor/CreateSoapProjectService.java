package com.fortmocks.mock.soap.model.project.processor;

import com.fortmocks.core.model.Service;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.mock.soap.model.project.processor.message.input.CreateSoapProjectInput;
import com.fortmocks.mock.soap.model.project.processor.message.output.CreateSoapProjectOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class CreateSoapProjectService extends AbstractSoapProjectProcessor implements Service<CreateSoapProjectInput, CreateSoapProjectOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<CreateSoapProjectOutput> process(final Task<CreateSoapProjectInput> task) {
        final CreateSoapProjectInput input = task.getInput();
        final SoapProjectDto soapProject = input.getSoapProject();
        final SoapProjectDto savedSoapProject = save(soapProject);
        final CreateSoapProjectOutput output = new CreateSoapProjectOutput();
        output.setSavedSoapProject(savedSoapProject);
        return createResult(output);
    }
}
