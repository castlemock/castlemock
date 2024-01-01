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

import com.castlemock.model.core.http.ContentEncoding;
import com.castlemock.model.core.http.HttpHeader;

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
public class SoapResponse {

    private String body;
    private String mockResponseName;
    private Integer httpStatusCode;
    private String contentType;
    private List<HttpHeader> httpHeaders;
    private List<ContentEncoding> contentEncodings;

    private SoapResponse(){

    }

    private SoapResponse(final Builder builder){
        this.body = Objects.requireNonNull(builder.body, "body");
        this.httpStatusCode = Objects.requireNonNull(builder.httpStatusCode, "httpStatusCode");
        this.httpHeaders = Objects.requireNonNull(builder.httpHeaders, "httpHeaders");
        this.contentEncodings = Objects.requireNonNull(builder.contentEncodings, "contentEncodings");
        this.contentType = builder.contentType;
        this.mockResponseName = builder.mockResponseName;
    }

    @XmlElement
    public String getBody() {
        return body;
    }

    @XmlElement
    public Optional<String> getMockResponseName() {
        return Optional.ofNullable(mockResponseName);
    }

    @XmlElement
    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    @XmlElement
    public Optional<String> getContentType() {
        return Optional.ofNullable(contentType);
    }

    @XmlElementWrapper(name = "httpHeaders")
    @XmlElement(name = "httpHeader")
    public List<HttpHeader> getHttpHeaders() {
        return httpHeaders;
    }

    @XmlElementWrapper(name = "contentEncodings")
    @XmlElement(name = "contentEncoding")
    public List<ContentEncoding> getContentEncodings() {
        return contentEncodings;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String body;
        private String mockResponseName;
        private Integer httpStatusCode;
        private String contentType;
        private List<HttpHeader> httpHeaders;
        private List<ContentEncoding> contentEncodings;

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

        public Builder contentEncodings(final List<ContentEncoding> contentEncodings) {
            this.contentEncodings = contentEncodings;
            return this;
        }

        public SoapResponse build() {
            return new SoapResponse(this);
        }
    }
}
