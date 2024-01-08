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

package com.castlemock.model.mock.soap.domain;

import com.castlemock.model.core.http.ContentEncoding;
import com.castlemock.model.core.http.HttpHeader;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
public class SoapMockResponse {

    private String id;
    private String name;
    private String body;
    private String operationId;
    private SoapMockResponseStatus status;
    private Integer httpStatusCode;
    private Boolean usingExpressions;
    private List<HttpHeader> httpHeaders;
    private List<ContentEncoding> contentEncodings;
    private List<SoapXPathExpression> xpathExpressions;

    private SoapMockResponse(){

    }

    private SoapMockResponse(final Builder builder){
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
    public String getId() {
        return id;
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
    public String getOperationId() {
        return operationId;
    }

    @XmlElement
    public SoapMockResponseStatus getStatus() {
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
        return httpHeaders;
    }

    @XmlElementWrapper(name = "contentEncodings")
    @XmlElement(name = "contentEncoding")
    public List<ContentEncoding> getContentEncodings() {
        return contentEncodings;
    }
    
    @XmlElementWrapper(name = "xpathExpressions")
    @XmlElement(name = "xpathExpression")
    public List<SoapXPathExpression> getXpathExpressions() {
        return xpathExpressions;
    }

    public Builder toBuilder() {
        return builder()
                .id(id)
                .name(name)
                .body(body)
                .operationId(operationId)
                .status(status)
                .httpStatusCode(httpStatusCode)
                .usingExpressions(usingExpressions)
                .httpHeaders(httpHeaders.stream()
                        .map(HttpHeader::toBuilder)
                        .map(HttpHeader.Builder::build)
                        .collect(Collectors.toList()))
                .contentEncodings(new ArrayList<>(contentEncodings))
                .xpathExpressions(xpathExpressions
                        .stream()
                        .map(SoapXPathExpression::toBuilder)
                        .map(SoapXPathExpression.Builder::build)
                        .collect(Collectors.toList()));
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SoapMockResponse that = (SoapMockResponse) o;
        return usingExpressions == that.usingExpressions && Objects.equals(id, that.id)
                && Objects.equals(name, that.name) && Objects.equals(body, that.body)
                && Objects.equals(operationId, that.operationId) && status == that.status
                && Objects.equals(httpStatusCode, that.httpStatusCode) && Objects.equals(httpHeaders, that.httpHeaders)
                && Objects.equals(contentEncodings, that.contentEncodings) && Objects.equals(xpathExpressions, that.xpathExpressions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, body, operationId, status, httpStatusCode,
                usingExpressions, httpHeaders, contentEncodings, xpathExpressions);
    }

    @Override
    public String toString() {
        return "SoapMockResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", body='" + body + '\'' +
                ", operationId='" + operationId + '\'' +
                ", status=" + status +
                ", httpStatusCode=" + httpStatusCode +
                ", usingExpressions=" + usingExpressions +
                ", httpHeaders=" + httpHeaders +
                ", contentEncodings=" + contentEncodings +
                ", xpathExpressions=" + xpathExpressions +
                '}';
    }

    public static final class Builder {
        private String id;
        private String name;
        private String body;
        private String operationId;
        private SoapMockResponseStatus status;
        private Integer httpStatusCode;
        private Boolean usingExpressions;
        private List<HttpHeader> httpHeaders;
        private List<ContentEncoding> contentEncodings;
        private List<SoapXPathExpression> xpathExpressions;

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

        public Builder operationId(final String operationId) {
            this.operationId = operationId;
            return this;
        }

        public Builder status(final SoapMockResponseStatus status) {
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

        public Builder httpHeaders(final List<HttpHeader> httpHeaders) {
            this.httpHeaders = httpHeaders;
            return this;
        }

        public Builder contentEncodings(final List<ContentEncoding> contentEncodings) {
            this.contentEncodings = contentEncodings;
            return this;
        }

        public Builder xpathExpressions(final List<SoapXPathExpression> xpathExpressions) {
            this.xpathExpressions = xpathExpressions;
            return this;
        }

        public SoapMockResponse build() {
            return new SoapMockResponse(this);
        }
    }
}
