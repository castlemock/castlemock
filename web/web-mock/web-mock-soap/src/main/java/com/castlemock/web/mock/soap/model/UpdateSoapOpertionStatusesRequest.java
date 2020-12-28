package com.castlemock.web.mock.soap.model;

import com.castlemock.model.mock.soap.domain.SoapOperationStatus;

import java.util.Objects;
import java.util.Set;

public class UpdateSoapOpertionStatusesRequest {

    private Set<String> operationIds;
    private SoapOperationStatus status;

    private UpdateSoapOpertionStatusesRequest() {

    }

    private UpdateSoapOpertionStatusesRequest(final Builder builder) {
        this.operationIds = Objects.requireNonNull(builder.operationIds);
        this.status = Objects.requireNonNull(builder.status);
    }

    public Set<String> getOperationIds() {
        return operationIds;
    }

    public SoapOperationStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateSoapOpertionStatusesRequest that = (UpdateSoapOpertionStatusesRequest) o;
        return Objects.equals(operationIds, that.operationIds) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationIds, status);
    }

    @Override
    public String toString() {
        return "UpdatePortStatusesRequest{" +
                "operationIds=" + operationIds +
                ", status=" + status +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Set<String> operationIds;
        private SoapOperationStatus status;

        private Builder() {
        }

        public Builder operationIds(Set<String> operationIds) {
            this.operationIds = operationIds;
            return this;
        }

        public Builder status(SoapOperationStatus status) {
            this.status = status;
            return this;
        }

        public UpdateSoapOpertionStatusesRequest build() {
            return new UpdateSoapOpertionStatusesRequest(this);
        }
    }
    
}
