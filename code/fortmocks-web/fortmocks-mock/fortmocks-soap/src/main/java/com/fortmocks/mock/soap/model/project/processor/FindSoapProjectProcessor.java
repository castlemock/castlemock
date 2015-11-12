package com.fortmocks.mock.soap.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.mock.soap.model.project.message.FindSoapProjectInput;
import com.fortmocks.mock.soap.model.project.message.FindSoapProjectOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class FindSoapProjectProcessor extends AbstractSoapProjectProcessor implements Processor<FindSoapProjectInput, FindSoapProjectOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<FindSoapProjectOutput> process(final Task<FindSoapProjectInput> task) {
        final FindSoapProjectInput input = task.getInput();
        final SoapProjectDto soapProject = find(input.getSoapProjectId());
        final FindSoapProjectOutput output = new FindSoapProjectOutput();
        output.setSoapProject(soapProject);
        return createResult(output);
    }
}
