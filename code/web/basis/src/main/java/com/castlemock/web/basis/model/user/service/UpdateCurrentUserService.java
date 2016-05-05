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

package com.castlemock.web.basis.model.user.service;

import com.castlemock.core.basis.model.Service;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.basis.model.user.dto.UserDto;
import com.castlemock.core.basis.model.user.service.message.input.UpdateCurrentUserInput;
import com.castlemock.core.basis.model.user.service.message.output.UpdateCurrentUserOutput;
import com.google.common.base.Preconditions;

import java.util.Date;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class UpdateCurrentUserService extends AbstractUserService implements Service<UpdateCurrentUserInput, UpdateCurrentUserOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<UpdateCurrentUserOutput> process(final ServiceTask<UpdateCurrentUserInput> serviceTask) {
        final UpdateCurrentUserInput input = serviceTask.getInput();
        final UserDto user = input.getUser();
        final String loggedInUsername = serviceTask.getServiceConsumer();

        if(!user.getUsername().equalsIgnoreCase(loggedInUsername)){
            final UserDto existingUser = findByUsername(user.getUsername());
            Preconditions.checkArgument(existingUser == null, "Invalid username. Username is already used");
        }


        final UserDto loggedInUser = findByUsername(loggedInUsername);
        loggedInUser.setUsername(user.getUsername());
        loggedInUser.setEmail(user.getEmail());
        loggedInUser.setPassword(user.getPassword());
        loggedInUser.setUpdated(new Date());


        update(loggedInUser.getId(), loggedInUser);
        final UpdateCurrentUserOutput output = new UpdateCurrentUserOutput(loggedInUser);
        return createServiceResult(output);
    }
}
