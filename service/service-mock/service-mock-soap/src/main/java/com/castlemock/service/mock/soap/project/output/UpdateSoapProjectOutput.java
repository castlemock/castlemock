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

package com.castlemock.service.mock.soap.project.output;

import com.castlemock.model.core.Output;
import com.castlemock.model.mock.soap.domain.SoapProject;

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class UpdateSoapProjectOutput implements Output {

    private final SoapProject project;

    private UpdateSoapProjectOutput(final Builder builder) {
        this.project = Objects.requireNonNull(builder.project);
    }

    public SoapProject getProject() {
        return project;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private SoapProject project;

        private Builder(){

        }

        public Builder project(final SoapProject project){
            this.project = project;
            return this;
        }

        public UpdateSoapProjectOutput build(){
            return new UpdateSoapProjectOutput(this);
        }
    }
}
