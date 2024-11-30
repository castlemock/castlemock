/*
 * Copyright 2024 Karl Dahlgren
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

package com.castlemock.repository.rest.file.event.model;

import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.repository.core.file.http.model.HttpHeaderFile;
import com.castlemock.repository.core.file.http.model.HttpParameterFile;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "restRequest")
@XmlAccessorType(XmlAccessType.NONE)
public class RestRequestFile {

    @XmlElement
    private String body;
    @XmlElement
    private String contentType;
    @XmlElement
    private String uri;
    @XmlElement
    private HttpMethod httpMethod;
    @XmlElementWrapper(name = "httpHeaders")
    @XmlElement(name = "httpHeader")
    private List<HttpParameterFile> httpParameters;
    @XmlElementWrapper(name = "httpParameters")
    @XmlElement(name = "httpParameter")
    private List<HttpHeaderFile> httpHeaders;

    private RestRequestFile() {

    }

    private RestRequestFile(final Builder builder) {
        this.uri = Objects.requireNonNull(builder.uri, "uri");
        this.httpMethod = Objects.requireNonNull(builder.httpMethod, "httpMethod");
        this.body = builder.body;
        this.contentType = builder.contentType;
        this.httpHeaders = builder.httpHeaders;
        this.httpParameters = builder.httpParameters;
    }

    public String getBody() {
        return body;
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

    public List<HttpHeaderFile> getHttpHeaders() {
        return httpHeaders;
    }

    public List<HttpParameterFile> getHttpParameters() {
        return httpParameters;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String body;
        private String contentType;
        private String uri;
        private HttpMethod httpMethod;
        private List<HttpParameterFile> httpParameters;
        private List<HttpHeaderFile> httpHeaders;

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

        public Builder httpParameters(final List<HttpParameterFile> httpParameters) {
            this.httpParameters = httpParameters;
            return this;
        }

        public Builder httpHeaders(final List<HttpHeaderFile> httpHeaders) {
            this.httpHeaders = httpHeaders;
            return this;
        }

        public RestRequestFile build() {
            return new RestRequestFile(this);
        }
    }
}