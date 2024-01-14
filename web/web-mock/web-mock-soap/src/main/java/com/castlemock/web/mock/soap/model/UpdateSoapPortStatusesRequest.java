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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = UpdateSoapPortStatusesRequest.Builder.class)
public class UpdateSoapPortStatusesRequest {

    private final Set<String> portIds;
    private final SoapOperationStatus status;

    private UpdateSoapPortStatusesRequest(final Builder builder) {
        this.portIds = Objects.requireNonNull(builder.portIds, "portIds");
        this.status = Objects.requireNonNull(builder.status, "status");
    }

    public Set<String> getPortIds() {
        return Optional.of(portIds)
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
        final UpdateSoapPortStatusesRequest that = (UpdateSoapPortStatusesRequest) o;
        return Objects.equals(portIds, that.portIds) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(portIds, status);
    }

    @Override
    public String toString() {
        return "UpdatePortStatusesRequest{" +
                "portIds=" + portIds +
                ", status=" + status +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {

        private Set<String> portIds;
        private SoapOperationStatus status;

        private Builder() {
        }

        public Builder portIds(final Set<String> portIds) {
            this.portIds = portIds;
            return this;
        }

        public Builder status(final SoapOperationStatus status) {
            this.status = status;
            return this;
        }

        public UpdateSoapPortStatusesRequest build() {
            return new UpdateSoapPortStatusesRequest(this);
        }
    }

}
