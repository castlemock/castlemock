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

    private RestRequestDto restRequest;
    private RestResponseDto restResponse;
    private String restMethodId;

    public RestRequestDto getRestRequest() {
        return restRequest;
    }

    public void setRestRequest(RestRequestDto restRequest) {
        this.restRequest = restRequest;
    }

    public RestResponseDto getRestResponse() {
        return restResponse;
    }

    public void setRestResponse(RestResponseDto restResponse) {
        this.restResponse = restResponse;
    }

    public String getRestMethodId() {
        return restMethodId;
    }

    public void setRestMethodId(String restMethodId) {
        this.restMethodId = restMethodId;
    }
}
