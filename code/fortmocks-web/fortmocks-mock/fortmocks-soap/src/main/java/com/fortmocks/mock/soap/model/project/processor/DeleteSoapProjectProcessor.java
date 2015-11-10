package com.fortmocks.mock.soap.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.core.model.user.message.DeleteUserInput;
import com.fortmocks.core.model.user.message.DeleteUserOutput;
import com.fortmocks.mock.soap.model.project.message.DeleteSoapProjectInput;
import com.fortmocks.mock.soap.model.project.message.DeleteSoapProjectOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class DeleteSoapProjectProcessor extends AbstractSoapProjectProcessor implements Processor<DeleteSoapProjectInput, DeleteSoapProjectOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<DeleteSoapProjectOutput> process(final Task<DeleteSoapProjectInput> task) {
        final DeleteSoapProjectInput input = task.getInput();
        final Long soapProjectId = input.getSoapProjectId();
        delete(soapProjectId);
        return createResult(new DeleteSoapProjectOutput());
    }
}
