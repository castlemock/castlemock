package com.castlemock.web.mock.rest.model;

import com.castlemock.model.mock.rest.domain.RestMockResponseStatus;

import java.util.Objects;
import java.util.Set;

public class UpdateRestMockResponseStatusesRequest {

    private Set<String> mockResponseIds;
    private RestMockResponseStatus status;

    private UpdateRestMockResponseStatusesRequest() {

    }

    private UpdateRestMockResponseStatusesRequest(final Builder builder) {
        this.mockResponseIds = Objects.requireNonNull(builder.mockResponseIds, "mockResponseIds");
        this.status = Objects.requireNonNull(builder.status, "status");
    }

    public Set<String> getMockResponseIds() {
        return mockResponseIds;
    }

    public RestMockResponseStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UpdateRestMockResponseStatusesRequest that = (UpdateRestMockResponseStatusesRequest) o;
        return Objects.equals(mockResponseIds, that.mockResponseIds) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mockResponseIds, status);
    }

    @Override
    public String toString() {
        return "UpdateRestMockResponseStatusesRequest{" +
                "mockResponseIds=" + mockResponseIds +
                ", status=" + status +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Set<String> mockResponseIds;
        private RestMockResponseStatus status;

        private Builder() {
        }

        public Builder mockResponseIds(final Set<String> mockResponseIds) {
            this.mockResponseIds = mockResponseIds;
            return this;
        }

        public Builder status(final RestMockResponseStatus status) {
            this.status = status;
            return this;
        }

        public UpdateRestMockResponseStatusesRequest build() {
            return new UpdateRestMockResponseStatusesRequest(this);
        }
    }
    
}
