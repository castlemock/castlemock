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

package com.castlemock.web.mock.rest.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@XmlRootElement
@JsonDeserialize(builder = UpdateRestMethodForwardedEndpointsRequest.Builder.class)
public class UpdateRestMethodForwardedEndpointsRequest {

    private final Set<String> methodIds;
    private final String forwardedEndpoint;

    private UpdateRestMethodForwardedEndpointsRequest(final Builder builder) {
        this.methodIds = Objects.requireNonNull(builder.methodIds, "methodIds");
        this.forwardedEndpoint = Objects.requireNonNull(builder.forwardedEndpoint, "forwardedEndpoint");
    }

    public Set<String> getMethodIds() {
        return Optional.of(methodIds)
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
        final UpdateRestMethodForwardedEndpointsRequest that = (UpdateRestMethodForwardedEndpointsRequest) o;
        return Objects.equals(methodIds, that.methodIds) &&
                Objects.equals(forwardedEndpoint, that.forwardedEndpoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(methodIds, forwardedEndpoint);
    }

    @Override
    public String toString() {
        return "UpdateOperationStatusesRequest{" +
                "methodIds=" + methodIds +
                ", forwardedEndpoint=" + forwardedEndpoint +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {

        private Set<String> methodIds;
        private String forwardedEndpoint;

        private Builder() {
        }

        public Builder methodIds(final Set<String> methodIds) {
            this.methodIds = methodIds;
            return this;
        }

        public Builder forwardedEndpoint(final String forwardedEndpoint) {
            this.forwardedEndpoint = forwardedEndpoint;
            return this;
        }

        public UpdateRestMethodForwardedEndpointsRequest build() {
            return new UpdateRestMethodForwardedEndpointsRequest(this);
        }
    }

}
