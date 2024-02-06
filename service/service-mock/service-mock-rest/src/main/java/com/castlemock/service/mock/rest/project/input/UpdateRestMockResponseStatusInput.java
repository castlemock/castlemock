package com.castlemock.service.mock.rest.project.input;

import com.castlemock.model.core.Input;
import com.castlemock.model.mock.rest.domain.RestMockResponseStatus;

import java.util.Objects;

public class UpdateRestMockResponseStatusInput implements Input {


    private final String projectId;
    private final String applicationId;
    private final String resourceId;
    private final String methodId;
    private final String mockResponseId;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UpdateRestMockResponseStatusInput that = (UpdateRestMockResponseStatusInput) o;
        return Objects.equals(projectId, that.projectId) && Objects.equals(applicationId, that.applicationId) && Objects.equals(resourceId, that.resourceId) && Objects.equals(methodId, that.methodId) && Objects.equals(mockResponseId, that.mockResponseId) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, applicationId, resourceId, methodId, mockResponseId, status);
    }

    @Override
    public String toString() {
        return "UpdateRestMockResponseStatusInput{" +
                "projectId='" + projectId + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", resourceId='" + resourceId + '\'' +
                ", methodId='" + methodId + '\'' +
                ", mockResponseId='" + mockResponseId + '\'' +
                ", status=" + status +
                '}';
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
