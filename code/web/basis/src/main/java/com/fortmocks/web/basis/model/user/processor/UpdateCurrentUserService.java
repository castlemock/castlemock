/*
 * Copyright 2015 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fortmocks.web.basis.model.user.processor;

import com.fortmocks.core.basis.model.Service;
import com.fortmocks.core.basis.model.Result;
import com.fortmocks.core.basis.model.Task;
import com.fortmocks.core.basis.model.user.dto.UserDto;
import com.fortmocks.core.basis.model.user.service.message.input.UpdateCurrentUserInput;
import com.fortmocks.core.basis.model.user.service.message.output.UpdateCurrentUserOutput;
import com.google.common.base.Preconditions;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class UpdateCurrentUserService extends AbstractUserProcessor implements Service<UpdateCurrentUserInput, UpdateCurrentUserOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the service
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
