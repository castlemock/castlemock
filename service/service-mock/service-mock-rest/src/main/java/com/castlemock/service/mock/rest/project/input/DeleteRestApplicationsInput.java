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
import com.castlemock.model.mock.rest.domain.RestApplication;

import java.util.List;
import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class DeleteRestApplicationsInput implements Input{

    private final String restProjectId;
    private final List<RestApplication> restApplications;

    private DeleteRestApplicationsInput(final Builder builder) {
        this.restProjectId = Objects.requireNonNull(builder.restProjectId);
        this.restApplications = Objects.requireNonNull(builder.restApplications);
    }

    public String getRestProjectId() {
        return restProjectId;
    }

    public List<RestApplication> getRestApplications() {
        return restApplications;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private String restProjectId;

        private List<RestApplication> restApplications;

        public Builder restProjectId(final String restProjectId){
            this.restProjectId = restProjectId;
            return this;
        }

        public Builder restApplications(final List<RestApplication> restApplications){
            this.restApplications = restApplications;
            return this;
        }

        public DeleteRestApplicationsInput build(){
            return new DeleteRestApplicationsInput(this);
        }

    }

}
