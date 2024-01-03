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
import com.castlemock.model.core.user.User;
import com.castlemock.service.core.user.input.UpdateCurrentUserInput;
import com.castlemock.service.core.user.output.UpdateCurrentUserOutput;
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
        final String loggedInUsername = serviceTask.getServiceConsumer();

        if(!input.getUsername().equalsIgnoreCase(loggedInUsername)){
            final User existingUser = findByUsername(input.getUsername()).orElse(null);
            Preconditions.checkArgument(existingUser == null, "Invalid username. Username is already used");
        }


        final User.Builder builder =
                findByUsername(loggedInUsername)
                        .orElseThrow()
                        .toBuilder()
                        .username(input.getUsername())
                        .email(input.getEmail().orElse(null))
                        .fullName(input.getFullName().orElse(null))
                        .updated(new Date());

        input.getPassword()
            .map(PASSWORD_ENCODER::encode)
            .ifPresent(builder::password);

        final User user = builder.build();

        update(user.getId(), user);
        return createServiceResult(UpdateCurrentUserOutput.builder()
                .updatedUser(user)
                .build());
    }
}
