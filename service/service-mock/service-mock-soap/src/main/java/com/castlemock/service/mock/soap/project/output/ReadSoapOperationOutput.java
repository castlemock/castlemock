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
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class ReadSoapOperationOutput implements Output{

    private final SoapOperation operation;

    private ReadSoapOperationOutput(final Builder builder) {
        this.operation = builder.operation;
    }

    public Optional<SoapOperation> getOperation() {
        return Optional.ofNullable(operation);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ReadSoapOperationOutput that = (ReadSoapOperationOutput) o;
        return Objects.equals(operation, that.operation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation);
    }

    @Override
    public String toString() {
        return "ReadSoapOperationOutput{" +
                "operation=" + operation +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private SoapOperation operation;

        private Builder(){

        }

        public Builder operation(final SoapOperation operation){
            this.operation = operation;
            return this;
        }

        public ReadSoapOperationOutput build(){
            return new ReadSoapOperationOutput(this);
        }
    }
}
