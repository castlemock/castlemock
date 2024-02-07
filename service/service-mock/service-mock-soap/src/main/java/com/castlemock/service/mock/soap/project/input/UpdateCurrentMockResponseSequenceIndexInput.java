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

import com.castlemock.model.core.Input;

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class UpdateCurrentMockResponseSequenceIndexInput implements Input {

    private final String projectId;
    private final String portId;
    private final String operationId;
    private final Integer currentResponseSequenceIndex;

    public UpdateCurrentMockResponseSequenceIndexInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.portId = Objects.requireNonNull(builder.portId, "portId");
        this.operationId = Objects.requireNonNull(builder.operationId, "operationId");
        this.currentResponseSequenceIndex = Objects.requireNonNull(builder.currentResponseSequenceIndex, "currentResponseSequenceIndex");
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UpdateCurrentMockResponseSequenceIndexInput that = (UpdateCurrentMockResponseSequenceIndexInput) o;
        return Objects.equals(projectId, that.projectId) && Objects.equals(portId, that.portId) &&
                Objects.equals(operationId, that.operationId) &&
                Objects.equals(currentResponseSequenceIndex, that.currentResponseSequenceIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, portId, operationId, currentResponseSequenceIndex);
    }

    @Override
    public String toString() {
        return "UpdateCurrentMockResponseSequenceIndexInput{" +
                "projectId='" + projectId + '\'' +
                ", portId='" + portId + '\'' +
                ", operationId='" + operationId + '\'' +
                ", currentResponseSequenceIndex=" + currentResponseSequenceIndex +
                '}';
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
