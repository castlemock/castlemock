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

package com.castlemock.core.mock.rest.model.project.dto;

import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestMethodStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestResponseStrategy;
import org.dozer.Mapping;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */

public class RestMethodDto {

    @Mapping("id")
    private String id;

    @Mapping("name")
    private String name;

    @Mapping("defaultBody")
    private String defaultBody;

    @Mapping("httpMethod")
    private HttpMethod httpMethod;

    @Mapping("forwardedEndpoint")
    private String forwardedEndpoint;

    @Mapping("status")
    private RestMethodStatus status;

    @Mapping("responseStrategy")
    private RestResponseStrategy responseStrategy;

    @Mapping("mockResponses")
    private List<RestMockResponseDto> mockResponses = new CopyOnWriteArrayList<RestMockResponseDto>();

    @Mapping("currentResponseSequenceIndex")
    private Integer currentResponseSequenceIndex = 0;

    private String invokeAddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultBody() {
        return defaultBody;
    }

    public void setDefaultBody(String defaultBody) {
        this.defaultBody = defaultBody;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public List<RestMockResponseDto> getMockResponses() {
        return mockResponses;
    }

    public void setMockResponses(List<RestMockResponseDto> mockResponses) {
        this.mockResponses = mockResponses;
    }

    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    public void setForwardedEndpoint(String forwardedEndpoint) {
        this.forwardedEndpoint = forwardedEndpoint;
    }

    public RestMethodStatus getStatus() {
        return status;
    }

    public void setStatus(RestMethodStatus status) {
        this.status = status;
    }

    public RestResponseStrategy getResponseStrategy() {
        return responseStrategy;
    }

    public void setResponseStrategy(RestResponseStrategy responseStrategy) {
        this.responseStrategy = responseStrategy;
    }

    public Integer getCurrentResponseSequenceIndex() {
        return currentResponseSequenceIndex;
    }

    public void setCurrentResponseSequenceIndex(Integer currentResponseSequenceIndex) {
        this.currentResponseSequenceIndex = currentResponseSequenceIndex;
    }

    public String getInvokeAddress() {
        return invokeAddress;
    }

    public void setInvokeAddress(String invokeAddress) {
        this.invokeAddress = invokeAddress;
    }
}
