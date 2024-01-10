package com.castlemock.web.mock.rest.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import java.util.Set;

@XmlRootElement
@JsonDeserialize(builder = DuplicateRestMockOperationsRequest.Builder.class)
public class DuplicateRestMockOperationsRequest {

    private final Set<String> mockResponseIds;

    private DuplicateRestMockOperationsRequest(final Builder builder) {
        this.mockResponseIds = Objects.requireNonNull(builder.mockResponseIds, "mockResponseIds");
    }

    public Set<String> getMockResponseIds() {
        return mockResponseIds;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DuplicateRestMockOperationsRequest that = (DuplicateRestMockOperationsRequest) o;
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

    @JsonPOJOBuilder(withPrefix = "")
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
