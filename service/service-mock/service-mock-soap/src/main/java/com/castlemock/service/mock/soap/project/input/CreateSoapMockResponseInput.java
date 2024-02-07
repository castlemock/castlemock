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

package com.castlemock.service.mock.soap.project.input;

import com.castlemock.model.core.Input;
import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.model.mock.soap.domain.SoapMockResponseStatus;
import com.castlemock.model.mock.soap.domain.SoapXPathExpression;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class CreateSoapMockResponseInput implements Input {

    private final String projectId;
    private final String portId;
    private final String operationId;
    private final String name;
    private final SoapMockResponseStatus status;

    private final String body;
    private final Integer httpStatusCode;
    private final List<HttpHeader> httpHeaders;
    private final List<SoapXPathExpression> xpathExpressions;

    private final Boolean usingExpressions;


    private CreateSoapMockResponseInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.portId = Objects.requireNonNull(builder.portId, "portId");
        this.operationId = Objects.requireNonNull(builder.operationId, "operationId");
        this.name = Objects.requireNonNull(builder.name,"name");
        this.status = Objects.requireNonNull(builder.status, "status");
        this.httpStatusCode = Objects.requireNonNull(builder.httpStatusCode, "httpStatusCode");
        this.httpHeaders = builder.httpHeaders;
        this.xpathExpressions = builder.xpathExpressions;
        this.body = builder.body;
        this.usingExpressions = builder.usingExpressions;
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

    public String getName() {
        return name;
    }

    public SoapMockResponseStatus getStatus() {
        return status;
    }

    public Optional<String> getBody() {
        return Optional.ofNullable(body);
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public Optional<Boolean> getUsingExpressions() {
        return Optional.ofNullable(usingExpressions);
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CreateSoapMockResponseInput that = (CreateSoapMockResponseInput) o;
        return Objects.equals(projectId, that.projectId) && Objects.equals(portId, that.portId) && Objects.equals(operationId, that.operationId) && Objects.equals(name, that.name) && status == that.status && Objects.equals(body, that.body) && Objects.equals(httpStatusCode, that.httpStatusCode) && Objects.equals(httpHeaders, that.httpHeaders) && Objects.equals(xpathExpressions, that.xpathExpressions) && Objects.equals(usingExpressions, that.usingExpressions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, portId, operationId, name, status, body,
                httpStatusCode, httpHeaders, xpathExpressions, usingExpressions);
    }

    @Override
    public String toString() {
        return "CreateSoapMockResponseInput{" +
                "projectId='" + projectId + '\'' +
                ", portId='" + portId + '\'' +
                ", operationId='" + operationId + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", body='" + body + '\'' +
                ", httpStatusCode=" + httpStatusCode +
                ", httpHeaders=" + httpHeaders +
                ", xpathExpressions=" + xpathExpressions +
                ", usingExpressions=" + usingExpressions +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String projectId;
        private String portId;
        private String operationId;
        private String name;
        private String body;
        private SoapMockResponseStatus status;
        private Integer httpStatusCode;
        private Boolean usingExpressions;
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

        public CreateSoapMockResponseInput build(){
            return new CreateSoapMockResponseInput(this);
        }
    }

}
