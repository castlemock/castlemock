package com.fortmocks.web.core.model.user.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.core.model.user.dto.UserDto;
import com.fortmocks.core.model.user.message.FindAllUsersInput;
import com.fortmocks.core.model.user.message.FindAllUsersOutput;
import com.fortmocks.core.model.user.message.FindUserInput;
import com.fortmocks.core.model.user.message.FindUserOutput;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class FindAllUsersProcessor extends AbstractUserProcessor implements Processor<FindAllUsersInput, FindAllUsersOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<FindAllUsersOutput> process(final Task<FindAllUsersInput> task) {
        final List<UserDto> users = findAll();
        final FindAllUsersOutput output = new FindAllUsersOutput();
        output.setUsers(users);
        return createResult(output);
    }
}
