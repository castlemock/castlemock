package com.castlemock.web.mock.rest.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@XmlRootElement
@JsonDeserialize(builder = UpdateRestApplicationForwardedEndpointsRequest.Builder.class)
public class UpdateRestApplicationForwardedEndpointsRequest {

    private final Set<String> applicationIds;
    private final String forwardedEndpoint;


    private UpdateRestApplicationForwardedEndpointsRequest(final Builder builder) {
        this.applicationIds = Objects.requireNonNull(builder.applicationIds, "applicationIds");
        this.forwardedEndpoint = Objects.requireNonNull(builder.forwardedEndpoint, "forwardedEndpoint");
    }

    public Set<String> getApplicationIds() {
        return Optional.of(applicationIds)
                .map(Set::copyOf)
                .orElseGet(Set::of);
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

    @JsonPOJOBuilder(withPrefix = "")
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
