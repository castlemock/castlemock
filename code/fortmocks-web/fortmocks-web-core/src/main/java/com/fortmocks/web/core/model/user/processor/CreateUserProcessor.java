package com.fortmocks.web.core.model.user.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.core.model.user.dto.UserDto;
import com.fortmocks.core.model.user.processor.message.input.CreateUserInput;
import com.fortmocks.core.model.user.processor.messge.output.CreateUserOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class CreateUserProcessor extends AbstractUserProcessor implements Processor<CreateUserInput, CreateUserOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<CreateUserOutput> process(final Task<CreateUserInput> task) {
        final CreateUserInput input = task.getInput();
        final UserDto user = input.getUser();
        final UserDto savedUser = save(user);
        final CreateUserOutput output = new CreateUserOutput();
        output.setSavedUser(savedUser);
        return createResult(output);
    }
}
