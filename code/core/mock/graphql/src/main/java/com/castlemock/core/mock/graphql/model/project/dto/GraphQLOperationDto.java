/*
 * Copyright 2018 Karl Dahlgren
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

package com.castlemock.core.mock.graphql.model.project.dto;

import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLOperationStatus;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLResponseStrategy;
import org.dozer.Mapping;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
public class GraphQLOperationDto {

    @Mapping("id")
    private String id;

    @Mapping("name")
    private String name;

    @Mapping("responseStrategy")
    private GraphQLResponseStrategy responseStrategy;

    @Mapping("status")
    private GraphQLOperationStatus status;

    @Mapping("httpMethod")
    private HttpMethod httpMethod;

    @Mapping("forwardedEndpoint")
    private String forwardedEndpoint;

    @Mapping("originalEndpoint")
    private String originalEndpoint;

    @Mapping("simulateNetworkDelay")
    private Boolean simulateNetworkDelay;

    @Mapping("networkDelay")
    private Long networkDelay;

    @Mapping("mockResponses")
    private List<GraphQLMockResponseDto> mockResponses = new CopyOnWriteArrayList<GraphQLMockResponseDto>();

    @Mapping("arguments")
    private List<GraphQLArgumentDto> arguments = new CopyOnWriteArrayList<GraphQLArgumentDto>();

    @Mapping("result")
    private GraphQLResultDto result;

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

    public GraphQLResponseStrategy getResponseStrategy() {
        return responseStrategy;
    }

    public void setResponseStrategy(GraphQLResponseStrategy responseStrategy) {
        this.responseStrategy = responseStrategy;
    }

    public GraphQLOperationStatus getStatus() {
        return status;
    }

    public void setStatus(GraphQLOperationStatus status) {
        this.status = status;
    }

    public List<GraphQLMockResponseDto> getMockResponses() {
        return mockResponses;
    }

    public void setMockResponses(List<GraphQLMockResponseDto> mockResponses) {
        this.mockResponses = mockResponses;
    }

    public List<GraphQLArgumentDto> getArguments() {
        return arguments;
    }

    public void setArguments(List<GraphQLArgumentDto> arguments) {
        this.arguments = arguments;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    public void setForwardedEndpoint(String forwardedEndpoint) {
        this.forwardedEndpoint = forwardedEndpoint;
    }

    public String getOriginalEndpoint() {
        return originalEndpoint;
    }

    public void setOriginalEndpoint(String originalEndpoint) {
        this.originalEndpoint = originalEndpoint;
    }

    public Boolean getSimulateNetworkDelay() {
        return simulateNetworkDelay;
    }

    public void setSimulateNetworkDelay(Boolean simulateNetworkDelay) {
        this.simulateNetworkDelay = simulateNetworkDelay;
    }

    public Long getNetworkDelay() {
        return networkDelay;
    }

    public void setNetworkDelay(Long networkDelay) {
        this.networkDelay = networkDelay;
    }

    public GraphQLResultDto getResult() {
        return result;
    }

    public void setResult(GraphQLResultDto result) {
        this.result = result;
    }
}
