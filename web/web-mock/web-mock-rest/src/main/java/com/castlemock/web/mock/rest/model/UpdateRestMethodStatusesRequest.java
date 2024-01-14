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
@JsonDeserialize(builder = UpdateRestMethodStatusesRequest.Builder.class)
public class UpdateRestMethodStatusesRequest {

    private final Set<String> methodIds;
    private final RestMethodStatus status;

    private UpdateRestMethodStatusesRequest(final Builder builder) {
        this.methodIds = Objects.requireNonNull(builder.methodIds, "methodIds");
        this.status = Objects.requireNonNull(builder.status, "status");
    }

    public Set<String> getMethodIds() {
        return Optional.of(methodIds)
                .map(Set::copyOf)
                .orElseGet(Set::of);
    }

    public RestMethodStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateRestMethodStatusesRequest that = (UpdateRestMethodStatusesRequest) o;
        return Objects.equals(methodIds, that.methodIds) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(methodIds, status);
    }

    @Override
    public String toString() {
        return "UpdatePortStatusesRequest{" +
                "methodIds=" + methodIds +
                ", status=" + status +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {

        private Set<String> methodIds;
        private RestMethodStatus status;

        private Builder() {
        }

        public Builder methodIds(final Set<String> methodIds) {
            this.methodIds = methodIds;
            return this;
        }

        public Builder status(final RestMethodStatus status) {
            this.status = status;
            return this;
        }

        public UpdateRestMethodStatusesRequest build() {
            return new UpdateRestMethodStatusesRequest(this);
        }
    }
    
}
