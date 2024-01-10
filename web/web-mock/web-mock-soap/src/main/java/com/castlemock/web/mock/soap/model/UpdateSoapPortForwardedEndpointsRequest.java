package com.castlemock.web.mock.soap.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import java.util.Set;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = UpdateSoapPortForwardedEndpointsRequest.Builder.class)
public class UpdateSoapPortForwardedEndpointsRequest {

    private final Set<String> portIds;
    private final String forwardedEndpoint;


    private UpdateSoapPortForwardedEndpointsRequest(final Builder builder) {
        this.portIds = Objects.requireNonNull(builder.portIds, "portIds");
        this.forwardedEndpoint = Objects.requireNonNull(builder.forwardedEndpoint, "forwardedEndpoint");
    }

    public Set<String> getPortIds() {
        return portIds;
    }

    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UpdateSoapPortForwardedEndpointsRequest that = (UpdateSoapPortForwardedEndpointsRequest) o;
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

    @JsonPOJOBuilder(withPrefix = "")
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
