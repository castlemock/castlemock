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

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class ReadRestMockResponseInput implements Input {

    @NotNull
    private final String restProjectId;
    @NotNull
    private final String restApplicationId;
    @NotNull
    private final String restResourceId;
    @NotNull
    private final String restMethodId;
    @NotNull
    private final String restMockResponse;

    private ReadRestMockResponseInput(final String restProjectId, final String restApplicationId,
                                      final String restResourceId, final String restMethodId,
                                      final String restMockResponse) {
        this.restProjectId = restProjectId;
        this.restApplicationId = restApplicationId;
        this.restResourceId = restResourceId;
        this.restMethodId = restMethodId;
        this.restMockResponse = restMockResponse;
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

    public String getRestMethodId() {
        return restMethodId;
    }

    public String getRestMockResponse() {
        return restMockResponse;
    }


    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private String restProjectId;
        private String restApplicationId;
        private String restResourceId;
        private String restMethodId;
        private String restMockResponse;

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

        public Builder restMethodId(final String restMethodId){
            this.restMethodId = restMethodId;
            return this;
        }

        public Builder restMockResponse(final String restMockResponse){
            this.restMockResponse = restMockResponse;
            return this;
        }

        public ReadRestMockResponseInput build(){
            return new ReadRestMockResponseInput(this.restProjectId,
                    this.restApplicationId, this.restResourceId,
                    this.restMethodId, this.restMockResponse);
        }

    }

}
