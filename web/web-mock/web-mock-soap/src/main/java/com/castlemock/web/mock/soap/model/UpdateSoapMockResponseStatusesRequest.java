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

import com.castlemock.model.mock.soap.domain.SoapMockResponseStatus;
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
@JsonDeserialize(builder = UpdateSoapMockResponseStatusesRequest.Builder.class)
public class UpdateSoapMockResponseStatusesRequest {

    private final Set<String> mockResponseIds;
    private final SoapMockResponseStatus status;

    private UpdateSoapMockResponseStatusesRequest(final Builder builder) {
        this.mockResponseIds = Objects.requireNonNull(builder.mockResponseIds, "mockResponseIds");
        this.status = Objects.requireNonNull(builder.status, "status");
    }

    public Set<String> getMockResponseIds() {
        return Optional.of(mockResponseIds)
                .map(Set::copyOf)
                .orElseGet(Set::of);
    }

    public SoapMockResponseStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UpdateSoapMockResponseStatusesRequest that = (UpdateSoapMockResponseStatusesRequest) o;
        return Objects.equals(mockResponseIds, that.mockResponseIds) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mockResponseIds, status);
    }

    @Override
    public String toString() {
        return "UpdateSoapMockResponseStatusesRequest{" +
                "mockResponseIds=" + mockResponseIds +
                ", status=" + status +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {

        private Set<String> mockResponseIds;
        private SoapMockResponseStatus status;

        private Builder() {
        }

        public Builder mockResponseIds(final Set<String> mockResponseIds) {
            this.mockResponseIds = mockResponseIds;
            return this;
        }

        public Builder status(final SoapMockResponseStatus status) {
            this.status = status;
            return this;
        }

        public UpdateSoapMockResponseStatusesRequest build() {
            return new UpdateSoapMockResponseStatusesRequest(this);
        }
    }
    
}
