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

package com.castlemock.service.mock.rest.project.input;

import com.castlemock.model.core.model.Input;
import com.castlemock.model.core.model.validation.NotNull;
import com.castlemock.model.mock.rest.domain.RestMockResponse;

import java.util.List;
import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class DeleteRestMockResponsesInput implements Input{

    @NotNull
    private final String restProjectId;
    @NotNull
    private final String restApplicationId;
    @NotNull
    private final String restResourceId;
    @NotNull
    private final String restMethodId;
    @NotNull
    private final List<RestMockResponse> restMockResponses;

    private DeleteRestMockResponsesInput(final Builder builder) {
        this.restProjectId = Objects.requireNonNull(builder.restProjectId);
        this.restApplicationId = Objects.requireNonNull(builder.restApplicationId);
        this.restResourceId = Objects.requireNonNull(builder.restResourceId);
        this.restMethodId = Objects.requireNonNull(builder.restMethodId);
        this.restMockResponses = Objects.requireNonNull(builder.restMockResponses);
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

    public List<RestMockResponse> getRestMockResponses() {
        return restMockResponses;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private String restProjectId;
        private String restApplicationId;
        private String restResourceId;
        private String restMethodId;
        private List<RestMockResponse> restMockResponses;

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

        public Builder restMockResponses(final List<RestMockResponse> restMockResponses){
            this.restMockResponses = restMockResponses;
            return this;
        }

        public DeleteRestMockResponsesInput build(){
            return new DeleteRestMockResponsesInput(this);
        }

    }

}
