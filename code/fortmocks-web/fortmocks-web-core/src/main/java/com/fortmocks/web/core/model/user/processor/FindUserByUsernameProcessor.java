package com.fortmocks.web.core.model.user.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.core.model.user.User;
import com.fortmocks.core.model.user.dto.UserDto;
import com.fortmocks.core.model.user.message.FindUserByUsernameInput;
import com.fortmocks.core.model.user.message.FindUserByUsernameOutput;
import com.fortmocks.core.model.user.message.UpdateUserInput;
import com.fortmocks.core.model.user.message.UpdateUserOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class FindUserByUsernameProcessor extends AbstractUserProcessor implements Processor<FindUserByUsernameInput, FindUserByUsernameOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<FindUserByUsernameOutput> process(final Task<FindUserByUsernameInput> task) {
        final FindUserByUsernameInput input = task.getInput();
        final String username = input.getUsername();
        final UserDto user = findByUsername(username);
        final FindUserByUsernameOutput output = new FindUserByUsernameOutput();
        output.setUser(user);
        return createResult(output);
    }
}
