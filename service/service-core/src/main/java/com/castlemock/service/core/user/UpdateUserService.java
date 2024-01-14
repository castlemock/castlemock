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
import com.castlemock.service.core.user.input.UpdateUserInput;
import com.castlemock.service.core.user.output.UpdateUserOutput;

import java.util.Date;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class UpdateUserService extends AbstractUserService implements Service<UpdateUserInput, UpdateUserOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<UpdateUserOutput> process(final ServiceTask<UpdateUserInput> serviceTask) {
        final UpdateUserInput input = serviceTask.getInput();
        final Optional<User> updatedUser = find(input.getId())
                .flatMap(user -> getAndUpdateUser(input, user));

        return createServiceResult(UpdateUserOutput.builder()
                .updatedUser(updatedUser.orElse(null))
                .build());
    }

    private Optional<User> getAndUpdateUser(final UpdateUserInput input, final User user) {
        final User.Builder builder = user.toBuilder()
                .username(input.getUsername())
                .fullName(input.getFullName().orElse(null))
                .email(input.getEmail().orElse(null))
                .role(input.getRole())
                .status(input.getStatus())
                .updated(new Date());

        input.getPassword()
                .ifPresent(password -> builder.password(input.getPassword().orElse(null)));

        return update(user.getId(), builder.build());
    }
}
