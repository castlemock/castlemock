/*
 * Copyright 2024 Karl Dahlgren
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

package com.castlemock.web.mock.soap.model;

import com.castlemock.model.mock.soap.domain.SoapOperationStatus;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = UpdateSoapOperationStatusesRequest.Builder.class)
public class UpdateSoapOperationStatusesRequest {

    private final Set<String> operationIds;
    private final SoapOperationStatus status;

    private UpdateSoapOperationStatusesRequest(final Builder builder) {
        this.operationIds = Objects.requireNonNull(builder.operationIds, "operationIds");
        this.status = Objects.requireNonNull(builder.status, "status");
    }

    public Set<String> getOperationIds() {
        return Optional.of(operationIds)
                .map(Set::copyOf)
                .orElseGet(Set::of);
    }

    public SoapOperationStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UpdateSoapOperationStatusesRequest that = (UpdateSoapOperationStatusesRequest) o;
        return Objects.equals(operationIds, that.operationIds) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationIds, status);
    }

    @Override
    public String toString() {
        return "UpdatePortStatusesRequest{" +
                "operationIds=" + operationIds +
                ", status=" + status +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {

        private Set<String> operationIds;
        private SoapOperationStatus status;

        private Builder() {
        }

        public Builder operationIds(final Set<String> operationIds) {
            this.operationIds = operationIds;
            return this;
        }

        public Builder status(final SoapOperationStatus status) {
            this.status = status;
            return this;
        }

        public UpdateSoapOperationStatusesRequest build() {
            return new UpdateSoapOperationStatusesRequest(this);
        }
    }
    
}
