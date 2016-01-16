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

package com.fortmocks.core.mock.rest.model.event.domain;

import com.fortmocks.core.basis.model.event.domain.Event;
import com.fortmocks.core.mock.rest.model.event.dto.RestRequestDto;
import com.fortmocks.core.mock.rest.model.event.dto.RestResponseDto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */

@XmlRootElement
public class RestEvent extends Event {

    private RestRequestDto request;
    private RestResponseDto response;
    private String projectId;
    private String applicationId;
    private String resourceId;
    private String methodId;

    public RestRequestDto getRequest() {
        return request;
    }

    public void setRequest(RestRequestDto request) {
        this.request = request;
    }

    public RestResponseDto getResponse() {
        return response;
    }

    public void setResponse(RestResponseDto response) {
        this.response = response;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }
}
