package com.castlemock.repository.rest.file.event.model;

import com.castlemock.model.core.http.HttpContentEncoding;
import com.castlemock.repository.core.file.http.model.HttpHeaderFile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@XmlRootElement(name = "restResponse")
@XmlAccessorType(XmlAccessType.NONE)
public class RestResponseFile {

    @XmlElement
    private String body;
    @XmlElement
    private String mockResponseName;
    @XmlElement
    private Integer httpStatusCode;
    @XmlElement
    private String contentType;
    @XmlElementWrapper(name = "httpHeaders")
    @XmlElement(name = "httpHeader")
    private List<HttpHeaderFile> httpHeaders;
    @XmlElementWrapper(name = "contentEncodings")
    @XmlElement(name = "contentEncoding")
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

    public String getBody() {
        return body;
    }

    public String getMockResponseName() {
        return mockResponseName;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getContentType() {
        return contentType;
    }

    public List<HttpHeaderFile> getHttpHeaders() {
        return httpHeaders;
    }

    public List<HttpContentEncoding> getContentEncodings() {
        return contentEncodings;
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