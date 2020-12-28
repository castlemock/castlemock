package com.castlemock.web.mock.rest.model;

import com.castlemock.model.mock.rest.domain.RestMethodStatus;

import java.util.Objects;
import java.util.Set;

public class UpdateRestResourceStatusesRequest {

    private Set<String> resourceIds;
    private RestMethodStatus status;

    private UpdateRestResourceStatusesRequest() {

    }

    private UpdateRestResourceStatusesRequest(final Builder builder) {
        this.resourceIds = Objects.requireNonNull(builder.resourceIds);
        this.status = Objects.requireNonNull(builder.status);
    }

    public Set<String> getResourceIds() {
        return resourceIds;
    }

    public RestMethodStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateRestResourceStatusesRequest that = (UpdateRestResourceStatusesRequest) o;
        return Objects.equals(resourceIds, that.resourceIds) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceIds, status);
    }

    @Override
    public String toString() {
        return "UpdatePortStatusesRequest{" +
                "resourceIds=" + resourceIds +
                ", status=" + status +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Set<String> resourceIds;
        private RestMethodStatus status;

        private Builder() {
        }

        public Builder resourceIds(Set<String> resourceIds) {
            this.resourceIds = resourceIds;
            return this;
        }

        public Builder status(RestMethodStatus status) {
            this.status = status;
            return this;
        }

        public UpdateRestResourceStatusesRequest build() {
            return new UpdateRestResourceStatusesRequest(this);
        }
    }
    
}
