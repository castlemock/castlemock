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

package com.castlemock.core.mock.rest.service.project.input;

import com.castlemock.core.basis.model.Input;
import com.castlemock.core.basis.model.validation.NotNull;
import com.castlemock.core.mock.rest.model.project.domain.RestApplication;

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class CreateRestApplicationInput implements Input {

    @NotNull
    private final String projectId;
    @NotNull
    private final RestApplication application;

    private CreateRestApplicationInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId);
        this.application = Objects.requireNonNull(builder.application);
    }

    public String getProjectId() {
        return projectId;
    }

    public RestApplication getApplication() {
        return application;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {
        private String projectId;
        private RestApplication application;


        public Builder projectId(final String restProjectId){
            this.projectId = restProjectId;
            return this;
        }

        public Builder application(final RestApplication restApplication){
            this.application = restApplication;
            return this;
        }

        public CreateRestApplicationInput build(){
            return new CreateRestApplicationInput(this);
        }

    }

}
