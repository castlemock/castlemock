/*
 * Copyright 2020 Karl Dahlgren
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

package com.castlemock.web.mock.rest.model;

import com.castlemock.model.core.http.HttpContentEncoding;
import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.model.mock.rest.domain.RestHeaderQuery;
import com.castlemock.model.mock.rest.domain.RestJsonPathExpression;
import com.castlemock.model.mock.rest.domain.RestMockResponseStatus;
import com.castlemock.model.mock.rest.domain.RestParameterQuery;
import com.castlemock.model.mock.rest.domain.RestXPathExpression;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.52
 */
@XmlRootElement
@JsonDeserialize(builder = UpdateRestMockResponseRequest.Builder.class)
public class UpdateRestMockResponseRequest {

    private final String name;
    private final String body;
    private final Integer httpStatusCode;
    private final RestMockResponseStatus status;
    private final Boolean usingExpressions;
    private final List<HttpHeader> httpHeaders;
    private final List<HttpContentEncoding> contentEncodings;
    private final List<RestParameterQuery> parameterQueries;
    private final List<RestXPathExpression> xpathExpressions;
    private final List<RestJsonPathExpression> jsonPathExpressions;
    private final List<RestHeaderQuery> headerQueries;


    private UpdateRestMockResponseRequest(final Builder builder){
        this.name = Objects.requireNonNull(builder.name, "name");
        this.body = Objects.requireNonNull(builder.body, "body");
        this.httpStatusCode = Objects.requireNonNull(builder.httpStatusCode, "httpStatusCode");
        this.status = Objects.requireNonNull(builder.status, "status");
        this.usingExpressions = builder.usingExpressions;
        this.httpHeaders = Optional.ofNullable(builder.httpHeaders).orElseGet(List::of);
        this.contentEncodings = Optional.ofNullable(builder.contentEncodings).orElseGet(List::of);
        this.parameterQueries = Optional.ofNullable(builder.parameterQueries).orElseGet(List::of);
        this.xpathExpressions = Optional.ofNullable(builder.xpathExpressions).orElseGet(List::of);
        this.jsonPathExpressions = Optional.ofNullable(builder.jsonPathExpressions).orElseGet(List::of);
        this.headerQueries =Optional.ofNullable(builder.headerQueries).orElseGet(List::of);
    }

    @XmlElement
    public String getName() {
        return name;
    }

    @XmlElement
    public String getBody() {
        return body;
    }

    @XmlElement
    public RestMockResponseStatus getStatus() {
        return status;
    }

    @XmlElement
    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    @XmlElement
    public Optional<Boolean> getUsingExpressions() {
        return Optional.ofNullable(usingExpressions);
    }

    @XmlElementWrapper(name = "httpHeaders")
    @XmlElement(name = "httpHeader")
    public List<HttpHeader> getHttpHeaders() {
        return Optional.ofNullable(httpHeaders)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    @XmlElementWrapper(name = "contentEncodings")
    @XmlElement(name = "contentEncoding")
    public List<HttpContentEncoding> getContentEncodings() {
        return Optional.ofNullable(contentEncodings)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    @XmlElementWrapper(name = "parameterQueries")
    @XmlElement(name = "parameterQuery")
    public List<RestParameterQuery> getParameterQueries() {
        return Optional.ofNullable(parameterQueries)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    @XmlElementWrapper(name = "xpathExpressions")
    @XmlElement(name = "xpathExpression")
    public List<RestXPathExpression> getXpathExpressions() {
        return Optional.ofNullable(xpathExpressions)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    @XmlElementWrapper(name = "jsonPathExpressions")
    @XmlElement(name = "jsonPathExpression")
    public List<RestJsonPathExpression> getJsonPathExpressions() {
        return Optional.ofNullable(jsonPathExpressions)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    @XmlElementWrapper(name = "headerQueries")
    @XmlElement(name = "headerQuery")
    public List<RestHeaderQuery> getHeaderQueries() {
        return Optional.ofNullable(headerQueries)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UpdateRestMockResponseRequest that = (UpdateRestMockResponseRequest) o;
        return usingExpressions == that.usingExpressions &&
                Objects.equals(name, that.name) &&
                Objects.equals(body, that.body) &&
                Objects.equals(httpStatusCode, that.httpStatusCode) &&
                status == that.status &&
                Objects.equals(httpHeaders, that.httpHeaders) &&
                Objects.equals(contentEncodings, that.contentEncodings) &&
                Objects.equals(parameterQueries, that.parameterQueries) &&
                Objects.equals(xpathExpressions, that.xpathExpressions) &&
                Objects.equals(jsonPathExpressions, that.jsonPathExpressions) &&
                Objects.equals(headerQueries, that.headerQueries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, body, httpStatusCode, status, usingExpressions, httpHeaders, contentEncodings, parameterQueries, xpathExpressions, jsonPathExpressions, headerQueries);
    }

    @Override
    public String toString() {
        return "RestMockResponse{" +
                ", name='" + name + '\'' +
                ", body='" + body + '\'' +
                ", httpStatusCode=" + httpStatusCode +
                ", status=" + status +
                ", usingExpressions=" + usingExpressions +
                ", httpHeaders=" + httpHeaders +
                ", contentEncodings=" + contentEncodings +
                ", parameterQueries=" + parameterQueries +
                ", xpathExpressions=" + xpathExpressions +
                ", jsonPathExpressions=" + jsonPathExpressions +
                ", headerQueries=" + headerQueries +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {

        private String name;
        private String body;
        private Integer httpStatusCode;
        private RestMockResponseStatus status;
        private Boolean usingExpressions;
        private List<HttpHeader> httpHeaders;
        private List<HttpContentEncoding> contentEncodings;
        private List<RestParameterQuery> parameterQueries;
        private List<RestXPathExpression> xpathExpressions;
        private List<RestJsonPathExpression> jsonPathExpressions;
        private List<RestHeaderQuery> headerQueries;

        private Builder() {
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder body(final String body) {
            this.body = body;
            return this;
        }

        public Builder httpStatusCode(final Integer httpStatusCode) {
            this.httpStatusCode = httpStatusCode;
            return this;
        }

        public Builder status(final RestMockResponseStatus status) {
            this.status = status;
            return this;
        }

        public Builder usingExpressions(final Boolean usingExpressions) {
            this.usingExpressions = usingExpressions;
            return this;
        }

        public Builder httpHeaders(final List<HttpHeader> httpHeaders) {
            this.httpHeaders = httpHeaders;
            return this;
        }

        public Builder contentEncodings(final List<HttpContentEncoding> contentEncodings) {
            this.contentEncodings = contentEncodings;
            return this;
        }

        public Builder parameterQueries(final List<RestParameterQuery> parameterQueries) {
            this.parameterQueries = parameterQueries;
            return this;
        }

        public Builder xpathExpressions(final List<RestXPathExpression> xpathExpressions) {
            this.xpathExpressions = xpathExpressions;
            return this;
        }

        public Builder jsonPathExpressions(final List<RestJsonPathExpression> jsonPathExpressions) {
            this.jsonPathExpressions = jsonPathExpressions;
            return this;
        }

        public Builder headerQueries(final List<RestHeaderQuery> headerQueries) {
            this.headerQueries = headerQueries;
            return this;
        }

        public UpdateRestMockResponseRequest build() {
            return new UpdateRestMockResponseRequest(this);
        }
    }
}
