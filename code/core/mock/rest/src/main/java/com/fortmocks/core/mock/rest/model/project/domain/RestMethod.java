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

package com.fortmocks.core.mock.rest.model.project.domain;

import com.fortmocks.core.basis.model.Saveable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
public class RestMethod implements Saveable<Long> {

    private Long id;
    private String name;
    private String defaultBody;
    private RestMethodType restMethodType;
    private String forwardedEndpoint;
    private RestMethodStatus restMethodStatus;
    private RestResponseStrategy restResponseStrategy;
    private List<RestMockResponse> restMockResponses;
    private Integer currentResponseSequenceIndex;

    @Override
    @XmlElement
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getDefaultBody() {
        return defaultBody;
    }

    public void setDefaultBody(String defaultBody) {
        this.defaultBody = defaultBody;
    }

    @XmlElement
    public RestMethodType getRestMethodType() {
        return restMethodType;
    }

    public void setRestMethodType(RestMethodType restMethodType) {
        this.restMethodType = restMethodType;
    }

    @XmlElementWrapper(name = "restMockResponses")
    @XmlElement(name = "restMockResponse")
    public List<RestMockResponse> getRestMockResponses() {
        return restMockResponses;
    }

    public void setRestMockResponses(List<RestMockResponse> restMockResponses) {
        this.restMockResponses = restMockResponses;
    }

    @XmlElement
    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    public void setForwardedEndpoint(String forwardedEndpoint) {
        this.forwardedEndpoint = forwardedEndpoint;
    }

    @XmlElement
    public RestMethodStatus getRestMethodStatus() {
        return restMethodStatus;
    }

    public void setRestMethodStatus(RestMethodStatus restMethodStatus) {
        this.restMethodStatus = restMethodStatus;
    }

    @XmlElement
    public RestResponseStrategy getRestResponseStrategy() {
        return restResponseStrategy;
    }

    public void setRestResponseStrategy(RestResponseStrategy restResponseStrategy) {
        this.restResponseStrategy = restResponseStrategy;
    }

    @XmlElement
    public Integer getCurrentResponseSequenceIndex() {
        return currentResponseSequenceIndex;
    }

    public void setCurrentResponseSequenceIndex(Integer currentResponseSequenceIndex) {
        this.currentResponseSequenceIndex = currentResponseSequenceIndex;
    }
}
