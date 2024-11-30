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

import com.castlemock.model.core.http.HttpContentEncoding;
import com.castlemock.model.core.http.HttpHeader;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import jakarta.xml.bind.annotation.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = SoapResponse.Builder.class)
public class SoapResponse {

    @XmlElement
    private final String body;

    @XmlElement
    private final String mockResponseName;

    @XmlElement
    private final Integer httpStatusCode;

    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String contentType;

    @XmlElementWrapper(name = "httpHeaders")
    @XmlElement(name = "httpHeader")
    private final List<HttpHeader> httpHeaders;

    @XmlElementWrapper(name = "contentEncodings")
    @XmlElement(name = "contentEncoding")
    private final List<HttpContentEncoding> contentEncodings;

    private SoapResponse(final Builder builder){
        this.body = Objects.requireNonNull(builder.body, "body");
        this.httpStatusCode = Objects.requireNonNull(builder.httpStatusCode, "httpStatusCode");
        this.httpHeaders = Optional.ofNullable(builder.httpHeaders).orElseGet(List::of);
        this.contentEncodings = builder.contentEncodings;
        this.contentType = builder.contentType;
        this.mockResponseName = builder.mockResponseName;
    }

    public String getBody() {
        return body;
    }

    public Optional<String> getMockResponseName() {
        return Optional.ofNullable(mockResponseName);
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public Optional<String> getContentType() {
        return Optional.ofNullable(contentType);
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

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final SoapResponse that = (SoapResponse) o;
        return Objects.equals(body, that.body) && Objects.equals(mockResponseName, that.mockResponseName) &&
                Objects.equals(httpStatusCode, that.httpStatusCode) && Objects.equals(contentType, that.contentType) &&
                Objects.equals(httpHeaders, that.httpHeaders) && Objects.equals(contentEncodings, that.contentEncodings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body, mockResponseName, httpStatusCode, contentType, httpHeaders, contentEncodings);
    }

    @Override
    public String toString() {
        return "SoapResponse{" +
                "body='" + body + '\'' +
                ", mockResponseName='" + mockResponseName + '\'' +
                ", httpStatusCode=" + httpStatusCode +
                ", contentType='" + contentType + '\'' +
                ", httpHeaders=" + httpHeaders +
                ", contentEncodings=" + contentEncodings +
                '}';
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {

        private String body;
        private String mockResponseName;
        private Integer httpStatusCode;
        private String contentType;
        private List<HttpHeader> httpHeaders;
        private List<HttpContentEncoding> contentEncodings;

        private Builder() {
        }

        public Builder body(final String body) {
            this.body = body;
            return this;
        }

        public Builder mockResponseName(final String mockResponseName) {
            this.mockResponseName = mockResponseName;
            return this;
        }

        public Builder httpStatusCode(final Integer httpStatusCode) {
            this.httpStatusCode = httpStatusCode;
            return this;
        }

        public Builder contentType(final String contentType) {
            this.contentType = contentType;
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

        public SoapResponse build() {
            return new SoapResponse(this);
        }
    }
}
