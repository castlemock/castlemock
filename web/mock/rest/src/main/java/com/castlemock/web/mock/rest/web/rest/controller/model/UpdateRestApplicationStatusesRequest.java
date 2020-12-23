package com.castlemock.web.mock.rest.web.rest.controller.model;

import com.castlemock.core.mock.rest.model.project.domain.RestMethodStatus;

import java.util.Objects;
import java.util.Set;

public class UpdateRestApplicationStatusesRequest {

    private Set<String> applicationIds;
    private RestMethodStatus status;

    private UpdateRestApplicationStatusesRequest() {

    }

    private UpdateRestApplicationStatusesRequest(final Builder builder) {
        this.applicationIds = Objects.requireNonNull(builder.applicationIds);
        this.status = Objects.requireNonNull(builder.status);
    }

    public Set<String> getApplicationIds() {
        return applicationIds;
    }

    public RestMethodStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateRestApplicationStatusesRequest that = (UpdateRestApplicationStatusesRequest) o;
        return Objects.equals(applicationIds, that.applicationIds) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationIds, status);
    }

    @Override
    public String toString() {
        return "UpdatePortStatusesRequest{" +
                "applicationIds=" + applicationIds +
                ", status=" + status +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Set<String> applicationIds;
        private RestMethodStatus status;

        private Builder() {
        }

        public Builder applicationIds(Set<String> applicationIds) {
            this.applicationIds = applicationIds;
            return this;
        }

        public Builder status(RestMethodStatus status) {
            this.status = status;
            return this;
        }

        public UpdateRestApplicationStatusesRequest build() {
            return new UpdateRestApplicationStatusesRequest(this);
        }
    }

}
