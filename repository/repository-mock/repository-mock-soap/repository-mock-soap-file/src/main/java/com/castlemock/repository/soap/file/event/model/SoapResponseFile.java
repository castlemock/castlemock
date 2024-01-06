package com.castlemock.repository.soap.file.event.model;

import com.castlemock.model.core.http.ContentEncoding;
import com.castlemock.repository.core.file.http.model.HttpHeaderFile;
import org.dozer.Mapping;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "soapResponse")
@XmlSeeAlso(HttpHeaderFile.class)
public class SoapResponseFile {

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
    private List<ContentEncoding> contentEncodings;

    private SoapResponseFile() {

    }

    private SoapResponseFile(final Builder builder) {
        this.body = Objects.requireNonNull(builder.body, "body");
        this.httpStatusCode = Objects.requireNonNull(builder.httpStatusCode, "httpStatusCode");
        this.httpHeaders = Objects.requireNonNull(builder.httpHeaders, "httpHeaders");
        this.contentEncodings = Objects.requireNonNull(builder.contentEncodings, "contentEncodings");
        this.contentType = builder.contentType;
        this.mockResponseName = builder.mockResponseName;
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
    public List<ContentEncoding> getContentEncodings() {
        return contentEncodings;
    }

    public void setContentEncodings(List<ContentEncoding> contentEncodings) {
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
        private List<ContentEncoding> contentEncodings;

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

        public Builder contentEncodings(List<ContentEncoding> contentEncodings) {
            this.contentEncodings = contentEncodings;
            return this;
        }

        public SoapResponseFile build() {
            return new SoapResponseFile(this);
        }
    }
}