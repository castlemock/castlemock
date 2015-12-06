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

package com.fortmocks.core.mock.rest.model.project.service.message.input;

import com.fortmocks.core.basis.model.Input;
import com.fortmocks.core.basis.model.validation.NotNull;
import com.fortmocks.core.mock.rest.model.project.domain.RestMockResponseStatus;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateRestMockResponseStatusInput implements Input {

    @NotNull
    private String restProjectId;
    @NotNull
    private String restApplicationId;
    @NotNull
    private String restResourceId;
    @NotNull
    private String restMethodId;
    @NotNull
    private String restMockResponseId;
    @NotNull
    private RestMockResponseStatus status;

    public UpdateRestMockResponseStatusInput(String restProjectId, String restApplicationId, String restResourceId, String restMethodId, String restMockResponseId, RestMockResponseStatus status) {
        this.restProjectId = restProjectId;
        this.restApplicationId = restApplicationId;
        this.restResourceId = restResourceId;
        this.restMethodId = restMethodId;
        this.restMockResponseId = restMockResponseId;
        this.status = status;
    }

    public String getRestProjectId() {
        return restProjectId;
    }

    public void setRestProjectId(String restProjectId) {
        this.restProjectId = restProjectId;
    }

    public String getRestApplicationId() {
        return restApplicationId;
    }

    public void setRestApplicationId(String restApplicationId) {
        this.restApplicationId = restApplicationId;
    }

    public String getRestResourceId() {
        return restResourceId;
    }

    public void setRestResourceId(String restResourceId) {
        this.restResourceId = restResourceId;
    }

    public String getRestMethodId() {
        return restMethodId;
    }

    public void setRestMethodId(String restMethodId) {
        this.restMethodId = restMethodId;
    }

    public String getRestMockResponseId() {
        return restMockResponseId;
    }

    public void setRestMockResponseId(String restMockResponseId) {
        this.restMockResponseId = restMockResponseId;
    }

    public RestMockResponseStatus getStatus() {
        return status;
    }

    public void setStatus(RestMockResponseStatus status) {
        this.status = status;
    }
}
