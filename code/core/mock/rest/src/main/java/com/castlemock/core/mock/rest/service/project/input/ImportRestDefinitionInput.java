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
import com.castlemock.core.mock.rest.model.RestDefinitionType;

import java.io.File;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class ImportRestDefinitionInput implements Input {

    @NotNull
    private final String restProjectId;
    @NotNull
    private final boolean generateResponse;
    @NotNull
    private final RestDefinitionType definitionType;

    private final List<File> files;
    private final String location;

    private ImportRestDefinitionInput(final String restProjectId, final List<File> files, String location,
                                     final boolean generateResponse, final RestDefinitionType definitionType) {
        this.restProjectId = restProjectId;
        this.files = files;
        this.location = location;
        this.generateResponse = generateResponse;
        this.definitionType = definitionType;
    }

    public String getRestProjectId() {
        return restProjectId;
    }

    public List<File> getFiles() {
        return files;
    }

    public String getLocation() {
        return location;
    }

    public boolean isGenerateResponse() {
        return generateResponse;
    }

    public RestDefinitionType getDefinitionType() {
        return definitionType;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private String restProjectId;
        private boolean generateResponse;
        private RestDefinitionType definitionType;
        private List<File> files;
        private String location;

        public Builder restProjectId(final String restProjectId){
            this.restProjectId = restProjectId;
            return this;
        }

        public Builder generateResponse(final boolean generateResponse){
            this.generateResponse = generateResponse;
            return this;
        }

        public Builder definitionType(final RestDefinitionType definitionType){
            this.definitionType = definitionType;
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

        public ImportRestDefinitionInput build(){
            return new ImportRestDefinitionInput(this.restProjectId, this.files, this.location,
                    this.generateResponse, this.definitionType);
        }

    }
}
