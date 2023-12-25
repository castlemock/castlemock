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

package com.castlemock.service.mock.soap.project.input;

import com.castlemock.model.core.Input;
import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.model.core.validation.NotNull;
import com.castlemock.model.mock.soap.domain.SoapMockResponseStatus;
import com.castlemock.model.mock.soap.domain.SoapXPathExpression;
import com.castlemock.model.mock.soap.domain.SoapExpressionType;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Karl Dahlgren
 * @since 1.52
 */
public final class UpdateSoapMockResponseInput implements Input {

    @NotNull
    private final String projectId;
    @NotNull
    private final String portId;
    @NotNull
    private final String operationId;
    @NotNull
    private final String mockResponseId;
    @NotNull
    private final String name;
    @NotNull
    private final String body;
    @NotNull
    private final SoapMockResponseStatus status;
    @NotNull
    private final Integer httpStatusCode;
    @NotNull
    private final boolean usingExpressions;
    private final SoapExpressionType expressionType;
    @NotNull
    private final List<HttpHeader> httpHeaders;
    @NotNull
    private final List<SoapXPathExpression> xpathExpressions;

    public UpdateSoapMockResponseInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId);
        this.portId = Objects.requireNonNull(builder.portId);
        this.operationId = Objects.requireNonNull(builder.operationId);
        this.mockResponseId = Objects.requireNonNull(builder.mockResponseId);
        this.name = Objects.requireNonNull(builder.name);
        this.body = Objects.requireNonNull(builder.body);
        this.status = Objects.requireNonNull(builder.status);
        this.httpStatusCode = Objects.requireNonNull(builder.httpStatusCode);
        this.usingExpressions = Objects.requireNonNull(builder.usingExpressions);
        if (this.usingExpressions) {
            this.expressionType = Objects.requireNonNull(builder.expressionType);
        } else {
            this.expressionType = null;
        }
        this.httpHeaders = Optional.ofNullable(builder.httpHeaders).orElseGet(CopyOnWriteArrayList::new);
        this.xpathExpressions = Optional.ofNullable(builder.xpathExpressions).orElseGet(CopyOnWriteArrayList::new);
    }

    public String getProjectId() {
        return projectId;
    }

    public String getPortId() {
        return portId;
    }

    public String getOperationId() {
        return operationId;
    }

    public String getMockResponseId() {
        return mockResponseId;
    }

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }

    public SoapMockResponseStatus getStatus() {
        return status;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public boolean isUsingExpressions() {
        return usingExpressions;
    }

    public SoapExpressionType getExpressionType() {
        return expressionType;
    }

    public List<HttpHeader> getHttpHeaders() {
        return httpHeaders;
    }

    public List<SoapXPathExpression> getXpathExpressions() {
        return xpathExpressions;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String projectId;
        private String portId;
        private String operationId;
        private String mockResponseId;
        private String name;
        private String body;
        private SoapMockResponseStatus status;
        private Integer httpStatusCode;
        private Boolean usingExpressions;
        private SoapExpressionType expressionType;
        private List<HttpHeader> httpHeaders;
        private List<SoapXPathExpression> xpathExpressions;

        private Builder(){

        }

        public Builder projectId(final String projectId){
            this.projectId = projectId;
            return this;
        }

        public Builder portId(final String portId){
            this.portId = portId;
            return this;
        }

        public Builder operationId(final String operationId){
            this.operationId = operationId;
            return this;
        }

        public Builder mockResponseId(final String mockResponseId){
            this.mockResponseId = mockResponseId;
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

        public Builder expressionType(final SoapExpressionType expressionType) {
            this.expressionType = expressionType;
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

        public UpdateSoapMockResponseInput build(){
            return new UpdateSoapMockResponseInput(this);
        }
    }
}
