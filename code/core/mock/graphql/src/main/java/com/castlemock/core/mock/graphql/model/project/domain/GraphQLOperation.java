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

import com.castlemock.core.basis.model.Saveable;
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
public class GraphQLOperation implements Saveable<String> {

    private String id;
    private String name;
    private long networkDelay;
    private HttpMethod httpMethod;
    private String forwardedEndpoint;
    private String originalEndpoint;
    private boolean simulateNetworkDelay;
    private GraphQLOperationStatus status;
    private GraphQLResponseStrategy responseStrategy;
    private List<GraphQLMockResponse> mockResponses = new CopyOnWriteArrayList<GraphQLMockResponse>();
    private List<GraphQLArgument> arguments = new CopyOnWriteArrayList<GraphQLArgument>();

    @Override
    @XmlElement
    public String getId() {
        return id;
    }

    @Override
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

    @XmlElement
    public long getNetworkDelay() {
        return networkDelay;
    }

    public void setNetworkDelay(long networkDelay) {
        this.networkDelay = networkDelay;
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
    public boolean isSimulateNetworkDelay() {
        return simulateNetworkDelay;
    }

    public void setSimulateNetworkDelay(boolean simulateNetworkDelay) {
        this.simulateNetworkDelay = simulateNetworkDelay;
    }

    @XmlElementWrapper(name = "mockResponses")
    @XmlElement(name = "mockResponse")
    public List<GraphQLMockResponse> getMockResponses() {
        return mockResponses;
    }

    public void setMockResponses(List<GraphQLMockResponse> mockResponses) {
        this.mockResponses = mockResponses;
    }

    @XmlElementWrapper(name = "arguments")
    @XmlElement(name = "argument")
    public List<GraphQLArgument> getArguments() {
        return arguments;
    }

    public void setArguments(List<GraphQLArgument> arguments) {
        this.arguments = arguments;
    }
}
