package com.castlemock.repository.soap.file.event.model;

import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.mock.soap.domain.SoapVersion;
import com.castlemock.repository.core.file.http.model.HttpHeaderFile;
import org.dozer.Mapping;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "soapRequest")
public class SoapRequestFile {

    @Mapping("body")
    private String body;
    @Mapping("envelope")
    private String envelope;
    @Mapping("contentType")
    private String contentType;
    @Mapping("uri")
    private String uri;
    @Mapping("httpMethod")
    private HttpMethod httpMethod;
    @Mapping("operationName")
    private String operationName;
    @Mapping("operationIdentifier")
    private SoapOperationIdentifierFile operationIdentifier;
    @Mapping("soapVersion")
    private SoapVersion soapVersion;
    @Mapping("httpHeaders")
    private List<HttpHeaderFile> httpHeaders;

    private SoapRequestFile() {

    }

    private SoapRequestFile(final Builder builder) {
        this.body = Objects.requireNonNull(builder.body, "body");
        this.envelope = builder.envelope;
        this.contentType = builder.contentType;
        this.uri = Objects.requireNonNull(builder.uri, "uri");
        this.httpMethod = Objects.requireNonNull(builder.httpMethod, "httpMethod");
        this.operationName = builder.operationName;
        this.soapVersion = Objects.requireNonNull(builder.soapVersion, "soapVersion");
        this.httpHeaders = Objects.requireNonNull(builder.httpHeaders, "httpHeaders");
        this.operationIdentifier = Objects.requireNonNull(builder.operationIdentifier, "operationIdentifier");
    }

    @XmlElement
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @XmlElement
    public String getEnvelope() {
        return envelope;
    }

    public void setEnvelope(String envelope) {
        this.envelope = envelope;
    }

    @XmlElement
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @XmlElement
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @XmlElement
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    @XmlElement
    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    @XmlElement
    public SoapOperationIdentifierFile getOperationIdentifier() {
        return operationIdentifier;
    }

    public void setOperationIdentifier(SoapOperationIdentifierFile operationIdentifier) {
        this.operationIdentifier = operationIdentifier;
    }

    @XmlElement
    public SoapVersion getSoapVersion() {
        return soapVersion;
    }

    public void setSoapVersion(SoapVersion soapVersion) {
        this.soapVersion = soapVersion;
    }

    @XmlElementWrapper(name = "httpHeaders")
    @XmlElement(name = "httpHeader")
    public List<HttpHeaderFile> getHttpHeaders() {
        return httpHeaders;
    }

    public void setHttpHeaders(List<HttpHeaderFile> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String body;
        private String envelope;
        private String contentType;
        private String uri;
        private HttpMethod httpMethod;
        private String operationName;
        private SoapOperationIdentifierFile operationIdentifier;
        private SoapVersion soapVersion;
        private List<HttpHeaderFile> httpHeaders;

        private Builder() {
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder envelope(String envelope) {
            this.envelope = envelope;
            return this;
        }

        public Builder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public Builder httpMethod(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public Builder operationName(String operationName) {
            this.operationName = operationName;
            return this;
        }

        public Builder operationIdentifier(SoapOperationIdentifierFile operationIdentifier) {
            this.operationIdentifier = operationIdentifier;
            return this;
        }

        public Builder soapVersion(SoapVersion soapVersion) {
            this.soapVersion = soapVersion;
            return this;
        }

        public Builder httpHeaders(List<HttpHeaderFile> httpHeaders) {
            this.httpHeaders = httpHeaders;
            return this;
        }

        public SoapRequestFile build() {
            return new SoapRequestFile(this);
        }
    }
}