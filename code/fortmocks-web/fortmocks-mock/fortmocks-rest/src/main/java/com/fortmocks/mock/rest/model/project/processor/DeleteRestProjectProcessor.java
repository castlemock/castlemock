package com.fortmocks.mock.rest.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.project.message.DeleteRestProjectInput;
import com.fortmocks.mock.rest.model.project.message.DeleteRestProjectOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class DeleteRestProjectProcessor extends AbstractRestProjectProcessor implements Processor<DeleteRestProjectInput, DeleteRestProjectOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<DeleteRestProjectOutput> process(final Task<DeleteRestProjectInput> task) {
        final DeleteRestProjectInput input = task.getInput();
        final Long restProjectId = input.getRestProjectId();
        delete(restProjectId);
        return createResult(new DeleteRestProjectOutput());
    }
}
