package com.fortmocks.web.core.model.user.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.core.model.user.processor.message.input.DeleteUserInput;
import com.fortmocks.core.model.user.processor.messge.output.DeleteUserOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class DeleteUserProcessor extends AbstractUserProcessor implements Processor<DeleteUserInput, DeleteUserOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<DeleteUserOutput> process(final Task<DeleteUserInput> task) {
        final DeleteUserInput input = task.getInput();
        final Long userId = input.getUserId();
        delete(userId);
        return createResult(new DeleteUserOutput());
    }
}
