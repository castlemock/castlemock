package com.fortmocks.web.core.model.user.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.core.model.user.dto.UserDto;
import com.fortmocks.core.model.user.message.FindUserInput;
import com.fortmocks.core.model.user.message.FindUserOutput;
import com.fortmocks.core.model.user.message.SaveUserInput;
import com.fortmocks.core.model.user.message.SaveUserOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class SaveUserProcessor extends AbstractUserProcessor implements Processor<SaveUserInput, SaveUserOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<SaveUserOutput> process(final Task<SaveUserInput> task) {
        final SaveUserInput input = task.getInput();
        final UserDto user = input.getUser();
        final UserDto savedUser = save(user);
        final SaveUserOutput output = new SaveUserOutput();
        output.setSavedUser(savedUser);
        return createResult(output);
    }
}
