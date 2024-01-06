package com.castlemock.repository.soap.file.project.model;

import com.castlemock.model.core.Saveable;
import com.castlemock.model.core.http.ContentEncoding;
import com.castlemock.model.mock.soap.domain.SoapMockResponseStatus;
import com.castlemock.repository.core.file.http.model.HttpHeaderFile;
import org.dozer.Mapping;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@XmlRootElement(name = "soapMockResponse")
@XmlSeeAlso({HttpHeaderFile.class, SoapXPathExpressionFile.class})
public class SoapMockResponseFile implements Saveable<String> {

    @Mapping("id")
    private String id;
    @Mapping("name")
    private String name;
    @Mapping("body")
    private String body;
    @Mapping("operationId")
    private String operationId;
    @Mapping("status")
    private SoapMockResponseStatus status;
    @Mapping("httpStatusCode")
    private Integer httpStatusCode;
    @Mapping("usingExpressions")
    private Boolean usingExpressions;
    @Mapping("httpHeaders")
    private List<HttpHeaderFile> httpHeaders = new CopyOnWriteArrayList<>();
    @Mapping("contentEncodings")
    private List<ContentEncoding> contentEncodings = new CopyOnWriteArrayList<>();
    @Mapping("xpathExpressions")
    private List<SoapXPathExpressionFile> xpathExpressions = new CopyOnWriteArrayList<>();

    private SoapMockResponseFile() {

    }

    private SoapMockResponseFile(final Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "id");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.body = Objects.requireNonNull(builder.body, "body");
        this.operationId = Objects.requireNonNull(builder.operationId, "operationId");
        this.status = Objects.requireNonNull(builder.status, "status");
        this.httpStatusCode = Objects.requireNonNull(builder.httpStatusCode, "httpStatusCode");
        this.usingExpressions = builder.usingExpressions;
        this.httpHeaders = Optional.ofNullable(builder.httpHeaders)
                .orElseGet(List::of);
        this.contentEncodings = Optional.ofNullable(builder.contentEncodings)
                .orElseGet(List::of);
        this.xpathExpressions = Optional.ofNullable(builder.xpathExpressions)
                .orElseGet(List::of);
    }

    @XmlElement
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @XmlElement
    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    @XmlElement
    public SoapMockResponseStatus getStatus() {
        return status;
    }

    public void setStatus(SoapMockResponseStatus status) {
        this.status = status;
    }

    @XmlElement
    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    @XmlElement
    public boolean getUsingExpressions() {
        return usingExpressions;
    }

    public void setUsingExpressions(boolean usingExpressions) {
        this.usingExpressions = usingExpressions;
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

    @XmlElementWrapper(name = "xpathExpressions")
    @XmlElement(name = "xpathExpression")
    public List<SoapXPathExpressionFile> getXpathExpressions() {
        return xpathExpressions;
    }

    public void setXpathExpressions(List<SoapXPathExpressionFile> xpathExpressions) {
        this.xpathExpressions = xpathExpressions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof SoapMockResponseFile that))
            return false;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String name;
        private String body;
        private String operationId;
        private SoapMockResponseStatus status;
        private Integer httpStatusCode;
        private Boolean usingExpressions;
        private List<HttpHeaderFile> httpHeaders;
        private List<ContentEncoding> contentEncodings;
        private List<SoapXPathExpressionFile> xpathExpressions;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder operationId(String operationId) {
            this.operationId = operationId;
            return this;
        }

        public Builder status(SoapMockResponseStatus status) {
            this.status = status;
            return this;
        }

        public Builder httpStatusCode(Integer httpStatusCode) {
            this.httpStatusCode = httpStatusCode;
            return this;
        }

        public Builder usingExpressions(Boolean usingExpressions) {
            this.usingExpressions = usingExpressions;
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

        public Builder xpathExpressions(List<SoapXPathExpressionFile> xpathExpressions) {
            this.xpathExpressions = xpathExpressions;
            return this;
        }

        public SoapMockResponseFile build() {
            return new SoapMockResponseFile(this);
        }
    }
}