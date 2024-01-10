package com.castlemock.repository.rest.file.project.model;

import com.castlemock.model.core.Saveable;
import com.castlemock.model.core.http.HttpContentEncoding;
import com.castlemock.model.mock.rest.domain.RestMockResponseStatus;
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

@XmlRootElement(name = "restMockResponse")
@XmlSeeAlso(HttpHeaderFile.class)
public class RestMockResponseFile implements Saveable<String> {

    @Mapping("id")
    private String id;
    @Mapping("name")
    private String name;
    @Mapping("body")
    private String body;
    @Mapping("methodId")
    private String methodId;
    @Mapping("status")
    private RestMockResponseStatus status;
    @Mapping("httpStatusCode")
    private Integer httpStatusCode;
    @Mapping("usingExpressions")
    private Boolean usingExpressions;
    @Mapping("httpHeaders")
    private List<HttpHeaderFile> httpHeaders = new CopyOnWriteArrayList<>();
    @Mapping("contentEncodings")
    private List<HttpContentEncoding> contentEncodings = new CopyOnWriteArrayList<>();
    @Mapping("parameterQueries")
    private List<RestParameterQueryFile> parameterQueries = new CopyOnWriteArrayList<>();
    @Mapping("xpathExpressions")
    private List<RestXPathExpressionFile> xpathExpressions = new CopyOnWriteArrayList<>();
    @Mapping("jsonPathExpressions")
    private List<RestJsonPathExpressionFile> jsonPathExpressions = new CopyOnWriteArrayList<>();
    @Mapping("headerQueries")
    private List<RestHeaderQueryFile> headerQueries = new CopyOnWriteArrayList<>();

    private RestMockResponseFile() {

    }

    private RestMockResponseFile(final Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "id");
        this.status = Objects.requireNonNull(builder.status, "status");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.methodId = Objects.requireNonNull(builder.methodId, "methodId");
        this.httpStatusCode = Objects.requireNonNull(builder.httpStatusCode, "httpStatusCode");
        this.usingExpressions = Objects.requireNonNull(builder.usingExpressions, "usingExpressions");
        this.body = builder.body;
        this.httpHeaders = Optional.ofNullable(builder.httpHeaders)
                .orElseGet(List::of);
        this.contentEncodings = Optional.ofNullable(builder.contentEncodings)
                .orElseGet(List::of);
        this.parameterQueries = Optional.ofNullable(builder.parameterQueries)
                .orElseGet(List::of);
        this.xpathExpressions = Optional.ofNullable(builder.xpathExpressions)
                .orElseGet(List::of);
        this.jsonPathExpressions = Optional.ofNullable(builder.jsonPathExpressions)
                .orElseGet(List::of);
        this.headerQueries = Optional.ofNullable(builder.headerQueries)
                .orElseGet(List::of);
    }

    @Override
    @XmlElement
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
    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    @XmlElement
    public RestMockResponseStatus getStatus() {
        return status;
    }

    public void setStatus(RestMockResponseStatus status) {
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
    public List<HttpContentEncoding> getContentEncodings() {
        return contentEncodings;
    }

    public void setContentEncodings(List<HttpContentEncoding> contentEncodings) {
        this.contentEncodings = contentEncodings;
    }

    @XmlElementWrapper(name = "parameterQueries")
    @XmlElement(name = "parameterQuery")
    public List<RestParameterQueryFile> getParameterQueries() {
        return parameterQueries;
    }

    public void setParameterQueries(List<RestParameterQueryFile> parameterQueries) {
        this.parameterQueries = parameterQueries;
    }

    @XmlElementWrapper(name = "xpathExpressions")
    @XmlElement(name = "xpathExpression")
    public List<RestXPathExpressionFile> getXpathExpressions() {
        return xpathExpressions;
    }

    public void setXpathExpressions(List<RestXPathExpressionFile> xpathExpressions) {
        this.xpathExpressions = xpathExpressions;
    }

    @XmlElementWrapper(name = "jsonPathExpressions")
    @XmlElement(name = "jsonPathExpression")
    public List<RestJsonPathExpressionFile> getJsonPathExpressions() {
        return jsonPathExpressions;
    }

    public void setJsonPathExpressions(List<RestJsonPathExpressionFile> jsonPathExpressions) {
        this.jsonPathExpressions = jsonPathExpressions;
    }

    @XmlElementWrapper(name = "headerQueries")
    @XmlElement(name = "headerQuery")
    public List<RestHeaderQueryFile> getHeaderQueries() {
        return headerQueries;
    }

    public void setHeaderQueries(List<RestHeaderQueryFile> headerQueries) {
        this.headerQueries = headerQueries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof RestMockResponseFile that))
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
        private String methodId;
        private RestMockResponseStatus status;
        private Integer httpStatusCode;
        private Boolean usingExpressions;
        private List<HttpHeaderFile> httpHeaders;
        private List<HttpContentEncoding> contentEncodings;
        private List<RestParameterQueryFile> parameterQueries;
        private List<RestXPathExpressionFile> xpathExpressions;
        private List<RestJsonPathExpressionFile> jsonPathExpressions;
        private List<RestHeaderQueryFile> headerQueries;

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

        public Builder methodId(String methodId) {
            this.methodId = methodId;
            return this;
        }

        public Builder status(RestMockResponseStatus status) {
            this.status = status;
            return this;
        }

        public Builder httpStatusCode(Integer httpStatusCode) {
            this.httpStatusCode = httpStatusCode;
            return this;
        }

        public Builder usingExpressions(boolean usingExpressions) {
            this.usingExpressions = usingExpressions;
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

        public Builder parameterQueries(List<RestParameterQueryFile> parameterQueries) {
            this.parameterQueries = parameterQueries;
            return this;
        }

        public Builder xpathExpressions(List<RestXPathExpressionFile> xpathExpressions) {
            this.xpathExpressions = xpathExpressions;
            return this;
        }

        public Builder jsonPathExpressions(List<RestJsonPathExpressionFile> jsonPathExpressions) {
            this.jsonPathExpressions = jsonPathExpressions;
            return this;
        }

        public Builder headerQueries(List<RestHeaderQueryFile> headerQueries) {
            this.headerQueries = headerQueries;
            return this;
        }

        public RestMockResponseFile build() {
            return new RestMockResponseFile(this);
        }
    }
}