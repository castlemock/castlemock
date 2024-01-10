package com.castlemock.web.mock.rest.model;

import com.castlemock.model.mock.rest.domain.RestMethodStatus;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import java.util.Set;

@XmlRootElement
@JsonDeserialize(builder = UpdateRestApplicationStatusesRequest.Builder.class)
public class UpdateRestApplicationStatusesRequest {

    private final Set<String> applicationIds;
    private final RestMethodStatus status;


    private UpdateRestApplicationStatusesRequest(final Builder builder) {
        this.applicationIds = Objects.requireNonNull(builder.applicationIds, "applicationIds");
        this.status = Objects.requireNonNull(builder.status, "status");
    }

    public Set<String> getApplicationIds() {
        return applicationIds;
    }

    public RestMethodStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UpdateRestApplicationStatusesRequest that = (UpdateRestApplicationStatusesRequest) o;
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

    @JsonPOJOBuilder(withPrefix = "")
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
