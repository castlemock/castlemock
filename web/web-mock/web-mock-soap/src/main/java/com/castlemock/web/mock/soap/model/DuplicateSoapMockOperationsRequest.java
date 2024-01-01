package com.castlemock.web.mock.soap.model;

import java.util.Objects;
import java.util.Set;

public class DuplicateSoapMockOperationsRequest {

    private Set<String> mockResponseIds;

    private DuplicateSoapMockOperationsRequest() {

    }

    private DuplicateSoapMockOperationsRequest(final Builder builder) {
        this.mockResponseIds = Objects.requireNonNull(builder.mockResponseIds, "mockResponseIds");
    }

    public Set<String> getMockResponseIds() {
        return mockResponseIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DuplicateSoapMockOperationsRequest that = (DuplicateSoapMockOperationsRequest) o;
        return Objects.equals(mockResponseIds, that.mockResponseIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mockResponseIds);
    }

    @Override
    public String toString() {
        return "DuplicateSoapMockOperationsRequest{" +
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

        public DuplicateSoapMockOperationsRequest build() {
            return new DuplicateSoapMockOperationsRequest(this);
        }
    }

}
