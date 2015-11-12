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

package com.fortmocks.mock.rest.model.project.dto;

import com.fortmocks.mock.rest.model.project.domain.RestContentType;
import com.fortmocks.mock.rest.model.project.domain.RestMockResponseStatus;
import org.dozer.Mapping;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class RestMockResponseDto {

    @Mapping("id")
    private Long id;

    @Mapping("name")
    private String name;

    @Mapping("body")
    private String body;

    @Mapping("httpStatusCode")
    private Integer httpStatusCode;

    @Mapping("restMockResponseStatus")
    private RestMockResponseStatus restMockResponseStatus;

    @Mapping("restContentType")
    private RestContentType restContentType;

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public RestMockResponseStatus getRestMockResponseStatus() {
        return restMockResponseStatus;
    }

    public void setRestMockResponseStatus(RestMockResponseStatus restMockResponseStatus) {
        this.restMockResponseStatus = restMockResponseStatus;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public RestContentType getRestContentType() {
        return restContentType;
    }

    public void setRestContentType(RestContentType restContentType) {
        this.restContentType = restContentType;
    }
}
