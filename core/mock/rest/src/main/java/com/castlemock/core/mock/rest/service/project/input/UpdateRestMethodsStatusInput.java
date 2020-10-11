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
import com.castlemock.core.mock.rest.model.project.domain.RestMethodStatus;

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class UpdateRestMethodsStatusInput implements Input {

    @NotNull
    private final String projectId;
    @NotNull
    private final String applicationId;
    @NotNull
    private final String resourceId;
    @NotNull
    private final String methodId;
    @NotNull
    private final RestMethodStatus methodStatus;

    private UpdateRestMethodsStatusInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId);
        this.applicationId = Objects.requireNonNull(builder.applicationId);
        this.resourceId = Objects.requireNonNull(builder.resourceId);
        this.methodId = Objects.requireNonNull(builder.methodId);
        this.methodStatus = Objects.requireNonNull(builder.methodStatus);
    }

    public String getProjectId() {
        return projectId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getMethodId() {
        return methodId;
    }

    public RestMethodStatus getMethodStatus() {
        return methodStatus;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private String projectId;
        private String applicationId;
        private String resourceId;
        private String methodId;
        private RestMethodStatus methodStatus;

        public Builder projectId(final String projectId){
            this.projectId = projectId;
            return this;
        }

        public Builder applicationId(final String applicationId){
            this.applicationId = applicationId;
            return this;
        }

        public Builder resourceId(final String resourceId){
            this.resourceId = resourceId;
            return this;
        }

        public Builder methodId(final String methodId){
            this.methodId = methodId;
            return this;
        }

        public Builder methodStatus(final RestMethodStatus methodStatus){
            this.methodStatus = methodStatus;
            return this;
        }

        public UpdateRestMethodsStatusInput build(){
            return new UpdateRestMethodsStatusInput(this);
        }

    }


}
