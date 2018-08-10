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

package com.castlemock.core.mock.rest.service.project.input;

import com.castlemock.core.basis.model.Input;
import com.castlemock.core.basis.model.validation.NotNull;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class UpdateRestMethodsForwardedEndpointInput implements Input {

    @NotNull
    private final String restProjectId;
    @NotNull
    private final String restApplicationId;
    @NotNull
    private final String restResourceId;
    @NotNull
    private final List<RestMethod> restMethods;
    @NotNull
    private final String forwardedEndpoint;

    private UpdateRestMethodsForwardedEndpointInput(String restProjectId,
                                                   String restApplicationId,
                                                   String restResourceId,
                                                   List<RestMethod> restMethods,
                                                   String forwardedEndpoint) {
        this.restProjectId = restProjectId;
        this.restApplicationId = restApplicationId;
        this.restResourceId = restResourceId;
        this.restMethods = restMethods;
        this.forwardedEndpoint = forwardedEndpoint;
    }

    public String getRestProjectId() {
        return restProjectId;
    }

    public String getRestApplicationId() {
        return restApplicationId;
    }

    public String getRestResourceId() {
        return restResourceId;
    }

    public List<RestMethod> getRestMethods() {
        return restMethods;
    }

    public String getForwardedEndpoint() {
        return forwardedEndpoint;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private String restProjectId;
        private String restApplicationId;
        private String restResourceId;
        private List<RestMethod> restMethods;
        private String forwardedEndpoint;

        public Builder restProjectId(final String restProjectId){
            this.restProjectId = restProjectId;
            return this;
        }

        public Builder restApplicationId(final String restApplicationId){
            this.restApplicationId = restApplicationId;
            return this;
        }

        public Builder restResourceId(final String restResourceId){
            this.restResourceId = restResourceId;
            return this;
        }

        public Builder restMethods(final List<RestMethod> restMethods){
            this.restMethods = restMethods;
            return this;
        }

        public Builder forwardedEndpoint(final String forwardedEndpoint){
            this.forwardedEndpoint = forwardedEndpoint;
            return this;
        }


        public UpdateRestMethodsForwardedEndpointInput build(){
            return new UpdateRestMethodsForwardedEndpointInput(this.restProjectId,
                    this.restApplicationId, this.restResourceId,
                    this.restMethods, this.forwardedEndpoint);
        }

    }


}
