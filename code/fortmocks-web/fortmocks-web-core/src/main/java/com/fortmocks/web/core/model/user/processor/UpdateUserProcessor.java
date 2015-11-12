package com.fortmocks.web.core.model.user.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.core.model.user.dto.UserDto;
import com.fortmocks.core.model.user.processor.message.input.UpdateUserInput;
import com.fortmocks.core.model.user.processor.messge.output.UpdateUserOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class UpdateUserProcessor extends AbstractUserProcessor implements Processor<UpdateUserInput, UpdateUserOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<UpdateUserOutput> process(final Task<UpdateUserInput> task) {
        final UpdateUserInput input = task.getInput();
        final Long userId = input.getUserId();
        final UserDto user = input.getUser();
        update(userId, user);
        return createResult(new UpdateUserOutput());
    }
}
