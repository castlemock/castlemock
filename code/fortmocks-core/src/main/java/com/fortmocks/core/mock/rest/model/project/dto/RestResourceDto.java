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

import com.fortmocks.core.mock.rest.model.project.RestResourceMethod;
import com.fortmocks.core.mock.rest.model.project.RestResourceStatus;
import com.fortmocks.core.mock.rest.model.project.RestResponseStrategy;
import org.dozer.Mapping;

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

    @Mapping("restResourceMethod")
    private RestResourceMethod restResourceMethod;

    @Mapping("restResourceStatus")
    private RestResourceStatus restResourceStatus;

    @Mapping("restResponseStrategy")
    private RestResponseStrategy restResponseStrategy;

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

    public RestResourceMethod getRestResourceMethod() {
        return restResourceMethod;
    }

    public void setRestResourceMethod(RestResourceMethod restResourceMethod) {
        this.restResourceMethod = restResourceMethod;
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
}
