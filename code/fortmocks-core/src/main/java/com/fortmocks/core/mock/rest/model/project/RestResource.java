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

package com.fortmocks.core.mock.rest.model.project;

import com.fortmocks.core.base.model.Saveable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
public class RestResource implements Saveable<Long> {

    private Long id;
    private String name;
    private String uri;
    private String forwardedEndpoint;
    private List<RestMethod> restMethods;
    private RestResourceStatus restResourceStatus;
    private RestResponseStrategy restResponseStrategy;

    @Override
    @XmlElement
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
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @XmlElement
    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    public void setForwardedEndpoint(String forwardedEndpoint) {
        this.forwardedEndpoint = forwardedEndpoint;
    }

    @XmlElement
    public RestResourceStatus getRestResourceStatus() {
        return restResourceStatus;
    }

    public void setRestResourceStatus(RestResourceStatus restResourceStatus) {
        this.restResourceStatus = restResourceStatus;
    }

    @XmlElement
    public RestResponseStrategy getRestResponseStrategy() {
        return restResponseStrategy;
    }

    public void setRestResponseStrategy(RestResponseStrategy restResponseStrategy) {
        this.restResponseStrategy = restResponseStrategy;
    }

    public List<RestMethod> getRestMethods() {
        return restMethods;
    }

    public void setRestMethods(List<RestMethod> restMethods) {
        this.restMethods = restMethods;
    }
}
