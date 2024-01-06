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

package com.castlemock.service.mock.rest.project.input;

import com.castlemock.model.core.Input;
import com.castlemock.model.core.validation.NotNull;

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class CreateRestApplicationInput implements Input {

    @NotNull
    private final String projectId;
    @NotNull
    private final String name;

    private CreateRestApplicationInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.name = Objects.requireNonNull(builder.name, "name");
    }

    public String getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {
        private String projectId;
        private String name;


        public Builder projectId(final String restProjectId){
            this.projectId = restProjectId;
            return this;
        }

        public Builder name(final String name){
            this.name = name;
            return this;
        }

        public CreateRestApplicationInput build(){
            return new CreateRestApplicationInput(this);
        }

    }

}
