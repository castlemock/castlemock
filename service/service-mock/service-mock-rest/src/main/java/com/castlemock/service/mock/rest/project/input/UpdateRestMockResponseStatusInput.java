package com.castlemock.service.mock.rest.project.input;

import com.castlemock.model.core.Input;
import com.castlemock.model.core.validation.NotNull;
import com.castlemock.model.mock.rest.domain.RestMockResponseStatus;

import java.util.Objects;

public class UpdateRestMockResponseStatusInput implements Input {


    @NotNull
    private final String projectId;
    @NotNull
    private final String applicationId;
    @NotNull
    private final String resourceId;
    @NotNull
    private final String methodId;
    @NotNull
    private final String mockResponseId;
    @NotNull
    private final RestMockResponseStatus status;

    public UpdateRestMockResponseStatusInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.applicationId = Objects.requireNonNull(builder.applicationId, "applicationId");
        this.resourceId = Objects.requireNonNull(builder.resourceId, "resourceId");
        this.methodId = Objects.requireNonNull(builder.methodId, "methodId");
        this.mockResponseId = Objects.requireNonNull(builder.mockResponseId, "mockResponseId");
        this.status = Objects.requireNonNull(builder.status, "status");
    }

    public String getProjectId() {
        return projectId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getMethodId() {
        return methodId;
    }

    public String getMockResponseId() {
        return mockResponseId;
    }

    public RestMockResponseStatus getStatus() {
        return status;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {

        private String projectId;
        private String applicationId;
        private String resourceId;
        private String methodId;
        private String mockResponseId;
        private RestMockResponseStatus status;

        public Builder projectId(final String projectId){
            this.projectId = projectId;
            return this;
        }

        public Builder applicationId(final String applicationId){
            this.applicationId = applicationId;
            return this;
        }

        public Builder resourceId(final String resourceId){
            this.resourceId = resourceId;
            return this;
        }

        public Builder methodId(final String methodId){
            this.methodId = methodId;
            return this;
        }

        public Builder mockResponseId(final String mockResponseId){
            this.mockResponseId = mockResponseId;
            return this;
        }

        public Builder status(final RestMockResponseStatus status){
            this.status = status;
            return this;
        }

        public UpdateRestMockResponseStatusInput build(){
            return new UpdateRestMockResponseStatusInput(this);
        }
    }

}
