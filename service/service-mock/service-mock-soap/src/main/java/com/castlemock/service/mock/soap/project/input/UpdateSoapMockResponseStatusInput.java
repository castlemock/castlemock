/*
 * Copyright 2020 Karl Dahlgren
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

package com.castlemock.service.mock.soap.project.input;

import com.castlemock.model.core.Input;
import com.castlemock.model.core.validation.NotNull;
import com.castlemock.model.mock.soap.domain.SoapMockResponseStatus;

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.52
 */
public final class UpdateSoapMockResponseStatusInput implements Input {

    @NotNull
    private final String projectId;
    @NotNull
    private final String portId;
    @NotNull
    private final String operationId;
    @NotNull
    private final String mockResponseId;
    @NotNull
    private final SoapMockResponseStatus mockResponseStatus;

    public UpdateSoapMockResponseStatusInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.portId = Objects.requireNonNull(builder.portId, "portId");
        this.operationId = Objects.requireNonNull(builder.operationId, "operationId");
        this.mockResponseId = Objects.requireNonNull(builder.mockResponseId, "mockResponseId");
        this.mockResponseStatus = Objects.requireNonNull(builder.mockResponseStatus, "mockResponseStatus");
    }

    public String getProjectId() {
        return projectId;
    }

    public String getPortId() {
        return portId;
    }

    public String getOperationId() {
        return operationId;
    }

    public String getMockResponseId() {
        return mockResponseId;
    }

    public SoapMockResponseStatus getMockResponseStatus() {
        return mockResponseStatus;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String projectId;
        private String portId;
        private String operationId;
        private String mockResponseId;
        private SoapMockResponseStatus mockResponseStatus;

        private Builder(){

        }

        public Builder projectId(final String projectId){
            this.projectId = projectId;
            return this;
        }

        public Builder portId(final String portId){
            this.portId = portId;
            return this;
        }

        public Builder operationId(final String operationId){
            this.operationId = operationId;
            return this;
        }

        public Builder mockResponseId(final String mockResponseId){
            this.mockResponseId = mockResponseId;
            return this;
        }

        public Builder mockResponseStatus(final SoapMockResponseStatus mockResponseStatus){
            this.mockResponseStatus = mockResponseStatus;
            return this;
        }

        public UpdateSoapMockResponseStatusInput build(){
            return new UpdateSoapMockResponseStatusInput(this);
        }
    }
}
