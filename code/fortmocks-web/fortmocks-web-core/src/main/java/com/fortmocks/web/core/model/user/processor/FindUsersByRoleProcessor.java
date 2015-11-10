package com.fortmocks.web.core.model.user.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.core.model.user.Role;
import com.fortmocks.core.model.user.dto.UserDto;
import com.fortmocks.core.model.user.message.FindUsersByRoleInput;
import com.fortmocks.core.model.user.message.FindUsersByRoleOutput;
import com.fortmocks.core.model.user.message.UpdateUserInput;
import com.fortmocks.core.model.user.message.UpdateUserOutput;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class FindUsersByRoleProcessor extends AbstractUserProcessor implements Processor<FindUsersByRoleInput, FindUsersByRoleOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<FindUsersByRoleOutput> process(final Task<FindUsersByRoleInput> task) {
        final FindUsersByRoleInput input = task.getInput();
        final Role role = input.getRole();
        final List<UserDto> users = findByRole(role);
        final FindUsersByRoleOutput output = new FindUsersByRoleOutput();
        output.setUsers(users);
        return createResult(output);
    }
}
