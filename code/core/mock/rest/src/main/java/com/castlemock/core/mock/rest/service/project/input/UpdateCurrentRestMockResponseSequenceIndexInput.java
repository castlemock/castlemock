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
public final class UpdateCurrentRestMockResponseSequenceIndexInput implements Input {

    @NotNull
    private final String restProjectId;
    @NotNull
    private final String restApplicationId;
    @NotNull
    private final String restResourceId;
    @NotNull
    private final String restMethodId;
    @NotNull
    private final Integer currentRestMockResponseSequenceIndex;

    private UpdateCurrentRestMockResponseSequenceIndexInput(final String restProjectId, final String restApplicationId,
                                                            final String restResourceId, final String restMethodId,
                                                            final Integer currentRestMockResponseSequenceIndex) {
        this.restProjectId = restProjectId;
        this.restApplicationId = restApplicationId;
        this.restResourceId = restResourceId;
        this.restMethodId = restMethodId;
        this.currentRestMockResponseSequenceIndex = currentRestMockResponseSequenceIndex;
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

    public Integer getCurrentRestMockResponseSequenceIndex() {
        return currentRestMockResponseSequenceIndex;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private String restProjectId;
        private String restApplicationId;
        private String restResourceId;
        private String restMethodId;
        private Integer currentRestMockResponseSequenceIndex;

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

        public Builder currentRestMockResponseSequenceIndex(final Integer currentRestMockResponseSequenceIndex){
            this.currentRestMockResponseSequenceIndex = currentRestMockResponseSequenceIndex;
            return this;
        }

        public UpdateCurrentRestMockResponseSequenceIndexInput build(){
            return new UpdateCurrentRestMockResponseSequenceIndexInput(this.restProjectId,
                    this.restApplicationId, this.restResourceId,
                    this.restMethodId, this.currentRestMockResponseSequenceIndex);
        }

    }


}
