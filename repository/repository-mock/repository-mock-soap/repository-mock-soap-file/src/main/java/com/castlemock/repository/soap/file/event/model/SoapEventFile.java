package com.castlemock.repository.soap.file.event.model;

import com.castlemock.repository.core.file.event.model.EventFile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "soapEvent")
@XmlAccessorType(XmlAccessType.NONE)
public class SoapEventFile extends EventFile {

    @XmlElement
    private SoapRequestFile request;
    @XmlElement
    private SoapResponseFile response;
    @XmlElement
    private String projectId;
    @XmlElement
    private String portId;
    @XmlElement
    private String operationId;

    private SoapEventFile() {

    }

    private SoapEventFile(final Builder builder) {
        super(builder);
        this.request = Objects.requireNonNull(builder.request, "request");
        this.response = builder.response;
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.portId = Objects.requireNonNull(builder.portId, "portId");
        this.operationId = Objects.requireNonNull(builder.operationId, "operationId");
    }


    public SoapRequestFile getRequest() {
        return request;
    }

    public SoapResponseFile getResponse() {
        return response;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getPortId() {
        return portId;
    }

    public String getOperationId() {
        return operationId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EventFile.Builder<Builder> {

        private SoapRequestFile request;
        private SoapResponseFile response;
        private String projectId;
        private String portId;
        private String operationId;

        private Builder() {

        }

        public Builder request(final SoapRequestFile request) {
            this.request = request;
            return this;
        }

        public Builder response(final SoapResponseFile response) {
            this.response = response;
            return this;
        }

        public Builder projectId(final String projectId) {
            this.projectId = projectId;
            return this;
        }

        public Builder portId(final String portId) {
            this.portId = portId;
            return this;
        }

        public Builder operationId(final String operationId) {
            this.operationId = operationId;
            return this;
        }

        public SoapEventFile build() {
            return new SoapEventFile(this);
        }

    }

}