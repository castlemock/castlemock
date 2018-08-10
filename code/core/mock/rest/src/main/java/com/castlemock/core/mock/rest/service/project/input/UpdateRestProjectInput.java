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
import com.castlemock.core.mock.rest.model.project.domain.RestProject;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class UpdateRestProjectInput implements Input {

    @NotNull
    private final String restProjectId;
    @NotNull
    private final RestProject restProject;

    private UpdateRestProjectInput(String restProjectId, RestProject restProject) {
        this.restProjectId = restProjectId;
        this.restProject = restProject;
    }

    public String getRestProjectId() {
        return restProjectId;
    }

    public RestProject getRestProject() {
        return restProject;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private String restProjectId;
        private RestProject restProject;

        public Builder restProjectId(final String restProjectId){
            this.restProjectId = restProjectId;
            return this;
        }

        public Builder restProject(final RestProject restProject){
            this.restProject = restProject;
            return this;
        }

        public UpdateRestProjectInput build(){
            return new UpdateRestProjectInput(this.restProjectId, this.restProject);
        }

    }


}
