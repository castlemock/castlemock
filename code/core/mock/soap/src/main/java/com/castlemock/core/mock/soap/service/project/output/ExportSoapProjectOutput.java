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

package com.castlemock.core.mock.soap.service.project.output;

import com.castlemock.core.basis.model.Output;

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class ExportSoapProjectOutput implements Output{

    private final String project;

    private ExportSoapProjectOutput(final Builder builder) {
        this.project = Objects.requireNonNull(builder.project);
    }

    public String getProject() {
        return project;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String project;

        private Builder(){

        }

        public Builder project(final String project){
            this.project = project;
            return this;
        }

        public ExportSoapProjectOutput build(){
            return new ExportSoapProjectOutput(this);
        }
    }
}
