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

package com.castlemock.service.mock.soap.project.input;

import com.castlemock.model.core.model.Input;
import com.castlemock.model.core.model.validation.NotNull;
import com.castlemock.model.mock.soap.domain.SoapMockResponse;

import java.util.List;
import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class DeleteSoapMockResponsesInput implements Input{

    @NotNull
    private final String projectId;
    @NotNull
    private final String portId;
    @NotNull
    private final String operationId;
    @NotNull
    private final List<SoapMockResponse> mockResponses;

    private DeleteSoapMockResponsesInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId);
        this.portId = Objects.requireNonNull(builder.portId);
        this.operationId = Objects.requireNonNull(builder.operationId);
        this.mockResponses = Objects.requireNonNull(builder.mockResponses);
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

    public List<SoapMockResponse> getMockResponses() {
        return mockResponses;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String projectId;
        private String portId;
        private String operationId;
        private List<SoapMockResponse> mockResponses;

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

        public Builder mockResponses(final List<SoapMockResponse> mockResponses){
            this.mockResponses = mockResponses;
            return this;
        }

        public DeleteSoapMockResponsesInput build(){
            return new DeleteSoapMockResponsesInput(this);
        }
    }
}
