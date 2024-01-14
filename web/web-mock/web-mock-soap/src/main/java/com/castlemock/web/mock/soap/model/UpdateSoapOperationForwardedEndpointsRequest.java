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
@JsonDeserialize(builder = UpdateSoapOperationForwardedEndpointsRequest.Builder.class)
public class UpdateSoapOperationForwardedEndpointsRequest {

    private final Set<String> operationIds;
    private final String forwardedEndpoint;

    private UpdateSoapOperationForwardedEndpointsRequest(final Builder builder) {
        this.operationIds = Objects.requireNonNull(builder.operationIds, "operationIds");
        this.forwardedEndpoint = Objects.requireNonNull(builder.forwardedEndpoint, "forwardedEndpoint");
    }

    public Set<String> getOperationIds() {
        return Optional.of(operationIds)
                .map(Set::copyOf)
                .orElseGet(Set::of);
    }

    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UpdateSoapOperationForwardedEndpointsRequest that = (UpdateSoapOperationForwardedEndpointsRequest) o;
        return Objects.equals(operationIds, that.operationIds) &&
                Objects.equals(forwardedEndpoint, that.forwardedEndpoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationIds, forwardedEndpoint);
    }

    @Override
    public String toString() {
        return "UpdateOperationStatusesRequest{" +
                "operationIds=" + operationIds +
                ", forwardedEndpoint=" + forwardedEndpoint +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {

        private Set<String> operationIds;
        private String forwardedEndpoint;

        private Builder() {
        }

        public Builder operationIds(final Set<String> operationIds) {
            this.operationIds = operationIds;
            return this;
        }

        public Builder forwardedEndpoint(final String forwardedEndpoint) {
            this.forwardedEndpoint = forwardedEndpoint;
            return this;
        }

        public UpdateSoapOperationForwardedEndpointsRequest build() {
            return new UpdateSoapOperationForwardedEndpointsRequest(this);
        }
    }

}
