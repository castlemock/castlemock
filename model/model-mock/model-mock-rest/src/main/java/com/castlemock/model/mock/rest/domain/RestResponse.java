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

import com.castlemock.model.core.http.HttpContentEncoding;
import com.castlemock.model.core.http.HttpHeader;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = RestResponse.Builder.class)
public class RestResponse {

    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String body;

    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String mockResponseName;

    @XmlElement
    private Integer httpStatusCode;

    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String contentType;

    @XmlElementWrapper(name = "httpHeaders")
    @XmlElement(name = "httpHeader")
    private List<HttpHeader> httpHeaders;

    @XmlElementWrapper(name = "contentEncodings")
    @XmlElement(name = "contentEncoding")
    private List<HttpContentEncoding> contentEncodings;

    public RestResponse(){

    }

    private RestResponse(final Builder builder){
        this.httpStatusCode = Objects.requireNonNull(builder.httpStatusCode, "httpStatusCode");
        this.body = builder.body;
        this.contentType = builder.contentType;
        this.mockResponseName = builder.mockResponseName;
        this.contentEncodings = Optional.ofNullable(builder.contentEncodings)
                .orElseGet(List::of);
        this.httpHeaders = Optional.ofNullable(builder.httpHeaders)
                .orElseGet(List::of);

    }

    public Optional<String> getBody() {
        return Optional.ofNullable(body);
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
        return List.copyOf(httpHeaders);
    }

    public List<HttpContentEncoding> getContentEncodings() {
        return List.copyOf(contentEncodings);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RestResponse that)) return false;
        return Objects.equals(body, that.body) &&
                Objects.equals(mockResponseName, that.mockResponseName) &&
                Objects.equals(httpStatusCode, that.httpStatusCode) &&
                Objects.equals(contentType, that.contentType) &&
                Objects.equals(httpHeaders, that.httpHeaders) &&
                Objects.equals(contentEncodings, that.contentEncodings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body, mockResponseName, httpStatusCode, contentType, httpHeaders, contentEncodings);
    }

    @Override
    public String toString() {
        return "RestResponse{" +
                "body='" + body + '\'' +
                ", mockResponseName='" + mockResponseName + '\'' +
                ", httpStatusCode=" + httpStatusCode +
                ", contentType='" + contentType + '\'' +
                ", httpHeaders=" + httpHeaders +
                ", contentEncodings=" + contentEncodings +
                '}';
    }

    public static Builder builder() {
        return new Builder();
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

        public RestResponse build() {
            return new RestResponse(this);
        }
    }
}
