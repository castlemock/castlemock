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
public class SoapMockResponse {

    private String id;
    private String name;
    private String body;
    private String operationId;
    private SoapMockResponseStatus status;
    private Integer httpStatusCode;
    private boolean usingExpressions;
    @Deprecated
    private String xpathExpression;
    private List<HttpHeader> httpHeaders = new CopyOnWriteArrayList<HttpHeader>();
    private List<ContentEncoding> contentEncodings = new CopyOnWriteArrayList<ContentEncoding>();
    private List<SoapXPathExpression> xpathExpressions = new CopyOnWriteArrayList<SoapXPathExpression>();

    public SoapMockResponse(){

    }

    private SoapMockResponse(final Builder builder){
        this.id = builder.id;
        this.name = Objects.requireNonNull(builder.name);
        this.body = Objects.requireNonNull(builder.body);
        this.operationId = Objects.requireNonNull(builder.operationId);
        this.status = Objects.requireNonNull(builder.status);
        this.httpStatusCode = Objects.requireNonNull(builder.httpStatusCode);
        this.usingExpressions = Objects.requireNonNull(builder.usingExpressions);
        this.xpathExpression = builder.xpathExpression;
        this.httpHeaders = Optional.ofNullable(builder.httpHeaders).orElseGet(CopyOnWriteArrayList::new);
        this.contentEncodings = Optional.ofNullable(builder.contentEncodings).orElseGet(CopyOnWriteArrayList::new);
        this.xpathExpressions = Optional.ofNullable(builder.xpathExpressions).orElseGet(CopyOnWriteArrayList::new);

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

    @XmlElement
    @Deprecated
    public String getXpathExpression() {
        return xpathExpression;
    }

    @Deprecated
    public void setXpathExpression(String xpathExpression) {
        this.xpathExpression = xpathExpression;
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
    public List<SoapXPathExpression> getXpathExpressions() {
        return xpathExpressions;
    }

    public void setXpathExpressions(List<SoapXPathExpression> xpathExpressions) {
        this.xpathExpressions = xpathExpressions;
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
                .xpathExpression(xpathExpression)
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

    public static final class Builder {
        private String id;
        private String name;
        private String body;
        private String operationId;
        private SoapMockResponseStatus status;
        private Integer httpStatusCode;
        private Boolean usingExpressions;
        private String xpathExpression;
        private List<HttpHeader> httpHeaders = new CopyOnWriteArrayList<HttpHeader>();
        private List<ContentEncoding> contentEncodings = new CopyOnWriteArrayList<ContentEncoding>();
        private List<SoapXPathExpression> xpathExpressions = new CopyOnWriteArrayList<SoapXPathExpression>();

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

        public Builder xpathExpression(final String xpathExpression) {
            this.xpathExpression = xpathExpression;
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
