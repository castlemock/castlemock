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

import com.castlemock.model.mock.rest.domain.RestMethodStatus;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@XmlRootElement
@JsonDeserialize(builder = UpdateRestResourceStatusesRequest.Builder.class)
public class UpdateRestResourceStatusesRequest {

    private final Set<String> resourceIds;
    private final RestMethodStatus status;

    private UpdateRestResourceStatusesRequest(final Builder builder) {
        this.resourceIds = Objects.requireNonNull(builder.resourceIds, "resourceIds");
        this.status = Objects.requireNonNull(builder.status, "status");
    }

    public Set<String> getResourceIds() {
        return Optional.of(resourceIds)
                .map(Set::copyOf)
                .orElseGet(Set::of);
    }

    public RestMethodStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UpdateRestResourceStatusesRequest that = (UpdateRestResourceStatusesRequest) o;
        return Objects.equals(resourceIds, that.resourceIds) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceIds, status);
    }

    @Override
    public String toString() {
        return "UpdatePortStatusesRequest{" +
                "resourceIds=" + resourceIds +
                ", status=" + status +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {

        private Set<String> resourceIds;
        private RestMethodStatus status;

        private Builder() {
        }

        public Builder resourceIds(Set<String> resourceIds) {
            this.resourceIds = resourceIds;
            return this;
        }

        public Builder status(RestMethodStatus status) {
            this.status = status;
            return this;
        }

        public UpdateRestResourceStatusesRequest build() {
            return new UpdateRestResourceStatusesRequest(this);
        }
    }
    
}
