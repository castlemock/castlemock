/*
 * Copyright 2015 Karl Dahlgren
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

package com.castlemock.model.mock.rest.domain;

import com.castlemock.model.core.http.ContentEncoding;
import com.castlemock.model.core.http.HttpHeader;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
public class RestMockResponse {

    private String id;
    private String name;
    private String body;
    private String methodId;
    private Integer httpStatusCode;
    private RestMockResponseStatus status;
    private boolean usingExpressions;
    private List<HttpHeader> httpHeaders = new CopyOnWriteArrayList<HttpHeader>();
    private List<ContentEncoding> contentEncodings = new CopyOnWriteArrayList<ContentEncoding>();
    private List<RestParameterQuery> parameterQueries = new CopyOnWriteArrayList<RestParameterQuery>();
    private List<RestXPathExpression> xpathExpressions = new CopyOnWriteArrayList<RestXPathExpression>();
    private List<RestJsonPathExpression> jsonPathExpressions = new CopyOnWriteArrayList<RestJsonPathExpression>();
    private List<RestHeaderQuery> headerQueries = new CopyOnWriteArrayList<RestHeaderQuery>();

    private RestMockResponse(){

    }

    private RestMockResponse(final Builder builder){
        this.id = Objects.requireNonNull(builder.id, "id");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.body = builder.body;
        this.methodId = Objects.requireNonNull(builder.methodId, "methodId");
        this.httpStatusCode = Objects.requireNonNull(builder.httpStatusCode, "httpStatusCode");
        this.status = Objects.requireNonNull(builder.status, "status");
        this.usingExpressions = Objects.requireNonNull(builder.usingExpressions, "usingExpressions");
        this.httpHeaders = Optional.ofNullable(builder.httpHeaders).orElseGet(CopyOnWriteArrayList::new);
        this.contentEncodings = Optional.ofNullable(builder.contentEncodings).orElseGet(CopyOnWriteArrayList::new);
        this.parameterQueries = Optional.ofNullable(builder.parameterQueries).orElseGet(CopyOnWriteArrayList::new);
        this.xpathExpressions = Optional.ofNullable(builder.xpathExpressions).orElseGet(CopyOnWriteArrayList::new);
        this.jsonPathExpressions = Optional.ofNullable(builder.jsonPathExpressions).orElseGet(CopyOnWriteArrayList::new);
        this.headerQueries = Optional.ofNullable(builder.headerQueries).orElseGet(CopyOnWriteArrayList::new);
    }

    @XmlElement
    public String getId() {
        return id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    @XmlElement
    public Optional<String> getBody() {
        return Optional.ofNullable(body);
    }

    @XmlElement
    public String getMethodId() {
        return methodId;
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
    public boolean isUsingExpressions() {
        return usingExpressions;
    }

    @XmlElementWrapper(name = "httpHeaders")
    @XmlElement(name = "httpHeader")
    public List<HttpHeader> getHttpHeaders() {
        return httpHeaders;
    }

    @XmlElementWrapper(name = "contentEncodings")
    @XmlElement(name = "contentEncoding")
    public List<ContentEncoding> getContentEncodings() {
        return contentEncodings;
    }

    @XmlElementWrapper(name = "parameterQueries")
    @XmlElement(name = "parameterQuery")
    public List<RestParameterQuery> getParameterQueries() {
        return parameterQueries;
    }

    @XmlElementWrapper(name = "xpathExpressions")
    @XmlElement(name = "xpathExpression")
    public List<RestXPathExpression> getXpathExpressions() {
        return xpathExpressions;
    }

    @XmlElementWrapper(name = "jsonPathExpressions")
    @XmlElement(name = "jsonPathExpression")
    public List<RestJsonPathExpression> getJsonPathExpressions() {
        return jsonPathExpressions;
    }

    @XmlElementWrapper(name = "headerQueries")
    @XmlElement(name = "headerQuery")
    public List<RestHeaderQuery> getHeaderQueries() {
        return headerQueries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestMockResponse that = (RestMockResponse) o;
        return usingExpressions == that.usingExpressions &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(body, that.body) &&
                Objects.equals(methodId, that.methodId) &&
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
        return Objects.hash(id, name, body, methodId, httpStatusCode, status, usingExpressions, httpHeaders, contentEncodings, parameterQueries, xpathExpressions, jsonPathExpressions, headerQueries);
    }

    @Override
    public String toString() {
        return "RestMockResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", body='" + body + '\'' +
                ", methodId='" + methodId + '\'' +
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



    public Builder toBuilder() {
        return builder()
                .id(id)
                .name(name)
                .body(body)
                .methodId(methodId)
                .status(status)
                .httpStatusCode(httpStatusCode)
                .usingExpressions(usingExpressions)
                .httpHeaders(httpHeaders.stream()
                        .map(HttpHeader::toBuilder)
                        .map(HttpHeader.Builder::build)
                        .collect(Collectors.toList()))
                .contentEncodings(new ArrayList<>(contentEncodings))
                .parameterQueries(parameterQueries
                        .stream()
                        .map(RestParameterQuery::toBuilder)
                        .map(RestParameterQuery.Builder::build)
                        .collect(Collectors.toList()))
                .xpathExpressions(xpathExpressions
                        .stream()
                        .map(RestXPathExpression::toBuilder)
                        .map(RestXPathExpression.Builder::build)
                        .collect(Collectors.toList()))
                .jsonPathExpressions(jsonPathExpressions
                        .stream()
                        .map(RestJsonPathExpression::toBuilder)
                        .map(RestJsonPathExpression.Builder::build)
                        .collect(Collectors.toList()))
                .headerQueries(headerQueries
                        .stream()
                        .map(RestHeaderQuery::toBuilder)
                        .map(RestHeaderQuery.Builder::build)
                        .collect(Collectors.toList()));
    }


    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String name;
        private String body;
        private String methodId;
        private Integer httpStatusCode;
        private RestMockResponseStatus status;
        private Boolean usingExpressions;
        private List<HttpHeader> httpHeaders = new CopyOnWriteArrayList<HttpHeader>();
        private List<ContentEncoding> contentEncodings = new CopyOnWriteArrayList<ContentEncoding>();
        private List<RestParameterQuery> parameterQueries = new CopyOnWriteArrayList<RestParameterQuery>();
        private List<RestXPathExpression> xpathExpressions = new CopyOnWriteArrayList<RestXPathExpression>();
        private List<RestJsonPathExpression> jsonPathExpressions = new CopyOnWriteArrayList<RestJsonPathExpression>();
        private List<RestHeaderQuery> headerQueries = new CopyOnWriteArrayList<RestHeaderQuery>();

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

        public Builder contentEncodings(final List<ContentEncoding> contentEncodings) {
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

        public RestMockResponse build() {
            return new RestMockResponse(this);
        }
    }
}
