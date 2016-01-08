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
import com.fortmocks.core.mock.rest.model.project.dto.RestResourceDto;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateRestResourcesForwardedEndpointInput implements Input {

    @NotNull
    private String restProjectId;
    @NotNull
    private String restApplicationId;
    @NotNull
    private List<RestResourceDto> restResources;
    @NotNull
    private String forwardedEndpoint;

    public UpdateRestResourcesForwardedEndpointInput(String restProjectId, String restApplicationId, List<RestResourceDto> restResources, String forwardedEndpoint) {
        this.restProjectId = restProjectId;
        this.restApplicationId = restApplicationId;
        this.restResources = restResources;
        this.forwardedEndpoint = forwardedEndpoint;
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

    public List<RestResourceDto> getRestResources() {
        return restResources;
    }

    public void setRestResources(List<RestResourceDto> restResources) {
        this.restResources = restResources;
    }

    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    public void setForwardedEndpoint(String forwardedEndpoint) {
        this.forwardedEndpoint = forwardedEndpoint;
    }
}
