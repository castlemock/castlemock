package com.castlemock.repository.rest.file.event.model;

import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.repository.core.file.http.model.HttpHeaderFile;
import com.castlemock.repository.core.file.http.model.HttpParameterFile;
import org.dozer.Mapping;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "restRequest")
public class RestRequestFile {

    @Mapping("body")
    private String body;
    @Mapping("contentType")
    private String contentType;
    @Mapping("uri")
    private String uri;
    @Mapping("httpMethod")
    private HttpMethod httpMethod;
    @Mapping("httpParameters")
    private List<HttpParameterFile> httpParameters;
    @Mapping("httpHeaders")
    private List<HttpHeaderFile> httpHeaders;

    private RestRequestFile() {

    }

    private RestRequestFile(final Builder builder) {
        this.uri = Objects.requireNonNull(builder.uri, "uri");
        this.httpMethod = Objects.requireNonNull(builder.httpMethod, "httpMethod");
        this.body = builder.body;
        this.contentType = builder.contentType;
        this.httpHeaders = builder.httpHeaders;
        this.httpParameters = builder.httpParameters;
    }

    @XmlElement
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    @XmlElementWrapper(name = "httpHeaders")
    @XmlElement(name = "httpHeader")
    public List<HttpHeaderFile> getHttpHeaders() {
        return httpHeaders;
    }

    public void setHttpHeaders(List<HttpHeaderFile> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    @XmlElementWrapper(name = "httpParameters")
    @XmlElement(name = "httpParameter")
    public List<HttpParameterFile> getHttpParameters() {
        return httpParameters;
    }

    public void setHttpParameters(List<HttpParameterFile> httpParameters) {
        this.httpParameters = httpParameters;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String body;
        private String contentType;
        private String uri;
        private HttpMethod httpMethod;
        private List<HttpParameterFile> httpParameters;
        private List<HttpHeaderFile> httpHeaders;

        private Builder() {
        }

        public Builder body(final String body) {
            this.body = body;
            return this;
        }

        public Builder contentType(final String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder uri(final String uri) {
            this.uri = uri;
            return this;
        }

        public Builder httpMethod(final HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public Builder httpParameters(final List<HttpParameterFile> httpParameters) {
            this.httpParameters = httpParameters;
            return this;
        }

        public Builder httpHeaders(final List<HttpHeaderFile> httpHeaders) {
            this.httpHeaders = httpHeaders;
            return this;
        }

        public RestRequestFile build() {
            return new RestRequestFile(this);
        }
    }
}