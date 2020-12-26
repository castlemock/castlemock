package com.castlemock.web.mock.soap.model;

import java.util.Objects;
import java.util.Set;

public class UpdateSoapPortForwardedEndpointsRequest {

    private Set<String> portIds;
    private String forwardedEndpoint;

    private UpdateSoapPortForwardedEndpointsRequest() {

    }

    private UpdateSoapPortForwardedEndpointsRequest(final Builder builder) {
        this.portIds = Objects.requireNonNull(builder.portIds);
        this.forwardedEndpoint = Objects.requireNonNull(builder.forwardedEndpoint);
    }

    public Set<String> getPortIds() {
        return portIds;
    }

    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateSoapPortForwardedEndpointsRequest that = (UpdateSoapPortForwardedEndpointsRequest) o;
        return Objects.equals(portIds, that.portIds) &&
                Objects.equals(forwardedEndpoint, that.forwardedEndpoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(portIds, forwardedEndpoint);
    }

    @Override
    public String toString() {
        return "UpdatePortStatusesRequest{" +
                "portIds=" + portIds +
                ", forwardedEndpoint=" + forwardedEndpoint +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Set<String> portIds;
        private String forwardedEndpoint;

        private Builder() {
        }

        public Builder portIds(final Set<String> portIds) {
            this.portIds = portIds;
            return this;
        }

        public Builder forwardedEndpoint(final String forwardedEndpoint) {
            this.forwardedEndpoint = forwardedEndpoint;
            return this;
        }

        public UpdateSoapPortForwardedEndpointsRequest build() {
            return new UpdateSoapPortForwardedEndpointsRequest(this);
        }
    }

}
