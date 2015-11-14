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

package com.fortmocks.core.mock.soap.model.project.domain;

import com.fortmocks.core.basis.model.Saveable;
import com.fortmocks.core.basis.model.event.domain.Event;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Set;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
public class SoapOperation implements Saveable<Long> {

    private Long id;
    private String name;
    private String uri;
    private Set<Event> events;
    private SoapResponseStrategy soapResponseStrategy;
    private List<SoapMockResponse> soapMockResponses;
    private SoapOperationStatus soapOperationStatus;
    private SoapOperationMethod soapOperationMethod;
    private SoapOperationType soapOperationType;
    private String defaultBody;
    private Integer currentResponseSequenceIndex;
    private String forwardedEndpoint;
    private String originalEndpoint;

    @XmlElement
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
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
    public SoapResponseStrategy getSoapResponseStrategy() {
        return soapResponseStrategy;
    }

    public void setSoapResponseStrategy(SoapResponseStrategy soapResponseStrategy) {
        this.soapResponseStrategy = soapResponseStrategy;
    }

    @XmlElementWrapper(name = "events")
    @XmlElement(name = "event")
    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    @XmlElement
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @XmlElement
    public SoapOperationStatus getSoapOperationStatus() {
        return soapOperationStatus;
    }

    public void setSoapOperationStatus(SoapOperationStatus soapOperationStatus) {
        this.soapOperationStatus = soapOperationStatus;
    }

    @XmlElementWrapper(name = "mockResponses")
    @XmlElement(name = "mockResponse")
    public List<SoapMockResponse> getSoapMockResponses() {
        return soapMockResponses;
    }

    public void setSoapMockResponses(List<SoapMockResponse> soapMockResponses) {
        this.soapMockResponses = soapMockResponses;
    }

    @XmlElement
    public SoapOperationMethod getSoapOperationMethod() {
        return soapOperationMethod;
    }

    public void setSoapOperationMethod(SoapOperationMethod soapOperationMethod) {
        this.soapOperationMethod = soapOperationMethod;
    }

    @XmlElement
    public SoapOperationType getSoapOperationType() {
        return soapOperationType;
    }

    public void setSoapOperationType(SoapOperationType soapOperationType) {
        this.soapOperationType = soapOperationType;
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

}
