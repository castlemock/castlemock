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

package com.castlemock.model.mock.rest.domain;

import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.core.http.HttpParameter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import jakarta.xml.bind.annotation.*;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = RestRequest.Builder.class)
public class RestRequest {

    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String body;

    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String contentType;

    @XmlElement
    private final String uri;

    @XmlElement
    private final HttpMethod httpMethod;

    @XmlElementWrapper(name = "httpHeaders")
    @XmlElement(name = "httpHeader")
    private final Set<HttpHeader> httpHeaders;

    @XmlElementWrapper(name = "httpParameters")
    @XmlElement(name = "httpParameter")
    private final Set<HttpParameter> httpParameters;

    private RestRequest(final Builder builder){
        this.uri = Objects.requireNonNull(builder.uri, "uri");
        this.httpMethod = Objects.requireNonNull(builder.httpMethod, "httpMethod");
        this.body = builder.body;
        this.contentType = builder.contentType;
        this.httpHeaders = builder.httpHeaders;
        this.httpParameters = builder.httpParameters;
    }

    public Optional<String> getBody() {
        return Optional.ofNullable(body);
    }

    public Optional<String> getContentType() {
        return Optional.ofNullable(contentType);
    }

    public String getUri() {
        return uri;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Set<HttpHeader> getHttpHeaders() {
        return Optional.ofNullable(httpHeaders)
                .map(Set::copyOf)
                .orElseGet(Set::of);
    }

    public Set<HttpParameter> getHttpParameters() {
        return Optional.ofNullable(httpParameters)
                .map(Set::copyOf)
                .orElseGet(Set::of);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RestRequest that)) return false;
        return Objects.equals(body, that.body) &&
                Objects.equals(contentType, that.contentType) &&
                Objects.equals(uri, that.uri) &&
                httpMethod == that.httpMethod &&
                Objects.equals(httpHeaders, that.httpHeaders) &&
                Objects.equals(httpParameters, that.httpParameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body, contentType, uri, httpMethod, httpHeaders, httpParameters);
    }

    @Override
    public String toString() {
        return "RestRequest{" +
                "body='" + body + '\'' +
                ", contentType='" + contentType + '\'' +
                ", uri='" + uri + '\'' +
                ", httpMethod=" + httpMethod +
                ", httpHeaders=" + httpHeaders +
                ", httpParameters=" + httpParameters +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private String body;
        private String contentType;
        private String uri;
        private HttpMethod httpMethod;
        private Set<HttpHeader> httpHeaders;
        private Set<HttpParameter> httpParameters;

        private Builder() {
        }

        public Builder body(final String body) {
            this.body = body;
            return this;
        }

        public Builder contentType(final String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder uri(final String uri) {
            this.uri = uri;
            return this;
        }

        public Builder httpMethod(final HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public Builder httpHeaders(final Set<HttpHeader> httpHeaders) {
            this.httpHeaders = httpHeaders;
            return this;
        }

        public Builder httpParameters(final Set<HttpParameter> httpParameters) {
            this.httpParameters = httpParameters;
            return this;
        }

        public RestRequest build() {
            return new RestRequest(this);
        }
    }
}
