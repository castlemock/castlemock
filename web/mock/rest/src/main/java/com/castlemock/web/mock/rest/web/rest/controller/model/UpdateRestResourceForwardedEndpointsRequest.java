package com.castlemock.web.mock.rest.web.rest.controller.model;

import java.util.Objects;
import java.util.Set;

public class UpdateRestResourceForwardedEndpointsRequest {

    private Set<String> resourceIds;
    private String forwardedEndpoint;

    private UpdateRestResourceForwardedEndpointsRequest() {

    }

    private UpdateRestResourceForwardedEndpointsRequest(final Builder builder) {
        this.resourceIds = Objects.requireNonNull(builder.resourceIds);
        this.forwardedEndpoint = Objects.requireNonNull(builder.forwardedEndpoint);
    }

    public Set<String> getResourceIds() {
        return resourceIds;
    }

    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateRestResourceForwardedEndpointsRequest that = (UpdateRestResourceForwardedEndpointsRequest) o;
        return Objects.equals(resourceIds, that.resourceIds) &&
                Objects.equals(forwardedEndpoint, that.forwardedEndpoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceIds, forwardedEndpoint);
    }

    @Override
    public String toString() {
        return "UpdateOperationStatusesRequest{" +
                "resourceIds=" + resourceIds +
                ", forwardedEndpoint=" + forwardedEndpoint +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Set<String> resourceIds;
        private String forwardedEndpoint;

        private Builder() {
        }

        public Builder resourceIds(final Set<String> resourceIds) {
            this.resourceIds = resourceIds;
            return this;
        }

        public Builder forwardedEndpoint(final String forwardedEndpoint) {
            this.forwardedEndpoint = forwardedEndpoint;
            return this;
        }

        public UpdateRestResourceForwardedEndpointsRequest build() {
            return new UpdateRestResourceForwardedEndpointsRequest(this);
        }
    }

}
