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

import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.model.core.http.HttpMethod;
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
@JsonDeserialize(builder = SoapRequest.Builder.class)
public class SoapRequest {

    @XmlElement
    private final String body;

    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String envelope;

    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String contentType;

    @XmlElement
    private final String uri;

    @XmlElement
    private final HttpMethod httpMethod;

    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String operationName;

    @XmlElement
    private final SoapVersion soapVersion;

    @XmlElementWrapper(name = "httpHeaders")
    @XmlElement(name = "httpHeader")
    private final Set<HttpHeader> httpHeaders;

    @XmlElement
    private final SoapOperationIdentifier operationIdentifier;

    private SoapRequest(final Builder builder){
        this.body = Objects.requireNonNull(builder.body, "body");
        this.envelope = builder.envelope;
        this.contentType = builder.contentType;
        this.uri = Objects.requireNonNull(builder.uri, "uri");
        this.httpMethod = Objects.requireNonNull(builder.httpMethod, "httpMethod");
        this.operationName = builder.operationName;
        this.soapVersion = Objects.requireNonNull(builder.soapVersion, "soapVersion");
        this.httpHeaders = Optional.ofNullable(builder.httpHeaders).orElseGet(Set::of);
        this.operationIdentifier = Objects.requireNonNull(builder.operationIdentifier, "operationIdentifier");
    }


    public String getBody() {
        return body;
    }

    public String getEnvelope() {
        return envelope;
    }

    public String getContentType() {
        return contentType;
    }

    public String getUri() {
        return uri;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getOperationName() {
        return operationName;
    }

    public SoapOperationIdentifier getOperationIdentifier() {
        return operationIdentifier;
    }

    public SoapVersion getSoapVersion() {
        return soapVersion;
    }

    public Set<HttpHeader> getHttpHeaders() {
        return Optional.ofNullable(httpHeaders)
                .map(Set::copyOf)
                .orElseGet(Set::of);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final SoapRequest that = (SoapRequest) o;
        return Objects.equals(body, that.body) && Objects.equals(envelope, that.envelope) && Objects.equals(contentType, that.contentType) && Objects.equals(uri, that.uri) && httpMethod == that.httpMethod && Objects.equals(operationName, that.operationName) && soapVersion == that.soapVersion && Objects.equals(httpHeaders, that.httpHeaders) && Objects.equals(operationIdentifier, that.operationIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body, envelope, contentType, uri, httpMethod, operationName,
                soapVersion, httpHeaders, operationIdentifier);
    }

    @Override
    public String toString() {
        return "SoapRequest{" +
                "body='" + body + '\'' +
                ", envelope='" + envelope + '\'' +
                ", contentType='" + contentType + '\'' +
                ", uri='" + uri + '\'' +
                ", httpMethod=" + httpMethod +
                ", operationName='" + operationName + '\'' +
                ", soapVersion=" + soapVersion +
                ", httpHeaders=" + httpHeaders +
                ", operationIdentifier=" + operationIdentifier +
                '}';
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {

        private String body;
        private String envelope;
        private String contentType;
        private String uri;
        private HttpMethod httpMethod;
        private String operationName;
        private SoapVersion soapVersion;
        private Set<HttpHeader> httpHeaders;
        private SoapOperationIdentifier operationIdentifier;

        private Builder() {
        }

        public Builder body(final String body) {
            this.body = body;
            return this;
        }

        public Builder envelope(final String envelope) {
            this.envelope = envelope;
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

        public Builder operationName(final String operationName) {
            this.operationName = operationName;
            return this;
        }

        public Builder soapVersion(final SoapVersion soapVersion) {
            this.soapVersion = soapVersion;
            return this;
        }

        public Builder httpHeaders(final Set<HttpHeader> httpHeaders) {
            this.httpHeaders = httpHeaders;
            return this;
        }

        public Builder operationIdentifier(final SoapOperationIdentifier operationIdentifier) {
            this.operationIdentifier = operationIdentifier;
            return this;
        }

        public SoapRequest build() {
            return new SoapRequest(this);
        }
    }
}
