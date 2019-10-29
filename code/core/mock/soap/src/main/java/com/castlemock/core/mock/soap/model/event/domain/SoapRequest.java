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

package com.castlemock.core.mock.soap.model.event.domain;

import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperationIdentifier;
import com.castlemock.core.mock.soap.model.project.domain.SoapVersion;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
public class SoapRequest {

    private String body;
    private String contentType;
    private String uri;
    private HttpMethod httpMethod;
    private String operationName;
    private SoapVersion soapVersion;
    private List<HttpHeader> httpHeaders;
    private SoapOperationIdentifier operationIdentifier;

    public SoapRequest(){

    }

    private SoapRequest(final Builder builder){
        this.body = builder.body;
        this.contentType = builder.contentType;
        this.uri = Objects.requireNonNull(builder.uri);
        this.httpMethod = Objects.requireNonNull(builder.httpMethod);
        this.operationName = Objects.requireNonNull(builder.operationName);
        this.soapVersion = Objects.requireNonNull(builder.soapVersion);
        this.httpHeaders = Objects.requireNonNull(builder.httpHeaders);
        this.operationIdentifier = Objects.requireNonNull(builder.operationIdentifier);
    }


    @XmlElement
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @XmlElement
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @XmlElement
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @XmlElement
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    @XmlElement
    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    @XmlElement
    public SoapOperationIdentifier getOperationIdentifier() {
        return operationIdentifier;
    }

    public void setOperationIdentifier(SoapOperationIdentifier operationIdentifier) {
        this.operationIdentifier = operationIdentifier;
    }

    @XmlElement
    public SoapVersion getSoapVersion() {
        return soapVersion;
    }

    public void setSoapVersion(SoapVersion soapVersion) {
        this.soapVersion = soapVersion;
    }

    @XmlElement
    public List<HttpHeader> getHttpHeaders() {
        return httpHeaders;
    }

    public void setHttpHeaders(List<HttpHeader> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String body;
        private String contentType;
        private String uri;
        private HttpMethod httpMethod;
        private String operationName;
        private SoapVersion soapVersion;
        private List<HttpHeader> httpHeaders;
        private SoapOperationIdentifier operationIdentifier;

        private Builder() {
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public Builder httpMethod(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public Builder operationName(String operationName) {
            this.operationName = operationName;
            return this;
        }

        public Builder soapVersion(SoapVersion soapVersion) {
            this.soapVersion = soapVersion;
            return this;
        }

        public Builder httpHeaders(List<HttpHeader> httpHeaders) {
            this.httpHeaders = httpHeaders;
            return this;
        }

        public Builder operationIdentifier(SoapOperationIdentifier operationIdentifier) {
            this.operationIdentifier = operationIdentifier;
            return this;
        }

        public SoapRequest build() {
            return new SoapRequest(this);
        }
    }
}
