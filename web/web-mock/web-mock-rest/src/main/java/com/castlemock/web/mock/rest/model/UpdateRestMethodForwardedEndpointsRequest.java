package com.castlemock.web.mock.rest.model;

import java.util.Objects;
import java.util.Set;

public class UpdateRestMethodForwardedEndpointsRequest {

    private Set<String> methodIds;
    private String forwardedEndpoint;

    private UpdateRestMethodForwardedEndpointsRequest() {

    }

    private UpdateRestMethodForwardedEndpointsRequest(final Builder builder) {
        this.methodIds = Objects.requireNonNull(builder.methodIds, "methodIds");
        this.forwardedEndpoint = Objects.requireNonNull(builder.forwardedEndpoint, "forwardedEndpoint");
    }

    public Set<String> getMethodIds() {
        return methodIds;
    }

    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UpdateRestMethodForwardedEndpointsRequest that = (UpdateRestMethodForwardedEndpointsRequest) o;
        return Objects.equals(methodIds, that.methodIds) &&
                Objects.equals(forwardedEndpoint, that.forwardedEndpoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(methodIds, forwardedEndpoint);
    }

    @Override
    public String toString() {
        return "UpdateOperationStatusesRequest{" +
                "methodIds=" + methodIds +
                ", forwardedEndpoint=" + forwardedEndpoint +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Set<String> methodIds;
        private String forwardedEndpoint;

        private Builder() {
        }

        public Builder resourceIds(final Set<String> methodIds) {
            this.methodIds = methodIds;
            return this;
        }

        public Builder forwardedEndpoint(final String forwardedEndpoint) {
            this.forwardedEndpoint = forwardedEndpoint;
            return this;
        }

        public UpdateRestMethodForwardedEndpointsRequest build() {
            return new UpdateRestMethodForwardedEndpointsRequest(this);
        }
    }

}
