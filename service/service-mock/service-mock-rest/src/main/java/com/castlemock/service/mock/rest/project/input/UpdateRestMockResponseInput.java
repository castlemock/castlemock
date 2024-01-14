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

import com.castlemock.model.core.Input;
import com.castlemock.model.core.http.HttpContentEncoding;
import com.castlemock.model.core.http.HttpHeader;
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
public final class UpdateRestMockResponseInput implements Input {

    private final String restProjectId;
    private final String restApplicationId;
    private final String restResourceId;
    private final String restMethodId;
    private final String restMockResponseId;
    private final String name;
    private final Integer httpStatusCode;
    private final RestMockResponseStatus status;
    private final String body;
    private final Boolean usingExpressions;
    private final List<HttpHeader> httpHeaders;
    private final List<HttpContentEncoding> contentEncodings;
    private final List<RestParameterQuery> parameterQueries;
    private final List<RestXPathExpression> xpathExpressions;
    private final List<RestJsonPathExpression> jsonPathExpressions;
    private final List<RestHeaderQuery> headerQueries;

    private UpdateRestMockResponseInput(final Builder builder) {
        this.restProjectId = Objects.requireNonNull(builder.restProjectId, "restProjectId");
        this.restApplicationId = Objects.requireNonNull(builder.restApplicationId, "restApplicationId");
        this.restResourceId = Objects.requireNonNull(builder.restResourceId, "restResourceId");
        this.restMethodId = Objects.requireNonNull(builder.restMethodId, "restMethodId");
        this.restMockResponseId = Objects.requireNonNull(builder.restMockResponseId, "restMockResponseId");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.httpStatusCode = Objects.requireNonNull(builder.httpStatusCode, "httpStatusCode");
        this.status = Objects.requireNonNull(builder.status, "status");
        this.body = builder.body;
        this.usingExpressions = builder.usingExpressions;
        this.httpHeaders = builder.httpHeaders;
        this.contentEncodings = builder.contentEncodings;
        this.parameterQueries = builder.parameterQueries;
        this.xpathExpressions = builder.xpathExpressions;
        this.jsonPathExpressions = builder.jsonPathExpressions;
        this.headerQueries = builder.headerQueries;
    }

    public String getRestProjectId() {
        return restProjectId;
    }

    public String getRestApplicationId() {
        return restApplicationId;
    }

    public String getRestResourceId() {
        return restResourceId;
    }

    public String getRestMethodId() {
        return restMethodId;
    }

    public String getRestMockResponseId() {
        return restMockResponseId;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getBody() {
        return Optional.ofNullable(body);
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public RestMockResponseStatus getStatus() {
        return status;
    }

    public Optional<Boolean> getUsingExpressions() {
        return Optional.ofNullable(usingExpressions);
    }

    public List<HttpHeader> getHttpHeaders() {
        return Optional.ofNullable(httpHeaders)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    public List<HttpContentEncoding> getContentEncodings() {
        return Optional.ofNullable(contentEncodings)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    public List<RestParameterQuery> getParameterQueries() {
        return Optional.ofNullable(parameterQueries)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    public List<RestXPathExpression> getXpathExpressions() {
        return Optional.ofNullable(xpathExpressions)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    public List<RestJsonPathExpression> getJsonPathExpressions() {
        return Optional.ofNullable(jsonPathExpressions)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    public List<RestHeaderQuery> getHeaderQueries() {
        return Optional.ofNullable(headerQueries)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private String restProjectId;
        private String restApplicationId;
        private String restResourceId;
        private String restMethodId;
        private String restMockResponseId;
        private String name;
        private String body;
        private Integer httpStatusCode;
        private RestMockResponseStatus status;
        private Boolean usingExpressions;
        private List<HttpHeader> httpHeaders;
        private List<HttpContentEncoding> contentEncodings;
        private List<RestParameterQuery> parameterQueries;
        private List<RestXPathExpression> xpathExpressions;
        private List<RestJsonPathExpression> jsonPathExpressions;
        private List<RestHeaderQuery> headerQueries;

        public Builder restProjectId(final String restProjectId){
            this.restProjectId = restProjectId;
            return this;
        }

        public Builder restApplicationId(final String restApplicationId){
            this.restApplicationId = restApplicationId;
            return this;
        }

        public Builder restResourceId(final String restResourceId){
            this.restResourceId = restResourceId;
            return this;
        }

        public Builder restMethodId(final String restMethodId){
            this.restMethodId = restMethodId;
            return this;
        }

        public Builder restMockResponseId(final String restMockResponseId){
            this.restMockResponseId = restMockResponseId;
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

        public Builder contentEncodings(final List<HttpContentEncoding> contentEncodings) {
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

        public UpdateRestMockResponseInput build(){
            return new UpdateRestMockResponseInput(this);
        }

    }

}
