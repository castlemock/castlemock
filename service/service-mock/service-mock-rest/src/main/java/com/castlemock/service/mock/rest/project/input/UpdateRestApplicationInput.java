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

import com.castlemock.model.core.Input;

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class UpdateRestApplicationInput implements Input {

    private final String restProjectId;
    private final String restApplicationId;
    private final String name;

    private UpdateRestApplicationInput(final Builder builder) {
        this.restProjectId = Objects.requireNonNull(builder.restProjectId, "restProjectId");
        this.restApplicationId = Objects.requireNonNull(builder.restApplicationId, "restApplicationId");
        this.name = Objects.requireNonNull(builder.name, "name");
    }

    public String getRestProjectId() {
        return restProjectId;
    }

    public String getRestApplicationId() {
        return restApplicationId;
    }

    public String getName() {
        return name;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private String restProjectId;
        private String restApplicationId;
        private String name;

        public Builder restProjectId(final String restProjectId){
            this.restProjectId = restProjectId;
            return this;
        }

        public Builder restApplicationId(final String restApplicationId){
            this.restApplicationId = restApplicationId;
            return this;
        }

        public Builder name(final String name){
            this.name = name;
            return this;
        }

        public UpdateRestApplicationInput build(){
            return new UpdateRestApplicationInput(this);
        }

    }


}
