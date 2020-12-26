package com.castlemock.web.mock.soap.model;

import java.util.Objects;
import java.util.Set;

public class UpdateSoapOperationForwardedEndpointsRequest {

    private Set<String> operationIds;
    private String forwardedEndpoint;

    private UpdateSoapOperationForwardedEndpointsRequest() {

    }

    private UpdateSoapOperationForwardedEndpointsRequest(final Builder builder) {
        this.operationIds = Objects.requireNonNull(builder.operationIds);
        this.forwardedEndpoint = Objects.requireNonNull(builder.forwardedEndpoint);
    }

    public Set<String> getOperationIds() {
        return operationIds;
    }

    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateSoapOperationForwardedEndpointsRequest that = (UpdateSoapOperationForwardedEndpointsRequest) o;
        return Objects.equals(operationIds, that.operationIds) &&
                Objects.equals(forwardedEndpoint, that.forwardedEndpoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationIds, forwardedEndpoint);
    }

    @Override
    public String toString() {
        return "UpdateOperationStatusesRequest{" +
                "operationIds=" + operationIds +
                ", forwardedEndpoint=" + forwardedEndpoint +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Set<String> operationIds;
        private String forwardedEndpoint;

        private Builder() {
        }

        public Builder operationIds(final Set<String> operationIds) {
            this.operationIds = operationIds;
            return this;
        }

        public Builder forwardedEndpoint(final String forwardedEndpoint) {
            this.forwardedEndpoint = forwardedEndpoint;
            return this;
        }

        public UpdateSoapOperationForwardedEndpointsRequest build() {
            return new UpdateSoapOperationForwardedEndpointsRequest(this);
        }
    }

}
