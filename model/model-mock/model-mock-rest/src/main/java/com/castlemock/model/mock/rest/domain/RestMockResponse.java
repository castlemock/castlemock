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

import com.castlemock.model.core.model.http.domain.ContentEncoding;
import com.castlemock.model.core.model.http.domain.HttpHeader;

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

    public RestMockResponse(){

    }

    private RestMockResponse(final Builder builder){
        this.id = builder.id;
        this.name = Objects.requireNonNull(builder.name);
        this.body = Objects.requireNonNull(builder.body);
        this.methodId = Objects.requireNonNull(builder.methodId);
        this.httpStatusCode = Objects.requireNonNull(builder.httpStatusCode);
        this.status = Objects.requireNonNull(builder.status);
        this.usingExpressions = Objects.requireNonNull(builder.usingExpressions);
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
    public boolean isUsingExpressions() {
        return usingExpressions;
    }

    public void setUsingExpressions(boolean usingExpressions) {
        this.usingExpressions = usingExpressions;
    }

    @XmlElementWrapper(name = "httpHeaders")
    @XmlElement(name = "httpHeader")
    public List<HttpHeader> getHttpHeaders() {
        return httpHeaders;
    }

    public void setHttpHeaders(List<HttpHeader> httpHeaders) {
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

    @XmlElementWrapper(name = "parameterQueries")
    @XmlElement(name = "parameterQuery")
    public List<RestParameterQuery> getParameterQueries() {
        return parameterQueries;
    }

    public void setParameterQueries(List<RestParameterQuery> parameterQueries) {
        this.parameterQueries = parameterQueries;
    }


    @XmlElementWrapper(name = "xpathExpressions")
    @XmlElement(name = "xpathExpression")
    public List<RestXPathExpression> getXpathExpressions() {
        return xpathExpressions;
    }

    public void setXpathExpressions(List<RestXPathExpression> xpathExpressions) {
        this.xpathExpressions = xpathExpressions;
    }


    @XmlElementWrapper(name = "jsonPathExpressions")
    @XmlElement(name = "jsonPathExpression")
    public List<RestJsonPathExpression> getJsonPathExpressions() {
        return jsonPathExpressions;
    }

    public void setJsonPathExpressions(List<RestJsonPathExpression> jsonPathExpressions) {
        this.jsonPathExpressions = jsonPathExpressions;
    }

    @XmlElementWrapper(name = "headerQueries")
    @XmlElement(name = "headerQuery")
    public List<RestHeaderQuery> getHeaderQueries() {
        return headerQueries;
    }

    public void setHeaderQueries(List<RestHeaderQuery> headerQueries) {
        this.headerQueries = headerQueries;
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
