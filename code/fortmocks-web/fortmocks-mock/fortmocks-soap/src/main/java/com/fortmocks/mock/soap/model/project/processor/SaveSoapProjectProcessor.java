package com.fortmocks.mock.soap.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.mock.soap.model.project.message.SaveSoapProjectInput;
import com.fortmocks.mock.soap.model.project.message.SaveSoapProjectOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class SaveSoapProjectProcessor extends AbstractSoapProjectProcessor implements Processor<SaveSoapProjectInput, SaveSoapProjectOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<SaveSoapProjectOutput> process(final Task<SaveSoapProjectInput> task) {
        final SaveSoapProjectInput input = task.getInput();
        final SoapProjectDto soapProject = input.getSoapProject();
        final SoapProjectDto savedSoapProject = save(soapProject);
        final SaveSoapProjectOutput output = new SaveSoapProjectOutput();
        output.setSavedSoapProject(savedSoapProject);
        return createResult(output);
    }
}
