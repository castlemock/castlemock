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
import com.castlemock.model.core.http.ContentEncoding;
import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.model.core.validation.NotNull;
import com.castlemock.model.mock.rest.domain.RestHeaderQuery;
import com.castlemock.model.mock.rest.domain.RestJsonPathExpression;
import com.castlemock.model.mock.rest.domain.RestMockResponseStatus;
import com.castlemock.model.mock.rest.domain.RestParameterQuery;
import com.castlemock.model.mock.rest.domain.RestXPathExpression;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class UpdateRestMockResponseInput implements Input {

    @NotNull
    private final String restProjectId;
    @NotNull
    private final String restApplicationId;
    @NotNull
    private final String restResourceId;
    @NotNull
    private final String restMethodId;
    @NotNull
    private final String restMockResponseId;
    @NotNull
    private final String name;
    @NotNull
    private final String body;
    @NotNull
    private final Integer httpStatusCode;
    @NotNull
    private final RestMockResponseStatus status;
    @NotNull
    private final boolean usingExpressions;
    @NotNull
    private final List<HttpHeader> httpHeaders;
    @NotNull
    private final List<ContentEncoding> contentEncodings;
    @NotNull
    private final List<RestParameterQuery> parameterQueries;
    @NotNull
    private final List<RestXPathExpression> xpathExpressions;
    @NotNull
    private final List<RestJsonPathExpression> jsonPathExpressions;
    @NotNull
    private final List<RestHeaderQuery> headerQueries;

    private UpdateRestMockResponseInput(final Builder builder) {
        this.restProjectId = Objects.requireNonNull(builder.restProjectId);
        this.restApplicationId = Objects.requireNonNull(builder.restApplicationId);
        this.restResourceId = Objects.requireNonNull(builder.restResourceId);
        this.restMethodId = Objects.requireNonNull(builder.restMethodId);
        this.restMockResponseId = Objects.requireNonNull(builder.restMockResponseId);
        this.name = Objects.requireNonNull(builder.name);
        this.body = Objects.requireNonNull(builder.body);
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

    public String getBody() {
        return body;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public RestMockResponseStatus getStatus() {
        return status;
    }

    public boolean isUsingExpressions() {
        return usingExpressions;
    }

    public List<HttpHeader> getHttpHeaders() {
        return httpHeaders;
    }

    public List<ContentEncoding> getContentEncodings() {
        return contentEncodings;
    }

    public List<RestParameterQuery> getParameterQueries() {
        return parameterQueries;
    }

    public List<RestXPathExpression> getXpathExpressions() {
        return xpathExpressions;
    }

    public List<RestJsonPathExpression> getJsonPathExpressions() {
        return jsonPathExpressions;
    }

    public List<RestHeaderQuery> getHeaderQueries() {
        return headerQueries;
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
        private List<HttpHeader> httpHeaders = new CopyOnWriteArrayList<>();
        private List<ContentEncoding> contentEncodings = new CopyOnWriteArrayList<>();
        private List<RestParameterQuery> parameterQueries = new CopyOnWriteArrayList<>();
        private List<RestXPathExpression> xpathExpressions = new CopyOnWriteArrayList<>();
        private List<RestJsonPathExpression> jsonPathExpressions = new CopyOnWriteArrayList<>();
        private List<RestHeaderQuery> headerQueries = new CopyOnWriteArrayList<>();

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

        public UpdateRestMockResponseInput build(){
            return new UpdateRestMockResponseInput(this);
        }

    }

}
