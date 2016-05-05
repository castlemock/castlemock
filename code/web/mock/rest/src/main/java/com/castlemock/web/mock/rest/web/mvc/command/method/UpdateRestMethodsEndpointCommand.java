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

package com.castlemock.web.mock.rest.web.mvc.command.method;


import com.castlemock.core.mock.rest.model.project.dto.RestMethodDto;

import java.util.List;

/**
 * The UpdateRestMethodsEndpointCommand is a command class and is used to identify which methods
 * will be updated with a new endpoint
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateRestMethodsEndpointCommand {

    private List<RestMethodDto> restMethods;
    private String forwardedEndpoint;

    public List<RestMethodDto> getRestMethods() {
        return restMethods;
    }

    public void setRestMethods(List<RestMethodDto> restMethods) {
        this.restMethods = restMethods;
    }

    /**
     * Returns the forwarded endpoint
     * @return The forwarded endpoint
     */
    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    /**
     * Sets a new value for the forwarded endpoint
     * @param forwardedEndpoint The new value for forwarded endpoint
     */
    public void setForwardedEndpoint(String forwardedEndpoint) {
        this.forwardedEndpoint = forwardedEndpoint;
    }
}

