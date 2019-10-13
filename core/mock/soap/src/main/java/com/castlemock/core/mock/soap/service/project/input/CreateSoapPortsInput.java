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

package com.castlemock.core.mock.soap.service.project.input;

import com.castlemock.core.basis.model.Input;
import com.castlemock.core.basis.model.validation.NotNull;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class CreateSoapPortsInput implements Input {

    @NotNull
    private final String projectId;
    @NotNull
    private final boolean generateResponse;

    private final boolean includeImports;

    private final List<File> files;
    private final String location;


    private CreateSoapPortsInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId);
        this.includeImports = builder.includeImports;
        this.generateResponse = builder.generateResponse;
        this.files = builder.files;
        this.location = builder.location;
    }

    public String getProjectId() {
        return projectId;
    }

    public boolean isGenerateResponse() {
        return generateResponse;
    }

    public List<File> getFiles() {
        return files;
    }

    public String getLocation() {
        return location;
    }

    public boolean isIncludeImports() {
        return includeImports;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private String projectId;
        private boolean includeImports;
        private boolean generateResponse;
        private List<File> files;
        private String location;

        private Builder(){

        }

        public Builder projectId(final String projectId){
            this.projectId = projectId;
            return this;
        }

        public Builder includeImports(final boolean includeImports){
            this.includeImports = includeImports;
            return this;
        }

        public Builder generateResponse(final boolean generateResponse){
            this.generateResponse = generateResponse;
            return this;
        }

        public Builder files(final List<File> files){
            this.files = files;
            return this;
        }

        public Builder location(final String location){
            this.location = location;
            return this;
        }

        public CreateSoapPortsInput build(){
            return new CreateSoapPortsInput(this);
        }

    }
}
