package com.castlemock.repository.rest.file.event.model;

import com.castlemock.repository.core.file.event.model.EventFile;
import org.dozer.Mapping;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "restEvent")
public class RestEventFile extends EventFile {

    @Mapping("request")
    private RestRequestFile request;
    @Mapping("response")
    private RestResponseFile response;
    @Mapping("projectId")
    private String projectId;
    @Mapping("applicationId")
    private String applicationId;
    @Mapping("resourceId")
    private String resourceId;
    @Mapping("methodId")
    private String methodId;

    private RestEventFile() {

    }

    private RestEventFile(final Builder builder) {
        super(builder);
        this.request = Objects.requireNonNull(builder.request, "request");
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.applicationId = Objects.requireNonNull(builder.applicationId, "applicationId");
        this.resourceId = Objects.requireNonNull(builder.resourceId, "resourceId");
        this.methodId = Objects.requireNonNull(builder.methodId, "methodId");
        this.response = builder.response;
    }

    public RestRequestFile getRequest() {
        return request;
    }

    public void setRequest(RestRequestFile request) {
        this.request = request;
    }

    public RestResponseFile getResponse() {
        return response;
    }

    public void setResponse(RestResponseFile response) {
        this.response = response;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EventFile.Builder<Builder> {
        private RestRequestFile request;
        private RestResponseFile response;
        private String projectId;
        private String applicationId;
        private String resourceId;
        private String methodId;

        private Builder() {
        }

        public Builder request(final RestRequestFile request) {
            this.request = request;
            return this;
        }

        public Builder response(final RestResponseFile response) {
            this.response = response;
            return this;
        }

        public Builder projectId(final String projectId) {
            this.projectId = projectId;
            return this;
        }

        public Builder applicationId(final String applicationId) {
            this.applicationId = applicationId;
            return this;
        }

        public Builder resourceId(final String resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        public Builder methodId(final String methodId) {
            this.methodId = methodId;
            return this;
        }

        public RestEventFile build() {
            return new RestEventFile(this);
        }

    }

}