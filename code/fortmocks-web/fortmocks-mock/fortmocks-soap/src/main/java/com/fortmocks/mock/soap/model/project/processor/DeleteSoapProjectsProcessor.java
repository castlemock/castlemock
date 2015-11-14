package com.fortmocks.mock.soap.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.mock.soap.model.project.processor.message.input.DeleteSoapProjectInput;
import com.fortmocks.mock.soap.model.project.processor.message.input.DeleteSoapProjectsInput;
import com.fortmocks.mock.soap.model.project.processor.message.output.DeleteSoapProjectOutput;
import com.fortmocks.mock.soap.model.project.processor.message.output.DeleteSoapProjectsOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class DeleteSoapProjectsProcessor extends AbstractSoapProjectProcessor implements Processor<DeleteSoapProjectsInput, DeleteSoapProjectsOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<DeleteSoapProjectsOutput> process(final Task<DeleteSoapProjectsInput> task) {
        final DeleteSoapProjectsInput input = task.getInput();
        for (SoapProjectDto soapProject : input.getSoapProjects()){
            delete(soapProject.getId());
        }
        return createResult(new DeleteSoapProjectsOutput());
    }
}
