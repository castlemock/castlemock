package com.castlemock.web.mock.rest.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import java.util.Set;

@XmlRootElement
@JsonDeserialize(builder = UpdateRestResourceForwardedEndpointsRequest.Builder.class)
public class UpdateRestResourceForwardedEndpointsRequest {

    private final Set<String> resourceIds;
    private final String forwardedEndpoint;
        private UpdateRestResourceForwardedEndpointsRequest(final Builder builder) {
        this.resourceIds = Objects.requireNonNull(builder.resourceIds, "resourceIds");
        this.forwardedEndpoint = Objects.requireNonNull(builder.forwardedEndpoint, "forwardedEndpoint");
    }

    public Set<String> getResourceIds() {
        return Set.copyOf(resourceIds);
    }

    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UpdateRestResourceForwardedEndpointsRequest that = (UpdateRestResourceForwardedEndpointsRequest) o;
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

    @JsonPOJOBuilder(withPrefix = "")
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
