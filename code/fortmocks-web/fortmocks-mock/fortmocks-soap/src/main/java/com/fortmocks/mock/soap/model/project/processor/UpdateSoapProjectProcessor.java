package com.fortmocks.mock.soap.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.mock.soap.model.project.processor.message.input.UpdateSoapProjectInput;
import com.fortmocks.mock.soap.model.project.processor.message.output.UpdateSoapProjectOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class UpdateSoapProjectProcessor extends AbstractSoapProjectProcessor implements Processor<UpdateSoapProjectInput, UpdateSoapProjectOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
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
