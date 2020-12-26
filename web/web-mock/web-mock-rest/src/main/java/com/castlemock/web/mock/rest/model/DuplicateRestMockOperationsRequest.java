package com.castlemock.web.mock.rest.model;

import java.util.Objects;
import java.util.Set;

public class DuplicateRestMockOperationsRequest {

    private Set<String> mockResponseIds;

    private DuplicateRestMockOperationsRequest() {

    }

    private DuplicateRestMockOperationsRequest(final Builder builder) {
        this.mockResponseIds = Objects.requireNonNull(builder.mockResponseIds);
    }

    public Set<String> getMockResponseIds() {
        return mockResponseIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DuplicateRestMockOperationsRequest that = (DuplicateRestMockOperationsRequest) o;
        return Objects.equals(mockResponseIds, that.mockResponseIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mockResponseIds);
    }

    @Override
    public String toString() {
        return "DuplicateRestMockOperationsRequest{" +
                "mockResponseIds=" + mockResponseIds +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Set<String> mockResponseIds;

        private Builder() {
        }

        public Builder mockResponseIds(final Set<String> mockResponseIds) {
            this.mockResponseIds = mockResponseIds;
            return this;
        }

        public DuplicateRestMockOperationsRequest build() {
            return new DuplicateRestMockOperationsRequest(this);
        }
    }

}
