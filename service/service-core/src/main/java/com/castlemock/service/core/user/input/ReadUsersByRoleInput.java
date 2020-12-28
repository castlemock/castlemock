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

package com.castlemock.service.core.user.input;

import com.castlemock.model.core.model.Input;
import com.castlemock.model.core.model.user.domain.Role;
import com.castlemock.model.core.model.validation.NotNull;
import com.castlemock.service.core.user.output.ReadUsersByRoleOutput;

/**
 * Read users with a specific role
 * @author Karl Dahlgren
 * @since 1.0
 * @see ReadUsersByRoleOutput
 */
public final class ReadUsersByRoleInput implements Input {

    @NotNull
    private final Role role;

    public ReadUsersByRoleInput(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

}
