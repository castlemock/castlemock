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

package com.castlemock.service.core.user;

import com.castlemock.model.core.Service;
import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.core.user.Status;
import com.castlemock.model.core.user.User;
import com.castlemock.model.core.utility.IdUtility;
import com.castlemock.service.core.user.input.CreateUserInput;
import com.castlemock.service.core.user.output.CreateUserOutput;

import java.util.Date;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class CreateUserService extends AbstractUserService implements Service<CreateUserInput, CreateUserOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<CreateUserOutput> process(final ServiceTask<CreateUserInput> serviceTask) {
        final CreateUserInput input = serviceTask.getInput();
        final User user = input.getUser();

        final User existingUser = findByUsername(user.getUsername())
                .orElse(null);
        if(existingUser != null){
            throw new IllegalArgumentException("User with the username '" + user.getUsername() + "' already exists.");
        }


        user.setId(IdUtility.generateId());
        user.setCreated(new Date());
        user.setUpdated(new Date());
        user.setStatus(Status.ACTIVE);
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));

        final User savedUser = save(user);
        return createServiceResult(CreateUserOutput.builder()
                .savedUser(savedUser)
                .build());
    }
}
