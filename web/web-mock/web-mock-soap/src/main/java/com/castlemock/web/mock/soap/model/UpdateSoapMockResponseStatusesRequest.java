package com.castlemock.web.mock.soap.model;

import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponseStatus;

import java.util.Objects;
import java.util.Set;

public class UpdateSoapMockResponseStatusesRequest {

    private Set<String> mockResponseIds;
    private SoapMockResponseStatus status;

    private UpdateSoapMockResponseStatusesRequest() {

    }

    private UpdateSoapMockResponseStatusesRequest(final Builder builder) {
        this.mockResponseIds = Objects.requireNonNull(builder.mockResponseIds);
        this.status = Objects.requireNonNull(builder.status);
    }

    public Set<String> getMockResponseIds() {
        return mockResponseIds;
    }

    public SoapMockResponseStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateSoapMockResponseStatusesRequest that = (UpdateSoapMockResponseStatusesRequest) o;
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
