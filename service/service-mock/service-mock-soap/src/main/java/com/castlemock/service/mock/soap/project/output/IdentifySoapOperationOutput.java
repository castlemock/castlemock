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

package com.castlemock.service.mock.soap.project.output;

import com.castlemock.model.core.Output;
import com.castlemock.model.mock.soap.domain.SoapOperation;

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class IdentifySoapOperationOutput implements Output{

    private final String projectId;

    private final String portId;

    private final String operationId;

    private final SoapOperation operation;

    private IdentifySoapOperationOutput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.portId = Objects.requireNonNull(builder.portId, "portId");
        this.operationId = Objects.requireNonNull(builder.operationId, "operationId");
        this.operation = Objects.requireNonNull(builder.operation, "operation");

    }

    public SoapOperation getOperation() {
        return operation;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final IdentifySoapOperationOutput that = (IdentifySoapOperationOutput) o;
        return Objects.equals(projectId, that.projectId) && Objects.equals(portId, that.portId) &&
                Objects.equals(operationId, that.operationId) && Objects.equals(operation, that.operation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, portId, operationId, operation);
    }

    @Override
    public String toString() {
        return "IdentifySoapOperationOutput{" +
                "projectId='" + projectId + '\'' +
                ", portId='" + portId + '\'' +
                ", operationId='" + operationId + '\'' +
                ", operation=" + operation +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String projectId;
        private String portId;
        private String operationId;
        private SoapOperation operation;

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

        public Builder operation(final SoapOperation operation){
            this.operation = operation;
            return this;
        }

        public IdentifySoapOperationOutput build(){
            return new IdentifySoapOperationOutput(this);
        }
    }
}
