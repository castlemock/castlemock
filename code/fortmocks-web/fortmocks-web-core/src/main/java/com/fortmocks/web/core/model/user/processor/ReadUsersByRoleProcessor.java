package com.fortmocks.web.core.model.user.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.core.model.user.domain.Role;
import com.fortmocks.core.model.user.dto.UserDto;
import com.fortmocks.core.model.user.processor.message.input.ReadUsersByRoleInput;
import com.fortmocks.core.model.user.processor.messge.output.ReadUsersByRoleOutput;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class ReadUsersByRoleProcessor extends AbstractUserProcessor implements Processor<ReadUsersByRoleInput, ReadUsersByRoleOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<ReadUsersByRoleOutput> process(final Task<ReadUsersByRoleInput> task) {
        final ReadUsersByRoleInput input = task.getInput();
        final Role role = input.getRole();
        final List<UserDto> users = findByRole(role);
        final ReadUsersByRoleOutput output = new ReadUsersByRoleOutput();
        output.setUsers(users);
        return createResult(output);
    }
}
