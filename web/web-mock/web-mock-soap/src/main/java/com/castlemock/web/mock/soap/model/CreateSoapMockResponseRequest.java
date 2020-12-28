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

import com.castlemock.model.core.model.http.domain.HttpHeader;
import com.castlemock.model.mock.soap.domain.SoapMockResponseStatus;
import com.castlemock.model.mock.soap.domain.SoapXPathExpression;

import java.util.List;
import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.52
 */
public class CreateSoapMockResponseRequest {

    private String name;
    private String body;
    private SoapMockResponseStatus status;
    private Integer httpStatusCode;
    private Boolean usingExpressions;
    private List<HttpHeader> httpHeaders;
    private List<SoapXPathExpression> xpathExpressions;

    public CreateSoapMockResponseRequest(){

    }

    private CreateSoapMockResponseRequest(final Builder builder){
        this.name = Objects.requireNonNull(builder.name);
        this.status = Objects.requireNonNull(builder.status);
        this.body = builder.body;
        this.httpStatusCode = builder.httpStatusCode;
        this.usingExpressions = builder.usingExpressions;
        this.httpHeaders = builder.httpHeaders;
        this.xpathExpressions = builder.xpathExpressions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SoapMockResponseStatus getStatus() {
        return status;
    }

    public void setStatus(SoapMockResponseStatus status) {
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public Boolean getUsingExpressions() {
        return usingExpressions;
    }

    public void setUsingExpressions(Boolean usingExpressions) {
        this.usingExpressions = usingExpressions;
    }

    public List<HttpHeader> getHttpHeaders() {
        return httpHeaders;
    }

    public void setHttpHeaders(List<HttpHeader> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public List<SoapXPathExpression> getXpathExpressions() {
        return xpathExpressions;
    }

    public void setXpathExpressions(List<SoapXPathExpression> xpathExpressions) {
        this.xpathExpressions = xpathExpressions;
    }

    public static Builder builder() {
        return new Builder();
    }

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
