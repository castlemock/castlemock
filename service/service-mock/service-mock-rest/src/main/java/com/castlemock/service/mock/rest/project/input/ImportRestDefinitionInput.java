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
import com.castlemock.model.mock.rest.RestDefinitionType;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class ImportRestDefinitionInput implements Input {

    private final String projectId;
    private final RestDefinitionType definitionType;

    private final Boolean generateResponse;
    private final List<File> files;
    private final String location;

    private ImportRestDefinitionInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.definitionType = Objects.requireNonNull(builder.definitionType, "definitionType");
        this.location = builder.location;
        this.generateResponse = builder.generateResponse;
        this.files = Optional.ofNullable(builder.files)
                .orElseGet(List::of);
    }

    public String getProjectId() {
        return projectId;
    }

    public List<File> getFiles() {
        return files;
    }

    public Optional<String> getLocation() {
        return Optional.ofNullable(location);
    }

    public Optional<Boolean> getGenerateResponse() {
        return Optional.ofNullable(generateResponse);
    }

    public RestDefinitionType getDefinitionType() {
        return definitionType;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImportRestDefinitionInput that = (ImportRestDefinitionInput) o;
        return Objects.equals(projectId, that.projectId) && definitionType == that.definitionType &&
                Objects.equals(generateResponse, that.generateResponse) && Objects.equals(files, that.files) &&
                Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, definitionType, generateResponse, files, location);
    }

    @Override
    public String toString() {
        return "ImportRestDefinitionInput{" +
                "projectId='" + projectId + '\'' +
                ", definitionType=" + definitionType +
                ", generateResponse=" + generateResponse +
                ", files=" + files +
                ", location='" + location + '\'' +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private String projectId;
        private Boolean generateResponse;
        private RestDefinitionType definitionType;
        private List<File> files;
        private String location;

        public Builder projectId(final String projectId){
            this.projectId = projectId;
            return this;
        }

        public Builder generateResponse(final Boolean generateResponse){
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
            return new ImportRestDefinitionInput(this);
        }

    }
}
