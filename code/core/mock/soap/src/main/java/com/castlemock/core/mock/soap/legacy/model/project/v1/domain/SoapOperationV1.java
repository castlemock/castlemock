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

package com.castlemock.core.mock.soap.legacy.model.project.v1.domain;

import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperationStatus;
import com.castlemock.core.mock.soap.model.project.domain.SoapResponseStrategy;
import com.castlemock.core.mock.soap.model.project.domain.SoapVersion;

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
public class SoapOperationV1 implements Saveable<String> {

    private String id;
    private String name;
    private String identifier;
    private List<SoapMockResponseV1> mockResponses = new CopyOnWriteArrayList<SoapMockResponseV1>();
    private SoapResponseStrategy responseStrategy;
    private SoapOperationStatus status;
    private HttpMethod httpMethod;
    private SoapVersion soapVersion;
    private String defaultBody;
    private Integer currentResponseSequenceIndex;
    private String forwardedEndpoint;
    private String originalEndpoint;
    private String defaultXPathMockResponseId;
    private boolean simulateNetworkDelay;
    private long networkDelay;

    @XmlElement
    @Override
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
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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
    public String getDefaultBody() {
        return defaultBody;
    }

    public void setDefaultBody(String defaultBody) {
        this.defaultBody = defaultBody;
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
    public boolean getSimulateNetworkDelay() {
        return simulateNetworkDelay;
    }

    public void setSimulateNetworkDelay(boolean simulateNetworkDelay) {
        this.simulateNetworkDelay = simulateNetworkDelay;
    }

    @XmlElement
    public long getNetworkDelay() {
        return networkDelay;
    }

    public void setNetworkDelay(long networkDelay) {
        this.networkDelay = networkDelay;
    }

    @XmlElement
    public String getDefaultXPathMockResponseId() {
        return defaultXPathMockResponseId;
    }

    public void setDefaultXPathMockResponseId(String defaultXPathMockResponseId) {
        this.defaultXPathMockResponseId = defaultXPathMockResponseId;
    }

    @XmlElementWrapper(name = "mockResponses")
    @XmlElement(name = "mockResponse")
    public List<SoapMockResponseV1> getMockResponses() {
        return mockResponses;
    }

    public void setMockResponses(List<SoapMockResponseV1> mockResponses) {
        this.mockResponses = mockResponses;
    }

}
