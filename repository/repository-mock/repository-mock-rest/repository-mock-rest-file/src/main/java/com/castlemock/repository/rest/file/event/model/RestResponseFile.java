package com.castlemock.repository.rest.file.event.model;

import com.castlemock.model.core.http.HttpContentEncoding;
import com.castlemock.repository.core.file.http.model.HttpHeaderFile;
import org.dozer.Mapping;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@XmlRootElement(name = "restResponse")
public class RestResponseFile {

    @Mapping("body")
    private String body;
    @Mapping("mockResponseName")
    private String mockResponseName;
    @Mapping("httpStatusCode")
    private Integer httpStatusCode;
    @Mapping("contentType")
    private String contentType;
    @Mapping("httpHeaders")
    private List<HttpHeaderFile> httpHeaders;
    @Mapping("contentEncodings")
    private List<HttpContentEncoding> contentEncodings;

    private RestResponseFile() {

    }

    private RestResponseFile(final Builder builder) {
        this.httpStatusCode = Objects.requireNonNull(builder.httpStatusCode, "httpStatusCode");
        this.body = builder.body;
        this.contentType = builder.contentType;
        this.mockResponseName = builder.mockResponseName;
        this.contentEncodings = Optional.ofNullable(builder.contentEncodings)
                .orElseGet(List::of);
        this.httpHeaders = Optional.ofNullable(builder.httpHeaders)
                .orElseGet(List::of);
    }

    @XmlElement
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @XmlElement
    public String getMockResponseName() {
        return mockResponseName;
    }

    public void setMockResponseName(String mockResponseName) {
        this.mockResponseName = mockResponseName;
    }

    @XmlElement
    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    @XmlElement
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @XmlElementWrapper(name = "httpHeaders")
    @XmlElement(name = "httpHeader")
    public List<HttpHeaderFile> getHttpHeaders() {
        return httpHeaders;
    }

    public void setHttpHeaders(List<HttpHeaderFile> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    @XmlElementWrapper(name = "contentEncodings")
    @XmlElement(name = "contentEncoding")
    public List<HttpContentEncoding> getContentEncodings() {
        return contentEncodings;
    }

    public void setContentEncodings(List<HttpContentEncoding> contentEncodings) {
        this.contentEncodings = contentEncodings;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String body;
        private String mockResponseName;
        private Integer httpStatusCode;
        private String contentType;
        private List<HttpHeaderFile> httpHeaders;
        private List<HttpContentEncoding> contentEncodings;

        private Builder() {
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder mockResponseName(String mockResponseName) {
            this.mockResponseName = mockResponseName;
            return this;
        }

        public Builder httpStatusCode(Integer httpStatusCode) {
            this.httpStatusCode = httpStatusCode;
            return this;
        }

        public Builder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder httpHeaders(List<HttpHeaderFile> httpHeaders) {
            this.httpHeaders = httpHeaders;
            return this;
        }

        public Builder contentEncodings(List<HttpContentEncoding> contentEncodings) {
            this.contentEncodings = contentEncodings;
            return this;
        }

        public RestResponseFile build() {
            return new RestResponseFile(this);
        }
    }
}