package com.castlemock.web.mock.rest.model;

import com.castlemock.core.mock.rest.model.project.domain.RestMethodStatus;

import java.util.Objects;
import java.util.Set;

public class UpdateRestMethodStatusesRequest {

    private Set<String> methodIds;
    private RestMethodStatus status;

    private UpdateRestMethodStatusesRequest() {

    }

    private UpdateRestMethodStatusesRequest(final Builder builder) {
        this.methodIds = Objects.requireNonNull(builder.methodIds);
        this.status = Objects.requireNonNull(builder.status);
    }

    public Set<String> getMethodIds() {
        return methodIds;
    }

    public RestMethodStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateRestMethodStatusesRequest that = (UpdateRestMethodStatusesRequest) o;
        return Objects.equals(methodIds, that.methodIds) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(methodIds, status);
    }

    @Override
    public String toString() {
        return "UpdatePortStatusesRequest{" +
                "methodIds=" + methodIds +
                ", status=" + status +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Set<String> methodIds;
        private RestMethodStatus status;

        private Builder() {
        }

        public Builder resourceIds(Set<String> methodIds) {
            this.methodIds = methodIds;
            return this;
        }

        public Builder status(RestMethodStatus status) {
            this.status = status;
            return this;
        }

        public UpdateRestMethodStatusesRequest build() {
            return new UpdateRestMethodStatusesRequest(this);
        }
    }
    
}
