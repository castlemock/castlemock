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

package com.castlemock.repository.soap.file.project.model;

import com.castlemock.model.core.Saveable;
import com.castlemock.model.core.http.HttpContentEncoding;
import com.castlemock.model.mock.soap.domain.SoapMockResponseStatus;
import com.castlemock.repository.core.file.http.model.HttpHeaderFile;

import jakarta.xml.bind.annotation.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@XmlRootElement(name = "soapMockResponse")
@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({HttpHeaderFile.class, SoapXPathExpressionFile.class})
public class SoapMockResponseFile implements Saveable<String> {

    @XmlElement
    private String id;
    @XmlElement
    private String name;
    @XmlElement
    private String body;
    @XmlElement
    private String operationId;
    @XmlElement
    private SoapMockResponseStatus status;
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
    @XmlElementWrapper(name = "xpathExpressions")
    @XmlElement(name = "xpathExpression")
    private List<SoapXPathExpressionFile> xpathExpressions;

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

    public String getOperationId() {
        return operationId;
    }

    public SoapMockResponseStatus getStatus() {
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

    public List<SoapXPathExpressionFile> getXpathExpressions() {
        return xpathExpressions;
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
        private List<HttpContentEncoding> contentEncodings;
        private List<SoapXPathExpressionFile> xpathExpressions;

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

        public Builder httpHeaders(final List<HttpHeaderFile> httpHeaders) {
            this.httpHeaders = httpHeaders;
            return this;
        }

        public Builder contentEncodings(final List<HttpContentEncoding> contentEncodings) {
            this.contentEncodings = contentEncodings;
            return this;
        }

        public Builder xpathExpressions(final List<SoapXPathExpressionFile> xpathExpressions) {
            this.xpathExpressions = xpathExpressions;
            return this;
        }

        public SoapMockResponseFile build() {
            return new SoapMockResponseFile(this);
        }
    }
}