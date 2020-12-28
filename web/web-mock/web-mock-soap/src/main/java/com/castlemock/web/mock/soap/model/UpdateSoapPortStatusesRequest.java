package com.castlemock.web.mock.soap.model;

import com.castlemock.model.mock.soap.domain.SoapOperationStatus;

import java.util.Objects;
import java.util.Set;

public class UpdateSoapPortStatusesRequest {

    private Set<String> portIds;
    private SoapOperationStatus status;

    private UpdateSoapPortStatusesRequest() {

    }

    private UpdateSoapPortStatusesRequest(final Builder builder) {
        this.portIds = Objects.requireNonNull(builder.portIds);
        this.status = Objects.requireNonNull(builder.status);
    }

    public Set<String> getPortIds() {
        return portIds;
    }

    public SoapOperationStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateSoapPortStatusesRequest that = (UpdateSoapPortStatusesRequest) o;
        return Objects.equals(portIds, that.portIds) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(portIds, status);
    }

    @Override
    public String toString() {
        return "UpdatePortStatusesRequest{" +
                "portIds=" + portIds +
                ", status=" + status +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Set<String> portIds;
        private SoapOperationStatus status;

        private Builder() {
        }

        public Builder portIds(Set<String> portIds) {
            this.portIds = portIds;
            return this;
        }

        public Builder status(SoapOperationStatus status) {
            this.status = status;
            return this;
        }

        public UpdateSoapPortStatusesRequest build() {
            return new UpdateSoapPortStatusesRequest(this);
        }
    }

}
