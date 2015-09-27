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

package com.fortmocks.core.mock.rest.model.project.dto;

import com.fortmocks.core.mock.rest.model.project.RestMethod;
import com.fortmocks.core.mock.rest.model.project.RestResourceStatus;
import com.fortmocks.core.mock.rest.model.project.RestResponseStrategy;
import org.dozer.Mapping;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class RestResourceDto {

    @Mapping("id")
    private Long id;

    @Mapping("name")
    private String name;

    @Mapping("uri")
    private String uri;

    @Mapping("forwardedEndpoint")
    private String forwardedEndpoint;

    @Mapping("restResourceStatus")
    private RestResourceStatus restResourceStatus;

    @Mapping("restResponseStrategy")
    private RestResponseStrategy restResponseStrategy;

    @Mapping("restMethods")
    private List<RestMethodDto> restMethods = new LinkedList<RestMethodDto>();

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

    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    public void setForwardedEndpoint(String forwardedEndpoint) {
        this.forwardedEndpoint = forwardedEndpoint;
    }

    public RestResourceStatus getRestResourceStatus() {
        return restResourceStatus;
    }

    public void setRestResourceStatus(RestResourceStatus restResourceStatus) {
        this.restResourceStatus = restResourceStatus;
    }

    public RestResponseStrategy getRestResponseStrategy() {
        return restResponseStrategy;
    }

    public void setRestResponseStrategy(RestResponseStrategy restResponseStrategy) {
        this.restResponseStrategy = restResponseStrategy;
    }

    public List<RestMethodDto> getRestMethods() {
        return restMethods;
    }

    public void setRestMethods(List<RestMethodDto> restMethods) {
        this.restMethods = restMethods;
    }
}
