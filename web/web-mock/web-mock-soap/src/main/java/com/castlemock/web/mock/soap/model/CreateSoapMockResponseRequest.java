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

package com.castlemock.web.mock.soap.model;

import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.model.mock.soap.domain.SoapMockResponseStatus;
import com.castlemock.model.mock.soap.domain.SoapXPathExpression;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.52
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = CreateSoapMockResponseRequest.Builder.class)
public class CreateSoapMockResponseRequest {

    private final String name;
    private final String body;
    private final SoapMockResponseStatus status;
    private final Integer httpStatusCode;
    private final Boolean usingExpressions;
    private final List<HttpHeader> httpHeaders;
    private final List<SoapXPathExpression> xpathExpressions;

    private CreateSoapMockResponseRequest(final Builder builder){
        this.name = Objects.requireNonNull(builder.name, "name");
        this.status = Objects.requireNonNull(builder.status, "status");
        this.body = builder.body;
        this.httpStatusCode = builder.httpStatusCode;
        this.usingExpressions = builder.usingExpressions;
        this.httpHeaders = Optional.ofNullable(builder.httpHeaders).orElseGet(List::of);
        this.xpathExpressions = Optional.ofNullable(builder.xpathExpressions).orElseGet(List::of);
    }

    public String getName() {
        return name;
    }

    public SoapMockResponseStatus getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public Boolean getUsingExpressions() {
        return usingExpressions;
    }

    public List<HttpHeader> getHttpHeaders() {
        return Optional.ofNullable(httpHeaders)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    public List<SoapXPathExpression> getXpathExpressions() {
        return Optional.ofNullable(xpathExpressions)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {

        private String name;
        private String body;
        private SoapMockResponseStatus status;
        private Integer httpStatusCode;
        private Boolean usingExpressions;
        private List<HttpHeader> httpHeaders;
        private List<SoapXPathExpression> xpathExpressions;

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

        public Builder xpathExpressions(final List<SoapXPathExpression> xpathExpressions) {
            this.xpathExpressions = xpathExpressions;
            return this;
        }

        public CreateSoapMockResponseRequest build() {
            return new CreateSoapMockResponseRequest(this);
        }
    }
}
