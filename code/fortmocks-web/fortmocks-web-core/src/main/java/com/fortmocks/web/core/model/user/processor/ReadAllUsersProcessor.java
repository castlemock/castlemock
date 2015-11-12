package com.fortmocks.web.core.model.user.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.core.model.user.dto.UserDto;
import com.fortmocks.core.model.user.processor.message.input.ReadAllUsersInput;
import com.fortmocks.core.model.user.processor.messge.output.ReadAllUsersOutput;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class ReadAllUsersProcessor extends AbstractUserProcessor implements Processor<ReadAllUsersInput, ReadAllUsersOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<ReadAllUsersOutput> process(final Task<ReadAllUsersInput> task) {
        final List<UserDto> users = findAll();
        final ReadAllUsersOutput output = new ReadAllUsersOutput();
        output.setUsers(users);
        return createResult(output);
    }
}
