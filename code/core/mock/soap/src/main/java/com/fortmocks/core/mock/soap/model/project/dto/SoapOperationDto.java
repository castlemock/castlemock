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

package com.fortmocks.core.mock.soap.model.project.dto;

import com.fortmocks.core.mock.soap.model.event.dto.SoapEventDto;
import com.fortmocks.core.mock.soap.model.project.domain.SoapOperationMethod;
import com.fortmocks.core.mock.soap.model.project.domain.SoapOperationStatus;
import com.fortmocks.core.mock.soap.model.project.domain.SoapOperationType;
import com.fortmocks.core.mock.soap.model.project.domain.SoapResponseStrategy;
import org.dozer.Mapping;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SoapOperationDto {

    @Mapping("id")
    private Long id;

    @Mapping("name")
    private String name;

    @Mapping("uri")
    private String uri;

    @Mapping("events")
    private List<SoapEventDto> events = new LinkedList<SoapEventDto>();

    @Mapping("soapMockResponses")
    private List<SoapMockResponseDto> soapMockResponses = new LinkedList<SoapMockResponseDto>();

    @Mapping("soapResponseStrategy")
    private SoapResponseStrategy soapResponseStrategy;

    @Mapping("soapOperationStatus")
    private SoapOperationStatus soapOperationStatus;

    @Mapping("soapOperationMethod")
    private SoapOperationMethod soapOperationMethod;

    @Mapping("soapOperationType")
    private SoapOperationType soapOperationType;

    @Mapping("defaultBody")
    private String defaultBody;

    @Mapping("currentResponseSequenceIndex")
    private Integer currentResponseSequenceIndex;

    @Mapping("forwardedEndpoint")
    private String forwardedEndpoint;

    @Mapping("originalEndpoint")
    private String originalEndpoint;

    private String invokeAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<SoapEventDto> getEvents() {
        return events;
    }

    public void setEvents(List<SoapEventDto> events) {
        this.events = events;
    }

    public SoapResponseStrategy getSoapResponseStrategy() {
        return soapResponseStrategy;
    }

    public void setSoapResponseStrategy(SoapResponseStrategy soapResponseStrategy) {
        this.soapResponseStrategy = soapResponseStrategy;
    }

    public SoapOperationStatus getSoapOperationStatus() {
        return soapOperationStatus;
    }

    public void setSoapOperationStatus(SoapOperationStatus soapOperationStatus) {
        this.soapOperationStatus = soapOperationStatus;
    }

    public List<SoapMockResponseDto> getSoapMockResponses() {
        return soapMockResponses;
    }

    public void setSoapMockResponses(List<SoapMockResponseDto> soapMockResponses) {
        this.soapMockResponses = soapMockResponses;
    }

    public String getInvokeAddress() {
        return invokeAddress;
    }

    public void setInvokeAddress(String invokeAddress) {
        this.invokeAddress = invokeAddress;
    }

    public String getDefaultBody() {
        return defaultBody;
    }

    public void setDefaultBody(String defaultBody) {
        this.defaultBody = defaultBody;
    }

    public SoapOperationMethod getSoapOperationMethod() {
        return soapOperationMethod;
    }

    public void setSoapOperationMethod(SoapOperationMethod soapOperationMethod) {
        this.soapOperationMethod = soapOperationMethod;
    }

    public SoapOperationType getSoapOperationType() {
        return soapOperationType;
    }

    public void setSoapOperationType(SoapOperationType soapOperationType) {
        this.soapOperationType = soapOperationType;
    }

    public Integer getCurrentResponseSequenceIndex() {
        return currentResponseSequenceIndex;
    }

    public void setCurrentResponseSequenceIndex(Integer currentResponseSequenceIndex) {
        this.currentResponseSequenceIndex = currentResponseSequenceIndex;
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
}
