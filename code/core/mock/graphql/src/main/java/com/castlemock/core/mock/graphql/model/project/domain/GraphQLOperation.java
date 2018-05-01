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

package com.castlemock.core.mock.graphql.model.project.domain;

import com.castlemock.core.basis.model.http.domain.HttpMethod;
import org.dozer.Mapping;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
public class GraphQLOperation {

    @Mapping("id")
    private String id;

    @Mapping("name")
    private String name;

    @Mapping("description")
    private String description;

    @Mapping("applicationId")
    private String applicationId;

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

    @Mapping("arguments")
    private List<GraphQLArgument> arguments = new CopyOnWriteArrayList<GraphQLArgument>();

    @Mapping("result")
    private GraphQLResult result;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
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

    public List<GraphQLArgument> getArguments() {
        return arguments;
    }

    public void setArguments(List<GraphQLArgument> arguments) {
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

    public GraphQLResult getResult() {
        return result;
    }

    public void setResult(GraphQLResult result) {
        this.result = result;
    }
}
