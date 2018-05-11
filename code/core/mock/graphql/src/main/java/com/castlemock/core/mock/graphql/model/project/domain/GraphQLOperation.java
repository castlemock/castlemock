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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
@XmlRootElement
public class GraphQLOperation {

    private String id;
    private String name;
    private String description;
    private String applicationId;
    private GraphQLResponseStrategy responseStrategy;
    private GraphQLOperationStatus status;
    private HttpMethod httpMethod;
    private String forwardedEndpoint;
    private String originalEndpoint;
    private Boolean simulateNetworkDelay;
    private Long networkDelay;
    private List<GraphQLArgument> arguments = new CopyOnWriteArrayList<GraphQLArgument>();
    private GraphQLResult result;

    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement
    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    @XmlElement
    public GraphQLResponseStrategy getResponseStrategy() {
        return responseStrategy;
    }

    public void setResponseStrategy(GraphQLResponseStrategy responseStrategy) {
        this.responseStrategy = responseStrategy;
    }

    @XmlElement
    public GraphQLOperationStatus getStatus() {
        return status;
    }

    public void setStatus(GraphQLOperationStatus status) {
        this.status = status;
    }

    @XmlElementWrapper(name = "arguments")
    @XmlElement(name = "argument")
    public List<GraphQLArgument> getArguments() {
        return arguments;
    }

    public void setArguments(List<GraphQLArgument> arguments) {
        this.arguments = arguments;
    }

    @XmlElement
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    @XmlElement
    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    public void setForwardedEndpoint(String forwardedEndpoint) {
        this.forwardedEndpoint = forwardedEndpoint;
    }

    @XmlElement
    public String getOriginalEndpoint() {
        return originalEndpoint;
    }

    public void setOriginalEndpoint(String originalEndpoint) {
        this.originalEndpoint = originalEndpoint;
    }

    @XmlElement
    public Boolean getSimulateNetworkDelay() {
        return simulateNetworkDelay;
    }

    public void setSimulateNetworkDelay(Boolean simulateNetworkDelay) {
        this.simulateNetworkDelay = simulateNetworkDelay;
    }

    @XmlElement
    public Long getNetworkDelay() {
        return networkDelay;
    }

    public void setNetworkDelay(Long networkDelay) {
        this.networkDelay = networkDelay;
    }

    @XmlElement
    public GraphQLResult getResult() {
        return result;
    }

    public void setResult(GraphQLResult result) {
        this.result = result;
    }
}
