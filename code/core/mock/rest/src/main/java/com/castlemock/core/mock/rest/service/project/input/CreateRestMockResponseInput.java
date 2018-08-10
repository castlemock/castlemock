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
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponse;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class CreateRestMockResponseInput implements Input {

    @NotNull
    private final String projectId;
    @NotNull
    private final String applicationId;
    @NotNull
    private final String resourceId;
    @NotNull
    private final String methodId;
    @NotNull
    private final RestMockResponse mockResponse;

    private CreateRestMockResponseInput(String projectId,
                                       String applicationId,
                                       String resourceId,
                                       String methodId,
                                       RestMockResponse mockResponse) {
        this.projectId = projectId;
        this.applicationId = applicationId;
        this.resourceId = resourceId;
        this.methodId = methodId;
        this.mockResponse = mockResponse;
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

    public RestMockResponse getMockResponse() {
        return mockResponse;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {
        private String projectId;
        private String applicationId;
        private String resourceId;
        private String methodId;
        private RestMockResponse mockResponse;


        public Builder projectId(final String restProjectId){
            this.projectId = restProjectId;
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

        public Builder mockResponse(final RestMockResponse mockResponse){
            this.mockResponse = mockResponse;
            return this;
        }

        public CreateRestMockResponseInput build(){
            return new CreateRestMockResponseInput(this.projectId, this.applicationId,
                    this.resourceId, this.methodId, this.mockResponse);
        }
    }



}
