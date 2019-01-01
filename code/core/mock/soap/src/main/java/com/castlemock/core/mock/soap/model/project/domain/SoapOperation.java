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

package com.castlemock.core.mock.soap.model.project.domain;

import com.castlemock.core.basis.model.http.domain.HttpMethod;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
public class SoapOperation {

    private String id;
    private String name;
    private String identifier;
    private SoapOperationIdentifier operationIdentifier;
    private SoapResponseStrategy responseStrategy;
    private SoapOperationStatus status;
    private HttpMethod httpMethod;
    private SoapVersion soapVersion;
    private String defaultBody;
    private Integer currentResponseSequenceIndex;
    private String forwardedEndpoint;
    private String originalEndpoint;
    private Boolean simulateNetworkDelay;
    private Long networkDelay;
    @Deprecated
    private String defaultXPathMockResponseId;
    private String defaultMockResponseId;
    private String portId;
    private Boolean mockOnFailure;
    private SoapOperationIdentifyStrategy identifyStrategy;

    private List<SoapMockResponse> mockResponses = new CopyOnWriteArrayList<SoapMockResponse>();

    private String invokeAddress;

    private String defaultResponseName;

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
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @XmlElement
    public SoapOperationIdentifier getOperationIdentifier() {
        return operationIdentifier;
    }

    public void setOperationIdentifier(SoapOperationIdentifier operationIdentifier) {
        this.operationIdentifier = operationIdentifier;
    }

    @XmlElement
    public String getPortId() {
        return portId;
    }

    public void setPortId(String portId) {
        this.portId = portId;
    }

    @XmlElement
    public SoapResponseStrategy getResponseStrategy() {
        return responseStrategy;
    }

    public void setResponseStrategy(SoapResponseStrategy responseStrategy) {
        this.responseStrategy = responseStrategy;
    }

    @XmlElement
    public SoapOperationStatus getStatus() {
        return status;
    }

    public void setStatus(SoapOperationStatus status) {
        this.status = status;
    }

    @XmlElementWrapper(name = "mockResponses")
    @XmlElement(name = "mockResponse")
    public List<SoapMockResponse> getMockResponses() {
        return mockResponses;
    }

    public void setMockResponses(List<SoapMockResponse> mockResponses) {
        this.mockResponses = mockResponses;
    }

    @XmlElement
    public String getInvokeAddress() {
        return invokeAddress;
    }

    public void setInvokeAddress(String invokeAddress) {
        this.invokeAddress = invokeAddress;
    }

    @XmlElement
    public String getDefaultBody() {
        return defaultBody;
    }

    public void setDefaultBody(String defaultBody) {
        this.defaultBody = defaultBody;
    }

    @XmlElement
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    @XmlElement
    public SoapVersion getSoapVersion() {
        return soapVersion;
    }

    public void setSoapVersion(SoapVersion soapVersion) {
        this.soapVersion = soapVersion;
    }

    @XmlElement
    public Integer getCurrentResponseSequenceIndex() {
        return currentResponseSequenceIndex;
    }

    public void setCurrentResponseSequenceIndex(Integer currentResponseSequenceIndex) {
        this.currentResponseSequenceIndex = currentResponseSequenceIndex;
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
    @Deprecated
    public String getDefaultXPathMockResponseId() {
        return defaultXPathMockResponseId;
    }

    public void setDefaultXPathMockResponseId(String defaultXPathMockResponseId) {
        this.defaultXPathMockResponseId = defaultXPathMockResponseId;
    }

    @XmlElement
    public String getDefaultResponseName() {
        return defaultResponseName;
    }

    public void setDefaultResponseName(String defaultResponseName) {
        this.defaultResponseName = defaultResponseName;
    }

    @XmlElement
    public Boolean getMockOnFailure() {
        return mockOnFailure;
    }

    public void setMockOnFailure(Boolean mockOnFailure) {
        this.mockOnFailure = mockOnFailure;
    }

    @XmlElement
    public SoapOperationIdentifyStrategy getIdentifyStrategy() {
        return identifyStrategy;
    }

    public void setIdentifyStrategy(SoapOperationIdentifyStrategy identifyStrategy) {
        this.identifyStrategy = identifyStrategy;
    }

    @XmlElement
    public String getDefaultMockResponseId() {
        return defaultMockResponseId;
    }

    public void setDefaultMockResponseId(String defaultMockResponseId) {
        this.defaultMockResponseId = defaultMockResponseId;
    }
}
