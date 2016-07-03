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

package com.castlemock.core.basis.model.user.service.message.input;

import com.castlemock.core.basis.model.Input;
import com.castlemock.core.basis.model.user.domain.Role;
import com.castlemock.core.basis.model.user.service.message.output.ReadUsersByRoleOutput;
import com.castlemock.core.basis.model.validation.NotNull;

/**
 * Read users with a specific role
 * @author Karl Dahlgren
 * @since 1.0
 * @see ReadUsersByRoleOutput
 */
public class ReadUsersByRoleInput implements Input {

    @NotNull
    private Role role;

    public ReadUsersByRoleInput(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
