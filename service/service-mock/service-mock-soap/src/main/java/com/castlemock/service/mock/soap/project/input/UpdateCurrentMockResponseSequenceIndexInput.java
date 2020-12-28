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

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class UpdateCurrentMockResponseSequenceIndexInput implements Input {

    @NotNull
    private final String projectId;
    @NotNull
    private final String portId;
    @NotNull
    private final String operationId;
    @NotNull
    private final Integer currentResponseSequenceIndex;

    public UpdateCurrentMockResponseSequenceIndexInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId);
        this.portId = Objects.requireNonNull(builder.portId);
        this.operationId = Objects.requireNonNull(builder.operationId);
        this.currentResponseSequenceIndex = Objects.requireNonNull(builder.currentResponseSequenceIndex);
    }

    public String getOperationId() {
        return operationId;
    }

    public Integer getCurrentResponseSequenceIndex() {
        return currentResponseSequenceIndex;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getPortId() {
        return portId;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String projectId;
        private String portId;
        private String operationId;
        private Integer currentResponseSequenceIndex;

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

        public Builder currentResponseSequenceIndex(final Integer currentResponseSequenceIndex){
            this.currentResponseSequenceIndex = currentResponseSequenceIndex;
            return this;
        }

        public UpdateCurrentMockResponseSequenceIndexInput build(){
            return new UpdateCurrentMockResponseSequenceIndexInput(this);
        }
    }
}
