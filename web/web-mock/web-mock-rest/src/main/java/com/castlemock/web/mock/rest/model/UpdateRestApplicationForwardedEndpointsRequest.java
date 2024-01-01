package com.castlemock.web.mock.rest.model;

import java.util.Objects;
import java.util.Set;

public class UpdateRestApplicationForwardedEndpointsRequest {

    private Set<String> applicationIds;
    private String forwardedEndpoint;

    private UpdateRestApplicationForwardedEndpointsRequest() {

    }

    private UpdateRestApplicationForwardedEndpointsRequest(final Builder builder) {
        this.applicationIds = Objects.requireNonNull(builder.applicationIds, "applicationIds");
        this.forwardedEndpoint = Objects.requireNonNull(builder.forwardedEndpoint, "forwardedEndpoint");
    }

    public Set<String> getApplicationIds() {
        return applicationIds;
    }

    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UpdateRestApplicationForwardedEndpointsRequest that = (UpdateRestApplicationForwardedEndpointsRequest) o;
        return Objects.equals(applicationIds, that.applicationIds) &&
                Objects.equals(forwardedEndpoint, that.forwardedEndpoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationIds, forwardedEndpoint);
    }

    @Override
    public String toString() {
        return "UpdatePortStatusesRequest{" +
                "applicationIds=" + applicationIds +
                ", forwardedEndpoint=" + forwardedEndpoint +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Set<String> applicationIds;
        private String forwardedEndpoint;

        private Builder() {
        }

        public Builder applicationIds(final Set<String> applicationIds) {
            this.applicationIds = applicationIds;
            return this;
        }

        public Builder forwardedEndpoint(final String forwardedEndpoint) {
            this.forwardedEndpoint = forwardedEndpoint;
            return this;
        }

        public UpdateRestApplicationForwardedEndpointsRequest build() {
            return new UpdateRestApplicationForwardedEndpointsRequest(this);
        }
    }

}
