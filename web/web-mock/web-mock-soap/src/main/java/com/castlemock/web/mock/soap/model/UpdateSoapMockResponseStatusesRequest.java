package com.castlemock.web.mock.soap.model;

import com.castlemock.model.mock.soap.domain.SoapMockResponseStatus;

import java.util.Objects;
import java.util.Set;

public class UpdateSoapMockResponseStatusesRequest {

    private Set<String> mockResponseIds;
    private SoapMockResponseStatus status;

    private UpdateSoapMockResponseStatusesRequest() {

    }

    private UpdateSoapMockResponseStatusesRequest(final Builder builder) {
        this.mockResponseIds = Objects.requireNonNull(builder.mockResponseIds, "mockResponseIds");
        this.status = Objects.requireNonNull(builder.status, "status");
    }

    public Set<String> getMockResponseIds() {
        return mockResponseIds;
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
