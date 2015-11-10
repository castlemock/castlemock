package com.fortmocks.web.core.model.user.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.core.model.user.dto.UserDto;
import com.fortmocks.core.model.user.message.UpdateCurrentUserInput;
import com.fortmocks.core.model.user.message.UpdateCurrentUserOutput;
import com.fortmocks.core.model.user.message.UpdateUserInput;
import com.fortmocks.core.model.user.message.UpdateUserOutput;
import com.google.common.base.Preconditions;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class UpdateCurrentUserProcessor extends AbstractUserProcessor implements Processor<UpdateCurrentUserInput, UpdateCurrentUserOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<UpdateCurrentUserOutput> process(final Task<UpdateCurrentUserInput> task) {
        final UpdateCurrentUserInput input = task.getInput();
        final UserDto user = input.getUser();
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String loggedInUsername = authentication.getName();

        if(!user.getUsername().equalsIgnoreCase(loggedInUsername)){
            final UserDto existingUser = findByUsername(user.getUsername());
            Preconditions.checkArgument(existingUser == null, "Invalid username. Username is already used");
        }


        final UserDto loggedInUser = findByUsername(loggedInUsername);
        loggedInUser.setUsername(user.getUsername());
        loggedInUser.setEmail(user.getEmail());
        loggedInUser.setUpdated(new Date());

        if(user.getPassword() != null && !user.getPassword().isEmpty()){
            user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        }

        final UserDto updatedUser = super.save(user);
        final UpdateCurrentUserOutput output = new UpdateCurrentUserOutput();
        output.setUpdatedUser(updatedUser);
        return createResult(output);
    }
}
