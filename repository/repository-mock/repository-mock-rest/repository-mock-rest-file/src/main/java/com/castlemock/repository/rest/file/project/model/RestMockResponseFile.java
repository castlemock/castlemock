/*
 * Copyright 2024 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.castlemock.repository.rest.file.project.model;

import com.castlemock.model.core.Saveable;
import com.castlemock.model.core.http.HttpContentEncoding;
import com.castlemock.model.mock.rest.domain.RestMockResponseStatus;
import com.castlemock.repository.core.file.http.model.HttpHeaderFile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@XmlRootElement(name = "restMockResponse")
@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso(HttpHeaderFile.class)
public class RestMockResponseFile implements Saveable<String> {

    @XmlElement
    private String id;
    @XmlElement
    private String name;
    @XmlElement
    private String body;
    @XmlElement
    private String methodId;
    @XmlElement
    private RestMockResponseStatus status;
    @XmlElement
    private Integer httpStatusCode;
    @XmlElement
    private Boolean usingExpressions;
    @XmlElementWrapper(name = "httpHeaders")
    @XmlElement(name = "httpHeader")
    private List<HttpHeaderFile> httpHeaders;
    @XmlElementWrapper(name = "contentEncodings")
    @XmlElement(name = "contentEncoding")
    private List<HttpContentEncoding> contentEncodings;
    @XmlElementWrapper(name = "parameterQueries")
    @XmlElement(name = "parameterQuery")
    private List<RestParameterQueryFile> parameterQueries;
    @XmlElementWrapper(name = "xpathExpressions")
    @XmlElement(name = "xpathExpression")
    private List<RestXPathExpressionFile> xpathExpressions;
    @XmlElementWrapper(name = "jsonPathExpressions")
    @XmlElement(name = "jsonPathExpression")
    private List<RestJsonPathExpressionFile> jsonPathExpressions;
    @XmlElementWrapper(name = "headerQueries")
    @XmlElement(name = "headerQuery")
    private List<RestHeaderQueryFile> headerQueries;

    private RestMockResponseFile() {

    }

    private RestMockResponseFile(final Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "id");
        this.status = Objects.requireNonNull(builder.status, "status");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.methodId = Objects.requireNonNull(builder.methodId, "methodId");
        this.httpStatusCode = Objects.requireNonNull(builder.httpStatusCode, "httpStatusCode");
        this.usingExpressions = builder.usingExpressions;
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
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }

    public String getMethodId() {
        return methodId;
    }

    public RestMockResponseStatus getStatus() {
        return status;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public Boolean getUsingExpressions() {
        return usingExpressions;
    }

    public List<HttpHeaderFile> getHttpHeaders() {
        return httpHeaders;
    }

    public List<HttpContentEncoding> getContentEncodings() {
        return contentEncodings;
    }

    public List<RestParameterQueryFile> getParameterQueries() {
        return parameterQueries;
    }

    public List<RestXPathExpressionFile> getXpathExpressions() {
        return xpathExpressions;
    }

    public List<RestJsonPathExpressionFile> getJsonPathExpressions() {
        return jsonPathExpressions;
    }

    public List<RestHeaderQueryFile> getHeaderQueries() {
        return headerQueries;
    }

    @Override
    public boolean equals(final Object o) {
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


        public Builder id(final String id) {
            this.id = id;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder body(final String body) {
            this.body = body;
            return this;
        }

        public Builder methodId(final String methodId) {
            this.methodId = methodId;
            return this;
        }

        public Builder status(final RestMockResponseStatus status) {
            this.status = status;
            return this;
        }

        public Builder httpStatusCode(final Integer httpStatusCode) {
            this.httpStatusCode = httpStatusCode;
            return this;
        }

        public Builder usingExpressions(final Boolean usingExpressions) {
            this.usingExpressions = usingExpressions;
            return this;
        }

        public Builder httpHeaders(final List<HttpHeaderFile> httpHeaders) {
            this.httpHeaders = httpHeaders;
            return this;
        }

        public Builder contentEncodings(final List<HttpContentEncoding> contentEncodings) {
            this.contentEncodings = contentEncodings;
            return this;
        }

        public Builder parameterQueries(final List<RestParameterQueryFile> parameterQueries) {
            this.parameterQueries = parameterQueries;
            return this;
        }

        public Builder xpathExpressions(final List<RestXPathExpressionFile> xpathExpressions) {
            this.xpathExpressions = xpathExpressions;
            return this;
        }

        public Builder jsonPathExpressions(final List<RestJsonPathExpressionFile> jsonPathExpressions) {
            this.jsonPathExpressions = jsonPathExpressions;
            return this;
        }

        public Builder headerQueries(final List<RestHeaderQueryFile> headerQueries) {
            this.headerQueries = headerQueries;
            return this;
        }

        public RestMockResponseFile build() {
            return new RestMockResponseFile(this);
        }
    }
}