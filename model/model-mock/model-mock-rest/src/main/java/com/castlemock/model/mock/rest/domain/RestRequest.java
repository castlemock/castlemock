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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
public class RestRequest {

    private String body;
    private String contentType;
    private String uri;
    private HttpMethod httpMethod;
    private List<HttpHeader> httpHeaders;
    private List<HttpParameter> httpParameters;

    private RestRequest(){

    }

    private RestRequest(final Builder builder){
        this.body = builder.body;
        this.contentType = builder.contentType;
        this.uri = Objects.requireNonNull(builder.uri);
        this.httpMethod = Objects.requireNonNull(builder.httpMethod);
        this.httpHeaders = Objects.requireNonNull(builder.httpHeaders);
        this.httpParameters = Objects.requireNonNull(builder.httpParameters);
    }

    @XmlElement
    public String getBody() {
        return body;
    }

    @XmlElement
    public String getContentType() {
        return contentType;
    }

    @XmlElement
    public String getUri() {
        return uri;
    }

    @XmlElement
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    @XmlElementWrapper(name = "httpHeaders")
    @XmlElement(name = "httpHeader")
    public List<HttpHeader> getHttpHeaders() {
        return httpHeaders;
    }

    @XmlElementWrapper(name = "httpParameters")
    @XmlElement(name = "httpParameter")
    public List<HttpParameter> getHttpParameters() {
        return httpParameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RestRequest)) return false;
        RestRequest that = (RestRequest) o;
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

    public static final class Builder {
        private String body;
        private String contentType;
        private String uri;
        private HttpMethod httpMethod;
        private List<HttpHeader> httpHeaders;
        private List<HttpParameter> httpParameters;

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

        public Builder httpHeaders(final List<HttpHeader> httpHeaders) {
            this.httpHeaders = httpHeaders;
            return this;
        }

        public Builder httpParameters(final List<HttpParameter> httpParameters) {
            this.httpParameters = httpParameters;
            return this;
        }

        public RestRequest build() {
            return new RestRequest(this);
        }
    }
}
