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

package com.fortmocks.core.mock.rest.model.event.dto;

import com.fortmocks.core.basis.model.http.domain.HttpMethod;
import com.fortmocks.core.basis.model.http.dto.HttpHeaderDto;
import com.fortmocks.core.basis.model.http.dto.HttpParameterDto;
import org.dozer.Mapping;

import java.util.List;
import java.util.Map;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class RestRequestDto {

    @Mapping("body")
    private String body;

    @Mapping("contentType")
    private String contentType;

    @Mapping("uri")
    private String uri;

    @Mapping("httpMethod")
    private HttpMethod httpMethod;

    @Mapping("serviceName")
    private String serviceName;

    @Mapping("httpHeaders")
    private List<HttpHeaderDto> httpHeaders;

    @Mapping("httpParameters")
    private List<HttpParameterDto> httpParameters;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public List<HttpHeaderDto> getHttpHeaders() {
        return httpHeaders;
    }

    public void setHttpHeaders(List<HttpHeaderDto> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public List<HttpParameterDto> getHttpParameters() {
        return httpParameters;
    }

    public void setHttpParameters(List<HttpParameterDto> httpParameters) {
        this.httpParameters = httpParameters;
    }
}
