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
import com.fortmocks.core.mock.rest.model.project.dto.RestApplicationDto;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateRestApplicationsForwardedEndpointInput implements Input {

    @NotNull
    private String restProjectId;
    @NotNull
    private List<RestApplicationDto> restApplications;
    @NotNull
    private String forwardedEndpoint;

    public UpdateRestApplicationsForwardedEndpointInput(String restProjectId, List<RestApplicationDto> restApplications, String forwardedEndpoint) {
        this.restProjectId = restProjectId;
        this.restApplications = restApplications;
        this.forwardedEndpoint = forwardedEndpoint;
    }

    public String getRestProjectId() {
        return restProjectId;
    }

    public void setRestProjectId(String restProjectId) {
        this.restProjectId = restProjectId;
    }

    public List<RestApplicationDto> getRestApplications() {
        return restApplications;
    }

    public void setRestApplications(List<RestApplicationDto> restApplications) {
        this.restApplications = restApplications;
    }

    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    public void setForwardedEndpoint(String forwardedEndpoint) {
        this.forwardedEndpoint = forwardedEndpoint;
    }
}
