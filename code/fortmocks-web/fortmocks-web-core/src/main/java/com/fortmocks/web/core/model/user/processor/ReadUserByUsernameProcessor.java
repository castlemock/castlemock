package com.fortmocks.web.core.model.user.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.core.model.user.dto.UserDto;
import com.fortmocks.core.model.user.processor.message.input.ReadUserByUsernameInput;
import com.fortmocks.core.model.user.processor.messge.output.ReadUserByUsernameOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class ReadUserByUsernameProcessor extends AbstractUserProcessor implements Processor<ReadUserByUsernameInput, ReadUserByUsernameOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<ReadUserByUsernameOutput> process(final Task<ReadUserByUsernameInput> task) {
        final ReadUserByUsernameInput input = task.getInput();
        final String username = input.getUsername();
        final UserDto user = findByUsername(username);
        final ReadUserByUsernameOutput output = new ReadUserByUsernameOutput();
        output.setUser(user);
        return createResult(output);
    }
}
