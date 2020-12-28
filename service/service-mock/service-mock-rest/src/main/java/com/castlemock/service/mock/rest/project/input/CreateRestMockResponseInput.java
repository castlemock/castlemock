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

package com.castlemock.service.mock.rest.project.input;

import com.castlemock.model.core.model.Input;
import com.castlemock.model.core.model.http.domain.ContentEncoding;
import com.castlemock.model.core.model.http.domain.HttpHeader;
import com.castlemock.model.core.model.validation.NotNull;
import com.castlemock.model.mock.rest.domain.RestHeaderQuery;
import com.castlemock.model.mock.rest.domain.RestJsonPathExpression;
import com.castlemock.model.mock.rest.domain.RestMockResponseStatus;
import com.castlemock.model.mock.rest.domain.RestParameterQuery;
import com.castlemock.model.mock.rest.domain.RestXPathExpression;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class CreateRestMockResponseInput implements Input {

    @NotNull
    private final String projectId;
    @NotNull
    private final String applicationId;
    @NotNull
    private final String resourceId;
    @NotNull
    private final String methodId;
    @NotNull
    private final String name;
    private final String body;
    private final Integer httpStatusCode;
    private final RestMockResponseStatus status;
    private final Boolean usingExpressions;
    private final List<HttpHeader> httpHeaders;
    private final List<ContentEncoding> contentEncodings;
    private final List<RestParameterQuery> parameterQueries;
    private final List<RestXPathExpression> xpathExpressions;
    private final List<RestJsonPathExpression> jsonPathExpressions;
    private final List<RestHeaderQuery> headerQueries;

    private CreateRestMockResponseInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId);
        this.applicationId = Objects.requireNonNull(builder.applicationId);
        this.resourceId = Objects.requireNonNull(builder.resourceId);
        this.methodId = Objects.requireNonNull(builder.methodId);
        this.name = Objects.requireNonNull(builder.name);
        this.body = builder.body;
        this.httpStatusCode = builder.httpStatusCode;
        this.status = builder.status;
        this.usingExpressions = builder.usingExpressions;
        this.httpHeaders = builder.httpHeaders;
        this.contentEncodings = builder.contentEncodings;
        this.parameterQueries = builder.parameterQueries;
        this.xpathExpressions = builder.xpathExpressions;
        this.jsonPathExpressions = builder.jsonPathExpressions;
        this.headerQueries = builder.headerQueries;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getMethodId() {
        return methodId;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getBody() {
        return Optional.ofNullable(body);
    }

    public Optional<Integer> getHttpStatusCode() {
        return Optional.ofNullable(httpStatusCode);
    }

    public Optional<RestMockResponseStatus> getStatus() {
        return Optional.ofNullable(status);
    }

    public Optional<Boolean> getUsingExpressions() {
        return Optional.ofNullable(usingExpressions);
    }

    public Optional<List<HttpHeader>> getHttpHeaders() {
        return Optional.ofNullable(httpHeaders);
    }

    public Optional<List<ContentEncoding>> getContentEncodings() {
        return Optional.ofNullable(contentEncodings);
    }

    public Optional<List<RestParameterQuery>> getParameterQueries() {
        return Optional.ofNullable(parameterQueries);
    }

    public Optional<List<RestXPathExpression>> getXpathExpressions() {
        return Optional.ofNullable(xpathExpressions);
    }

    public Optional<List<RestJsonPathExpression>> getJsonPathExpressions() {
        return Optional.ofNullable(jsonPathExpressions);
    }

    public Optional<List<RestHeaderQuery>> getHeaderQueries() {
        return Optional.ofNullable(headerQueries);
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {
        private String projectId;
        private String applicationId;
        private String resourceId;
        private String methodId;
        private String name;
        private String body;
        private Integer httpStatusCode;
        private RestMockResponseStatus status;
        private Boolean usingExpressions;
        private List<HttpHeader> httpHeaders;
        private List<ContentEncoding> contentEncodings;
        private List<RestParameterQuery> parameterQueries;
        private List<RestXPathExpression> xpathExpressions;
        private List<RestJsonPathExpression> jsonPathExpressions;
        private List<RestHeaderQuery> headerQueries;

        public Builder projectId(final String restProjectId){
            this.projectId = restProjectId;
            return this;
        }

        public Builder applicationId(final String applicationId){
            this.applicationId = applicationId;
            return this;
        }

        public Builder resourceId(final String resourceId){
            this.resourceId = resourceId;
            return this;
        }

        public Builder methodId(final String methodId){
            this.methodId = methodId;
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


        public CreateRestMockResponseInput build(){
            return new CreateRestMockResponseInput(this);
        }
    }



}
